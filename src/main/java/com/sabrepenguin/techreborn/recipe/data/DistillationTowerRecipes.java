package com.sabrepenguin.techreborn.recipe.data;

import com.sabrepenguin.techreborn.blocks.fluids.TRFluids;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.builders.MultiOutputBuilder;
import com.sabrepenguin.techreborn.recipe.registries.ITRRegistry;
import net.minecraft.item.ItemStack;

public class DistillationTowerRecipes {

	@SuppressWarnings("ConstantConditions")
	public static void init() {
		final ITRRegistry registry = RegistryHandler.instance().getDistillationTowerRegistry();

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(TRItems.cell.getCellWithFluid(TRFluids.OIL), 16)
				.addInput(new ItemStack(TRItems.cell, 33))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.SULFURIC_ACID), 16)
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.DIESEL), 16)
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 16)
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.GLYCERYL))
				.setRecipeTime(16000)
				.setEnergyCost(64)
				.build());
	}
}
