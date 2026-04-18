package com.sabrepenguin.techreborn.datagen.recipes;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.blocks.meta.BlockStorage;
import com.sabrepenguin.techreborn.blocks.meta.BlockStorage2;
import com.sabrepenguin.techreborn.datagen.builders.ShapedBuilder;
import com.sabrepenguin.techreborn.util.handlers.OreHandler;
import net.minecraft.item.ItemStack;

import java.io.File;

public class StandardRecipes {
	private static final String RECIPE_DIR = "src/main/resources/assets/" + Tags.MODID + "/recipes";

	public static void initRecipes(String fileSource) {
		File actualDirectory = new File(fileSource, RECIPE_DIR);
		for (BlockStorage.Storage e: BlockStorage.Storage.values()) {
			if (OreHandler.hasOre("ingot", e.getName())) {
				cube("ingot", e.getName(), "_block", new ItemStack(TRBlocks.storage, 1, e.meta()), new File(actualDirectory, "block"));
			}
			if (OreHandler.hasOre("gem", e.getName())) {
				cube("gem", e.getName(), "_block", new ItemStack(TRBlocks.storage, 1, e.meta()), new File(actualDirectory, "block"));
			}
		}
		for (BlockStorage2.Storage2 e: BlockStorage2.Storage2.values()) {
			if (OreHandler.hasOre("ingot", e.getName())) {
				cube("ingot", e.getName(), "_block", new ItemStack(TRBlocks.storage2, 1, e.meta()), new File(actualDirectory, "block"));
			}
			if (OreHandler.hasOre("gem", e.getName())) {
				cube("gem", e.getName(), "_block", new ItemStack(TRBlocks.storage2, 1, e.meta()), new File(actualDirectory, "block"));
			}
		}
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
