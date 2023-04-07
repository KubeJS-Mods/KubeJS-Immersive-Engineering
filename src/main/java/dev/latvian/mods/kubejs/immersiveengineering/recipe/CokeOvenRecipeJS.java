package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import dev.latvian.mods.kubejs.recipe.RecipeArguments;
import dev.latvian.mods.kubejs.util.ListJS;

/**
 * @author LatvianModder
 */
public class CokeOvenRecipeJS extends IERecipeJS {
	@Override
	public void create(RecipeArguments args) {
		outputItems.add(parseItemOutput(args.get(0)));
		inputItems.add(parseItemInput(args.get(1)));
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
		outputItems.add(parseItemOutput(json.get("result")));
		inputItems.add(parseItemInputIE(json.get("input")));
	}

	@Override
	public void serialize() {
		if (serializeOutputs) {
			json.add("result", outputItems.get(0).toJsonJS());
		}

		if (serializeInputs) {
			json.add("input", serializeIngredientStack(inputItems.get(0).kjs$asStack()));
		}
	}
}
