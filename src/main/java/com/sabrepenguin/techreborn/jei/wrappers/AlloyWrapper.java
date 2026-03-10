package com.sabrepenguin.techreborn.jei.wrappers;

import com.sabrepenguin.techreborn.recipe.BasicOutputRecipe;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import com.sabrepenguin.techreborn.util.ExtraStringUtils;
import mcp.MethodsReturnNonnullByDefault;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class AlloyWrapper implements IRecipeWrapper {
	private final BasicOutputRecipe recipe;

	public AlloyWrapper(BasicOutputRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		int time = recipe.getRecipeTime();
		int energyCost = recipe.getEnergyCost() * time;
		String energy = ExtraStringUtils.numberToCompactNumber(energyCost) + " FE";
		minecraft.fontRenderer.drawString(energy,
				(recipeWidth / 2) - (minecraft.fontRenderer.getStringWidth(energy) / 2),
				30, 0x444444);
		String timeString = (time/20) + " sec";
		minecraft.fontRenderer.drawString(timeString,
				(recipeWidth / 2) - (minecraft.fontRenderer.getStringWidth(timeString) / 2),
				30 + minecraft.fontRenderer.FONT_HEIGHT + 1, 0x444444);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<CountedIngredient> unpreparedIngredients = recipe.getInputs();
		List<List<ItemStack>> inputs = new ArrayList<>();
		for (CountedIngredient countedIngredient: unpreparedIngredients) {
			inputs.add(countedIngredient.resolveIngredients());
		}
		ingredients.setInputLists(VanillaTypes.ITEM, inputs);
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput().get(0));
	}
}
