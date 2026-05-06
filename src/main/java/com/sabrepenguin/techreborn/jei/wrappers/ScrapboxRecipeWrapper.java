package com.sabrepenguin.techreborn.jei.wrappers;

import com.sabrepenguin.techreborn.recipe.ChanceRecipe;
import com.sabrepenguin.techreborn.recipe.ITRRecipe;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ScrapboxRecipeWrapper implements IRecipeWrapper {
	private final List<ItemStack> input;
	private final ItemStack output;

	public ScrapboxRecipeWrapper(List<ItemStack> input, ItemStack output) {
		this.input = input;
		this.output = output;
	}

	public static List<ScrapboxRecipeWrapper> buildRecipes(Collection<ITRRecipe> recipes) {
		List<ScrapboxRecipeWrapper> wrappers = new ArrayList<>();
		for (ITRRecipe recipe: recipes) {
			List<CountedIngredient> unpreparedIngredients = recipe.getInputs();
			if (unpreparedIngredients.size() != 1)
				throw new RuntimeException("Scrapbox Recipes should have 1 input");
			List<ItemStack> items = unpreparedIngredients.get(0).resolveIngredients();
			List<ItemStack> out = (recipe instanceof ChanceRecipe chanceRecipe) ? chanceRecipe.getAllOutputs() : recipe.getOutput();
			for (ItemStack output : out) {
				wrappers.add(new ScrapboxRecipeWrapper(items, output));
			}
		}
		return wrappers;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM,  Arrays.asList(input));
		ingredients.setOutput(VanillaTypes.ITEM, output);
	}
}
