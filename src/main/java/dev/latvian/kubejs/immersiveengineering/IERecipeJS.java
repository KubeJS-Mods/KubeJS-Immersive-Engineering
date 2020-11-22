package dev.latvian.kubejs.immersiveengineering;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.kubejs.item.ingredient.IngredientJS;
import dev.latvian.kubejs.item.ingredient.IngredientStackJS;
import dev.latvian.kubejs.recipe.RecipeJS;

/**
 * @author LatvianModder
 */
public abstract class IERecipeJS extends RecipeJS
{
	@Override
	public JsonElement serializeIngredientStack(IngredientStackJS in)
	{
		if (in.getCount() == 1)
		{
			return in.ingredient.toJson();
		}

		JsonObject o = new JsonObject();
		o.addProperty("count", in.getCount());
		o.add("base_ingredient", in.ingredient.toJson());
		return o;
	}

	public IngredientStackJS parseIngredientItemIE(JsonElement json)
	{
		if (json.isJsonObject() && json.getAsJsonObject().has("base_ingredient"))
		{
			int c = json.getAsJsonObject().has("count") ? json.getAsJsonObject().get("count").getAsInt() : 1;
			return IngredientJS.of(json.getAsJsonObject().get("base_ingredient")).asIngredientStack();
		}

		return IngredientJS.of(json).asIngredientStack();
	}

	public IERecipeJS time(int t)
	{
		json.addProperty("time", t);
		return this;
	}
}