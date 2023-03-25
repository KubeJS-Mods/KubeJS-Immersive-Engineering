package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import com.google.gson.JsonArray;
import dev.latvian.mods.kubejs.recipe.RecipeArguments;
import dev.latvian.mods.kubejs.recipe.RecipeExceptionJS;
import dev.latvian.mods.kubejs.util.ListJS;

public class BlueprintCraftingRecipeJS extends IERecipeJS {

    @Override
    public void create(RecipeArguments args) {
        outputItems.add(parseItemOutput(args.get(0)));
        for (var input : ListJS.orSelf(args.get(1))) {
            inputItems.add(parseItemInput(input));
        }
        if (inputItems.size() > 6) {
            throw new RecipeExceptionJS("Recipe can only have 6 inputs");
        }
        if (args.size() >= 3) {
            blueprint(args.get(2).toString());
        }
    }

    public BlueprintCraftingRecipeJS blueprint(String category) {
        json.addProperty("category", category);
        save();
        return this;
    }

    @Override
    public void deserialize() {
        outputItems.add(parseItemOutput(json.get("result")));
        for (var result : json.get("inputs").getAsJsonArray()) {
            inputItems.add(parseItemInputIE(result));
        }
    }

    @Override
    public void serialize() {
        if (serializeOutputs) {
            json.add("result", outputItems.get(0).toJsonJS());
        }

        if (serializeInputs) {
            var jsonOutputs = new JsonArray();
            for (var ingredient : inputItems) {
                jsonOutputs.add(serializeIngredientStack(ingredient.kjs$asStack()));
            }
            json.add("inputs", jsonOutputs);
        }
    }
}
