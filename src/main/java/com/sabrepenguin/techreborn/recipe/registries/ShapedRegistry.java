package com.sabrepenguin.techreborn.recipe.registries;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sabrepenguin.techreborn.recipe.recipe.ITRRecipe;
import com.sabrepenguin.techreborn.recipe.recipe.ShapedRecipe;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ShapedRegistry implements ITRRegistry {
	protected final Multimap<Item, ShapedRecipe> shapedRecipes = HashMultimap.create();
	protected final List<ShapedRecipe> orderedRecipes = new ArrayList<>();

	public boolean addRecipe(ITRRecipe recipe) {
		if (!(recipe instanceof ShapedRecipe shapedRecipe))
			throw new RuntimeException("Not a shaped recipe");

		boolean added = false;
		for (CountedIngredient ingredient: shapedRecipe.getInputs()) {
			for (ItemStack resolved: ingredient.resolveIngredients()) {
				if (shapedRecipes.put(resolved.getItem(), shapedRecipe)) {
					added = true;
				}
			}
		}
		if (added) {
			orderedRecipes.add(shapedRecipe);
		}
		return added;
	}

	public Collection<ITRRecipe> getRecipes() {
		return orderedRecipes.stream().map(ShapedRecipe::asRecipe).collect(Collectors.toList());
	}

	public ITRRecipe getRecipe(List<ItemStack> stacks) {
		int size = (int) Math.sqrt(stacks.size());
		return getRecipe(stacks, size, size);
	}

	public ITRRecipe getRecipe(List<ItemStack> stacks, int width, int height) {
		Collection<ShapedRecipe> searchList = null;
		for (ItemStack stack: stacks) {
			Collection<ShapedRecipe> findList = shapedRecipes.get(stack.getItem());
			if (findList.isEmpty())
				continue;
			if (searchList == null)
				searchList = findList;
			if (searchList.size() > findList.size()) {
				searchList = findList;
			}
		}
		if (searchList == null)
			return null;
		for (ShapedRecipe candidate: searchList) {
			if (validateShapedRecipe(candidate, stacks, width, height)) {
				return candidate;
			}
		}
		return null;
	}

	public boolean validateShapedRecipe(ShapedRecipe recipe, List<ItemStack> stacks, int width, int height) {
		int recipeWidth = recipe.getWidth();
		int recipeHeight = recipe.getHeight();

		if (recipeWidth > width || recipeHeight > height)
			return false;

		for (int startRow = 0; startRow <= width - recipeWidth; startRow++) {
			for (int startCol = 0; startCol <= height - recipeHeight; startCol++) {
				if (matchRecipe(recipe, stacks, width, height, startRow, startCol)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean matchRecipe(ShapedRecipe recipe, List<ItemStack> stacks, int invW, int invH, int startX, int startY) {
		for (int invY = 0; invY < invH; invY++) {
			for (int invX = 0; invX < invW; invX++) {
				int slot = (invY * invW) + invX;
				ItemStack stack = stacks.get(slot);

				int recipeX = invX - startX;
				int recipeY = invY - startY;

				boolean inBounds = recipeX >= 0 && recipeX < recipe.getWidth() && recipeY >= 0 && recipeY < recipe.getHeight();

				if (inBounds) {
					CountedIngredient ingredient = recipe.getIngredient(recipeX, recipeY);
					if (ingredient == null || ingredient.getIngredient() == Ingredient.EMPTY) {
						if (!stack.isEmpty())
							return false;
					} else {
						if (!ingredient.getIngredient().apply(stack) && stack.getCount() < ingredient.getCount()) {
							return false;
						}
					}
				} else {
					if (!stack.isEmpty())
						return false;
				}
			}
		}
		return true;
	}

	public ITRRecipe getRecipe(IItemHandler handler) {
		int size = (int) Math.sqrt(handler.getSlots());
		return getRecipe(handler, size, size);
	}

	public ITRRecipe getRecipe(IItemHandler handler, int width, int height) {
		Collection<ShapedRecipe> searchList = null;
		for (int i = 0; i < handler.getSlots(); i++) {
			Collection<ShapedRecipe> findList = shapedRecipes.get(handler.getStackInSlot(i).getItem());
			if (findList.isEmpty())
				continue;
			if (searchList == null)
				searchList = findList;
			if (searchList.size() > findList.size()) {
				searchList = findList;
			}
		}
		if (searchList == null)
			return null;
		for (ShapedRecipe candidate: searchList) {
			if (validateShapedRecipe(candidate, handler, width, height)) {
				return candidate;
			}
		}
		return null;
	}

	private boolean validateShapedRecipe(ShapedRecipe recipe, IItemHandler handler, int width, int height) {
		int recipeWidth = recipe.getWidth();
		int recipeHeight = recipe.getHeight();

		if (recipeWidth > width || recipeHeight > height)
			return false;

		for (int startRow = 0; startRow <= width - recipeWidth; startRow++) {
			for (int startCol = 0; startCol <= height - recipeHeight; startCol++) {
				if (matchRecipe(recipe, handler, width, height, startRow, startCol)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean matchRecipe(ShapedRecipe recipe, IItemHandler handler, int invW, int invH, int startX, int startY) {
		for (int invY = 0; invY < invH; invY++) {
			for (int invX = 0; invX < invW; invX++) {
				int slot = (invY * invW) + invX;
				ItemStack stack = handler.getStackInSlot(slot);

				int recipeX = invX - startX;
				int recipeY = invY - startY;

				boolean inBounds = recipeX >= 0 && recipeX < recipe.getWidth() && recipeY >= 0 && recipeY < recipe.getHeight();

				if (inBounds) {
					CountedIngredient ingredient = recipe.getIngredient(recipeX, recipeY);
					if (ingredient == null || ingredient.getIngredient() == Ingredient.EMPTY) {
						if (!stack.isEmpty())
							return false;
					} else {
						if (!ingredient.getIngredient().apply(stack) && stack.getCount() < ingredient.getCount()) {
							return false;
						}
					}
				} else {
					if (!stack.isEmpty())
						return false;
				}
			}
		}
		return true;
	}
}
