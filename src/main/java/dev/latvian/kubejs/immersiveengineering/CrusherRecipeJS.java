package dev.latvian.kubejs.immersiveengineering;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.kubejs.item.ItemStackJS;
import dev.latvian.kubejs.util.ListJS;
import net.minecraftforge.common.crafting.CraftingHelper;

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
				outputItems.add(stackJS.hasChance() ? stackJS : stackJS.chance(1D));
			}
		}

		json.addProperty("energy", 6000);
	}

	@Override
	public void deserialize()
	{
		outputItems.add(parseResultItem(json.get("result")));
		inputItems.add(parseIngredientItemIE(json.get("input")));

		if (json.has("secondaries"))
		{
			for (JsonElement e : json.get("secondaries").getAsJsonArray())
			{
				JsonObject o = e.getAsJsonObject();

				if (CraftingHelper.processConditions(o, "conditions"))
				{
					ItemStackJS stack = parseResultItem(o.get("output"));
					stack.setChance(o.get("chance").getAsDouble());
					outputItems.add(stack);
				}
			}
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
					JsonObject o = new JsonObject();
					ItemStackJS is = outputItems.get(i).getCopy();
					o.addProperty("chance", is.getChance());
					is.removeChance();
					o.add("output", is.toResultJson());
					array.add(o);
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
