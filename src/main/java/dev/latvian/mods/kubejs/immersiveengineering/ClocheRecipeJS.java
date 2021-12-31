package dev.latvian.mods.kubejs.immersiveengineering;

import blusunrize.immersiveengineering.api.crafting.ClocheRenderFunction;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.util.ListJS;
import dev.latvian.mods.kubejs.util.MapJS;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;

/**
 * @author LatvianModder
 */
public class ClocheRecipeJS extends IERecipeJS {
	public ClocheRenderFunction.ClocheRenderReference renderReference;

	@Override
	public void create(ListJS args) {
		outputItems.addAll(parseResultItemList(args.get(0)));
		inputItems.add(parseIngredientItem(args.get(1)).asIngredientStack());
		inputItems.add(parseIngredientItem(args.get(2)).asIngredientStack());
		json.addProperty("time", 640);
		render(args.size() >= 4 ? args.get(3) : null);
	}

	public ClocheRecipeJS render(@Nullable Object o) {
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
		outputItems.addAll(parseResultItemList(json.get("results")));
		inputItems.add(parseIngredientItemIE(json.get("input")));
		inputItems.add(parseIngredientItemIE(json.get("soil")));
		boolean i = serializeInputs;
		renderReference = ClocheRenderFunction.ClocheRenderReference.deserialize(json.get("render").getAsJsonObject());
		serializeInputs = i;
	}

	@Override
	public void serialize() {
		if (serializeOutputs) {
			var results = new JsonArray();

			for (ItemStackJS is : outputItems) {
				results.add(is.toResultJson());
			}

			json.add("results", results);
		}

		if (serializeInputs) {
			json.add("input", inputItems.get(0).toJson());
			json.add("soil", inputItems.get(1).toJson());
			json.add("render", renderReference.serialize());
		}
	}
}
