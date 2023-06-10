package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import blusunrize.immersiveengineering.api.IEApi;
import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.item.ingredient.IngredientJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import net.minecraft.util.GsonHelper;

public class IERecipeJS extends RecipeJS {
	@Override
	public JsonElement writeInputItem(InputItem value) {
		if (value.count > 1) {
			var json = new JsonObject();
			json.add("base_ingredient", value.ingredient.toJson());
			json.addProperty("count", value.count);
			return json;
		} else {
			return value.ingredient.toJson();
		}
	}

	@Override
	public InputItem readInputItem(Object from) {
		if (from instanceof JsonObject j && j.has("base_ingredient")) {
			int c = GsonHelper.getAsInt(j, "count", 1);
			return IngredientJS.of(j.get("base_ingredient")).kjs$withCount(c);
		}

		return super.readInputItem(from);
	}

	@Override
	public OutputItem readOutputItem(Object from) {
		if (from instanceof JsonObject j && !j.has("item")) {
			return OutputItem.of(IEApi.getPreferredStackbyMod(IngredientWithSize.deserialize(j).getMatchingStacks()));
		}

		return super.readOutputItem(from);
	}
}