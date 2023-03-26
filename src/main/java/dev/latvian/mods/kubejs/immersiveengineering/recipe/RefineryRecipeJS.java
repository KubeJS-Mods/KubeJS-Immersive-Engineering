package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import blusunrize.immersiveengineering.api.ApiUtils;
import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
import dev.architectury.hooks.fluid.forge.FluidStackHooksForge;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.recipe.RecipeArguments;
import dev.latvian.mods.kubejs.util.ListJS;
import dev.latvian.mods.kubejs.util.MapJS;
import net.minecraft.util.GsonHelper;

public class RefineryRecipeJS extends IERecipeJS {
    @Override
    public void create(RecipeArguments args) {
        outputFluids.add(FluidStackHooksForge.toForge(FluidStackJS.of(args.get(0)).getFluidStack()));
        for (var input : ListJS.orSelf(args.get(1))) {
            inputFluids.add(FluidTagInput.deserialize(MapJS.json(input)));
        }
        if (args.size() >= 3) {
            inputItems.add(parseItemInput(args.get(2)));
        }
        json.addProperty("energy", args.size() >= 4 ? (args.getInt(3, 80)) : 80);
    }

    @Override
    public void deserialize() {
        outputFluids.set(0, ApiUtils.jsonDeserializeFluidStack(GsonHelper.getAsJsonObject(json, "result")));
        inputFluids.add(FluidTagInput.deserialize(GsonHelper.getAsJsonObject(json, "input0")));
        if (json.has("input1")) {
            inputFluids.add(FluidTagInput.deserialize(GsonHelper.getAsJsonObject(json, "input1")));
        }
        if (json.has("catalyst")) {
            inputItems.add(parseItemInputIE(json.get("catalyst")));
        }
    }

    @Override
    public void serialize() {
        if (serializeOutputs) {
            json.add("result", ApiUtils.jsonSerializeFluidStack(outputFluids.get(0)));
        }
        if (serializeInputs) {
            json.add("input0", inputFluids.get(0).serialize());
            if (inputFluids.size() > 1) {
                json.add("input1", inputFluids.get(1).serialize());
            }
            if (inputItems.size() > 0) {
                json.add("catalyst", serializeIngredientStack(inputItems.get(0).kjs$asStack()));
            }
        }
    }
}
