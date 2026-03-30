package com.sabrepenguin.techreborn.recipe.data;

import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.Dust;
import com.sabrepenguin.techreborn.items.materials.Ingot;
import com.sabrepenguin.techreborn.items.materials.Part;
import com.sabrepenguin.techreborn.items.materials.Plate;
import com.sabrepenguin.techreborn.recipe.BasicOutputRecipe;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.builders.BasicBuilder;
import com.sabrepenguin.techreborn.recipe.registries.BasicRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CompressorRecipes {
	private static final BasicRegistry REGISTRY = RegistryHandler.instance().getCompressorRegistry();

	public static void init() {
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(TRItems.ingot, 1, Ingot.IngotMeta.advanced_alloy.metadata())
				.setOutput(TRItems.plates, 1, Plate.PlateMeta.advanced_alloy.metadata())
				.setRecipeTime(400)
				.setEnergyCost(80)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(TRItems.part, 1, Part.PartMeta.carbon_mesh.metadata())
				.setOutput(TRItems.plates, 1, Plate.PlateMeta.carbon.metadata())
				.setRecipeTime(400)
				.setEnergyCost(8)
				.build());
		InternalUtils.registerTemplateOneToOne(REGISTRY, "gem", 1, "plate", 1, 400, 8);
		InternalUtils.registerTemplateReplaceFirst(REGISTRY, "gem", "dust", 1, "plate", 1, 400, 8);
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("plankWood")
				.setOutput(TRItems.plates, 1, Plate.PlateMeta.wood.metadata())
				.setRecipeTime(400)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(TRItems.part, 1, Part.PartMeta.plantball.metadata())
				.setOutput(TRItems.part, 1, Part.PartMeta.compressed_plantball.metadata())
				.setRecipeTime(400)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("treeLeaves", 8)
				.setOutput(TRItems.part, 1, Part.PartMeta.compressed_plantball.metadata())
				.setRecipeTime(400)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("treeSapling", 8)
				.setOutput(TRItems.part, 1, Part.PartMeta.compressed_plantball.metadata())
				.setRecipeTime(400)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(plantball(Items.REEDS, 8));
		REGISTRY.addRecipe(plantball(Item.getItemFromBlock(Blocks.CACTUS), 8));
		REGISTRY.addRecipe(plantball(Items.WHEAT, 8));
		REGISTRY.addRecipe(plantball(Items.CARROT, 8));
		REGISTRY.addRecipe(plantball(Items.POTATO, 8));
		REGISTRY.addRecipe(plantball(Items.APPLE, 8));
		REGISTRY.addRecipe(plantball(Items.MELON, 64));
		REGISTRY.addRecipe(plantball(Item.getItemFromBlock(Blocks.MELON_BLOCK), 8));
		REGISTRY.addRecipe(plantball(Item.getItemFromBlock(Blocks.PUMPKIN), 8));
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(TRItems.dust, 1, Dust.MetaDust.thorium.metadata())
				.setOutput(TRItems.ingot, 1, Ingot.IngotMeta.thorium.metadata())
				.setRecipeTime(400)
				.setEnergyCost(8)
				.build());
	}

	private static BasicOutputRecipe plantball(Item item, int count) {
		return new BasicBuilder<>()
				.addInput(new ItemStack(item), count)
				.setOutput(TRItems.part, 1, Part.PartMeta.compressed_plantball.metadata())
				.setRecipeTime(400)
				.setEnergyCost(8)
				.build();
	}
}
