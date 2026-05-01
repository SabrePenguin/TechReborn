package com.sabrepenguin.techreborn.recipe.data;

import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.Ingot;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.builders.CartesianBuilder;
import com.sabrepenguin.techreborn.recipe.registries.BasicRegistry;

import java.util.Arrays;
import java.util.List;

public class AlloyRecipes {
	public static final BasicRegistry REGISTRY = RegistryHandler.instance().getAlloyRegistry();

	public static void init() {
		List<String> types = Arrays.asList("dust", "ingot");
		new CartesianBuilder().addOres(types, "copper", 3)
				.addOres(types, "zinc")
				.setOutput(TRItems.ingot, 4, Ingot.IngotMeta.brass.metadata())
				.setRecipeTime(200)
				.setEnergyCost(64)
				.buildAll(REGISTRY);
		new CartesianBuilder().addOres(types, "copper", 3)
				.addOres(types, "tin")
				.setOutput(TRItems.ingot, 4, Ingot.IngotMeta.bronze.metadata())
				.setRecipeTime(200)
				.setEnergyCost(64)
				.buildAll(REGISTRY);
		new CartesianBuilder().addOres(types, "gold")
				.addOres(types, "silver")
				.setOutput(TRItems.ingot, 2, Ingot.IngotMeta.electrum.metadata())
				.setRecipeTime(200)
				.setEnergyCost(64)
				.buildAll(REGISTRY);
		new CartesianBuilder().addOres(types, "iron", 2)
				.addOres(types, "nickel")
				.setOutput(TRItems.ingot, 3, Ingot.IngotMeta.invar.metadata())
				.setRecipeTime(200)
				.setEnergyCost(64)
				.buildAll(REGISTRY);
	}
}
