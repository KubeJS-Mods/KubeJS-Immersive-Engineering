package dev.latvian.kubejs.immersiveengineering;

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
			return IngredientJS.of(json.getAsJsonObject().get("base_ingredient")).count(c).asIngredientStack();
		}

		return IngredientJS.of(json).asIngredientStack();
	}

	public IERecipeJS time(int t)
	{
		json.addProperty("time", t);
		save();
		return this;
	}

	public IERecipeJS energy(int e)
	{
		json.addProperty("energy", e);
		save();
		return this;
	}

	@Override
	public ItemStackJS parseResultItem(@Nullable Object o)
	{
		if (o instanceof JsonElement && ((JsonElement) o).isJsonObject() && ((JsonElement) o).getAsJsonObject().has("base_ingredient"))
		{
			return parseIngredientItemIE(((JsonElement) o).getAsJsonObject()).getFirst();
		}

		return super.parseResultItem(o);
	}
}