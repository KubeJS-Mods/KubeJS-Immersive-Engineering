package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeExceptionJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface BottlingRecipeSchema {
	class ValidatedRecipe extends IERecipeJS {
		@Override
		public void afterLoaded() {
			if (newRecipe && getValue(RESULTS).length > 3) {
				throw new RecipeExceptionJS("Recipe can only have 3 results");
			}
		}
	}


	RecipeKey<OutputItem[]> RESULTS = ItemComponents.OUTPUT_ARRAY.key("results");

	// FIXME: immersive's input fluid system is FUCKED
	RecipeKey<InputFluid> FLUID = FluidComponents.INPUT.key("fluid");

	RecipeKey<InputItem[]> INPUTS = ItemComponents.INPUT_ARRAY.key("inputs").alt("input");


	RecipeSchema SCHEMA = new RecipeSchema(ValidatedRecipe.class, ValidatedRecipe::new, RESULTS, FLUID, INPUTS);
}
