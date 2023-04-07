package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.recipe.RecipeArguments;
import dev.latvian.mods.kubejs.util.ListJS;
import dev.latvian.mods.kubejs.util.UtilsJS;
import net.minecraft.world.item.ItemStack;

/**
 * @author LatvianModder
 */
public class MetalPressRecipeJS extends IERecipeJS {

	ItemStack mold = ItemStack.EMPTY;

	@Override
	public void create(RecipeArguments args) {
		outputItems.add(parseItemOutput(args.get(0)));
		inputItems.add(parseItemInput(args.get(1)));
		mold = parseItemOutput(args.get(2));
		json.addProperty("energy", args.size() >= 4 ? UtilsJS.parseInt(args.get(3), 2400) : 2400);
	}

	@Override
	public void deserialize() {
		outputItems.add(parseItemOutput(json.get("result")));
		inputItems.add(parseItemInputIE(json.get("input")));
		mold = parseItemOutput(json.get("mold"));
	}

	@Override
	public void serialize() {
		if (serializeOutputs) {
			json.add("result", outputItems.get(0).toJsonJS());
		}

		if (serializeInputs) {
			json.add("input", serializeIngredientStack(inputItems.get(0).kjs$asStack()));
			json.addProperty("mold", mold.kjs$getId());
		}
	}
}
