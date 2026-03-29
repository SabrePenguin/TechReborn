package com.sabrepenguin.techreborn.recipe.builders;

import com.sabrepenguin.techreborn.recipe.BasicOutputRecipe;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BasicBuilder<T extends BasicBuilder<T>> {
	protected List<CountedIngredient> ingredients;
	protected ItemStack output;
	protected int recipeTime;
	protected int energyCost;

	public BasicBuilder() {
		ingredients = new ArrayList<>();
	}

	public T addInput(String oredict) {
		return addInput(oredict, 1);
	}

	public T addInput(String oredict, int count) {
		this.ingredients.add(new CountedIngredient(count, oredict));
		return self();
	}

	public T addInput(ItemStack item) {
		return addInput(item, 1);
	}

	public T addInput(ItemStack item, int count) {
		this.ingredients.add(new CountedIngredient(count, item));
		return self();
	}

	public T setOutput(ItemStack stack) {
		output = stack;
		return self();
	}

	public T setOutput(Item item, int count) {
		return setOutput(new ItemStack(item, count));
	}

	public T setOutput(Item item, int count, int metadata) {
		return setOutput(new ItemStack(item, count, metadata));
	}

	public T setEnergyCost(int cost) {
		energyCost = cost;
		return self();
	}

	public T setRecipeTime(int time) {
		recipeTime = time;
		return self();
	}

	public BasicOutputRecipe build() {
		if (ingredients.isEmpty())
			throw new RuntimeException("Recipe ingredients cannot be empty");
		if (output == null || output.isEmpty())
			throw new RuntimeException("Output cannot be empty");
		if (energyCost < 0)
			throw new RuntimeException("Energy cost must be 0 or greater");
		if (recipeTime <= 0)
			throw new RuntimeException("Recipe time must be greater than 0");

		return new BasicOutputRecipe(ingredients, output, recipeTime, energyCost);
	}

	@SuppressWarnings("unchecked")
	protected T self() {
		return (T) this;
	}
}
