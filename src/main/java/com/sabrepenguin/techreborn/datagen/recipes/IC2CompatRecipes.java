package com.sabrepenguin.techreborn.datagen.recipes;

import com.sabrepenguin.techreborn.datagen.builders.ReplaceableShapedBuilder;
import com.sabrepenguin.techreborn.datagen.builders.ingredients.ListIngredient;
import com.sabrepenguin.techreborn.datagen.builders.ingredients.OreDictIngredient;

import java.io.File;

public class IC2CompatRecipes {
	public static void initRecipes(File file) {
		File location = new File(file, "ic2compat");
		layered(file, "ingotRefinedIron", "ingotBronze", "ingotTin");
		new ReplaceableShapedBuilder<>()
				.name("testjson")
				.pattern("TTT")
				.pattern("MMM")
				.pattern("BBB")
				.define('T', new OreDictIngredient("ingotRefinedIron", 1))
				.define('M', new ListIngredient()
						.addIngredient(new OreDictIngredient("ingotBronze", 1))
						.addIngredient(new OreDictIngredient("ingotBrass", 1)))
				.define('B', new OreDictIngredient("ingotTin", 1))
				.withOutput(IC2Duplicates.CABLE_COPPER.getIngredient()) // Fake
				.save(location);
	}

	private static void layered(File file, String top, String middle, String bottom) {

	}
}
