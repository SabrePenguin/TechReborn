package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.machines.BlockIronAlloyFurnace;
import com.sabrepenguin.techreborn.blocks.machines.IronFurnace;
import com.sabrepenguin.techreborn.blocks.meta.BlockStorage;
import com.sabrepenguin.techreborn.blocks.meta.BlockStorage2;
import com.sabrepenguin.techreborn.tileentity.TRTileEntity;
import com.sabrepenguin.techreborn.tileentity.tier0.TileEntityIronAlloyFurnace;
import com.sabrepenguin.techreborn.tileentity.tier0.TileEntityIronFurnace;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = Tags.MODID)
@GameRegistry.ObjectHolder(Tags.MODID)
public class TRBlocks {
    public static final Block storage = null;
    public static final Block storage2 = null;
    public static final IronFurnace iron_furnace = null;
	public static final BlockIronAlloyFurnace iron_alloy_furnace = null;

    public static Block[] getBlocks() {
        final Block[] blocks = {
                new BlockStorage(),
                new BlockStorage2(),
                new IronFurnace(),
				new BlockIronAlloyFurnace(),
        };

        return blocks;
    }
    public static List<Block> getAllBlocks() {
        List<Block> allBlocks = new ArrayList<>();
        Collections.addAll(allBlocks,
                TRBlocks.storage,
                TRBlocks.storage2,
                TRBlocks.iron_furnace,
				TRBlocks.iron_alloy_furnace
        );
        return allBlocks;
    }

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		for (Block block: TRBlocks.getBlocks()) {
			event.getRegistry().register(block);
		}
		TRTileEntity.registerTileEntity();
	}
}
