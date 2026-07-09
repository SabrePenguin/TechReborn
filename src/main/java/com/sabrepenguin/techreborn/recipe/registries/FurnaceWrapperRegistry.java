package com.sabrepenguin.techreborn.recipe.registries;

import com.sabrepenguin.techreborn.recipe.recipe.FurnaceWrapperRecipe;
import com.sabrepenguin.techreborn.recipe.recipe.ITRRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FurnaceWrapperRegistry implements ITRRegistry {
	private final FurnaceWrapperRecipe recipe;

	public FurnaceWrapperRegistry() {
		this.recipe = new FurnaceWrapperRecipe();
	}

	@Override
	public Collection<ITRRecipe> getRecipes() {
		return Collections.emptyList();
	}

	@Override
	public boolean addRecipe(ITRRecipe recipe) {
		return false;
	}

	@Override
	public ITRRecipe getRecipe(List<ItemStack> ingredientList) {
		if (ingredientList.size() != 1)
			return null;
		ItemStack in = ingredientList.get(0);
		recipe.setRecipe(in);
		return recipe;
	}

	@Override
	public ITRRecipe getRecipe(IItemHandler handler) {
		if (handler.getSlots() < 1)
			return null;
		ItemStack in = handler.getStackInSlot(0);
		if (in.isEmpty())
			return null;
		recipe.setRecipe(in);
		return recipe;
	}
}
