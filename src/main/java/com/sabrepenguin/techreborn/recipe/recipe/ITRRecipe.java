package com.sabrepenguin.techreborn.recipe.recipe;

import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.Collections;
import java.util.List;

public interface ITRRecipe {
	List<CountedIngredient> getInputs();
	List<ItemStack> getOutput();
	int getRecipeTime();
	int getEnergyCost();
	boolean matches(IItemHandler handler);

	/**
	 * Removes the inputs from the given handler. This will not add to the output.
	 * @param handler The input handler
	 * @return Any container items
	 */
	List<ItemStack> apply(IItemHandler handler);
}
