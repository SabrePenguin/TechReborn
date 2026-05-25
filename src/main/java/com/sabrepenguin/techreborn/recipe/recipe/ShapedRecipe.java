package com.sabrepenguin.techreborn.recipe.recipe;

import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ShapedRecipe extends BasicOutputRecipe {
	protected int width;
	protected int height;

	public ShapedRecipe(int size, List<CountedIngredient> inputs, ItemStack output, int recipeTime, int energyCost) {
		this(size, size, inputs, output, recipeTime, energyCost);
	}

	public ShapedRecipe(int width, int height, List<CountedIngredient> inputs, ItemStack output, int recipeTime, int energyCost) {
		super(inputs, output, recipeTime, energyCost);
		this.width = width;
		this.height = height;
	}

	/**
	 * Zero-based ingredient location. Calculated as (Y * recipeWidth) + X
	 * @param x The x position
	 * @param y The y position
	 * @return The CountedIngredient at the position
	 */
	public CountedIngredient getIngredient(int x, int y) {
		if (x > width || y > height)
			throw new RuntimeException("Invalid recipe location sent");
		return inputs.get((y * width) + x);
	}

	public ITRRecipe asRecipe() {
		return this;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
