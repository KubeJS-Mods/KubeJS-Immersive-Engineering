package dev.latvian.kubejs.immersiveengineering;

import blusunrize.immersiveengineering.api.crafting.ClocheRenderFunction;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import dev.latvian.kubejs.item.ItemStackJS;
import dev.latvian.kubejs.util.ListJS;
import dev.latvian.kubejs.util.MapJS;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import javax.annotation.Nullable;

/**
 * @author LatvianModder
 */
public class ClocheRecipeJS extends IERecipeJS
{
	public ClocheRenderFunction.ClocheRenderReference renderReference;

	@Override
	public void create(ListJS args)
	{
		outputItems.addAll(parseResultItemList(args.get(0)));
		inputItems.add(parseIngredientItem(args.get(1)));
		json.addProperty("time", 640);
		render(args.size() >= 3 ? args.get(2) : null);
	}

	public ClocheRecipeJS render(@Nullable Object o)
	{
		renderReference = null;

		try
		{
			if (o instanceof JsonElement)
			{
				renderReference = ClocheRenderFunction.ClocheRenderReference.deserialize(((JsonElement) o).getAsJsonObject());
			}
			else
			{
				MapJS m = MapJS.of(o);

				if (m != null)
				{
					renderReference = ClocheRenderFunction.ClocheRenderReference.deserialize(m.toJson());
				}
			}
		}
		catch (Exception ex)
		{
		}

		if (renderReference == null)
		{
			Block block = Block.getBlockFromItem(outputItems.get(0).getItem());

			if (block == Blocks.AIR)
			{
				renderReference = new ClocheRenderFunction.ClocheRenderReference("crop", Blocks.WHEAT);
			}
			else
			{
				renderReference = new ClocheRenderFunction.ClocheRenderReference("crop", block);
			}
		}

		serializeInputs = true;
		return this;
	}

	@Override
	public void deserialize()
	{
		outputItems.addAll(parseResultItemList(json.get("results")));
		inputItems.add(parseIngredientItemIE(json.get("input")));
		inputItems.add(parseIngredientItemIE(json.get("soil")));
		boolean i = serializeInputs;
		renderReference = ClocheRenderFunction.ClocheRenderReference.deserialize(json.get("render").getAsJsonObject());
		serializeInputs = i;
	}

	@Override
	public void serialize()
	{
		if (serializeOutputs)
		{
			JsonArray array = new JsonArray();

			for (ItemStackJS is : outputItems)
			{
				array.add(is.toResultJson());
			}

			json.add("results", array);
		}

		if (serializeInputs)
		{
			json.add("input", inputItems.get(0).toJson());
			json.add("soil", inputItems.get(1).toJson());
			json.add("render", renderReference.serialize());
		}
	}
}
