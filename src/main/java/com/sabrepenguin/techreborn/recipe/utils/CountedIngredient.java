package com.sabrepenguin.techreborn.recipe.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CountedIngredient {
	private static final CountedIngredient EMPTY = new CountedIngredient(Ingredient.EMPTY, 0);

	private final int count;
	private final Ingredient ingredient;

	public CountedIngredient(Ingredient ingredient, int count) {
		this.count = count;
		this.ingredient = ingredient;
	}

	public static CountedIngredient getEmpty() {
		return EMPTY;
	}

	public CountedIngredient(Item item, int count) {
		this.count = count;
		ingredient = Ingredient.fromItem(item);
	}

	public CountedIngredient(int count, ItemStack stack) {
		this.count = count;
		ingredient = Ingredient.fromStacks(stack);
	}

	public CountedIngredient(int count, String oreDict) {
		this.count = count;
		if (!OreDictionary.doesOreNameExist(oreDict)) {
			throw new RuntimeException("Could not create ore dictionary counted ingredient");
		}
		ingredient = Ingredient.fromStacks(OreDictionary.getOres(oreDict).stream().toArray(ItemStack[]::new));
	}

	public List<ItemStack> resolveIngredients() {
		List<ItemStack> outputs = Arrays.stream(ingredient.getMatchingStacks()).collect(Collectors.toList());
		outputs.forEach(stack -> stack.setCount(count));
		return outputs;
	}

	public int getCount() {
		return this.count;
	}

	public boolean matches(ItemStack stack) {
		if (!ingredient.apply(stack))
			return false;
		return stack.getCount() >= count;
	}

	public Ingredient getIngredient() {
		return this.ingredient;
	}
}
