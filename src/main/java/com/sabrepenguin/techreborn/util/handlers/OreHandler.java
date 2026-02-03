package com.sabrepenguin.techreborn.util.handlers;

import com.sabrepenguin.techreborn.blocks.TRBlocks;
import net.minecraftforge.oredict.OreDictionary;

public class OreHandler {
	public static void initOres() {
		OreDictionary.registerOre("treeSapling", TRBlocks.rubber_sapling);
		OreDictionary.registerOre("saplingRubber", TRBlocks.rubber_sapling);
		OreDictionary.registerOre("logWood", TRBlocks.rubber_log);
		OreDictionary.registerOre("logRubber", TRBlocks.rubber_log);
		OreDictionary.registerOre("treeLeaves", TRBlocks.rubber_leaves);
		OreDictionary.registerOre("leavesRubber", TRBlocks.rubber_leaves);
	}
}
