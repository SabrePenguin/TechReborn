package com.sabrepenguin.techreborn.recipe.registries;

import com.sabrepenguin.techreborn.recipe.BasicOutputRecipe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.Collection;
import java.util.List;

public interface ITRRegistry {
	Collection<BasicOutputRecipe> getRecipes();
	boolean addRecipe(BasicOutputRecipe recipe);
	BasicOutputRecipe getRecipe(List<ItemStack> ingredientList);
	BasicOutputRecipe getRecipe(IItemHandler handler);
	void sortRecipes();
}
