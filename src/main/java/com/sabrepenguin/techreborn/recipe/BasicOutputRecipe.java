package com.sabrepenguin.techreborn.recipe;

import com.google.common.base.Objects;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import com.sabrepenguin.techreborn.util.ItemStackWrapper;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BasicOutputRecipe {
	private final List<CountedIngredient> inputs;

	private final ItemStack output;

	private final int recipeTime;
	private final int energyCost;

	public BasicOutputRecipe(List<CountedIngredient> inputs, ItemStack output, int recipeTime, int energyCost) {
		this.inputs = inputs;
		this.output = output;
		this.recipeTime = recipeTime;
		this.energyCost = energyCost;
	}

	public List<CountedIngredient> getInputs() {
		return inputs;
	}

	public ItemStack getOutput() {
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
				new ItemStackWrapper(this.output)
		);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BasicOutputRecipe other) {
			if (this.recipeTime != other.recipeTime) return false;
			if (!this.inputs.equals(other.inputs)) return false;
			if (!this.output.isItemEqual(other.output)) return false;
			return true;
		}
		return false;
	}
}
