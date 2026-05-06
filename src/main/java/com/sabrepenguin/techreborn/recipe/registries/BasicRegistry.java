package com.sabrepenguin.techreborn.recipe.registries;

import com.google.common.base.Preconditions;
import com.sabrepenguin.techreborn.recipe.ITRRecipe;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.*;

public class BasicRegistry implements ITRRegistry {
	private final Set<ITRRecipe> recipes = new HashSet<>();
	private final List<ITRRecipe> sorted_recipes = new ArrayList<>(); // TODO: Use combined recipe string in TreeMap

	@Override
	public Collection<ITRRecipe> getRecipes() {
		return Collections.unmodifiableCollection(sorted_recipes);
	}

	@Override
	public boolean addRecipe(ITRRecipe recipe) {
		Preconditions.checkNotNull(recipe);
		boolean result = this.recipes.add(recipe);
		if (result) {
			sorted_recipes.add(recipe);
		}
		return result;
	}

	@Override
	public ITRRecipe getRecipe(List<ItemStack> ingredientList) {
		for (ITRRecipe recipe: recipes) {
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

	@Override
	public ITRRecipe getRecipe(IItemHandler handler) {
		for (ITRRecipe recipe: recipes) {
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

	@Override
	public void sortRecipes() {
	}
}
