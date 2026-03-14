package com.sabrepenguin.techreborn.recipe;

import com.google.common.base.Preconditions;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.*;

public class BasicRegistry {
	private final Set<BasicOutputRecipe> recipes = new HashSet<>();
	private List<BasicOutputRecipe> sorted_recipes = new ArrayList<>();
	private final Map<String, BasicOutputRecipe> unsorted_recipes = new TreeMap<>();

	public Collection<BasicOutputRecipe> getRecipes() {
		return Collections.unmodifiableCollection(sorted_recipes);
	}

	public boolean addRecipe(String recipeName, BasicOutputRecipe recipe) {
		Preconditions.checkNotNull(recipe);
		boolean result = this.recipes.add(recipe);
		if (result) {
			unsorted_recipes.put(recipeName, recipe);
		}
		return result;
	}

	public BasicOutputRecipe getRecipe(List<ItemStack> ingredientList) {
		for (BasicOutputRecipe recipe: recipes) {
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

	public BasicOutputRecipe getRecipe(IItemHandler handler) {
		for (BasicOutputRecipe recipe: recipes) {
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

	public void sortRecipes() {
		sorted_recipes = new ArrayList<>(unsorted_recipes.values());
		unsorted_recipes.clear();
	}
}
