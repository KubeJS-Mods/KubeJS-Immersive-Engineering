package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentValue;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentWithParent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;


public interface MetalPressRecipeSchema {

	RecipeComponent<OutputItem> MOLD_COMPONENT = new RecipeComponentWithParent<>() {
		@Override
		public RecipeComponent<OutputItem> parentComponent() {
			return ItemComponents.OUTPUT;
		}

		@Override
		public void writeToJson(RecipeJS recipe, RecipeComponentValue<OutputItem> value, JsonObject json) {
			var output = value.value.item;
			if (!output.isEmpty()) {
				json.addProperty("mold", output.kjs$getId());
			}
		}

		public String toString() {
			return this.parentComponent().toString();
		}
	};

	RecipeKey<OutputItem> RESULT = ItemComponents.OUTPUT.key("result");
	RecipeKey<InputItem> INPUT = ItemComponents.INPUT.key("input");
	RecipeKey<OutputItem> MOLD = MOLD_COMPONENT.key("mold").allowEmpty().optional(OutputItem.EMPTY);
	RecipeKey<Integer> ENERGY = IERecipes.energy(2400);

	RecipeSchema SCHEMA = new RecipeSchema(IERecipeJS.class, IERecipeJS::new, RESULT, INPUT, MOLD, ENERGY);
}
