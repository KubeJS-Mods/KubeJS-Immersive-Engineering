package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;


public interface AlloyRecipeSchema {
	RecipeKey<OutputItem> RESULT = ItemComponents.OUTPUT.key("result");
	RecipeKey<InputItem> INPUT0 = ItemComponents.INPUT.key("input0");
	RecipeKey<InputItem> INPUT1 = ItemComponents.INPUT.key("input1");
	RecipeKey<Long> TIME = IERecipes.time(200);

	RecipeSchema SCHEMA = new RecipeSchema(IERecipeJS.class, IERecipeJS::new, RESULT, INPUT0, INPUT1, TIME);
}
