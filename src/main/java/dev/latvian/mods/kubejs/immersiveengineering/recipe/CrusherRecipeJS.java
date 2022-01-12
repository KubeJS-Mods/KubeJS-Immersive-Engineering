package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.util.ListJS;
import net.minecraftforge.common.crafting.CraftingHelper;

/**
 * @author LatvianModder
 */
public class CrusherRecipeJS extends IERecipeJS {
	@Override
	public void create(ListJS args) {
		outputItems.add(parseResultItem(args.get(0)));
		inputItems.add(parseIngredientItem(args.get(1)).asIngredientStack());

		if (args.size() >= 3) {
			outputItems.addAll(parseResultItemList(args.get(2)));
		}

		json.addProperty("energy", 6000);
	}

	@Override
	public void deserialize() {
		outputItems.add(parseResultItem(json.get("result")));

		if (json.has("secondaries")) {
			for (var element : json.get("secondaries").getAsJsonArray()) {
				var secondary = element.getAsJsonObject();

				if (CraftingHelper.processConditions(secondary, "conditions")) {
					ItemStackJS stack = parseResultItem(secondary.get("output"));

					if (secondary.has("chance")) {
						stack.setChance(secondary.get("chance").getAsDouble());
					}

					outputItems.add(stack);
				}
			}
		}

		inputItems.add(parseIngredientItemIE(json.get("input")));
	}

	@Override
	public void serialize() {
		if (serializeOutputs) {
			json.add("result", outputItems.get(0).toResultJson());

			var secondaries = new JsonArray();

			for (int i = 1; i < outputItems.size(); i++) {
				JsonObject o = new JsonObject();
				ItemStackJS is = outputItems.get(i).copy();
				o.addProperty("chance", is.hasChance() ? is.getChance() : 1D);
				is.removeChance();
				o.add("output", is.toResultJson());
				secondaries.add(o);
			}

			json.add("secondaries", secondaries);
		}

		if (serializeInputs) {
			json.add("input", inputItems.get(0).toJson());
		}
	}
}
