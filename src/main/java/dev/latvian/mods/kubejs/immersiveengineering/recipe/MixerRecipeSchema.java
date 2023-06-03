package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface MixerRecipeSchema {
	RecipeKey<OutputFluid> RESULT = FluidComponents.OUTPUT.key("result");
	RecipeKey<InputFluid> FLUID = FluidComponents.INPUT.key("fluid");
	RecipeKey<InputItem[]> INPUTS = ItemComponents.INPUT_ARRAY.key("inputs");
	RecipeKey<Integer> ENERGY = IERecipes.energy(2400);

	RecipeSchema SCHEMA = new RecipeSchema(IERecipeJS.class, IERecipeJS::new, RESULT, FLUID, INPUTS, ENERGY);
}
