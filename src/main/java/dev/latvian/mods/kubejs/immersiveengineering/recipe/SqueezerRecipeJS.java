package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import blusunrize.immersiveengineering.api.ApiUtils;
import dev.architectury.hooks.fluid.forge.FluidStackHooksForge;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.util.ListJS;
import dev.latvian.mods.kubejs.util.UtilsJS;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.fluids.FluidStack;

public class SqueezerRecipeJS extends IERecipeJS {

	public SqueezerRecipeJS() {
		outputFluids.add(0, FluidStack.EMPTY);
		outputItems.add(0, ItemStackJS.EMPTY);
	}

	@Override
	public void create(ListJS args) {
		for (var result : ListJS.orSelf(args.get(0))) {
			if (result instanceof FluidStackJS fluid) {
				outputFluids.set(0, FluidStackHooksForge.toForge(fluid.getFluidStack()));
			} else {
				outputItems.set(0, parseResultItem(result));
			}
		}
		inputItems.add(parseIngredientItem(args.get(1)).asIngredientStack());
		json.addProperty("energy", args.size() >= 3 ? UtilsJS.parseInt(args.get(2), 2400) : 2400);
	}

	@Override
	public void deserialize() {
		if(json.has("fluid")) {
			outputFluids.set(0, ApiUtils.jsonDeserializeFluidStack(GsonHelper.getAsJsonObject(json, "fluid")));
		}
		if(json.has("result")) {
			outputItems.set(0, parseResultItem(json.get("result")));
		}
		inputItems.add(parseIngredientItemIE(json.get("input")));
	}

	@Override
	public void serialize() {
		if(serializeOutputs) {
			if(!outputFluids.get(0).isEmpty()) {
				json.add("fluid", ApiUtils.jsonSerializeFluidStack(outputFluids.get(0)));
			}
			if(!outputItems.get(0).isEmpty()) {
				json.add("result", outputItems.get(0).toResultJson());
			}
		}

		if(serializeInputs) {
			json.add("input", inputItems.get(0).toJson());
		}
	}
}
