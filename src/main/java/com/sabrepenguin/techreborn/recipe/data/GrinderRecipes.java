package com.sabrepenguin.techreborn.recipe.data;

import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.Dust;
import com.sabrepenguin.techreborn.items.materials.Part;
import com.sabrepenguin.techreborn.recipe.BasicOutputRecipe;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.builders.BasicBuilder;
import com.sabrepenguin.techreborn.recipe.registries.BasicRegistry;
import com.sabrepenguin.techreborn.util.ExtraStringUtils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class GrinderRecipes {
	public static final BasicRegistry REGISTRY = RegistryHandler.instance().getGrinderRegistry();

	private static BasicOutputRecipe stringToRecipe(String name) {
		return stringToRecipe(name, 2);
	}

	private static BasicOutputRecipe stringToRecipe(String name, int count) {
		return new BasicBuilder<>()
				.addInput("ore" + ExtraStringUtils.capitalizeByUnderscore(name))
				.setOutput(TRItems.dust, count, Dust.MetaDust.valueOf(name).metadata())
				.setRecipeTime(300)
				.setEnergyCost(8)
				.build();
	}

	public static void init() {
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("oreCoal")
				.setOutput(new ItemStack(Items.COAL, 3, 0))
				.setRecipeTime(300)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("oreDiamond")
				.setOutput(new ItemStack(Items.DIAMOND, 2))
				.setRecipeTime(300)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("oreEmerald")
				.setOutput(new ItemStack(Items.EMERALD, 2))
				.setRecipeTime(300)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("oreLapis")
				.setOutput(new ItemStack(Items.DYE, 10, 4))
				.setRecipeTime(300)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("oreRedstone")
				.setOutput(new ItemStack(Items.REDSTONE, 6))
				.setRecipeTime(300)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("oreIron")
				.setOutput(TRItems.dust, 2, Dust.MetaDust.iron.metadata())
				.setRecipeTime(300)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(stringToRecipe("gold"));
		REGISTRY.addRecipe(stringToRecipe("galena"));
		REGISTRY.addRecipe(stringToRecipe("ruby"));
		REGISTRY.addRecipe(stringToRecipe("sapphire"));
		REGISTRY.addRecipe(stringToRecipe("bauxite"));
		REGISTRY.addRecipe(stringToRecipe("pyrite"));
		REGISTRY.addRecipe(stringToRecipe("cinnabar"));
		REGISTRY.addRecipe(stringToRecipe("sphalerite"));
		REGISTRY.addRecipe(stringToRecipe("tungsten"));
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("oreSheldonite")
				.setOutput(TRItems.dust, 2, Dust.MetaDust.platinum.metadata())
				.setRecipeTime(300)
				.setEnergyCost(8)
				.build());
		try {
			REGISTRY.addRecipe(stringToRecipe("platinum"));
		} catch (RuntimeException e) {
			TechReborn.LOGGER.info("No platinum oredict");
		}
		REGISTRY.addRecipe(stringToRecipe("peridot"));
		try {
			REGISTRY.addRecipe(new BasicBuilder<>()
					.addInput("oreOlivine")
					.setOutput(TRItems.dust, 2, Dust.MetaDust.peridot.metadata())
					.setRecipeTime(300)
					.setEnergyCost(8)
					.build());
		} catch (RuntimeException e) {
			TechReborn.LOGGER.info("No ore type \"Olivine\" exists");
		}
		REGISTRY.addRecipe(stringToRecipe("sodalite", 12));
		REGISTRY.addRecipe(stringToRecipe("copper"));
		REGISTRY.addRecipe(stringToRecipe("tin"));
		REGISTRY.addRecipe(stringToRecipe("silver"));
		REGISTRY.addRecipe(stringToRecipe("lead"));
		InternalUtils.registerTemplateOneToOne(REGISTRY, "ingot", 1, "dust", 1, 300, 8);
		InternalUtils.registerTemplateOneToOne(REGISTRY, "gem", 1, "dust", 1, 300, 8);
		InternalUtils.registerTemplateOneToOne(REGISTRY, "plate", 1, "dust", 1, 300, 8);
		InternalUtils.registerTemplateOneToOne(REGISTRY, "block", 1, "dust", 9, 300, 8);
		InternalUtils.registerTemplateOneToOne(REGISTRY, "stone", 1, "dust", 1, 300, 8);
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(Blocks.COBBLESTONE))
				.setOutput(new ItemStack(Blocks.SAND))
				.setRecipeTime(300)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(Blocks.END_STONE))
				.setOutput(TRItems.dust, 1, Dust.MetaDust.endstone.metadata())
				.setRecipeTime(300)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(Blocks.GLOWSTONE))
				.setOutput(Items.GLOWSTONE_DUST, 4)
				.setRecipeTime(300)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(Blocks.NETHERRACK))
				.setOutput(TRItems.dust, 1, Dust.MetaDust.netherrack.metadata())
				.setRecipeTime(300)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(Blocks.OBSIDIAN))
				.setOutput(TRItems.dust, 4, Dust.MetaDust.obsidian.metadata())
				.setRecipeTime(300)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(Items.BLAZE_ROD))
				.setOutput(Items.BLAZE_POWDER, 4)
				.setRecipeTime(300)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(Items.BONE))
				.setOutput(Items.DYE, 6, 15)
				.setRecipeTime(300)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(Items.CLAY_BALL))
				.setOutput(TRItems.dust, 1, Dust.MetaDust.clay.metadata())
				.setRecipeTime(300)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(Items.ENDER_PEARL))
				.setOutput(TRItems.dust, 2, Dust.MetaDust.ender_pearl.metadata())
				.setRecipeTime(300)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(Items.ENDER_EYE))
				.setOutput(TRItems.dust, 2, Dust.MetaDust.ender_eye.metadata())
				.setRecipeTime(300)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(Blocks.GRAVEL))
				.setOutput(Items.FLINT, 1)
				.setRecipeTime(300)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(new ItemStack(Blocks.MAGMA))
				.setOutput(Items.MAGMA_CREAM, 4)
				.setRecipeTime(300)
				.setEnergyCost(8)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput(TRItems.part, 1, Part.PartMeta.plantball.metadata())
				.setOutput(new ItemStack(Blocks.DIRT))
				.setRecipeTime(300)
				.setEnergyCost(8)
				.build());
	}
}
