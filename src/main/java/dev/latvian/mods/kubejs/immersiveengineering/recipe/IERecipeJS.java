package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import blusunrize.immersiveengineering.api.IEApi;
import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.item.ingredient.IngredientJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.TimeComponent;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;


public class IERecipeJS extends RecipeJS {
	public final List<Ingredient> inputItems = new ArrayList<>(1);
	public final List<ItemStack> outputItems = new ArrayList<>(1);
	public final List<FluidTagInput> inputFluids = new ArrayList<>(1);
	public final List<FluidStack> outputFluids = new ArrayList<>(1);

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
		if (from instanceof JsonObject json && json.has("base_ingredient")) {
			int c = GsonHelper.getAsInt(json, "count", 1);
			return IngredientJS.of(json.get("base_ingredient")).kjs$withCount(c);
		}

		return super.readInputItem(from);
	}

	@Override
	public OutputItem readOutputItem(Object from) {
		if (from instanceof JsonObject json && !json.has("item")) {
			return OutputItem.of(IEApi.getPreferredStackbyMod(IngredientWithSize.deserialize(json).getMatchingStacks()));
		}
		return super.readOutputItem(from);
	}
}