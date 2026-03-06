package com.sabrepenguin.techreborn.jei.wrappers;

import com.sabrepenguin.techreborn.recipe.fluids.FluidRecipeWrapper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FluidRecipeJEIWrapper implements IShapedCraftingRecipeWrapper {
	private final FluidRecipeWrapper recipe;

	public FluidRecipeJEIWrapper(FluidRecipeWrapper recipe) {
		this.recipe = recipe;
	}

	@Override
	public int getWidth() {
		return recipe.getRecipeWidth();
	}

	@Override
	public int getHeight() {
		return recipe.getRecipeHeight();
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<List<ItemStack>> itemstacks = new ArrayList<>();
		for (Ingredient ingredient: recipe.getIngredients()) {
			itemstacks.add(Arrays.asList(ingredient.getMatchingStacks()));
		}

		ingredients.setInputLists(VanillaTypes.ITEM, itemstacks);
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
	}
}
