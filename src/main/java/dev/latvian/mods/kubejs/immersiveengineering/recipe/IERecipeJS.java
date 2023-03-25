package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import blusunrize.immersiveengineering.api.IEApi;
import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.item.ingredient.IngredientJS;
import dev.latvian.mods.kubejs.item.ingredient.IngredientStack;
import dev.latvian.mods.kubejs.recipe.IngredientMatch;
import dev.latvian.mods.kubejs.recipe.ItemInputTransformer;
import dev.latvian.mods.kubejs.recipe.ItemOutputTransformer;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LatvianModder
 */
public abstract class IERecipeJS extends RecipeJS {
	public final List<Ingredient> inputItems = new ArrayList<>(1);
	public final List<ItemStack> outputItems = new ArrayList<>(1);
	public final List<FluidTagInput> inputFluids = new ArrayList<>(1);
	public final List<FluidStack> outputFluids = new ArrayList<>(1);

	@Override
	public JsonElement serializeIngredientStack(IngredientStack in) {
		if (in.getCount() == 1) {
			return in.getIngredient().toJson();
		}

		JsonObject o = new JsonObject();
		o.addProperty("count", in.getCount());
		o.add("base_ingredient", in.getIngredient().toJson());
		return o;
	}

	public Ingredient parseItemInputIE(JsonElement json) {
		if (json instanceof JsonObject obj && obj.has("base_ingredient")) {
			int c = GsonHelper.getAsInt(obj, "count", 1);
			return IngredientJS.of(obj.get("base_ingredient")).kjs$withCount(c);
		}

		return IngredientJS.of(json);
	}

	public IERecipeJS time(int t) {
		json.addProperty("time", t);
		save();
		return this;
	}

	public IERecipeJS energy(int e) {
		json.addProperty("energy", e);
		save();
		return this;
	}

	@Override
	public ItemStack parseItemOutput(@Nullable Object o) {
		if (o instanceof JsonObject json) {
			if (json.has("base_ingredient") || json.has("tag")) {
				return ItemStackJS.of(IEApi.getPreferredStackbyMod(IngredientWithSize.deserialize(json).getMatchingStacks()));
			}
		}

		return super.parseItemOutput(o);
	}

	@Override
	public boolean hasInput(IngredientMatch match) {
		for (var in : inputItems) {
			if (match.contains(in)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean replaceInput(IngredientMatch match, Ingredient with, ItemInputTransformer transformer) {
		boolean changed = false;

		if (hasInput(match)) {
			for (int i = 0; i < inputItems.size(); i++) {
				var ingredient = inputItems.get(i);

				if (match.contains(ingredient)) {
					inputItems.set(i, transformer.transform(this, match, ingredient, with));
					changed = true;
				}
			}
		}

		return changed;
	}

	@Override
	public boolean hasOutput(IngredientMatch match) {
		for (var out : outputItems) {
			if (match.contains(out)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean replaceOutput(IngredientMatch match, ItemStack with, ItemOutputTransformer transformer) {
		boolean changed = false;

		if (hasOutput(match)) {
			for (int i = 0; i < outputItems.size(); i++) {
				var outputItem = outputItems.get(i);

				if (match.contains(outputItem)) {
					outputItems.set(i, transformer.transform(this, match, outputItem, with));
					changed = true;
				}
			}
		}

		return changed;
	}
}