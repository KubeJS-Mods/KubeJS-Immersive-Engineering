package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import dev.latvian.mods.kubejs.recipe.RecipeArguments;
import dev.latvian.mods.kubejs.util.ListJS;

/**
 * @author LatvianModder
 */
public class BlastFurnaceRecipeJS extends IERecipeJS {
	@Override
	public void create(RecipeArguments args) {
		outputItems.add(parseItemOutput(args.get(0)));
		inputItems.add(parseItemInput(args.get(1)));

		if (args.size() >= 3) {
			outputItems.add(parseItemOutput(args.get(2)));
		}

		json.addProperty("time", 1200);
	}

	@Override
	public void deserialize() {
		outputItems.add(parseItemOutput(json.get("result")));
		inputItems.add(parseItemInputIE(json.get("input")));

		if (json.has("slag")) {
			outputItems.add(parseItemOutput(json.get("slag")));
		}
	}

	@Override
	public void serialize() {
		if (serializeOutputs) {
			json.add("result", outputItems.get(0).toJsonJS());

			if (outputItems.size() >= 2) {
				json.add("slag", outputItems.get(1).toJsonJS());
			}
		}

		if (serializeInputs) {
			json.add("input", serializeIngredientStack(inputItems.get(0).kjs$asStack()));
		}
	}
}
