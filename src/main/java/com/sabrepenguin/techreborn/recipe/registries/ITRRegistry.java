package com.sabrepenguin.techreborn.recipe.registries;

import com.sabrepenguin.techreborn.recipe.ITRRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.Collection;
import java.util.List;

public interface ITRRegistry {
	Collection<ITRRecipe> getRecipes();
	boolean addRecipe(ITRRecipe recipe);
	ITRRecipe getRecipe(List<ItemStack> ingredientList);
	ITRRecipe getRecipe(IItemHandler handler);
	void sortRecipes();
}
