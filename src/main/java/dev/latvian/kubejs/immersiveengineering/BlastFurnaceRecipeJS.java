package dev.latvian.kubejs.immersiveengineering;

import dev.latvian.kubejs.util.ListJS;

/**
 * @author LatvianModder
 */
public class BlastFurnaceRecipeJS extends IERecipeJS
{
	@Override
	public void create(ListJS args)
	{
		outputItems.add(parseResultItem(args.get(0)));
		inputItems.add(parseIngredientItem(args.get(1)).asIngredientStack());

		if (args.size() >= 3)
		{
			outputItems.add(parseResultItem(args.get(2)));
		}

		json.addProperty("time", 1200);
	}

	@Override
	public void deserialize()
	{
		outputItems.add(parseResultItem(json.get("result")));
		inputItems.add(parseIngredientItemIE(json.get("input")));

		if (json.has("slag"))
		{
			outputItems.add(parseResultItem(json.get("slag")));
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
				json.add("slag", outputItems.get(1).toResultJson());
			}
		}

		if (serializeInputs)
		{
			json.add("input", inputItems.get(0).toJson());
		}
	}
}
