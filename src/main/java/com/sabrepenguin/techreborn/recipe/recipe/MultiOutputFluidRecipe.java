package com.sabrepenguin.techreborn.recipe.recipe;

import com.google.common.base.Objects;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import com.sabrepenguin.techreborn.recipe.utils.RecipeUtils;
import com.sabrepenguin.techreborn.util.ItemStackWrapper;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MultiOutputFluidRecipe implements ITRFluidRecipe {
	protected final List<CountedIngredient> inputs;
	protected final List<FluidStack> fluidInputs;

	private final List<ItemStack> output;
	private final List<FluidStack> fluidOutput;

	private final int recipeTime;
	private final int energyCost;

	public MultiOutputFluidRecipe(@Nullable List<CountedIngredient> inputs, @Nullable List<FluidStack> fluidInputs,
								  @Nullable List<ItemStack> output, @Nullable List<FluidStack> fluidOutput, int recipeTime, int energyCost) {
		this.inputs = inputs == null ? Collections.emptyList() : inputs;
		this.fluidInputs = fluidInputs == null ? Collections.emptyList() : fluidInputs;
		this.output = output == null ? Collections.emptyList() : output;
		this.fluidOutput = fluidOutput == null ? Collections.emptyList() : fluidOutput;
		this.recipeTime = recipeTime;
		this.energyCost = energyCost;
	}

	@Override
	public List<FluidStack> getFluidInputs() {
		return fluidInputs;
	}

	@Override
	public List<FluidStack> getFluidOutputs() {
		return fluidOutput;
	}

	public List<CountedIngredient> getInputs() {
		return inputs;
	}

	public List<ItemStack> getOutput() {
		return output;
	}

	public int getRecipeTime() {
		return recipeTime;
	}

	public int getEnergyCost() {
		return energyCost;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(
				this.recipeTime,
				this.inputs.hashCode(),
				this.output.stream().map(ItemStackWrapper::new).collect(Collectors.toList())
		);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MultiOutputFluidRecipe other) {
			if (this.recipeTime != other.recipeTime) return false;
			if (!this.inputs.equals(other.inputs)) return false;
			if (!this.output.stream().map(ItemStackWrapper::new).collect(Collectors.toList()).equals(other.output.stream().map(ItemStackWrapper::new).collect(Collectors.toList()))) return false;
			return true;
		}
		return false;
	}

	@Override
	public boolean matches(IItemHandler handler) {
		return RecipeUtils.checkRecipeValid(handler, inputs);
	}

	@Override
	public List<ItemStack> apply(IItemHandler handler) {
		return RecipeUtils.applyRecipeToHandler(handler, inputs);
	}

	@Override
	public List<FluidStack> apply(IFluidHandler handler) {
		return RecipeUtils.applyRecipeToHandler(handler, fluidInputs);
	}
}
