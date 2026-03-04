package com.sabrepenguin.techreborn.jei.wrappers;

import com.ibm.icu.text.CompactDecimalFormat;
import com.ibm.icu.util.ULocale;
import com.sabrepenguin.techreborn.recipe.AlloyRecipe;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
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
	private final AlloyRecipe recipe;
	private static final CompactDecimalFormat FORMAT = CompactDecimalFormat.getInstance(ULocale.getDefault(), CompactDecimalFormat.CompactStyle.SHORT);

	public AlloyWrapper(AlloyRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		int energyCost = recipe.getEnergyCost();
		String energy = FORMAT.format(energyCost) + " FE";
		minecraft.fontRenderer.drawString(energy,
				(recipeWidth / 2) - (minecraft.fontRenderer.getStringWidth(energy) / 2),
				30, 0x444444);
		int time = recipe.getRecipeTime();
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
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
	}
}
