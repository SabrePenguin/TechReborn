package com.sabrepenguin.techreborn.recipe.builders;

import com.sabrepenguin.techreborn.recipe.recipe.ITRRecipe;
import com.sabrepenguin.techreborn.recipe.recipe.MultiOutputFluidRecipe;
import com.sabrepenguin.techreborn.recipe.recipe.MultiOutputRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class MultiOutputBuilder extends BasicBuilder<MultiOutputBuilder> {
	protected List<FluidStack> fluidInputs;
	protected List<FluidStack> fluidOutputs;
	protected List<ItemStack> output = new ArrayList<>();

	public MultiOutputBuilder addFluidInput(FluidStack fluid) {
		if (fluidInputs == null) {
			fluidInputs = new ArrayList<>();
		}
		fluidInputs.add(fluid);
		return this;
	}

	public MultiOutputBuilder addFluidOutput(FluidStack fluid) {
		if (fluidOutputs == null) {
			fluidOutputs = new ArrayList<>();
		}
		fluidOutputs.add(fluid);
		return this;
	}

	public MultiOutputBuilder addOutput(ItemStack stack) {
		output.add(stack);
		return this;
	}

	public MultiOutputBuilder addOutput(ItemStack stack, int count) {
		stack.setCount(count);
		return addOutput(stack);
	}

	public MultiOutputBuilder addOutput(Item item, int count) {
		return addOutput(new ItemStack(item, count));
	}

	public MultiOutputBuilder addOutput(Block block, int count) {
		return addOutput(new ItemStack(block, count));
	}

	@Override
	public ITRRecipe build() {
		if (ingredients.isEmpty())
			throw new RuntimeException("Recipe ingredients cannot be empty");
		if (output.isEmpty())
			throw new RuntimeException("Output cannot be empty");
		if (energyCost < 0)
			throw new RuntimeException("Energy cost must be 0 or greater");
		if (recipeTime <= 0)
			throw new RuntimeException("Recipe time must be greater than 0");
		if (fluidInputs == null && fluidOutputs == null) {
			return new MultiOutputRecipe(ingredients, output, recipeTime, energyCost);
		} else {
			return new MultiOutputFluidRecipe(ingredients, fluidInputs, output, fluidOutputs, recipeTime, energyCost);
		}
	}
}
