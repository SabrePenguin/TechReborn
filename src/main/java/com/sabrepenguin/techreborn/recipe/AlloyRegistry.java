package com.sabrepenguin.techreborn.recipe;

import com.google.common.base.Preconditions;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.*;

public class AlloyRegistry {
	private final Set<AlloyRecipe> recipes = new HashSet<>();

	public Collection<AlloyRecipe> getRecipes() {
		return Collections.unmodifiableCollection(recipes);
	}

	public boolean addRecipe(AlloyRecipe recipe) {
		Preconditions.checkNotNull(recipe);
		return this.recipes.add(recipe);
	}

	public AlloyRecipe getRecipe(List<ItemStack> ingredientList) {
		for (AlloyRecipe recipe: recipes) {
			if (ingredientList.size() != recipe.getInputs().size()) continue;
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

	public AlloyRecipe getRecipe(IItemHandler handler) {
		for (AlloyRecipe recipe: recipes) {
			if (handler.getSlots() != recipe.getInputs().size()) continue;
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
}
