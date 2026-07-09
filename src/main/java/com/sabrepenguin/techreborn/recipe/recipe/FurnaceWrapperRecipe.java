package com.sabrepenguin.techreborn.recipe.recipe;

import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FurnaceWrapperRecipe implements ITRRecipe {

	protected List<ItemStack> input = new ArrayList<>(1);
	protected List<ItemStack> output = new ArrayList<>(1);

	public FurnaceWrapperRecipe() {
		input.add(ItemStack.EMPTY);
		output.add(ItemStack.EMPTY);
	}

	@Override
	public List<CountedIngredient> getInputs() {
		return Collections.emptyList();
	}

	@Override
	public List<ItemStack> getOutput() {
		return output;
	}

	@Override
	public int getRecipeTime() {
		return 200;
	}

	@Override
	public int getEnergyCost() {
		return 24;
	}

	public void setRecipe(ItemStack input) {
		this.input.set(0, input);
		this.output.set(0, FurnaceRecipes.instance().getSmeltingResult(input));
	}

	@Override
	public boolean matches(IItemHandler handler) {
		ItemStack in = handler.getStackInSlot(0);
		if (in.isEmpty()) return false;
		ItemStack req = input.get(0);
		return (in.isItemEqual(req));
	}

	@Override
	public List<ItemStack> apply(IItemHandler handler) {
		handler.extractItem(0, 1, false);
		return Collections.emptyList();
	}
}
