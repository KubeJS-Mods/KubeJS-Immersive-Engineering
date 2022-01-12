package dev.latvian.kubejs.immersiveengineering.recipe;

import blusunrize.immersiveengineering.api.IEApi;
import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.kubejs.item.ItemStackJS;
import dev.latvian.kubejs.item.ingredient.IngredientJS;
import dev.latvian.kubejs.item.ingredient.IngredientStackJS;
import dev.latvian.kubejs.recipe.RecipeJS;

import javax.annotation.Nullable;

/**
 * @author LatvianModder
 */
public abstract class IERecipeJS extends RecipeJS {
	@Override
	public JsonElement serializeIngredientStack(IngredientStackJS in) {
		if (in.getCount() == 1) {
			return in.ingredient.toJson();
		}

		JsonObject o = new JsonObject();
		o.addProperty("count", in.getCount());
		o.add("base_ingredient", in.ingredient.toJson());
		return o;
	}

	@Override
	public IngredientJS convertReplacedInput(int index, IngredientJS oldIngredient, IngredientJS newIngredient) {
		return newIngredient.asIngredientStack();
	}

	public IngredientStackJS parseIngredientItemIE(JsonElement json) {
		if (json.isJsonObject() && json.getAsJsonObject().has("base_ingredient")) {
			int c = json.getAsJsonObject().has("count") ? json.getAsJsonObject().get("count").getAsInt() : 1;
			return IngredientJS.of(json.getAsJsonObject().get("base_ingredient")).withCount(c).asIngredientStack();
		}

		return IngredientJS.of(json).asIngredientStack();
	}

	public IERecipeJS time(int t) {
		json.addProperty("time", t);
		save();
		return this;
	}

	public IERecipeJS energy(int e) {
		json.addProperty("energy", e);
		save();
		return this;
	}

	@Override
	public ItemStackJS parseResultItem(@Nullable Object o) {
		if (o instanceof JsonElement && ((JsonElement) o).isJsonObject()) {
			JsonObject json = ((JsonElement) o).getAsJsonObject();

			if (json.has("base_ingredient") || json.has("tag")) {
				return ItemStackJS.of(IEApi.getPreferredStackbyMod(IngredientWithSize.deserialize(json).getMatchingStacks()));
			}
		}

		return super.parseResultItem(o);
	}
}