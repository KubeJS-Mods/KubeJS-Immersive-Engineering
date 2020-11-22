package dev.latvian.kubejs.immersiveengineering;

import com.google.gson.JsonArray;
import dev.latvian.kubejs.item.ItemStackJS;
import dev.latvian.kubejs.util.ListJS;

/**
 * @author LatvianModder
 */
public class CrusherRecipeJS extends IERecipeJS
{
	@Override
	public void create(ListJS args)
	{
		outputItems.add(parseResultItem(args.get(0)));
		inputItems.add(parseIngredientItem(args.get(1)));

		if (args.size() >= 3)
		{
			for (ItemStackJS stackJS : parseResultItemList(args.get(2)))
			{
				outputItems.add(stackJS.getChance() < 0D ? stackJS.chance(1D) : stackJS);
			}
		}
	}

	@Override
	public void deserialize()
	{
		outputItems.add(parseResultItem(json.get("result")));
		inputItems.add(parseIngredientItemIE(json.get("input")));

		if (json.has("secondaries"))
		{
			outputItems.addAll(parseResultItemList(json.get("secondaries")));
		}
	}

	@Override
	public void serialize()
	{
		if (serializeOutputs)
		{
			json.add("result", outputItems.get(0).toResultJson());

			if (outputItems.size() >= 2)
			{
				JsonArray array = new JsonArray();

				for (int i = 1; i < outputItems.size(); i++)
				{
					array.add(outputItems.get(i).toResultJson());
				}

				json.add("secondaries", array);
			}
		}

		if (serializeInputs)
		{
			json.add("input", inputItems.get(0).toJson());
		}
	}
}
