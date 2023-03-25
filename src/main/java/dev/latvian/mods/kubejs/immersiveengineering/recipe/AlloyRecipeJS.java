package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import dev.latvian.mods.kubejs.recipe.RecipeArguments;
import dev.latvian.mods.kubejs.util.ListJS;

/**
 * @author LatvianModder
 */
public class AlloyRecipeJS extends IERecipeJS {
	@Override
	public void create(RecipeArguments args) {
		outputItems.add(parseItemOutput(args.get(0)));
		inputItems.add(parseItemInput(args.get(1)));
		inputItems.add(parseItemInput(args.get(2)));
		json.addProperty("time", 200);
	}

	@Override
	public void deserialize() {
		outputItems.add(parseItemOutput(json.get("result")));
		inputItems.add(parseItemInputIE(json.get("input0")));
		inputItems.add(parseItemInputIE(json.get("input1")));
	}

	@Override
	public void serialize() {
		if (serializeOutputs) {
			json.add("result", outputItems.get(0).toJsonJS());
		}

		if (serializeInputs) {
			json.add("input0", serializeIngredientStack(inputItems.get(0).kjs$asStack()));
			json.add("input1", serializeIngredientStack(inputItems.get(1).kjs$asStack()));
		}
	}
}
