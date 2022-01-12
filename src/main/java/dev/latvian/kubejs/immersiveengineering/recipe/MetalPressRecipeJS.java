package dev.latvian.kubejs.immersiveengineering.recipe;

import dev.latvian.kubejs.util.ListJS;

/**
 * @author LatvianModder
 */
public class MetalPressRecipeJS extends IERecipeJS {
	@Override
	public void create(ListJS args) {
		outputItems.add(parseResultItem(args.get(0)));
		inputItems.add(parseIngredientItem(args.get(1)).asIngredientStack());
		inputItems.add(parseIngredientItem(args.get(2)).asIngredientStack());
		json.addProperty("energy", args.size() >= 4 ? ((Number) args.get(3)).intValue() : 2400);
	}

	@Override
	public void deserialize() {
		outputItems.add(parseResultItem(json.get("result")));
		inputItems.add(parseIngredientItemIE(json.get("input")));
		inputItems.add(parseIngredientItemIE(json.get("mold")));
	}

	@Override
	public void serialize() {
		if (serializeOutputs) {
			json.add("result", outputItems.get(0).toResultJson());
		}

		if (serializeInputs) {
			json.add("input", inputItems.get(0).toJson());
			json.add("mold", inputItems.get(1).toJson());
		}
	}
}
