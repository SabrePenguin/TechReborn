package com.sabrepenguin.techreborn.util.handlers;

import com.sabrepenguin.techreborn.blocks.TRBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreHandler {
	public static void initOres() {
		OreDictionary.registerOre("treeSapling", TRBlocks.rubber_sapling);
		OreDictionary.registerOre("saplingRubber", TRBlocks.rubber_sapling);
		OreDictionary.registerOre("logWood", TRBlocks.rubber_log);
		OreDictionary.registerOre("logRubber", TRBlocks.rubber_log);
		OreDictionary.registerOre("plankWood", TRBlocks.rubber_planks);
		OreDictionary.registerOre("plankRubber", TRBlocks.rubber_planks);
		OreDictionary.registerOre("slabWood", TRBlocks.rubber_plank_slab);
		OreDictionary.registerOre("stairWood", TRBlocks.rubber_plank_stair);
		OreDictionary.registerOre("treeLeaves", TRBlocks.rubber_leaves);
		OreDictionary.registerOre("leavesRubber", TRBlocks.rubber_leaves);

		OreDictionary.registerOre("treeLeaves", new ItemStack(Blocks.LEAVES, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("treeLeaves", new ItemStack(Blocks.LEAVES2, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("treeSapling", new ItemStack(Blocks.SAPLING, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("gemCoal", new ItemStack(Items.COAL));
		OreDictionary.registerOre("gemCharcoal", new ItemStack(Items.COAL, 1, 1));
	}
}
