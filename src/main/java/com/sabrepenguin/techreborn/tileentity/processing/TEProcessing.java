package com.sabrepenguin.techreborn.tileentity.processing;

import com.sabrepenguin.techreborn.jei.TRRecipePlugin;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.tileentity.common.TileEntityOneToOne;

public class TEProcessing {
	public static class TileEntityGrinder extends TileEntityOneToOne {
		public TileEntityGrinder() {
			super("tile.techreborn.grinder.name", 40000, 128, RegistryHandler.instance().getGrinderRegistry(), TRRecipePlugin.GRINDER_UID);
		}
	}
	public static class TileEntityExtractor extends TileEntityOneToOne {
		public TileEntityExtractor() {
			super("tile.techreborn.extractor.name", 40000, 128, RegistryHandler.instance().getExtractorRegistry(), TRRecipePlugin.EXTRACTOR_UID);
		}
	}
	public static class TileEntityPlateBendingMachine extends TileEntityOneToOne {
		public TileEntityPlateBendingMachine() {
			super("tile.techreborn.plate_bending_machine.name", 40000, 128, RegistryHandler.instance().getPlateBenderRegistry(), TRRecipePlugin.PLATE_BENDING_UID);
		}
	}
	public static class TileEntityWireMill extends TileEntityOneToOne {
		public TileEntityWireMill() {
			super("tile.techreborn.wire_mill.name", 40000, 128, RegistryHandler.instance().getWireMillRegistry(), TRRecipePlugin.WIRE_MILL_UID);
		}
	}
	public static class TileEntityCompressor extends TileEntityOneToOne {
		public TileEntityCompressor() {
			super("tile.techreborn.compressor.name", 40000, 128, RegistryHandler.instance().getCompressorRegistry(), TRRecipePlugin.COMPRESSOR_UID);
		}
	}
}
