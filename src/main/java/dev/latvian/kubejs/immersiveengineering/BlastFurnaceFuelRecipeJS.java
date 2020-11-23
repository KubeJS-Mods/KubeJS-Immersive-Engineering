package dev.latvian.kubejs.immersiveengineering;

import dev.latvian.kubejs.util.ListJS;

/**
 * @author LatvianModder
 */
public class BlastFurnaceFuelRecipeJS extends IERecipeJS
{
	@Override
	public void create(ListJS args)
	{
		inputItems.add(parseIngredientItem(args.get(1)));
		json.addProperty("time", (Number) args.get(0));
	}

	@Override
	public void deserialize()
	{
		inputItems.add(parseIngredientItemIE(json.get("input")));
	}

	@Override
	public void serialize()
	{
		if (serializeInputs)
		{
			json.add("input", inputItems.get(0).toJson());
		}
	}
}
