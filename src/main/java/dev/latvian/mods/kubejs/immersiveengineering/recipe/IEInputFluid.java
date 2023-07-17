package dev.latvian.mods.kubejs.immersiveengineering.recipe;

import blusunrize.immersiveengineering.api.crafting.FluidTagInput;
import dev.architectury.hooks.fluid.forge.FluidStackHooksForge;
import dev.latvian.mods.kubejs.fluid.FluidLike;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.fluid.InputFluid;

public record IEInputFluid(FluidTagInput input) implements InputFluid {
	@Override
	public long kjs$getAmount() {
		return input.getAmount();
	}

	@Override
	public boolean kjs$isEmpty() {
		return input.getMatchingFluidStacks().isEmpty();
	}

	@Override
	public FluidLike kjs$copy(long amount) {
		return new IEInputFluid(input.withAmount((int) amount));
	}

	@Override
	public boolean matches(FluidLike other) {
		if (other instanceof IEInputFluid in) {
			return input.equals(in.input);
		} else if (other instanceof FluidStackJS stack) {
			return input.test(FluidStackHooksForge.toForge(stack.getFluidStack()));
		}
		return false;
	}
}
