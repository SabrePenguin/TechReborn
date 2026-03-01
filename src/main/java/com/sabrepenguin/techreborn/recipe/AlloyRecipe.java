package com.sabrepenguin.techreborn.recipe;

import com.google.common.base.Objects;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import com.sabrepenguin.techreborn.util.ItemStackWrapper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;

public class AlloyRecipe {
	@Nonnull
	private final List<CountedIngredient> inputs;

	@Nonnull
	private final ItemStack output;

	private final int recipeTime;
	private final int energyCost;

	public AlloyRecipe(@Nonnull List<CountedIngredient> inputs, @Nonnull ItemStack output, int recipeTime,int energyCost) {
		this.inputs = inputs;
		this.output = output;
		this.recipeTime = recipeTime;
		this.energyCost = energyCost;
	}

	@Nonnull
	public List<CountedIngredient> getInputs() {
		return inputs;
	}

	@Nonnull
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
		if (obj instanceof AlloyRecipe other) {
			if (this.recipeTime != other.recipeTime) return false;
			if (!this.inputs.equals(other.inputs)) return false;
			if (!this.output.isItemEqual(other.output)) return false;
			return true;
		}
		return false;
	}
}
