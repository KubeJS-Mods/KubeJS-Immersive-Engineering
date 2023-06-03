package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import com.mojang.datafixers.util.Either;
import dev.latvian.mods.kubejs.fluid.OutputFluid;
import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.FluidComponents;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.OrRecipeComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeConstructor;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface SqueezerRecipeSchema {
	RecipeKey<OutputFluid> FLUID = FluidComponents.OUTPUT.key("fluid").defaultOptional();
	RecipeKey<OutputItem> RESULT = ItemComponents.OUTPUT.key("result").defaultOptional();
	RecipeKey<InputItem> INPUT = ItemComponents.INPUT.key("input");
	RecipeKey<Integer> ENERGY = IERecipes.energy(2400);

	RecipeKey<Either<OutputFluid, OutputItem>[]> FLUID_OR_RESULT = FluidComponents.OUTPUT_OR_ITEM_ARRAY.key("fluid_or_result"); // used for JS constructor

	RecipeConstructor.Factory FACTORY = (recipe, schemaType, keys, from) -> {
		recipe.setValue(INPUT, from.getValue(recipe, INPUT));
		recipe.setValue(ENERGY, from.getValue(recipe, ENERGY));

		var outputArray = from.getValue(recipe, FLUID_OR_RESULT);
		for (var output : outputArray) {
			output.map(
					fluid -> recipe.setValue(FLUID, fluid),
					item -> recipe.setValue(RESULT, item)
			);
		}
	};

	RecipeSchema SCHEMA = new RecipeSchema(IERecipeJS.class, IERecipeJS::new, FLUID, RESULT, INPUT, ENERGY)
			.constructor(FACTORY, FLUID_OR_RESULT, INPUT, ENERGY);
}
