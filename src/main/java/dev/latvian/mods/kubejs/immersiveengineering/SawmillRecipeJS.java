package dev.latvian.mods.kubejs.immersiveengineering;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.util.ListJS;
import dev.latvian.mods.kubejs.util.MapJS;
import net.minecraftforge.common.crafting.CraftingHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LatvianModder
 */
public class SawmillRecipeJS extends IERecipeJS {
	public List<Boolean> stripping = new ArrayList<>();
	public boolean hasStripped = false;

	@Override
	public void create(ListJS args) {
		outputItems.add(parseResultItem(args.get(0)));
		inputItems.add(parseIngredientItem(args.get(1)).asIngredientStack());

		if (args.size() >= 3) {
			for (var o : ListJS.orSelf(args.get(2))) {
				MapJS m = MapJS.of(o);

				if (m != null && m.containsKey("stripping") && m.containsKey("output")) {
					outputItems.add(parseResultItem(m.get("output")));
					stripping.add((Boolean) m.get("stripping"));
				} else {
					outputItems.add(parseResultItem(o));
					stripping.add(false);
				}
			}
		}

		if (args.size() >= 4) {
			outputItems.add(parseResultItem(args.get(3)));
			hasStripped = true;
		}

		json.addProperty("energy", 1600);
	}

	@Override
	public void deserialize() {
		outputItems.add(parseResultItem(json.get("result")));

		if (json.has("secondaries")) {
			for (var element : json.get("secondaries").getAsJsonArray()) {
				JsonObject secondary = element.getAsJsonObject();

				if (CraftingHelper.processConditions(secondary, "conditions")) {
					ItemStackJS stack = parseResultItem(secondary.get("output"));

					if (!stack.isEmpty()) {
						if (secondary.has("chance")) {
							stack.setChance(secondary.get("chance").getAsDouble());
						}

						outputItems.add(stack);
						stripping.add(secondary.has("stripping") && secondary.get("stripping").getAsBoolean());
					}
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

			for (int i = 1; i < (outputItems.size() - (hasStripped ? 1 : 0)); i++) {
				JsonObject o = new JsonObject();
				ItemStackJS is = outputItems.get(i).copy();
				o.addProperty("stripping", stripping.get(i - 1));
				o.add("output", is.toResultJson());
				secondaries.add(o);
			}

			json.add("secondaries", secondaries);

			if (hasStripped) {
				json.add("stripped", outputItems.get(outputItems.size() - 1).toResultJson());
			}
		}

		if (serializeInputs) {
			json.add("input", inputItems.get(0).toJson());
		}
	}
}
