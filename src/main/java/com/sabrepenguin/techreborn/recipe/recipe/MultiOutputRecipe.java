package com.sabrepenguin.techreborn.recipe.recipe;

import com.google.common.base.Objects;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import com.sabrepenguin.techreborn.util.ItemStackWrapper;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MultiOutputRecipe implements ITRRecipe {
	protected final List<CountedIngredient> inputs;

	private final List<ItemStack> output;

	private final int recipeTime;
	private final int energyCost;

	public MultiOutputRecipe(List<CountedIngredient> inputs, List<ItemStack> output, int recipeTime, int energyCost) {
		this.inputs = inputs;
		this.output = output;
		this.recipeTime = recipeTime;
		this.energyCost = energyCost;
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
		if (obj instanceof MultiOutputRecipe other) {
			if (this.recipeTime != other.recipeTime) return false;
			if (!this.inputs.equals(other.inputs)) return false;
			if (!this.output.stream().map(ItemStackWrapper::new).collect(Collectors.toList()).equals(other.output.stream().map(ItemStackWrapper::new).collect(Collectors.toList()))) return false;
			return true;
		}
		return false;
	}
}
