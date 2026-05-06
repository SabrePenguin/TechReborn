package com.sabrepenguin.techreborn.recipe.data;

import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.Ingot;
import com.sabrepenguin.techreborn.items.materials.Part;
import com.sabrepenguin.techreborn.items.materials.Plate;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.builders.BasicBuilder;
import com.sabrepenguin.techreborn.recipe.registries.BasicRegistry;
import net.minecraft.item.ItemStack;

public class SolidCanningMachineRecipes {
	public static final BasicRegistry REGISTRY = RegistryHandler.instance().getSolidCanningMachineRegistry();

	@SuppressWarnings("ConstantConditions")
	public static void init() {
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(TRItems.part, 1, Part.PartMeta.compressed_plantball.metadata())
				.addInput(new ItemStack(TRItems.cell))
				.setOutput(TRItems.part, 1, Part.PartMeta.bio_cell.metadata())
				.setRecipeTime(100)
				.setEnergyCost(4)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(TRItems.ingot, 1, Ingot.IngotMeta.thorium.metadata())
				.addInput(new ItemStack(TRItems.cell))
				.setOutput(new ItemStack(TRItems.single_thorium_fuel_rod))
				.setRecipeTime(100)
				.setEnergyCost(4)
				.build());
	}
}
