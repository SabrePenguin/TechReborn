package com.sabrepenguin.techreborn.recipe.registries;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sabrepenguin.techreborn.recipe.recipe.ITRRecipe;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShapelessRegistry implements ITRRegistry {
	protected final Multimap<Item, ITRRecipe> shapelessRecipes = HashMultimap.create();
	protected final List<ITRRecipe> orderedRecipes = new ArrayList<>();

	public boolean addRecipe(ITRRecipe recipe) {
		boolean added = false;
		for (CountedIngredient ingredient: recipe.getInputs()) {
			for (ItemStack resolved: ingredient.resolveIngredients()) {
				if (shapelessRecipes.put(resolved.getItem(), recipe)) {
					added = true;
				}
			}
		}
		if (added) {
			orderedRecipes.add(recipe);
		}
		return added;
	}

	public Collection<ITRRecipe> getRecipes() {
		return orderedRecipes;
	}

	public ITRRecipe getRecipe(List<ItemStack> stacks) {
		Collection<ITRRecipe> searchList = null;
		for (ItemStack stack: stacks) {
			Collection<ITRRecipe> findList = shapelessRecipes.get(stack.getItem());
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
		for (ITRRecipe candidate: searchList) {
			if (validateShapelessRecipe(candidate, stacks)) {
				return candidate;
			}
		}
		return null;
	}

	public boolean validateShapelessRecipe(ITRRecipe recipe, List<ItemStack> stacks) {
		List<CountedIngredient> components = recipe.getInputs();
		boolean[] consumedSlot = new boolean[stacks.size()];
		int matched = 0;
		for (CountedIngredient c: components) {
			boolean found = false;
			for (int i = 0; i < stacks.size(); i++) {
				if (consumedSlot[i]) continue;
				if (c.matches(stacks.get(i))) {
					consumedSlot[i] = true;
					found = true;
					matched++;
					break;
				}
			}
			if (!found)
				return false;
		}
		return matched == components.size();
	}

	public ITRRecipe getRecipe(IItemHandler handler) {
		Collection<ITRRecipe> searchList = null;
		for (int i = 0; i < handler.getSlots(); i++) {
			Collection<ITRRecipe> findList = shapelessRecipes.get(handler.getStackInSlot(i).getItem());
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
		for (ITRRecipe candidate: searchList) {
			if (validateShapelessRecipe(candidate, handler)) {
				return candidate;
			}
		}
		return null;
	}

	public boolean validateShapelessRecipe(ITRRecipe recipe, IItemHandler handler) {
		List<CountedIngredient> components = recipe.getInputs();
		boolean[] consumedSlot = new boolean[handler.getSlots()];
		int matched = 0;
		for (CountedIngredient c: components) {
			boolean found = false;
			for (int i = 0; i < handler.getSlots(); i++) {
				if (consumedSlot[i]) continue;
				ItemStack slotStack = handler.getStackInSlot(i);
				if (c.matches(slotStack)) {
					consumedSlot[i] = true;
					found = true;
					matched++;
					break;
				}
			}
			if (!found)
				return false;
		}
		return matched == components.size();
	}
}
