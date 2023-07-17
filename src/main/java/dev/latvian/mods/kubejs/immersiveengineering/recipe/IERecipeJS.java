package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import blusunrize.immersiveengineering.api.IEApi;
import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.item.ingredient.IngredientJS;
import dev.latvian.mods.kubejs.recipe.RecipeExceptionJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipesEventJS;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.kubejs.util.MapJS;
import dev.latvian.mods.kubejs.util.Tags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import java.util.Map;

public class IERecipeJS extends RecipeJS {
	@Override
	public JsonElement writeInputItem(InputItem value) {
		if (value.count > 1) {
			var json = new JsonObject();
			json.add("base_ingredient", value.ingredient.toJson());
			json.addProperty("count", value.count);
			return json;
		} else {
			return value.ingredient.toJson();
		}
	}

	@Override
	public InputItem readInputItem(Object from) {
		try {
			if (from instanceof JsonObject j && j.has("base_ingredient")) {
				int c = GsonHelper.getAsInt(j, "count", 1);
				return IngredientJS.of(j.get("base_ingredient")).kjs$withCount(c);
			}

			return super.readInputItem(from);
		} catch (Exception ex) {
			ConsoleJS.SERVER.error("[%s] Failed to read IE input item from %s".formatted(type.id, from), ex, RecipesEventJS.SKIP_ERROR);
			return InputItem.EMPTY;
		}
	}

	@Override
	public OutputItem readOutputItem(Object from) {
		try {
			if (from instanceof JsonObject j && !j.has("item")) {
				return OutputItem.of(IEApi.getPreferredStackbyMod(IngredientWithSize.deserialize(j).getMatchingStacks()));
			}

			return super.readOutputItem(from);
		} catch (Exception ex) {
			ConsoleJS.SERVER.error("[%s] Failed to read IE output item from %s".formatted(type.id, from), ex, RecipesEventJS.SKIP_ERROR);
			return OutputItem.EMPTY;
		}
	}

	@Override
	public InputFluid readInputFluid(Object from) {
		// immersive for some reason **only** accepts fluid tags as inputs, so here we are i guess
		if (from instanceof IEInputFluid in) {
			return in;
		} else if (from instanceof FluidTagInput tag) {
			return new IEInputFluid(tag);
		} else if (from instanceof CharSequence c && c.charAt(0) == '#') {
			var tag = Tags.fluid(new ResourceLocation(c.subSequence(1, c.length()).toString()));
			return new IEInputFluid(new FluidTagInput(tag, 1000));
		} else if (from instanceof Map<?, ?> || from instanceof JsonObject) {
			var json = MapJS.json(from);
			if (json.has("fluid")) {
				throw new RecipeExceptionJS("Invalid key 'fluid' in IE input fluid json %s! Note that Immersive Engineering only accepts fluid tags as inputs!".formatted(from));
			}

			try {
				return new IEInputFluid(FluidTagInput.deserialize(json));
			} catch (JsonParseException ex) {
				throw new RecipeExceptionJS("Failed to deserialize IE input fluid json!", ex);
			}
		}

		throw new RecipeExceptionJS("[%s] Failed to read IE input fluid from %s! Note that Immersive Engineering only accepts fluid tags as inputs!".formatted(type.id, from));
	}
}