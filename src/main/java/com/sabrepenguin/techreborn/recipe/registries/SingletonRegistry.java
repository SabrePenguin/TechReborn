package com.sabrepenguin.techreborn.recipe.registries;

import com.sabrepenguin.techreborn.recipe.ITRRecipe;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SingletonRegistry implements ITRRegistry {
	private ITRRecipe recipe;

	@Override
	public Collection<ITRRecipe> getRecipes() {
		return new ArrayList<>();
	}

	@Override
	public boolean addRecipe(ITRRecipe recipe) {
		this.recipe = recipe;
		return true;
	}

	@Override
	public ITRRecipe getRecipe(List<ItemStack> ingredientList) {
		if (ingredientList.size() == recipe.getInputs().size()) {
			List<CountedIngredient> copy = new ArrayList<>(recipe.getInputs());
			for (ItemStack i: ingredientList) {
				copy.stream()
						.filter(ingredient -> ingredient.matches(i))
						.findFirst()
						.ifPresent(copy::remove);
			}
			if (copy.isEmpty()) return recipe;
		}
		return null;
	}

	@Override
	public ITRRecipe getRecipe(IItemHandler handler) {
		if (handler.getSlots() == recipe.getInputs().size()) {
			List<CountedIngredient> copy = new ArrayList<>(recipe.getInputs());
			for (int i = 0; i < handler.getSlots(); i++) {
				final ItemStack item = handler.getStackInSlot(i);
				copy.stream()
						.filter(ingredient -> ingredient.matches(item))
						.findFirst()
						.ifPresent(copy::remove);
			}
			if (copy.isEmpty()) return recipe;
		}
		return null;
	}

	@Override
	public void sortRecipes() {
	}
}
