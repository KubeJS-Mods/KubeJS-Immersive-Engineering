package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;


public interface BlastFurnaceRecipeSchema {
	RecipeKey<OutputItem> RESULT = ItemComponents.OUTPUT.key("result");
	RecipeKey<InputItem> INPUT = ItemComponents.INPUT.key("input");
	RecipeKey<OutputItem> SLAG = ItemComponents.OUTPUT.key("slag").defaultOptional();
	RecipeKey<Long> TIME = IERecipes.time(1200);

	RecipeSchema SCHEMA = new RecipeSchema(IERecipeJS.class, IERecipeJS::new, RESULT, INPUT, SLAG, TIME);
}
