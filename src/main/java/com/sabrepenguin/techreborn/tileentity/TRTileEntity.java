package com.sabrepenguin.techreborn.tileentity;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.tileentity.processing.TileEntityAlloySmelter;
import com.sabrepenguin.techreborn.tileentity.processing.TileEntityElectricFurnace;
import com.sabrepenguin.techreborn.tileentity.tier0.TileEntityIronAlloyFurnace;
import com.sabrepenguin.techreborn.tileentity.tier0.TileEntityIronFurnace;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TRTileEntity {
	public static void registerTileEntity() {
		GameRegistry.registerTileEntity(TileEntityIronFurnace.class, new ResourceLocation(Tags.MODID, "iron_furnace"));
		GameRegistry.registerTileEntity(TileEntityIronAlloyFurnace.class, new ResourceLocation(Tags.MODID, "iron_alloy_furnace"));
		GameRegistry.registerTileEntity(TileEntityElectricFurnace.class, new ResourceLocation(Tags.MODID, "electric_furnace"));
		GameRegistry.registerTileEntity(TileEntityAlloySmelter.class, new ResourceLocation(Tags.MODID, "alloy_furnace"));
	}
}
