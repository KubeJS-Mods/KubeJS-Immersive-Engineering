package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;


public interface ClocheFertilizerRecipeSchema {
	RecipeKey<InputItem> INPUT = InputItem.input("input");
	RecipeKey<Float> GROWTH_MODIFIER = NumberComponent.FLOAT.key("growthModifier").optional(1.25f);

	RecipeSchema SCHEMA = new RecipeSchema(IERecipeJS.class, IERecipeJS::new, INPUT, GROWTH_MODIFIER);
}
