package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import dev.latvian.mods.kubejs.util.ListJS;

/**
 * @author LatvianModder
 */
public class CokeOvenRecipeJS extends IERecipeJS {
	@Override
	public void create(ListJS args) {
		outputItems.add(parseResultItem(args.get(0)));
		inputItems.add(parseIngredientItem(args.get(1)).asIngredientStack());
		json.addProperty("creosote", 250);
		json.addProperty("time", 900);
	}

	public CokeOvenRecipeJS creosote(int c) {
		json.addProperty("creosote", c);
		save();
		return this;
	}

	@Override
	public void deserialize() {
		outputItems.add(parseResultItem(json.get("result")));
		inputItems.add(parseIngredientItemIE(json.get("input")));
	}

	@Override
	public void serialize() {
		if (serializeOutputs) {
			json.add("result", outputItems.get(0).toResultJson());
		}

		if (serializeInputs) {
			json.add("input", inputItems.get(0).toJson());
		}
	}
}
