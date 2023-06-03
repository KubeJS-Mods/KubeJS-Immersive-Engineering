package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface FermenterRecipeSchema {
	RecipeKey<OutputFluid> FLUID = FluidComponents.OUTPUT.key("fluid");
	RecipeKey<InputItem> INPUT = ItemComponents.INPUT.key("input");
	RecipeKey<OutputItem> RESULT = ItemComponents.OUTPUT.key("result");
	RecipeKey<Integer> ENERGY = IERecipes.energy(6400);

	RecipeSchema SCHEMA = new RecipeSchema(IERecipeJS.class, IERecipeJS::new, FLUID, INPUT, RESULT, ENERGY);
}
