package com.sabrepenguin.techreborn.tileentity.processing;

import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.util.ModLoadedUtil;
import ic2.api.item.IC2Items;
import net.minecraft.item.ItemStack;

public class TileEntityIC2Recycler extends TileEntityRecycler {

	private static final ItemStack originalOutput = recipeOutput;
	private static final ItemStack ic2Output = IC2Items.getItem("crafting", "scrap_box");

	public TileEntityIC2Recycler() {
		if (TechRebornConfig.machineConfig.recycler.ic2Scrap && ModLoadedUtil.IC2_LOADED) {
			recipeOutput = ic2Output;
		} else {
			recipeOutput = originalOutput;
		}
	}
}
