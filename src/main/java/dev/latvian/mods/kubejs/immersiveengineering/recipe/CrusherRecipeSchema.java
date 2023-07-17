package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
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
		public JsonElement write(RecipeJS recipe, OutputItem value) {
			JsonObject json = new JsonObject();
			json.add("output", RecipeComponentWithParent.super.write(recipe, value));
			if (value.hasChance()) {
				json.addProperty("chance", value.getChance());
			}
			return json;
		}

		@Override
		public OutputItem read(RecipeJS recipe, Object from) {
			if (from instanceof JsonObject json) {
				var output = OutputItem.EMPTY;
				if (CraftingHelper.processConditions(json, "conditions", ICondition.IContext.EMPTY)) {
					output = RecipeComponentWithParent.super.read(recipe, json.get("output"));

					if (json.has("chance")) {
						output = output.withChance(json.get("chance").getAsDouble());
					}
				}
				return output;
			}
			return RecipeComponentWithParent.super.read(recipe, from);
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
