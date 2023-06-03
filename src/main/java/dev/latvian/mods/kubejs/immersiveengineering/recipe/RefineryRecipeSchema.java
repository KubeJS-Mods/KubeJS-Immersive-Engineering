package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.recipe.RecipeExceptionJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeConstructor;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface RefineryRecipeSchema {
	RecipeKey<OutputFluid> RESULT = FluidComponents.OUTPUT.key("result");
	RecipeKey<InputFluid> INPUT0 = FluidComponents.INPUT.key("input0");
	RecipeKey<InputFluid> INPUT1 = FluidComponents.INPUT.key("input1").defaultOptional();
	RecipeKey<InputItem> CATALYST = ItemComponents.INPUT.key("catalyst").defaultOptional();
	RecipeKey<Integer> ENERGY = IERecipes.energy(80);

	RecipeKey<InputFluid[]> INPUTS = FluidComponents.INPUT_ARRAY.key("inputs"); // used for the JS constructor

	RecipeConstructor.Factory FACTORY = (recipe, schemaType, keys, from) -> {
		recipe.setValue(RESULT, from.getValue(recipe, RESULT));
		recipe.setValue(CATALYST, from.getValue(recipe, CATALYST));
		recipe.setValue(ENERGY, from.getValue(recipe, ENERGY));

		var inputArray = recipe.getValue(INPUTS);
		switch (inputArray.length) {
			case 1 -> recipe.setValue(INPUT0, inputArray[0]);
			case 2 -> recipe.setValue(INPUT0, inputArray[0]).setValue(INPUT1, inputArray[1]);
			case 0 -> throw new RecipeExceptionJS("Refinery recipe must have at least 1 fluid input!");
			default -> throw new RecipeExceptionJS("Refinery recipe cannot have more than 2 fluid inputs!");
		}
	};

	RecipeSchema SCHEMA = new RecipeSchema(IERecipeJS.class, IERecipeJS::new, RESULT, INPUT0, INPUT1, CATALYST, ENERGY)
			// constructor should use array for inputs rather than two separate input keys
			.constructor(FACTORY, RESULT, INPUTS)
			.constructor(FACTORY, RESULT, INPUTS, CATALYST)
			.constructor(FACTORY, RESULT, INPUTS, CATALYST, ENERGY);
}
