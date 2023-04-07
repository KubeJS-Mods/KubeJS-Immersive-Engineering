package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.recipe.RecipeArguments;
import dev.latvian.mods.kubejs.util.ListJS;
import dev.latvian.mods.kubejs.util.MapJS;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LatvianModder
 */
public class SawmillRecipeJS extends IERecipeJS {
	public List<Boolean> stripping = new ArrayList<>();
	public boolean hasStripped = false;

	@Override
	public void create(RecipeArguments args) {
		outputItems.add(parseItemOutput(args.get(0)));
		inputItems.add(parseItemInput(args.get(1)));

		if (args.size() >= 3) {
			for (var o : ListJS.orSelf(args.get(2))) {
				if (o instanceof Map<?,?> m && m.containsKey("stripping") && m.containsKey("output")) {
					outputItems.add(parseItemOutput(m.get("output")));
					stripping.add((Boolean) m.get("stripping"));
				} else {
					outputItems.add(parseItemOutput(o));
					stripping.add(false);
				}
			}
		}

		if (args.size() >= 4) {
			outputItems.add(parseItemOutput(args.get(3)));
			hasStripped = true;
		}

		json.addProperty("energy", 1600);
	}

	@Override
	public void deserialize() {
		outputItems.add(parseItemOutput(json.get("result")));

		if (json.has("secondaries")) {
			for (var element : json.get("secondaries").getAsJsonArray()) {
				JsonObject secondary = element.getAsJsonObject();

				if (CraftingHelper.processConditions(secondary, "conditions", ICondition.IContext.EMPTY)) {
					ItemStack stack = parseItemOutput(secondary.get("output"));

					if (!stack.isEmpty()) {
						if (secondary.has("chance")) {
							stack.kjs$setChance(secondary.get("chance").getAsDouble());
						}

						outputItems.add(stack);
						stripping.add(secondary.has("stripping") && secondary.get("stripping").getAsBoolean());
					}
				}
			}
		}

		inputItems.add(parseItemInputIE(json.get("input")));
	}

	@Override
	public void serialize() {
		if (serializeOutputs) {
			json.add("result", outputItems.get(0).toJsonJS());

			var secondaries = new JsonArray();

			for (int i = 1; i < (outputItems.size() - (hasStripped ? 1 : 0)); i++) {
				JsonObject o = new JsonObject();
				ItemStack is = outputItems.get(i).copy();
				o.addProperty("stripping", stripping.get(i - 1));
				o.add("output", is.toJsonJS());
				secondaries.add(o);
			}

			json.add("secondaries", secondaries);

			if (hasStripped) {
				json.add("stripped", outputItems.get(outputItems.size() - 1).toJsonJS());
			}
		}

		if (serializeInputs) {
			json.add("input", serializeIngredientStack(inputItems.get(0).kjs$asStack()));
		}
	}
}
