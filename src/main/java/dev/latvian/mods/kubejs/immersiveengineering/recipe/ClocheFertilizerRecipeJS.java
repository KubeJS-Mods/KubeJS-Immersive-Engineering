package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import dev.latvian.mods.kubejs.recipe.IngredientMatch;
import dev.latvian.mods.kubejs.recipe.ItemOutputTransformer;
import dev.latvian.mods.kubejs.recipe.RecipeArguments;
import net.minecraft.world.item.ItemStack;

/**
 * @author LatvianModder
 */
public class ClocheFertilizerRecipeJS extends IERecipeJS {
	@Override
	public void create(RecipeArguments args) {
		inputItems.add(parseItemInput(args.get(0)));
		json.addProperty("growthModifier", 1.25F);
	}

	public ClocheFertilizerRecipeJS growthModifier(float f) {
		json.addProperty("growthModifier", f);
		save();
		return this;
	}

	@Override
	public void deserialize() {
		inputItems.add(parseItemInputIE(json.get("input")));
	}

	@Override
	public void serialize() {
		if (serializeInputs) {
			json.add("input", inputItems.get(0).toJson());
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
