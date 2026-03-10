package com.sabrepenguin.techreborn.recipe;

import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface ITRRecipe {
	List<CountedIngredient> getInputs();
	List<ItemStack> getOutput();
}
