package com.sabrepenguin.techreborn.recipe.data;

import com.sabrepenguin.techreborn.blocks.BlockCable;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.builders.BasicBuilder;
import com.sabrepenguin.techreborn.recipe.registries.BasicRegistry;
import net.minecraft.item.Item;

public class WiremillRecipes {
	private static final BasicRegistry REGISTRY = RegistryHandler.instance().getWireMillRegistry();

	public static void init() {
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("ingotGold")
				.setOutput(Item.getItemFromBlock(TRBlocks.cable), 6, BlockCable.CableEnum.GOLD.metadata())
				.setRecipeTime(200)
				.setEnergyCost(4)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("ingotCopper")
				.setOutput(Item.getItemFromBlock(TRBlocks.cable), 3, BlockCable.CableEnum.COPPER.metadata())
				.setRecipeTime(100)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("ingotTin")
				.setOutput(Item.getItemFromBlock(TRBlocks.cable), 4, BlockCable.CableEnum.TIN.metadata())
				.setRecipeTime(140)
				.setEnergyCost(4)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("ingotRefinedIron")
				.setOutput(Item.getItemFromBlock(TRBlocks.cable), 6, BlockCable.CableEnum.HV.metadata())
				.setRecipeTime(200)
				.setEnergyCost(4)
				.build());
	}
}
