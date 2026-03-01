package com.sabrepenguin.techreborn.jei.wrappers;

import com.sabrepenguin.techreborn.recipe.AlloyRecipe;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import mcp.MethodsReturnNonnullByDefault;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class AlloyWrapper implements IRecipeWrapper {

	private final AlloyRecipe recipe;

	public AlloyWrapper(AlloyRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<CountedIngredient> unpreparedIngredients = recipe.getInputs();
		List<List<ItemStack>> inputs = new ArrayList<>();
		for (CountedIngredient countedIngredient: unpreparedIngredients) {
			inputs.add(countedIngredient.resolveIngredients());
		}
		ingredients.setInputLists(VanillaTypes.ITEM, inputs);
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
	}
}
