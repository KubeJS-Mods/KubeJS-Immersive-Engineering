package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeExceptionJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface BlueprintRecipeSchema {
	class ValidatedRecipe extends IERecipeJS {
		@Override
		public void afterLoaded() {
			if (newRecipe && getValue(INPUT).length > 6) {
				throw new RecipeExceptionJS("Recipe can only have 6 inputs!");
			}
		}
	}

	RecipeKey<OutputItem> RESULT = ItemComponents.OUTPUT.key("result");
	RecipeKey<InputItem[]> INPUT = ItemComponents.INPUT_ARRAY.key("inputs");
	RecipeKey<String> CATEGORY = StringComponent.ANY.key("category").preferred("blueprint").defaultOptional();

	RecipeSchema SCHEMA = new RecipeSchema(ValidatedRecipe.class, ValidatedRecipe::new, RESULT, INPUT, CATEGORY);
}
