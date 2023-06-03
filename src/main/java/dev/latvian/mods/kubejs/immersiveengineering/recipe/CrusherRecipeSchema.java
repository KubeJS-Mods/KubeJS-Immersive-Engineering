package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
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
		public OutputItem readFromJson(RecipeJS recipe, RecipeKey<OutputItem> key, JsonObject json) {
			if (CraftingHelper.processConditions(json, "conditions", ICondition.IContext.EMPTY)) {
				OutputItem stack = recipe.readOutputItem(json);

				if (json.has("chance")) {
					stack = stack.withChance(json.get("chance").getAsDouble());
				}

				return stack;
			}
			return OutputItem.EMPTY;
		}

		@Override
		public void writeToJson(RecipeComponentValue<OutputItem> value, JsonObject json) {
			var output = value.value;
			if (!output.isEmpty()) {
				json.add("output", value.recipe.writeOutputItem(output));
				if (output.hasChance()) {
					json.addProperty("chance", output.getChance());
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
