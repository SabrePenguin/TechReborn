package com.sabrepenguin.techreborn.recipe;

import com.google.common.base.Preconditions;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AlloyRegistry {
	private final Set<AlloyRecipe> recipes = new HashSet<>();

	public Collection<AlloyRecipe> getRecipes() {
		return Collections.unmodifiableCollection(recipes);
	}

	public boolean addRecipe(AlloyRecipe recipe) {
		Preconditions.checkNotNull(recipe);
		return this.recipes.add(recipe);
	}
}
