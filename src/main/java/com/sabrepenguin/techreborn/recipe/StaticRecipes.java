package com.sabrepenguin.techreborn.recipe;

import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.recipe.registries.BasicRegistry;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.Arrays;

public class StaticRecipes {

	public static void addExtractorRecipes() {
		BasicRegistry extractor = RegistryHandler.instance().getExtractorRegistry();
		FluidRegistry.getRegisteredFluids().forEach(
				(name, fluid) -> {
					extractor.addRecipe("10_" + name, new BasicOutputRecipe(
							Arrays.asList(new CountedIngredient(1, TRItems.cell.getCellWithFluid(fluid))),
							new ItemStack(TRItems.cell),
							40,
							8)
					);
				}
		);
		for(int i = 1; i < 16; i++) { //TODO: Fix this up to not be java, or find an oredict way
			extractor.addRecipe("wool_" + i, new BasicOutputRecipe(
					Arrays.asList(new CountedIngredient(1, new ItemStack(Blocks.WOOL, 1, i))), new ItemStack(Blocks.WOOL), 400, 8
			));
		}
	}
}
