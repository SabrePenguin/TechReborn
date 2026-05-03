package com.sabrepenguin.techreborn.util.handlers;

import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.Ingot;
import com.sabrepenguin.techreborn.util.ExtraStringUtils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.StringUtils;

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
		OreDictionary.registerOre("glassReinforced", TRBlocks.reinforced_glass);

		OreDictionary.registerOre("lapotronCrystal", TRItems.lapotroncrystal);
		OreDictionary.registerOre("energyCrystal", TRItems.energycrystal);
		OreDictionary.registerOre("drillBasic", TRItems.steel_drill);
		OreDictionary.registerOre("drillDiamond", TRItems.diamonddrill);

		OreDictionary.registerOre("treeLeaves", new ItemStack(Blocks.LEAVES, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("treeLeaves", new ItemStack(Blocks.LEAVES2, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("treeSapling", new ItemStack(Blocks.SAPLING, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("gemCoal", new ItemStack(Items.COAL));
		OreDictionary.registerOre("gemCharcoal", new ItemStack(Items.COAL, 1, 1));

		OreDictionary.registerOre("ingotAluminium", new ItemStack(TRItems.ingot, 1, Ingot.IngotMeta.aluminum.metadata()));
	}

	public static boolean hasOre(String oreType, String name) {
		String newName = toOre(oreType, name);
		return OreDictionary.doesOreNameExist(newName) && !OreDictionary.getOres(newName).isEmpty();
	}

	public static String toOre(String oreType, String name) {
		return oreType + StringUtils.capitalize(ExtraStringUtils.capitalizeByUnderscore(name));
	}
}
