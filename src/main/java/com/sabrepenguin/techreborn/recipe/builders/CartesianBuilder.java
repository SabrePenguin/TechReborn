package com.sabrepenguin.techreborn.recipe.builders;

import com.google.common.collect.Lists;
import com.sabrepenguin.techreborn.recipe.BasicOutputRecipe;
import com.sabrepenguin.techreborn.recipe.registries.ITRRegistry;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import com.sabrepenguin.techreborn.util.ExtraStringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartesianBuilder extends BasicBuilder<CartesianBuilder> {
	protected List<Pair<List<String>, Pair<String, Integer>>> ores = new ArrayList<>();

	public CartesianBuilder addOres(List<String> oretypes, String ore) {
		return addOres(oretypes, ore, 1);
	}

	public CartesianBuilder addOres(List<String> oretypes, String ore, int count) {
		ores.add(Pair.of(oretypes, Pair.of(ore, count)));
		return this;
	}

	public void buildAll(ITRRegistry registry) {
		List<List<String>> output = Lists.cartesianProduct(ores.stream().map(Pair::getLeft).collect(Collectors.toList()));
		for (List<String> items : output) {
			final List<CountedIngredient> newIngredients = new ArrayList<>(this.ingredients);
			for (int index = 0; index < items.size(); index++) {
				Pair<String, Integer> ore = ores.get(index).getRight();
				String oreName = items.get(index) + ExtraStringUtils.capitalizeByUnderscore(ore.getLeft());
				newIngredients.add(new CountedIngredient(ore.getRight(), oreName));
			}
			registry.addRecipe(new BasicOutputRecipe(newIngredients, this.output, recipeTime, energyCost));
		}
	}
}
