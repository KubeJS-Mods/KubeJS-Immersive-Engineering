package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.util.ListJS;
import dev.latvian.mods.kubejs.util.UtilsJS;

/**
 * @author LatvianModder
 */
public class MetalPressRecipeJS extends IERecipeJS {

	ItemStackJS mold = ItemStackJS.EMPTY;

	@Override
	public void create(ListJS args) {
		outputItems.add(parseResultItem(args.get(0)));
		inputItems.add(parseIngredientItem(args.get(1)).asIngredientStack());
		mold = parseResultItem(args.get(2));
		json.addProperty("energy", args.size() >= 4 ? UtilsJS.parseInt(args.get(3), 2400) : 2400);
	}

	@Override
	public void deserialize() {
		outputItems.add(parseResultItem(json.get("result")));
		inputItems.add(parseIngredientItemIE(json.get("input")));
		mold = parseResultItem(json.get("mold"));
	}

	@Override
	public void serialize() {
		if (serializeOutputs) {
			json.add("result", outputItems.get(0).toResultJson());
		}

		if (serializeInputs) {
			json.add("input", inputItems.get(0).toJson());
			json.addProperty("mold", mold.getId());
		}
	}
}
