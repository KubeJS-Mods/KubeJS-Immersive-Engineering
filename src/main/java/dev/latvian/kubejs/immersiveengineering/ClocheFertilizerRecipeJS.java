package dev.latvian.kubejs.immersiveengineering;

import dev.latvian.kubejs.util.ListJS;

/**
 * @author LatvianModder
 */
public class ClocheFertilizerRecipeJS extends IERecipeJS {
	@Override
	public void create(ListJS args) {
		inputItems.add(parseIngredientItem(args.get(0)).asIngredientStack());
		json.addProperty("growthModifier", 1.25F);
	}

	public ClocheFertilizerRecipeJS growthModifier(float f) {
		json.addProperty("growthModifier", f);
		save();
		return this;
	}

	@Override
	public void deserialize() {
		inputItems.add(parseIngredientItemIE(json.get("input")));
	}

	@Override
	public void serialize() {
		if (serializeInputs) {
			json.add("input", inputItems.get(0).toJson());
		}
	}
}
