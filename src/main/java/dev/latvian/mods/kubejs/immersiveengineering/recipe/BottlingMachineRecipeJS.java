package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
import com.google.gson.JsonArray;
import dev.latvian.mods.kubejs.recipe.RecipeArguments;
import dev.latvian.mods.kubejs.recipe.RecipeExceptionJS;
import dev.latvian.mods.kubejs.util.ListJS;
import dev.latvian.mods.kubejs.util.MapJS;
import net.minecraft.util.GsonHelper;

public class BottlingMachineRecipeJS extends IERecipeJS {
    @Override
    public void create(RecipeArguments args) {
        for (var item : ListJS.orSelf(args.get(0))) {
            outputItems.add(parseItemOutput(item));
        }
        if (outputItems.size() > 3) {
            throw new RecipeExceptionJS("Recipe can only have 3 results");
        }
        inputFluids.add(FluidTagInput.deserialize(MapJS.json(args.get(1))));
        for (var item : ListJS.orSelf(args.get(2))) {
            inputItems.add(parseItemInput(item));
        }
    }

    @Override
    public void deserialize() {
        inputFluids.add(FluidTagInput.deserialize(GsonHelper.getAsJsonObject(json, "fluid")));
        if (json.has("inputs")) {
            for (var input : json.getAsJsonArray("inputs")) {
                inputItems.add(parseItemInputIE(input));
            }
        }
        if (json.has("input")) {
            inputItems.add(parseItemInputIE(json.get("input")));
        }
        for (var output : json.getAsJsonArray("results")) {
            outputItems.add(parseItemOutput(output));
        }
    }

    @Override
    public void serialize() {
        if (serializeOutputs) {
            var jsonOutputs = new JsonArray();
            for (var item : outputItems) {
                jsonOutputs.add(item.toJsonJS());
            }
            json.add("results", jsonOutputs);
        }

        if (serializeInputs) {
            json.add("fluid", inputFluids.get(0).serialize());

            if (inputItems.size() > 1) {
                var jsonInputs = new JsonArray();
                for (var input : inputItems) {
                    jsonInputs.add(serializeIngredientStack(input.kjs$asStack()));
                }
                json.add("inputs", jsonInputs);
            } else {
                json.add("input", serializeIngredientStack(inputItems.get(0).kjs$asStack()));
            }
        }
    }
}
