package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import blusunrize.immersiveengineering.api.ApiUtils;
import dev.architectury.hooks.fluid.forge.FluidStackHooksForge;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.recipe.RecipeArguments;
import net.minecraft.util.GsonHelper;

public class FermenterRecipeJS extends IERecipeJS {
    @Override
    public void create(RecipeArguments args) {
        outputFluids.add(FluidStackHooksForge.toForge(FluidStackJS.of(args.get(0)).getFluidStack()));
        inputItems.add(parseItemInput(args.get(1)));
        if (args.size() >= 3) {
            outputItems.add(parseItemOutput(args.get(2)));
        }
        json.addProperty("energy", args.size() >= 4 ? (args.getInt(3, 6400)) : 6400);
    }

    @Override
    public void deserialize() {
        outputFluids.add(ApiUtils.jsonDeserializeFluidStack(GsonHelper.getAsJsonObject(json, "fluid")));
        inputItems.add(parseItemInputIE(json.get("input")));
        if (json.has("result")) {
            outputItems.add(parseItemOutput(json.get("result")));
        }
    }

    @Override
    public void serialize() {
        if (serializeOutputs) {
            json.add("fluid", ApiUtils.jsonSerializeFluidStack(outputFluids.get(0)));
            if (outputItems.size() > 0) {
                json.add("result", outputItems.get(0).toJsonJS());
            }
        }
        if (serializeInputs) {
            json.add("input", inputItems.get(0).toJson());
        }
    }
}
