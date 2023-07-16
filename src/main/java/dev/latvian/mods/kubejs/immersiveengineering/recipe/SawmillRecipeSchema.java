package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentWithParent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

import java.util.Map;


public interface SawmillRecipeSchema {
	class StrippingOutputItem extends OutputItem {
		public StrippingOutputItem(OutputItem parent) {
			super(parent.item, parent.chance);
		}
	}

	RecipeComponent<OutputItem> STRIPPING_OUTPUT_ITEM = new RecipeComponentWithParent<>() {
		@Override
		public RecipeComponent<OutputItem> parentComponent() {
			return ItemComponents.OUTPUT;
		}

		@Override
		public boolean hasPriority(RecipeJS recipe, Object from) {
			return from instanceof Map<?, ?> m && m.containsKey("output") && m.containsKey("stripping") || from instanceof JsonObject json && json.has("output") && json.has("stripping");
		}

		@Override
		public JsonElement write(RecipeJS recipe, OutputItem value) {
			if (value instanceof StrippingOutputItem) {
				var json = new JsonObject();
				json.add("item", ItemComponents.OUTPUT.write(recipe, value));
				json.addProperty("stripping", true);
				return json;
			}

			return ItemComponents.OUTPUT.write(recipe, value);
		}

		@Override
		public OutputItem read(RecipeJS recipe, Object from) {
			if (from instanceof Map<?, ?> m && m.containsKey("output") && m.containsKey("stripping")) {
				var item = ItemComponents.OUTPUT.read(recipe, m.get("output"));
				return (Boolean) m.get("stripping") ? new StrippingOutputItem(item) : item;
			} else if (from instanceof JsonObject j && j.has("output") && j.has("stripping")) {
				var item = ItemComponents.OUTPUT.read(recipe, j.get("output"));
				return j.get("stripping").getAsBoolean() ? new StrippingOutputItem(item) : item;
			}

			return ItemComponents.OUTPUT.read(recipe, from);
		}
	};

	RecipeKey<OutputItem> RESULT = ItemComponents.OUTPUT.key("result");
	RecipeKey<InputItem> INPUT = ItemComponents.INPUT.key("input");
	RecipeKey<Either<OutputItem, OutputItem>[]> SECONDARIES = STRIPPING_OUTPUT_ITEM.or(ItemComponents.OUTPUT).asArray().key("secondaries");
	RecipeKey<OutputItem> STRIPPED = ItemComponents.OUTPUT.key("stripped").allowEmpty().optional(OutputItem.EMPTY);
	RecipeKey<Integer> ENERGY = IERecipes.energy(1600);

	RecipeSchema SCHEMA = new RecipeSchema(IERecipeJS.class, IERecipeJS::new, RESULT, INPUT, SECONDARIES, STRIPPED, ENERGY);
}
