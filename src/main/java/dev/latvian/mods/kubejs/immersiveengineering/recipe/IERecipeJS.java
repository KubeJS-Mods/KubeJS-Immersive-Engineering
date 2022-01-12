package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import blusunrize.immersiveengineering.api.IEApi;
import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
import blusunrize.immersiveengineering.api.crafting.IngredientWithSize;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.item.ingredient.IngredientJS;
import dev.latvian.mods.kubejs.item.ingredient.IngredientStackJS;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LatvianModder
 */
public abstract class IERecipeJS extends RecipeJS {

	public final List<FluidTagInput> inputFluids = new ArrayList<>(1);
	public final List<FluidStack> outputFluids = new ArrayList<>(1);

	@Override
	public JsonElement serializeIngredientStack(IngredientStackJS in) {
		if (in.getCount() == 1) {
			return in.ingredient.toJson();
		}

		JsonObject o = new JsonObject();
		o.addProperty("count", in.getCount());
		o.add("base_ingredient", in.ingredient.toJson());
		return o;
	}

	@Override
	public IngredientJS convertReplacedInput(int index, IngredientJS oldIngredient, IngredientJS newIngredient) {
		return newIngredient.asIngredientStack();
	}

	public IngredientStackJS parseIngredientItemIE(JsonElement json) {
		if (json instanceof JsonObject obj && obj.has("base_ingredient")) {
			int c = GsonHelper.getAsInt(obj, "count", 1);
			return IngredientJS.of(obj.get("base_ingredient")).withCount(c).asIngredientStack();
		}

		return IngredientJS.of(json).asIngredientStack();
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
	public ItemStackJS parseResultItem(@Nullable Object o) {
		if (o instanceof JsonObject json) {
			if (json.has("base_ingredient") || json.has("tag")) {
				return ItemStackJS.of(IEApi.getPreferredStackbyMod(IngredientWithSize.deserialize(json).getMatchingStacks()));
			}
		}

		return super.parseResultItem(o);
	}
}