package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import blusunrize.immersiveengineering.api.crafting.ClocheRenderFunction;
import com.google.gson.JsonArray;
import dev.latvian.mods.kubejs.recipe.RecipeArguments;
import dev.latvian.mods.kubejs.util.MapJS;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;

// FIXME
public interface ClocheRecipeSchema {
	public ClocheRenderFunction.ClocheRenderReference renderReference;

	@Override
	public void create(RecipeArguments args) {
		outputItems.addAll(parseItemOutputList(args.get(0)));
		inputItems.add(parseItemInput(args.get(1)));
		inputItems.add(parseItemInput(args.get(2)));
		json.addProperty("time", 640);
		render(args.size() >= 4 ? args.get(3) : null);
	}

	public ClocheRecipeSchema render(@Nullable Object o) {
		renderReference = null;

		try {
			renderReference = ClocheRenderFunction.ClocheRenderReference.deserialize(MapJS.json(o));
		} catch (Exception ignored) {
		}

		if (renderReference == null) {
			Block block = Block.byItem(outputItems.get(0).getItem());

			if (block == Blocks.AIR) {
				renderReference = new ClocheRenderFunction.ClocheRenderReference("crop", Blocks.WHEAT);
			} else {
				renderReference = new ClocheRenderFunction.ClocheRenderReference("crop", block);
			}
		}

		serializeInputs = true;
		save();
		return this;
	}

	@Override
	public void deserialize() {
		outputItems.addAll(parseItemOutputList(json.get("results")));
		inputItems.add(parseItemInputIE(json.get("input")));
		inputItems.add(parseItemInputIE(json.get("soil")));
		boolean i = serializeInputs;
		renderReference = ClocheRenderFunction.ClocheRenderReference.deserialize(json.get("render").getAsJsonObject());
		serializeInputs = i;
	}

	@Override
	public void serialize() {
		if (serializeOutputs) {
			var results = new JsonArray();

			for (ItemStack is : outputItems) {
				results.add(is.toJsonJS());
			}

			json.add("results", results);
		}

		if (serializeInputs) {
			json.add("input", serializeIngredientStack(inputItems.get(0).kjs$asStack()));
			json.add("soil", serializeIngredientStack(inputItems.get(1).kjs$asStack()));
			json.add("render", renderReference.serialize());
		}
	}
}
