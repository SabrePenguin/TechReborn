package com.sabrepenguin.techreborn.recipe.data;

import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.blocks.fluids.TRFluids;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.Part;
import com.sabrepenguin.techreborn.recipe.BasicOutputRecipe;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.builders.BasicBuilder;
import com.sabrepenguin.techreborn.recipe.registries.BasicRegistry;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.Arrays;

public class ExtractorRecipes {
	private static final BasicRegistry REGISTRY = RegistryHandler.instance().getExtractorRegistry();

	public static void init() {
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(TRBlocks.rubber_sapling))
				.setOutput(TRItems.part, 1, Part.PartMeta.rubber.metadata())
				.setRecipeTime(400)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(TRBlocks.rubber_log))
				.setOutput(TRItems.part, 1, Part.PartMeta.rubber.metadata())
				.setRecipeTime(400)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(Items.SLIME_BALL))
				.setOutput(TRItems.part, 2, Part.PartMeta.rubber.metadata())
				.setRecipeTime(400)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(TRItems.part, 1, Part.PartMeta.sap.metadata())
				.setOutput(TRItems.part, 3, Part.PartMeta.rubber.metadata())
				.setRecipeTime(400)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(TRItems.part, 1, Part.PartMeta.bio_cell.metadata())
				.setOutput(TRItems.cell.getCellWithFluid(TRFluids.BIOFUEL))
				.setRecipeTime(400)
				.setEnergyCost(8)
				.build());
		FluidRegistry.getRegisteredFluids().forEach(
				(name, fluid) -> REGISTRY.addRecipe(new BasicOutputRecipe(
						Arrays.asList(new CountedIngredient(1, TRItems.cell.getCellWithFluid(fluid))),
						new ItemStack(TRItems.cell),
						40,
						8)
				)
		);

		REGISTRY.addRecipe(plantMetadata(Blocks.RED_FLOWER, 0, Items.DYE, 2, 1));
		REGISTRY.addRecipe(plantMetadata(Blocks.RED_FLOWER, 1, Items.DYE, 2, 12));
		REGISTRY.addRecipe(plantMetadata(Blocks.RED_FLOWER, 2, Items.DYE, 2, 13));
		REGISTRY.addRecipe(plantMetadata(Blocks.RED_FLOWER, 3, Items.DYE, 2, 7));
		REGISTRY.addRecipe(plantMetadata(Blocks.RED_FLOWER, 4, Items.DYE, 2, 1));
		REGISTRY.addRecipe(plantMetadata(Blocks.RED_FLOWER, 5, Items.DYE, 2, 14));
		REGISTRY.addRecipe(plantMetadata(Blocks.RED_FLOWER, 6, Items.DYE, 2, 7));
		REGISTRY.addRecipe(plantMetadata(Blocks.RED_FLOWER, 7, Items.DYE, 2, 9));
		REGISTRY.addRecipe(plantMetadata(Blocks.RED_FLOWER, 8, Items.DYE, 2, 7));
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(Blocks.YELLOW_FLOWER))
				.setOutput(Items.DYE, 2, 11)
				.setRecipeTime(400)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(plantMetadata(Blocks.DOUBLE_PLANT, 0, Items.DYE, 2, 11));
		REGISTRY.addRecipe(plantMetadata(Blocks.DOUBLE_PLANT, 1, Items.DYE, 2, 13));
		REGISTRY.addRecipe(plantMetadata(Blocks.DOUBLE_PLANT, 4, Items.DYE, 2, 1));
		REGISTRY.addRecipe(plantMetadata(Blocks.DOUBLE_PLANT, 5, Items.DYE, 2, 9));
		REGISTRY.addRecipe(plantMetadata(Blocks.DOUBLE_PLANT, 2, Items.WHEAT_SEEDS, 2, 0));
		REGISTRY.addRecipe(plantMetadata(Blocks.DOUBLE_PLANT, 3, Items.WHEAT_SEEDS, 2, 0));
		REGISTRY.addRecipe(plantMetadata(Blocks.TALLGRASS, 1, Items.WHEAT_SEEDS, 1, 0));
		REGISTRY.addRecipe(plantMetadata(Blocks.TALLGRASS, 2, Items.WHEAT_SEEDS, 1, 0));
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(Blocks.DEADBUSH))
				.setOutput(Items.STICK, 2)
				.setRecipeTime(400)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("wool")
				.setOutput(Blocks.WOOL, 1, 0)
				.setRecipeTime(400)
				.setEnergyCost(8)
				.build());
	}

	private static BasicOutputRecipe plantMetadata(Block block, int inputMeta, Item output, int count, int metadata) {
		return new BasicBuilder<>()
				.addInput(new ItemStack(block, 1, inputMeta))
				.setOutput(new ItemStack(output, count, metadata))
				.setRecipeTime(400)
				.setEnergyCost(8)
				.build();
	}
}
