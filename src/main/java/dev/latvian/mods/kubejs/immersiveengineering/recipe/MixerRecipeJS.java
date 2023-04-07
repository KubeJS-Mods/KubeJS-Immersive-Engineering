package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import blusunrize.immersiveengineering.api.ApiUtils;
import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
import com.google.gson.JsonArray;
import dev.architectury.hooks.fluid.forge.FluidStackHooksForge;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.recipe.IngredientMatch;
import dev.latvian.mods.kubejs.recipe.ItemOutputTransformer;
import dev.latvian.mods.kubejs.recipe.RecipeArguments;
import dev.latvian.mods.kubejs.util.ListJS;
import dev.latvian.mods.kubejs.util.MapJS;
import dev.latvian.mods.kubejs.util.UtilsJS;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;

public class MixerRecipeJS extends IERecipeJS {

	@Override
	public void create(RecipeArguments args) {
		outputFluids.add(FluidStackHooksForge.toForge(FluidStackJS.of(args.get(0)).getFluidStack()));
		inputFluids.add(FluidTagInput.deserialize(MapJS.json(args.get(1))));
		for (var item : ListJS.orSelf(args.get(2))) {
			inputItems.add(parseItemInput(item));
		}
		json.addProperty("energy", args.size() >= 3 ? UtilsJS.parseInt(args.get(2), 2400) : 2400);
	}

	@Override
	public void deserialize() {
		outputFluids.add(ApiUtils.jsonDeserializeFluidStack(GsonHelper.getAsJsonObject(json, "result")));
		inputFluids.add(FluidTagInput.deserialize(GsonHelper.getAsJsonObject(json, "fluid")));
		for (var input : json.getAsJsonArray("inputs")) {
			inputItems.add(parseItemInputIE(input));
		}
	}

	@Override
	public void serialize() {
		if (serializeOutputs) {
			json.add("result", ApiUtils.jsonSerializeFluidStack(outputFluids.get(0)));
		}

		if (serializeInputs) {
			json.add("fluid", inputFluids.get(0).serialize());

			var inputs = new JsonArray();
			for (var input : inputItems) {
				inputs.add(input.toJson());
			}
			json.add("inputs", inputs);
		}
	}

	@Override
	public boolean hasOutput(IngredientMatch match) {
		return false;
	}

	@Override
	public boolean replaceOutput(IngredientMatch match, ItemStack with, ItemOutputTransformer transformer) {
		return false;
	}
}
