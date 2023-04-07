package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.recipe.RecipeArguments;
import dev.latvian.mods.kubejs.util.ListJS;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;

/**
 * @author LatvianModder
 */
public class CrusherRecipeJS extends IERecipeJS {
	@Override
	public void create(RecipeArguments args) {
		outputItems.add(parseItemOutput(args.get(0)));
		inputItems.add(parseItemInput(args.get(1)));

		if (args.size() >= 3) {
			outputItems.addAll(parseItemOutputList(args.get(2)));
		}

		json.addProperty("energy", 6000);
	}

	@Override
	public void deserialize() {
		outputItems.add(parseItemOutput(json.get("result")));

		if (json.has("secondaries")) {
			for (var element : json.get("secondaries").getAsJsonArray()) {
				var secondary = element.getAsJsonObject();

				if (CraftingHelper.processConditions(secondary, "conditions", ICondition.IContext.EMPTY)) {
					ItemStack stack = parseItemOutput(secondary.get("output"));

					if (secondary.has("chance")) {
						stack.kjs$setChance(secondary.get("chance").getAsDouble());
					}

					outputItems.add(stack);
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

			for (int i = 1; i < outputItems.size(); i++) {
				JsonObject o = new JsonObject();
				ItemStack is = outputItems.get(i).copy();
				o.addProperty("chance", Double.isNaN(is.kjs$getChance()) ? 1D : is.kjs$getChance());
				is.kjs$setChance(Double.NaN);
				o.add("output", is.toJsonJS());
				secondaries.add(o);
			}

			json.add("secondaries", secondaries);
		}

		if (serializeInputs) {
			json.add("input", serializeIngredientStack(inputItems.get(0).kjs$asStack()));
		}
	}
}
