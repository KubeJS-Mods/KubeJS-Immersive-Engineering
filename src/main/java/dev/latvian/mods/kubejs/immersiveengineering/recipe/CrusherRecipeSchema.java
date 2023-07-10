package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentValue;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentWithParent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;


public interface CrusherRecipeSchema {

	RecipeComponent<OutputItem> SECONDARY_OUTPUT_COMPONENT = new RecipeComponentWithParent<>() {
		@Override
		public RecipeComponent<OutputItem> parentComponent() {
			return ItemComponents.OUTPUT;
		}

		@Override
		public void readFromJson(RecipeComponentValue<OutputItem> cv, JsonObject json) {
			if (CraftingHelper.processConditions(json, "conditions", ICondition.IContext.EMPTY)) {
				cv.value = cv.recipe.readOutputItem(json);

				if (json.has("chance")) {
					cv.value = cv.value.withChance(json.get("chance").getAsDouble());
				}
			} else {
				cv.value = OutputItem.EMPTY;
			}
		}

		@Override
		public void writeToJson(RecipeComponentValue<OutputItem> cv, JsonObject json) {
			if (!cv.value.isEmpty()) {
				json.add("output", cv.recipe.writeOutputItem(cv.value));
				if (cv.value.hasChance()) {
					json.addProperty("chance", cv.value.getChance());
				}
			}
		}

		public String toString() {
			return this.parentComponent().toString();
		}
	};

	RecipeKey<OutputItem> RESULT = ItemComponents.OUTPUT.key("result");
	RecipeKey<InputItem> INPUT = ItemComponents.INPUT.key("input");
	RecipeKey<OutputItem[]> SECONDARIES = SECONDARY_OUTPUT_COMPONENT.asArray().key("secondaries").defaultOptional();
	RecipeKey<Integer> ENERGY = IERecipes.energy(6000);

	RecipeSchema SCHEMA = new RecipeSchema(IERecipeJS.class, IERecipeJS::new, RESULT, INPUT, SECONDARIES, ENERGY);
}
