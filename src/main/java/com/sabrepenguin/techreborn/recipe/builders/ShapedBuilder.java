package com.sabrepenguin.techreborn.recipe.builders;

import com.sabrepenguin.techreborn.recipe.recipe.ITRRecipe;
import com.sabrepenguin.techreborn.recipe.recipe.ShapedRecipe;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import it.unimi.dsi.fastutil.chars.Char2ObjectArrayMap;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ShapedBuilder extends BasicBuilder<ShapedBuilder> {
	protected int width;
	protected int height;

	protected List<String> pattern = new ArrayList<>();
	protected Char2ObjectArrayMap<CountedIngredient> countedIngredients = new Char2ObjectArrayMap<>();

	public ShapedBuilder(int size) {
		this(size, size);
	}

	public ShapedBuilder(int width, int height) {
		this.width = width;
		this.height = height;
		countedIngredients.put(' ', CountedIngredient.getEmpty());
	}

	public ShapedBuilder pattern(String pattern) {
		if (this.pattern.size() >= 3)
			throw new RuntimeException("Attempted to add fourth row to basic crafting shaped pattern");
		if (pattern.length() > 3)
			throw new RuntimeException("Recipe row too long: " + pattern);
		this.pattern.add(pattern);
		return self();
	}

	public ShapedBuilder define(char key, ItemStack ingredient, int count) {
		countedIngredients.put(key, new CountedIngredient(count, ingredient));
		return self();
	}

	public ShapedBuilder define(char key, Ingredient ingredient, int count) {
		countedIngredients.put(key, new CountedIngredient(ingredient, count));
		return self();
	}

	public ShapedBuilder define(char key, String oreDict) {
		return define(key, oreDict, 1);
	}

	public ShapedBuilder define(char key, String oreDict, int count) {
		countedIngredients.put(key, new CountedIngredient(count, oreDict));
		return self();
	}

	public ShapedRecipe buildShaped() {
		checkRecipe();
		for (String s: pattern) {
			for (char c: s.toCharArray()) {
				addInput(countedIngredients.get(c));
			}
		}
		if (ingredients.isEmpty())
			throw new RuntimeException("Recipe ingredients cannot be empty");
		if (output == null || output.isEmpty())
			throw new RuntimeException("Output cannot be empty");
		if (energyCost < 0)
			throw new RuntimeException("Energy cost must be 0 or greater");
		if (recipeTime <= 0)
			throw new RuntimeException("Recipe time must be greater than 0");

		return new ShapedRecipe(width, height, ingredients, output, recipeTime, energyCost);
	}

	@Override
	public ITRRecipe build() {
		checkRecipe();
		for (String s: pattern) {
			for (char c: s.toCharArray()) {
				addInput(countedIngredients.get(c));
			}
		}
		return super.build();
	}

	protected void checkRecipe() {
		Set<Character> checkedChars = new HashSet<>(countedIngredients.keySet());
		checkedChars.remove(' ');
		for (String p: pattern) {
			if (p.length() > width)
				throw new RuntimeException("Pattern length cannot be larger than " + width);
			for (char c: p.toCharArray()) {
				if (!countedIngredients.containsKey(c) && c != ' ') {
					throw new RuntimeException("No ingredient for key " + c);
				}
				checkedChars.remove(c);
			}
		}
		if (!checkedChars.isEmpty())
			throw new RuntimeException("Unused keys: " + checkedChars.stream().map(Object::toString).collect(Collectors.joining(", ")));
	}
}
