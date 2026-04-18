package com.sabrepenguin.techreborn.datagen.recipes;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.blocks.meta.BlockStorage;
import com.sabrepenguin.techreborn.blocks.meta.BlockStorage2;
import com.sabrepenguin.techreborn.datagen.builders.ShapedBuilder;
import com.sabrepenguin.techreborn.datagen.builders.ShapelessBuilder;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.*;
import com.sabrepenguin.techreborn.util.handlers.OreHandler;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.io.File;

public class StandardRecipes {
	private static final String RECIPE_DIR = "src/main/resources/assets/" + Tags.MODID + "/recipes";

	public static void initRecipes(String fileSource) {
		File actualDirectory = new File(fileSource, RECIPE_DIR);
		compression(actualDirectory);
		expansion(actualDirectory);
		throw new RuntimeException("Successfully built the files out, please restart without datagen enabled");
	}

	private static void compression(File file) {
		for (BlockStorage.Storage e: BlockStorage.Storage.values()) {
			if (OreHandler.hasOre("ingot", e.getName())) {
				cube("ingot", e.getName(), "_block", new ItemStack(TRBlocks.storage, 1, e.meta()), new File(file, "block"));
			}
			if (OreHandler.hasOre("gem", e.getName())) {
				cube("gem", e.getName(), "_block", new ItemStack(TRBlocks.storage, 1, e.meta()), new File(file, "block"));
			}
		}
		for (BlockStorage2.Storage2 e: BlockStorage2.Storage2.values()) {
			if (OreHandler.hasOre("ingot", e.getName())) {
				cube("ingot", e.getName(), "_block", new ItemStack(TRBlocks.storage2, 1, e.meta()), new File(file, "block"));
			}
			if (OreHandler.hasOre("gem", e.getName())) {
				cube("gem", e.getName(), "_block", new ItemStack(TRBlocks.storage2, 1, e.meta()), new File(file, "block"));
			}
		}
		for (Ingot.IngotMeta e: Ingot.IngotMeta.values()) {
			if (OreHandler.hasOre("nugget", e.getName())) {
				cube("nugget", e.getName(), "_ingotn", new ItemStack(TRItems.ingot, 1, e.metadata()), new File(file, "ingot"));
			}
		}
		cube("nugget", "iron", "_ingotn", new ItemStack(Items.IRON_INGOT), new File(file, "ingot"));
		cube("nugget", "diamond", "_gemn", new ItemStack(Items.DIAMOND), new File(file, "gem"));
		for (Dust.MetaDust e: Dust.MetaDust.values()) {
			if (OreHandler.hasOre("dustSmall", e.getName())) {
				smallcube("dustSmall", e.getName(), "_dust", new ItemStack(TRItems.dust, 1, e.metadata()), new File(file, "dust"));
			}
		}
		smallcube("dustSmall", "glowstone", "_dust", new ItemStack(Items.GLOWSTONE_DUST), new File(file, "dust"));
		smallcube("dustSmall", "redstone", "_dust", new ItemStack(Items.REDSTONE), new File(file, "dust"));
	}

	private static void expansion(File file) {
		for (Ingot.IngotMeta e: Ingot.IngotMeta.values()) {
			if (OreHandler.hasOre("block", e.getName())) {
				one_to_x("block", e.getName(), "_ingotb", new ItemStack(TRItems.ingot, 9, e.metadata()), new File(file, "ingot"));
			}
		}
		new ShapelessBuilder()
				.name("refined_iron_ingotb")
				.requires(new ItemStack(TRBlocks.storage2, 1, BlockStorage2.Storage2.REFINED_IRON.meta()))
				.withResult(new ItemStack(TRItems.ingot, 9, Ingot.IngotMeta.refined_iron.metadata()))
				.save(new File(file, "ingot"));
		for (Nugget.NuggetMeta e: Nugget.NuggetMeta.values()) {
			if (OreHandler.hasOre("ingot", e.getName())) {
				one_to_x("ingot", e.getName(), "_nugget", new ItemStack(TRItems.nuggets, 9, e.metadata()), new File(file, "nugget"));
			}
		}
		one_to_x("gem", "diamond", "_nugget", new ItemStack(TRItems.nuggets, 9, Nugget.NuggetMeta.diamond.metadata()), new File(file, "nugget"));
		for (Gem.GemMeta e: Gem.GemMeta.values()) {
			if (OreHandler.hasOre("block", e.getName())) {
				one_to_x("block", e.getName(), "_gemb", new ItemStack(TRItems.gem, 9, e.metadata()), new File(file, "gem"));
			}
		}
		for (DustSmall.DustSmallMeta e: DustSmall.DustSmallMeta.values()) {
			if (OreHandler.hasOre("dust", e.name())) {
				one_to_x("dust", e.getName(), "_smalldust", new ItemStack(TRItems.smalldust, 4, e.metadata()), new File(file, "smalldust"));
			}
		}
	}

	private static void one_to_x(String type, String name, String fileName, ItemStack out, File recipeDir) {
		new ShapelessBuilder()
				.name(name + fileName)
				.requires(OreHandler.toOre(type, name))
				.withResult(out)
				.save(recipeDir);
	}

	private static void smallcube(String type, String name, String fileName, ItemStack out, File recipeDir) {
		new ShapedBuilder()
				.name(name + fileName)
				.define('I', OreHandler.toOre(type, name))
				.pattern("II")
				.pattern("II")
				.withResult(out)
				.save(recipeDir);
	}

	private static void cube(String type, String name, String fileName, ItemStack out, File recipeDir) {
		new ShapedBuilder()
				.name(name + fileName)
				.define('I', OreHandler.toOre(type, name))
				.pattern("III")
				.pattern("III")
				.pattern("III")
				.withResult(out)
				.save(recipeDir);
	}
}
