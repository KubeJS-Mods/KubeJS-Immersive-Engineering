package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface FermenterRecipeSchema {
	RecipeKey<InputItem> INPUT = ItemComponents.INPUT.key("input");
	RecipeKey<OutputFluid> FLUID = FluidComponents.OUTPUT.key("fluid").defaultOptional();
	RecipeKey<OutputItem> RESULT = ItemComponents.OUTPUT.key("result").defaultOptional();
	RecipeKey<Integer> ENERGY = IERecipes.energy(6400);

	RecipeSchema SCHEMA = new RecipeSchema(IERecipeJS.class, IERecipeJS::new, INPUT, FLUID, RESULT, ENERGY);
}
