package dev.latvian.kubejs.immersiveengineering;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.kubejs.item.ItemStackJS;
import dev.latvian.kubejs.util.ListJS;
import dev.latvian.kubejs.util.MapJS;
import net.minecraftforge.common.crafting.CraftingHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LatvianModder
 */
public class SawmillRecipeJS extends IERecipeJS
{
	public List<Boolean> stripping = new ArrayList<>();
	public boolean hasStripped = false;

	@Override
	public void create(ListJS args)
	{
		outputItems.add(parseResultItem(args.get(0)));
		inputItems.add(parseIngredientItem(args.get(1)).asIngredientStack());

		if (args.size() >= 3)
		{
			for (Object o : ListJS.orSelf(args.get(2)))
			{
				MapJS m = MapJS.of(o);

				if (m != null && m.containsKey("stripping") && m.containsKey("output"))
				{
					outputItems.add(parseResultItem(m.get("output")));
					stripping.add((Boolean) m.get("stripping"));
				}
				else
				{
					outputItems.add(parseResultItem(o));
					stripping.add(false);
				}
			}
		}

		if (args.size() >= 4)
		{
			outputItems.add(parseResultItem(args.get(3)));
			hasStripped = true;
		}

		json.addProperty("energy", 1600);
	}

	@Override
	public void deserialize()
	{
		outputItems.add(parseResultItem(json.get("result")));

		if (json.has("secondaries"))
		{
			for (JsonElement e : json.get("secondaries").getAsJsonArray())
			{
				JsonObject o = e.getAsJsonObject();

				if (CraftingHelper.processConditions(o, "conditions"))
				{
					ItemStackJS stack = parseResultItem(o.get("output"));

					if (o.has("chance"))
					{
						stack.setChance(o.get("chance").getAsDouble());
					}

					outputItems.add(stack);
				}
			}
		}

		inputItems.add(parseIngredientItemIE(json.get("input")));
	}

	@Override
	public void serialize()
	{
		if (serializeOutputs)
		{
			json.add("result", outputItems.get(0).toResultJson());

			JsonArray array = new JsonArray();

			for (int i = 1; i < (outputItems.size() - (hasStripped ? 1 : 0)); i++)
			{
				JsonObject o = new JsonObject();
				ItemStackJS is = outputItems.get(i).getCopy();
				o.addProperty("stripping", stripping.get(i - 1));
				o.add("output", is.toResultJson());
				array.add(o);
			}

			json.add("secondaries", array);

			if (hasStripped)
			{
				json.add("stripped", outputItems.get(outputItems.size() - 1).toResultJson());
			}
		}

		if (serializeInputs)
		{
			json.add("input", inputItems.get(0).toJson());
		}
	}
}
