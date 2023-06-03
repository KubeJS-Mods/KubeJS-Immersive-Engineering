package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;


public interface ArcFurnaceRecipeSchema {
	RecipeKey<OutputItem[]> RESULTS = ItemComponents.OUTPUT_ARRAY.key("results");
	RecipeKey<InputItem> INPUT = ItemComponents.INPUT.key("input");
	RecipeKey<InputItem[]> ADDITIVES = ItemComponents.INPUT_ARRAY.key("additives");
	RecipeKey<OutputItem> SLAG = ItemComponents.OUTPUT.key("slag").defaultOptional();
	RecipeKey<Long> TIME = IERecipes.time(100);
	RecipeKey<Integer> ENERGY = IERecipes.energy(51200);

	RecipeSchema SCHEMA = new RecipeSchema(IERecipeJS.class, IERecipeJS::new, RESULTS, INPUT, ADDITIVES, SLAG, TIME, ENERGY);
}
