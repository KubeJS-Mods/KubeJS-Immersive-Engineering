package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import com.google.gson.JsonArray;
import dev.latvian.mods.kubejs.recipe.RecipeArguments;
import dev.latvian.mods.kubejs.util.ListJS;

/**
 * @author LatvianModder
 */
public class ArcFurnaceRecipeJS extends IERecipeJS {
	private boolean hasSlag = false;

	@Override
	public void create(RecipeArguments args) {
		outputItems.addAll(parseItemOutputList(args.get(0)));
		inputItems.add(parseItemInput(args.get(1)));

		if (args.size() >= 3) {
			inputItems.addAll(parseItemInputList(args.get(2)));
		}

		if (args.size() >= 4) {
			outputItems.add(parseItemOutput(args.get(3)));
			hasSlag = true;
		}

		json.addProperty("time", 100);
		json.addProperty("energy", 51200);
	}

	@Override
	public void deserialize() {
		outputItems.addAll(parseItemOutputList(json.get("results")));
		inputItems.add(parseItemInputIE(json.get("input")));

		if (json.has("additives")) {
			for (var element : json.get("additives").getAsJsonArray()) {
				inputItems.add(parseItemInputIE(element));
			}
		}

		if (json.has("slag")) {
			outputItems.add(parseItemOutput(json.get("slag")));
			hasSlag = true;
		}
	}

	@Override
	public void serialize() {
		if (serializeOutputs) {
			var results = new JsonArray();

			for (int i = 0; i < (outputItems.size() - (hasSlag ? 1 : 0)); i++) {
				results.add(outputItems.get(i).toJsonJS());
			}

			json.add("results", results);

			if (hasSlag) {
				json.add("slag", outputItems.get(outputItems.size() - 1).toJsonJS());
			}
		}

		if (serializeInputs) {
			json.add("input", serializeIngredientStack(inputItems.get(0).kjs$asStack()));

			var additives = new JsonArray();

			for (int i = 1; i < inputItems.size(); i++) {
				additives.add(serializeIngredientStack(inputItems.get(i).kjs$asStack()));
			}

			json.add("additives", additives);
		}
	}
}
