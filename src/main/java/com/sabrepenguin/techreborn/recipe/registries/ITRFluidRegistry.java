package com.sabrepenguin.techreborn.recipe.registries;

import com.sabrepenguin.techreborn.recipe.recipe.ITRFluidRecipe;
import com.sabrepenguin.techreborn.recipe.recipe.ITRRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.Collection;
import java.util.List;

public interface ITRFluidRegistry {
	Collection<ITRFluidRecipe> getRecipes();
	boolean addRecipe(ITRRecipe recipe);
	boolean addRecipe(ITRFluidRecipe recipe);
	ITRFluidRecipe getRecipe(List<ItemStack> ingredientList, List<FluidStack> fluidIngredients);
	ITRFluidRecipe getRecipe(IItemHandler handler, IFluidHandler fluidHandler);
}
