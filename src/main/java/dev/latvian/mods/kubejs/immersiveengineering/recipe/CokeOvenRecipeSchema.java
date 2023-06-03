package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;


public interface CokeOvenRecipeSchema {
	RecipeKey<OutputItem> RESULT = ItemComponents.OUTPUT.key("result");
	RecipeKey<InputItem> INPUT = ItemComponents.INPUT.key("input");
	RecipeKey<Integer> CREOSOTE = NumberComponent.INT.key("creosote").optional(250);
	RecipeKey<Long> TIME = IERecipes.time(900);

	RecipeSchema SCHEMA = new RecipeSchema(IERecipeJS.class, IERecipeJS::new, RESULT, INPUT, CREOSOTE, TIME);
}
