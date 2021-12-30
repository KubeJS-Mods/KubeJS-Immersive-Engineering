package dev.latvian.mods.kubejs.immersiveengineering;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.util.ListJS;

/**
 * @author LatvianModder
 */
public class ArcFurnaceRecipeJS extends IERecipeJS {
	private boolean hasSlag = false;

	@Override
	public void create(ListJS args) {
		outputItems.addAll(parseResultItemList(args.get(0)));
		inputItems.add(parseIngredientItem(args.get(1)).asIngredientStack());

		if (args.size() >= 3) {
			inputItems.addAll(parseIngredientItemStackList(args.get(2)));
		}

		if (args.size() >= 4) {
			outputItems.add(parseResultItem(args.get(3)));
			hasSlag = true;
		}

		json.addProperty("time", 100);
		json.addProperty("energy", 51200);
	}

	@Override
	public void deserialize() {
		outputItems.addAll(parseResultItemList(json.get("results")));
		inputItems.add(parseIngredientItemIE(json.get("input")));

		if (json.has("additives")) {
			for (var element : json.get("additives").getAsJsonArray()) {
				inputItems.add(parseIngredientItemIE(element));
			}
		}

		if (json.has("slag")) {
			outputItems.add(parseResultItem(json.get("slag")));
			hasSlag = true;
		}
	}

	@Override
	public void serialize() {
		if (serializeOutputs) {
			var results = new JsonArray();

			for (int i = 0; i < (outputItems.size() - (hasSlag ? 1 : 0)); i++) {
				results.add(outputItems.get(i).toResultJson());
			}

			json.add("results", results);

			if (hasSlag) {
				json.add("slag", outputItems.get(outputItems.size() - 1).toResultJson());
			}
		}

		if (serializeInputs) {
			json.add("input", inputItems.get(0).toJson());

			var additives = new JsonArray();

			for (int i = 1; i < inputItems.size(); i++) {
				additives.add(inputItems.get(i).toJson());
			}

			json.add("additives", additives);
		}
	}
}
