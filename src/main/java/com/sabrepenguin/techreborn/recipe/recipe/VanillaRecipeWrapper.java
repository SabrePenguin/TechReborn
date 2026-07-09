package com.sabrepenguin.techreborn.recipe.recipe;

import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import com.sabrepenguin.techreborn.recipe.utils.RecipeUtils;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VanillaRecipeWrapper implements ITRRecipe {
	private final List<CountedIngredient> ingredients;
	private final List<ItemStack> outputs;

	public VanillaRecipeWrapper(IRecipe vanillaRecipe, InventoryCrafting crafting) {
		ingredients = new ArrayList<>();
		for (Ingredient ingredient: vanillaRecipe.getIngredients()) {
			if (ingredient != Ingredient.EMPTY) {
				ingredients.add(new CountedIngredient(ingredient, 1));
			}
		}

		this.outputs = Collections.singletonList(vanillaRecipe.getCraftingResult(crafting));
	}

	@Override
	public List<CountedIngredient> getInputs() {
		return ingredients;
	}

	@Override
	public List<ItemStack> getOutput() {
		return outputs;
	}

	@Override
	public int getRecipeTime() {
		return TechRebornConfig.machineConfig.autocrafter.ticks;
	}

	@Override
	public int getEnergyCost() {
		return TechRebornConfig.machineConfig.autocrafter.feTick;
	}

	@Override
	public boolean matches(IItemHandler handler) {
		return RecipeUtils.checkRecipeValid(handler, ingredients); //TODO
	}
}
