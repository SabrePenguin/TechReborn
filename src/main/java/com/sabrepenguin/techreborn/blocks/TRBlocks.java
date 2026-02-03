package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.machines.BlockIronAlloyFurnace;
import com.sabrepenguin.techreborn.blocks.machines.IronFurnace;
import com.sabrepenguin.techreborn.blocks.meta.*;
import com.sabrepenguin.techreborn.tileentity.TRTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
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
	public static final Block ore = null;
    public static final IronFurnace iron_furnace = null;
	public static final BlockIronAlloyFurnace iron_alloy_furnace = null;
	public static final Block ore2 = null;
	public static final Block machine_frame = null;
	public static final Block machine_casing = null;
	public static final BlockRubberLog rubber_log = null;
	public static final BlockRubberLeaves rubber_leaves = null;
	public static final BlockRubberSapling rubber_sapling = null;
	public static final BlockPlanks rubber_planks = null;
	public static final BlockTRWoodenSlabs rubber_plank_slab = null;
	public static final BlockTRWoodenSlabs rubber_plank_double_slab = null;

    public static Block[] getBlocks() {
        final Block[] blocks = {
                new BlockStorage(),
                new BlockStorage2(),
                new IronFurnace(),
				new BlockIronAlloyFurnace(),
				new OreBlock(),
				new OreBlock2(),
				new BlockMachineFrame(),
				new BlockMachineCasing(),
				new BlockRubberLog(),
				new BlockRubberLeaves(),
				new BlockRubberSapling(),
				new BlockPlanks("rubber_planks", "rubber_planks"),
				new BlockTRWoodenSlabs.Half(Material.WOOD, "rubber_plank_slab"),
				new BlockTRWoodenSlabs.Double(Material.WOOD, "rubber_plank_double_slab")
        };

        return blocks;
    }
    public static List<Block> getAllBlocks() {
        List<Block> allBlocks = new ArrayList<>();
        Collections.addAll(allBlocks,
                TRBlocks.storage,
                TRBlocks.storage2,
                TRBlocks.iron_furnace,
				TRBlocks.iron_alloy_furnace,
				TRBlocks.ore,
				TRBlocks.ore2,
				TRBlocks.machine_frame,
				TRBlocks.machine_casing,
				TRBlocks.rubber_log,
				TRBlocks.rubber_leaves,
				TRBlocks.rubber_sapling,
				TRBlocks.rubber_planks,
				TRBlocks.rubber_plank_slab,
				TRBlocks.rubber_plank_double_slab
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
