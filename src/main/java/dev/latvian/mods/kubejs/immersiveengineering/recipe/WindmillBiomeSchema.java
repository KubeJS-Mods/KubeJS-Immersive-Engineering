package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import blusunrize.immersiveengineering.api.crafting.builders.WindmillBiomeBuilder;
import dev.latvian.mods.kubejs.recipe.IngredientMatch;
import dev.latvian.mods.kubejs.recipe.ItemInputTransformer;
import dev.latvian.mods.kubejs.recipe.ItemOutputTransformer;
import dev.latvian.mods.kubejs.recipe.RecipeArguments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public interface WindmillBiomeSchema {
    public ResourceLocation biome;
    public boolean isTag;

    @Override
    public void create(RecipeArguments args) {
        String s = args.getString(0, "");
        if (s.startsWith("#")) {
            isTag = true;
            biome = new ResourceLocation(s.substring(1));
        } else {
            biome = new ResourceLocation(s);
        }
        if (args.size() > 1) {
            modifier(args.getFloat(1, 1.0f));
        }
    }

    public WindmillBiomeSchema modifier(float mod) {
        json.addProperty(WindmillBiomeBuilder.MODIFIER_KEY, mod);
        save();
        return this;
    }

    @Override
    public void deserialize() {
        if (json.has(WindmillBiomeBuilder.SINGLE_BIOME_KEY)) {
            biome = new ResourceLocation(json.get(WindmillBiomeBuilder.SINGLE_BIOME_KEY).getAsString());
        } else if (json.has(WindmillBiomeBuilder.BIOME_TAG_KEY)) {
            isTag = true;
            biome = new ResourceLocation(json.get(WindmillBiomeBuilder.BIOME_TAG_KEY).getAsString());
        }
    }

    @Override
    public void serialize() {
        if (isTag) {
            json.addProperty(WindmillBiomeBuilder.BIOME_TAG_KEY, biome.toString());
        } else {
            json.addProperty(WindmillBiomeBuilder.SINGLE_BIOME_KEY, biome.toString());
        }
    }

    @Override
    public boolean hasInput(IngredientMatch match) {
        return false;
    }

    @Override
    public boolean replaceInput(IngredientMatch match, Ingredient with, ItemInputTransformer transformer) {
        return false;
    }

    @Override
    public boolean hasOutput(IngredientMatch match) {
        return false;
    }

    @Override
    public boolean replaceOutput(IngredientMatch match, ItemStack with, ItemOutputTransformer transformer) {
        return false;
    }
}