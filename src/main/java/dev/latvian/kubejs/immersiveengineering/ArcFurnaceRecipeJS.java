package dev.latvian.kubejs.immersiveengineering;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import dev.latvian.kubejs.util.ListJS;

/**
 * @author LatvianModder
 */
public class ArcFurnaceRecipeJS extends IERecipeJS
{
	private boolean hasSlag = false;

	@Override
	public void create(ListJS args)
	{
		outputItems.addAll(parseResultItemList(args.get(0)));
		inputItems.add(parseIngredientItem(args.get(1)));

		if (args.size() >= 3)
		{
			inputItems.addAll(parseIngredientItemList(args.get(2)));
		}

		if (args.size() >= 4)
		{
			outputItems.add(parseResultItem(args.get(3)));
			hasSlag = true;
		}
	}

	@Override
	public void deserialize()
	{
		outputItems.addAll(parseResultItemList(json.get("results")));
		inputItems.add(parseIngredientItemIE(json.get("input")));

		if (json.has("additives"))
		{
			for (JsonElement e : json.get("additives").getAsJsonArray())
			{
				inputItems.add(parseIngredientItemIE(e));
			}
		}

		if (json.has("slag"))
		{
			outputItems.add(parseResultItem(json.get("slag")));
			hasSlag = true;
		}
	}

	@Override
	public void serialize()
	{
		if (serializeOutputs)
		{
			JsonArray array = new JsonArray();

			for (int i = 0; i < (outputItems.size() - (hasSlag ? 1 : 0)); i++)
			{
				array.add(outputItems.get(i).toResultJson());
			}

			json.add("results", array);

			if (hasSlag)
			{
				json.add("slag", outputItems.get(outputItems.size() - 1).toResultJson());
			}
		}

		if (serializeInputs)
		{
			json.add("input", inputItems.get(0).toJson());

			JsonArray array = new JsonArray();

			for (int i = 1; i < inputItems.size(); i++)
			{
				array.add(inputItems.get(i).toJson());
			}

			json.add("additives", array);
		}
	}
}
