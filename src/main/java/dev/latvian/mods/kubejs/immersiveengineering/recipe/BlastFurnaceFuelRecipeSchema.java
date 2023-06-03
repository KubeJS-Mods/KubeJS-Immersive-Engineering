package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;


public interface BlastFurnaceFuelRecipeSchema {
	RecipeKey<InputItem> INPUT = ItemComponents.INPUT.key("input");
	RecipeKey<Long> TIME = IERecipes.time(300);

	RecipeSchema SCHEMA = new RecipeSchema(IERecipeJS.class, IERecipeJS::new, INPUT, TIME);
}
