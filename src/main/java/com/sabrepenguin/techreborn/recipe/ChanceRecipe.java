package com.sabrepenguin.techreborn.recipe;

import com.google.common.base.Objects;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import com.sabrepenguin.techreborn.util.ItemStackWrapper;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ChanceRecipe implements ITRRecipe {
	private static final Random RANDOM = new Random();
	private final List<CountedIngredient> inputs;

	private final List<ItemStack> output;

	private final int recipeTime;
	private final int energyCost;

	public ChanceRecipe(List<CountedIngredient> inputs, List<ItemStack> outputs, int recipeTime, int energyCost) {
		this.inputs = inputs;
		this.output = outputs;
		this.recipeTime = recipeTime;
		this.energyCost = energyCost;
	}

	@Override
	public List<CountedIngredient> getInputs() {
		return inputs;
	}

	@Override
	public List<ItemStack> getOutput() {
		return Collections.singletonList(output.get(RANDOM.nextInt(output.size())));
	}

	public List<ItemStack> getAllOutputs() {
		return output;
	}

	@Override
	public int getRecipeTime() {
		return recipeTime;
	}

	@Override
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
		if (obj instanceof ChanceRecipe other) {
			if (this.recipeTime != other.recipeTime) return false;
			if (!this.inputs.equals(other.inputs)) return false;
			if (!this.output.equals(other.output)) return false;
			return true;
		}
		return false;
	}
}
