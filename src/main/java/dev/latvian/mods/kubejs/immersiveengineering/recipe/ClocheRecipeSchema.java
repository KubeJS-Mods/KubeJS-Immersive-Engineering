package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import blusunrize.immersiveengineering.api.crafting.ClocheRenderFunction;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.world.level.block.Blocks;

public interface ClocheRecipeSchema {
	RecipeKey<OutputItem[]> RESULT = ItemComponents.OUTPUT_ARRAY.key("results");
	RecipeKey<InputItem> INPUT = ItemComponents.INPUT.key("input");
	RecipeKey<InputItem> SOIL = ItemComponents.INPUT.key("soil");
	RecipeKey<Long> TIME = IERecipes.time(640);
	RecipeKey<ClocheRenderFunction.ClocheRenderReference> RENDER = IERecipes.RENDER_COMPONENT.key("render").optional(new ClocheRenderFunction.ClocheRenderReference("crop", Blocks.WHEAT)).alwaysWrite();

	RecipeSchema SCHEMA = new RecipeSchema(IERecipeJS.class, IERecipeJS::new, RESULT, INPUT, SOIL, TIME, RENDER);
}
