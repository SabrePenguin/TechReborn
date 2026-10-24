package com.sabrepenguin.techreborn.recipe.registries;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.recipe.recipe.ITRFluidRecipe;
import com.sabrepenguin.techreborn.recipe.recipe.ITRRecipe;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FluidRegistry implements ITRFluidRegistry {
	protected final Multimap<Item, ITRFluidRecipe> shapelessRecipes = HashMultimap.create();
	protected final Multimap<Fluid, ITRFluidRecipe> shapelessFluidRecipes = HashMultimap.create();
	protected final List<ITRFluidRecipe> orderedRecipes = new ArrayList<>();

	public boolean addRecipe(ITRRecipe recipe) {
		if (recipe instanceof ITRFluidRecipe fluidRecipe) {
			return addRecipe(fluidRecipe);
		}
		TechReborn.LOGGER.error("Recipe was not a fluid recipe");
		return false;
	}

	public boolean addRecipe(ITRFluidRecipe recipe) {
		boolean added = false;
		for (CountedIngredient ingredient: recipe.getInputs()) {
			for (ItemStack resolved: ingredient.resolveIngredients()) {
				if (shapelessRecipes.put(resolved.getItem(), recipe)) {
					added = true;
				}
			}
		}
		for (FluidStack stack : recipe.getFluidInputs()) {
			if (shapelessFluidRecipes.put(stack.getFluid(), recipe)) {
				added = true;
			}
		}
		if (added) {
			orderedRecipes.add(recipe);
		}
		return added;
	}

	public Collection<ITRFluidRecipe> getRecipes() {
		return orderedRecipes;
	}

	public ITRFluidRecipe getRecipe(List<ItemStack> stacks, List<FluidStack> fluidStacks) {
		Collection<ITRFluidRecipe> searchList = null;
		for (ItemStack stack: stacks) {
			Collection<ITRFluidRecipe> findList = shapelessRecipes.get(stack.getItem());
			if (findList.isEmpty())
				continue;
			if (searchList == null)
				searchList = findList;
			if (searchList.size() > findList.size()) {
				searchList = findList;
			}
		}
		for (FluidStack stack: fluidStacks) {
			Collection<ITRFluidRecipe> findList = shapelessFluidRecipes.get(stack.getFluid());
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
		for (ITRFluidRecipe candidate: searchList) {
			if (validateShapelessRecipe(candidate, stacks, fluidStacks)) {
				return candidate;
			}
		}
		return null;
	}

	public boolean validateShapelessRecipe(ITRFluidRecipe recipe, List<ItemStack> stacks, List<FluidStack> fluidStacks) {
		List<CountedIngredient> components = recipe.getInputs();
		List<FluidStack> fluidComponents = recipe.getFluidInputs();
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
		boolean[] consumedFluidSlot = new boolean[fluidStacks.size()];
		for (FluidStack c: fluidComponents) {
			boolean found = false;
			for (int i = 0; i < fluidStacks.size(); i++) {
				if (consumedFluidSlot[i]) continue;
				if (c.isFluidEqual(fluidStacks.get(i)) && fluidStacks.get(i).amount >= c.amount) {
					consumedFluidSlot[i] = true;
					found = true;
					matched++;
					break;
				}
			}
			if (!found)
				return false;
		}
		return matched == components.size() + fluidComponents.size();
	}

	public ITRFluidRecipe getRecipe(IItemHandler handler, IFluidHandler fluidHandler) {
		Collection<ITRFluidRecipe> searchList = null;
		for (int i = 0; i < handler.getSlots(); i++) {
			Collection<ITRFluidRecipe> findList = shapelessRecipes.get(handler.getStackInSlot(i).getItem());
			if (findList.isEmpty())
				continue;
			if (searchList == null)
				searchList = findList;
			if (searchList.size() > findList.size()) {
				searchList = findList;
			}
		}
		if (fluidHandler == null)
			return null;
		IFluidTankProperties[] properties = fluidHandler.getTankProperties();
		for (IFluidTankProperties property : properties) {
			FluidStack contents = property.getContents();
			if (contents == null)
				continue;
			Collection<ITRFluidRecipe> findList = shapelessFluidRecipes.get(contents.getFluid());
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
		for (ITRFluidRecipe candidate: searchList) {
			if (validateShapelessRecipe(candidate, handler, fluidHandler)) {
				return candidate;
			}
		}
		return null;
	}

	public boolean validateShapelessRecipe(ITRFluidRecipe recipe, IItemHandler handler, IFluidHandler fluidHandler) {
		List<CountedIngredient> components = recipe.getInputs();
		List<FluidStack> fluidComponents = recipe.getFluidInputs();
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
		IFluidTankProperties[] properties = fluidHandler.getTankProperties();
		boolean[] consumedFluidSlot = new boolean[properties.length];
		for (FluidStack c: fluidComponents) {
			boolean found = false;
			for (int i = 0; i < properties.length; i++) {
				FluidStack fluidStack = properties[i].getContents();
				if (fluidStack == null) continue;
				if (consumedFluidSlot[i]) continue;
				if (c.isFluidEqual(fluidStack) && fluidStack.amount >= c.amount) {
					consumedFluidSlot[i] = true;
					found = true;
					matched++;
					break;
				}
			}
			if (!found)
				return false;
		}
		return matched == components.size() + fluidComponents.size();
	}
}
