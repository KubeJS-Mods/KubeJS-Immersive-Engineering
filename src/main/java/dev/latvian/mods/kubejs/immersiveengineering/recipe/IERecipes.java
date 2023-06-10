package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import blusunrize.immersiveengineering.api.crafting.ClocheRenderFunction;
import blusunrize.immersiveengineering.api.crafting.builders.ThermoelectricSourceBuilder;
import blusunrize.immersiveengineering.api.crafting.builders.WindmillBiomeBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.recipe.RecipeExceptionJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ComponentRole;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentValue;
import dev.latvian.mods.kubejs.recipe.component.TimeComponent;
import dev.latvian.mods.kubejs.registry.KubeJSRegistries;
import dev.latvian.mods.kubejs.util.MapJS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public interface IERecipes {
	record HeatSource(ResourceLocation id, boolean tag) {
	}

	record BiomeFilter(ResourceLocation id, boolean tag) {
	}

	static RecipeKey<Long> time() {
		return TimeComponent.TICKS.key("time");
	}

	static RecipeKey<Long> time(long def) {
		return time().optional(def).alwaysWrite();
	}

	static RecipeKey<Integer> energy() {
		return NumberComponent.INT.key("energy");
	}

	static RecipeKey<Integer> energy(int def) {
		return energy().optional(def);
	}

	RecipeComponent<ClocheRenderFunction.ClocheRenderReference> RENDER_COMPONENT = new RecipeComponent<>() {
		@Override
		public ComponentRole role() {
			return ComponentRole.INPUT;
		}

		@Override
		public Class<?> componentClass() {
			return ClocheRenderFunction.ClocheRenderReference.class;
		}

		@Override
		public JsonElement write(RecipeJS recipe, ClocheRenderFunction.ClocheRenderReference value) {
			return value.serialize();
		}

		@Override
		public ClocheRenderFunction.ClocheRenderReference read(RecipeJS recipe, Object from) {
			if (from instanceof Block block) {
				return new ClocheRenderFunction.ClocheRenderReference("crop", block);
			}

			return ClocheRenderFunction.ClocheRenderReference.deserialize(MapJS.json(from));
		}
	};

	RecipeComponent<HeatSource> HEAT_SOURCE_COMPONENT = new RecipeComponent<>() {
		@Override
		public Class<?> componentClass() {
			return HeatSource.class;
		}

		@Override
		public JsonElement write(RecipeJS recipe, HeatSource value) {
			throw new RecipeExceptionJS("Heat Source component can't be written");
		}

		@Override
		public HeatSource read(RecipeJS recipe, Object from) {
			if (from instanceof HeatSource s) {
				return s;
			} else if (from instanceof CharSequence) {
				var s = from.toString();

				if (s.startsWith("#")) {
					return new HeatSource(new ResourceLocation(s.substring(1)), true);
				} else {
					return new HeatSource(new ResourceLocation(s), false);
				}
			} else if (from instanceof Block) {
				return new HeatSource(new ResourceLocation(((Block) from).kjs$getId()), false);
			}

			throw new RecipeExceptionJS("Can't parse HeatSource from " + from);
		}

		@Override
		public void writeToJson(RecipeComponentValue<HeatSource> value, JsonObject json) {
			if (value.value.tag) {
				json.addProperty(ThermoelectricSourceBuilder.BLOCK_TAG_KEY, value.value.id.toString());
			} else {
				json.addProperty(ThermoelectricSourceBuilder.SINGLE_BLOCK_KEY, value.value.id.toString());
			}
		}

		@Override
		public HeatSource readFromJson(RecipeJS recipe, RecipeKey<HeatSource> key, JsonObject json) {
			if (json.has(ThermoelectricSourceBuilder.BLOCK_TAG_KEY)) {
				return new HeatSource(new ResourceLocation(json.get(ThermoelectricSourceBuilder.BLOCK_TAG_KEY).getAsString()), true);
			} else {
				return new HeatSource(new ResourceLocation(json.get(ThermoelectricSourceBuilder.SINGLE_BLOCK_KEY).getAsString()), false);
			}
		}
	};

	RecipeComponent<BiomeFilter> BIOME_FILTER_COMPONENT = new RecipeComponent<>() {
		@Override
		public Class<?> componentClass() {
			return BiomeFilter.class;
		}

		@Override
		public JsonElement write(RecipeJS recipe, BiomeFilter value) {
			throw new RecipeExceptionJS("Biome filter component can't be written");
		}

		@Override
		public BiomeFilter read(RecipeJS recipe, Object from) {
			if (from instanceof BiomeFilter s) {
				return s;
			} else if (from instanceof CharSequence) {
				var s = from.toString();

				if (s.startsWith("#")) {
					return new BiomeFilter(new ResourceLocation(s.substring(1)), true);
				} else {
					return new BiomeFilter(new ResourceLocation(s), false);
				}
			} else if (from instanceof Biome biome) {
				return new BiomeFilter(KubeJSRegistries.biomes().getId(biome), false);
			}

			throw new RecipeExceptionJS("Can't parse HeatSource from " + from);
		}

		@Override
		public void writeToJson(RecipeComponentValue<BiomeFilter> value, JsonObject json) {
			if (value.value.tag) {
				json.addProperty(WindmillBiomeBuilder.BIOME_TAG_KEY, value.value.id.toString());
			} else {
				json.addProperty(WindmillBiomeBuilder.SINGLE_BIOME_KEY, value.value.id.toString());
			}
		}

		@Override
		public BiomeFilter readFromJson(RecipeJS recipe, RecipeKey<BiomeFilter> key, JsonObject json) {
			if (json.has(WindmillBiomeBuilder.BIOME_TAG_KEY)) {
				return new BiomeFilter(new ResourceLocation(json.get(WindmillBiomeBuilder.BIOME_TAG_KEY).getAsString()), true);
			} else {
				return new BiomeFilter(new ResourceLocation(json.get(WindmillBiomeBuilder.SINGLE_BIOME_KEY).getAsString()), false);
			}
		}
	};
}
