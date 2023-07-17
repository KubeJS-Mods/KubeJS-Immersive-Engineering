package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
import dev.latvian.mods.kubejs.fluid.FluidLike;
import dev.latvian.mods.kubejs.fluid.InputFluid;
import dev.latvian.mods.kubejs.item.ingredient.TagContext;

public record IEInputFluid(FluidTagInput input) implements InputFluid {
	@Override
	public long kjs$getAmount() {
		return input.getAmount();
	}

	@Override
	public boolean kjs$isEmpty() {
		if (TagContext.INSTANCE.getValue().areTagsBound()) {
			return input.getMatchingFluidStacks().isEmpty();
		} else {
			return false; // if tags aren't bound, don't bother checking
		}
	}

	@Override
	public FluidLike kjs$copy(long amount) {
		return new IEInputFluid(input.withAmount((int) amount));
	}

	@Override
	public boolean matches(FluidLike other) {
		// don't allow matching for input replacements for now
		return false;
	}
}
