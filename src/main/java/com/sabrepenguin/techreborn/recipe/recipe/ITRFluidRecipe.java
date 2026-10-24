package com.sabrepenguin.techreborn.recipe.recipe;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.List;

public interface ITRFluidRecipe extends ITRRecipe {
	List<FluidStack> getFluidInputs();
	List<FluidStack> getFluidOutputs();

	List<FluidStack> apply(IFluidHandler handler);
}
