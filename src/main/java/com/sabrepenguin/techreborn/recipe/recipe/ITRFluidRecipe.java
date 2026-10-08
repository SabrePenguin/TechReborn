package com.sabrepenguin.techreborn.recipe.recipe;

import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public interface ITRFluidRecipe extends ITRRecipe {
	List<FluidStack> getFluidInputs();
	List<FluidStack> getFluidOutputs();
}
