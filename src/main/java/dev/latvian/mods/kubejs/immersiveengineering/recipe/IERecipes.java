package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.TimeComponent;

public interface IERecipes {
	static RecipeKey<Long> time() {
		return TimeComponent.TICKS.key("time");
	}

	static RecipeKey<Long> time(long def) {
		return time().optional(def);
	}

	static RecipeKey<Integer> energy() {
		return NumberComponent.INT.key("energy");
	}

	static RecipeKey<Integer> energy(int def) {
		return energy().optional(def);
	}
}
