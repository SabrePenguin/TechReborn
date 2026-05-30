package com.sabrepenguin.techreborn.tileentity;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.tileentity.cable.TileEntityCable;
import com.sabrepenguin.techreborn.tileentity.misc.TileEntityChargeOMat;
import com.sabrepenguin.techreborn.tileentity.misc.TileEntityMatterFabricator;
import com.sabrepenguin.techreborn.tileentity.processing.*;
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
		GameRegistry.registerTileEntity(TEProcessing.TileEntityExtractor.class, new ResourceLocation(Tags.MODID, "extractor"));
		GameRegistry.registerTileEntity(TEProcessing.TileEntityGrinder.class, new ResourceLocation(Tags.MODID, "grinder"));
		GameRegistry.registerTileEntity(TEProcessing.TileEntityPlateBendingMachine.class, new ResourceLocation(Tags.MODID, "plate_bender"));
		GameRegistry.registerTileEntity(TileEntityRecycler.class, new ResourceLocation(Tags.MODID, "recycler"));
		GameRegistry.registerTileEntity(TEProcessing.TileEntityWireMill.class, new ResourceLocation(Tags.MODID, "wire_mill"));
		GameRegistry.registerTileEntity(TEProcessing.TileEntityCompressor.class, new ResourceLocation(Tags.MODID, "compressor"));
		GameRegistry.registerTileEntity(TEProcessing.TileEntityScrapboxinator.class, new ResourceLocation(Tags.MODID, "scrapboxinator"));

		GameRegistry.registerTileEntity(TEProcessing.TileEntityAssemblingMachine.class, new ResourceLocation(Tags.MODID, "assembling_machine"));
		GameRegistry.registerTileEntity(TEProcessing.TileEntityChemicalReactor.class, new ResourceLocation(Tags.MODID, "chemical_reactor"));
		GameRegistry.registerTileEntity(TEProcessing.TileEntitySolidCanningMachine.class, new ResourceLocation(Tags.MODID, "solid_canning_machine"));

		GameRegistry.registerTileEntity(TileEntityChargeOMat.class, new ResourceLocation(Tags.MODID, "charge_o_mat"));
		GameRegistry.registerTileEntity(TileEntityMatterFabricator.class, new ResourceLocation(Tags.MODID, "matter_fabricator"));
		GameRegistry.registerTileEntity(TileEntityRollingMachine.class, new ResourceLocation(Tags.MODID, "rolling_machine"));
		GameRegistry.registerTileEntity(TileEntityAutoCraftingTable.class, new ResourceLocation(Tags.MODID, "auto_crafter"));

		GameRegistry.registerTileEntity(TileEntityIndustrialCentrifuge.class, new ResourceLocation(Tags.MODID, "industrial_centrifuge"));

		GameRegistry.registerTileEntity(TileEntityCable.class, new ResourceLocation(Tags.MODID, "cable"));
	}
}
