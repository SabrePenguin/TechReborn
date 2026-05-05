package com.sabrepenguin.techreborn.recipe.data;

import com.sabrepenguin.techreborn.blocks.BlockCable;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.Part;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.builders.BasicBuilder;
import com.sabrepenguin.techreborn.recipe.registries.BasicRegistry;
import com.sabrepenguin.techreborn.util.ModLoadedUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class AssemblingMachineRecipes {
	private static final BasicRegistry REGISTRY = RegistryHandler.instance().getAssemblingMachineRegistry();

	@SuppressWarnings("ConstantConditions")
	public static void init() {
		ItemStack basicCircuit = new ItemStack(TRItems.part, 2, Part.PartMeta.basic_circuit_board.metadata());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("plateRefinedIron")
				.addInput("plateElectrum", 2)
				.setOutput(basicCircuit)
				.setRecipeTime(800)
				.setEnergyCost(4)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("plateAluminum")
				.addInput("plateElectrum", 2)
				.setOutput(basicCircuit)
				.setRecipeTime(800)
				.setEnergyCost(4)
				.build());
		if (ModLoadedUtil.IC2_LOADED && TechRebornConfig.compat.ic2.deduplicate) {
			REGISTRY.addRecipe(new BasicBuilder<>()
					.addInput(basicCircuit)
					.addInput(com.sabrepenguin.techreborn.datagen.recipes.IC2Duplicates.CABLE_COPPER.getIc2Stack())
					.setOutput(TRItems.part, 2, Part.PartMeta.basic_circuit_board.metadata())
					.setRecipeTime(1600)
					.setEnergyCost(4)
					.build());
			REGISTRY.addRecipe(new BasicBuilder<>()
					.addInput("plateCarbon", 4)
					.addInput(com.sabrepenguin.techreborn.datagen.recipes.IC2Duplicates.GENERATOR.getIc2Stack())
					.setOutput(new ItemStack(TRBlocks.wind_mill))
					.setRecipeTime(6400)
					.setEnergyCost(32)
					.build());
			REGISTRY.addRecipe(new BasicBuilder<>()
					.addInput("plateMagnalium", 2)
					.addInput(com.sabrepenguin.techreborn.datagen.recipes.IC2Duplicates.GENERATOR.getIc2Stack())
					.setOutput(new ItemStack(TRBlocks.wind_mill))
					.setRecipeTime(6400)
					.setEnergyCost(32)
					.build());
			REGISTRY.addRecipe(new BasicBuilder<>()
					.addInput("plateAluminum", 4)
					.addInput(com.sabrepenguin.techreborn.datagen.recipes.IC2Duplicates.GENERATOR.getIc2Stack())
					.setOutput(new ItemStack(TRBlocks.water_mill))
					.setRecipeTime(6400)
					.setEnergyCost(32)
					.build());
		} else {
			REGISTRY.addRecipe(new BasicBuilder<>()
					.addInput(basicCircuit)
					.addInput(new ItemStack(TRBlocks.cable, 3, BlockCable.CableEnum.INSULATEDCOPPER.metadata()))
					.setOutput(TRItems.part, 2, Part.PartMeta.basic_circuit_board.metadata())
					.setRecipeTime(1600)
					.setEnergyCost(4)
					.build());
			REGISTRY.addRecipe(new BasicBuilder<>()
					.addInput("plateCarbon", 4)
					.addInput(new ItemStack(TRBlocks.solid_fuel_generator))
					.setOutput(new ItemStack(TRBlocks.wind_mill))
					.setRecipeTime(6400)
					.setEnergyCost(32)
					.build());
			REGISTRY.addRecipe(new BasicBuilder<>()
					.addInput("plateMagnalium", 2)
					.addInput(new ItemStack(TRBlocks.solid_fuel_generator))
					.setOutput(new ItemStack(TRBlocks.wind_mill))
					.setRecipeTime(6400)
					.setEnergyCost(32)
					.build());
			REGISTRY.addRecipe(new BasicBuilder<>()
					.addInput("plateAluminum", 4)
					.addInput(new ItemStack(TRBlocks.solid_fuel_generator))
					.setOutput(new ItemStack(TRBlocks.water_mill))
					.setRecipeTime(6400)
					.setEnergyCost(32)
					.build());
		}
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("circuitBasic")
				.addInput("plateElectrum", 2)
				.setOutput(TRItems.part, 1, Part.PartMeta.advanced_circuit_board.metadata())
				.setRecipeTime(1600)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("plateSilicon")
				.addInput("plateElectrum", 4)
				.setOutput(TRItems.part, 2, Part.PartMeta.advanced_circuit_board.metadata())
				.setRecipeTime(1600)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(Items.DYE, 1, 4))
				.addInput("dustElectrum")
				.setOutput(TRItems.part, 2, Part.PartMeta.advanced_circuit_parts.metadata())
				.setRecipeTime(800)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("dustLazurite")
				.addInput("dustElectrum")
				.setOutput(TRItems.part, 2, Part.PartMeta.advanced_circuit_parts.metadata())
				.setRecipeTime(800)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(TRItems.part, 2, Part.PartMeta.advanced_circuit_parts.metadata()))
				.addInput(new ItemStack(TRItems.part, 1, Part.PartMeta.advanced_circuit_board.metadata()))
				.setOutput(TRItems.part, 1, Part.PartMeta.advanced_circuit.metadata())
				.setRecipeTime(160)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("platePlatinum")
				.addInput("circuitAdvanced")
				.setOutput(TRItems.part, 1, Part.PartMeta.processor_circuit_board.metadata())
				.setRecipeTime(3200)
				.setEnergyCost(16)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("gemEmerald", 8)
				.addInput("circuitAdvanced")
				.setOutput(TRItems.part, 4, Part.PartMeta.data_storage_circuit.metadata())
				.setRecipeTime(3200)
				.setEnergyCost(16)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("gemPeridot", 8)
				.addInput("circuitAdvanced")
				.setOutput(TRItems.part, 4, Part.PartMeta.data_storage_circuit.metadata())
				.setRecipeTime(3200)
				.setEnergyCost(32)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("dustEmerald", 8)
				.addInput("circuitAdvanced")
				.setOutput(TRItems.part, 4, Part.PartMeta.data_storage_circuit.metadata())
				.setRecipeTime(3200)
				.setEnergyCost(32)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("dustPeridot", 8)
				.addInput("circuitAdvanced")
				.setOutput(TRItems.part, 4, Part.PartMeta.data_storage_circuit.metadata())
				.setRecipeTime(3200)
				.setEnergyCost(32)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(TRItems.part, 1, Part.PartMeta.data_storage_circuit.metadata()))
				.addInput(new ItemStack(TRItems.part, 1, Part.PartMeta.processor_circuit_board.metadata()))
				.setOutput(TRItems.part, 4, Part.PartMeta.data_control_circuit.metadata())
				.setRecipeTime(3200)
				.setEnergyCost(32)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(TRItems.part, 1, Part.PartMeta.processor_circuit_board.metadata()))
				.addInput("lapotronCrystal")
				.setOutput(TRItems.part, 1, Part.PartMeta.energy_flow_circuit.metadata())
				.setRecipeTime(3200)
				.setEnergyCost(16)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(TRItems.part, 1, Part.PartMeta.data_control_circuit.metadata()))
				.addInput(new ItemStack(TRItems.part, 8, Part.PartMeta.data_storage_circuit.metadata()))
				.setOutput(TRItems.part, 1, Part.PartMeta.data_orb.metadata())
				.setRecipeTime(12800)
				.setEnergyCost(64)
				.build());

	}
}
