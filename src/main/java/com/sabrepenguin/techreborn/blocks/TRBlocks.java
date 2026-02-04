package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.machines.BlockComputerCube;
import com.sabrepenguin.techreborn.blocks.machines.BlockIronAlloyFurnace;
import com.sabrepenguin.techreborn.blocks.machines.IronFurnace;
import com.sabrepenguin.techreborn.blocks.meta.*;
import com.sabrepenguin.techreborn.tileentity.TRTileEntity;
import net.minecraft.block.Block;
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
	public static final BlockTRPlanks rubber_planks = null;
	public static final BlockTRWoodenSlabs rubber_plank_slab = null;
	public static final BlockTRWoodenSlabs rubber_plank_double_slab = null;
	public static final BlockTRWoodenStairs rubber_plank_stair = null;
	public static final BlockReinforcedGlass reinforced_glass = null;
	public static final BlockAlarm alarm = null;
	public static final BlockComputerCube computercube = null;
	public static final BlockRefinedIronFence refined_iron_fence = null;
	public static final BlockFusionCoil fusion_coil = null;
	public static final BlockFusionControlComputer fusion_control_computer = null;
	public static final BlockNuke nuke = null;

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
				TRBlocks.rubber_plank_double_slab,
				TRBlocks.rubber_plank_stair,
				TRBlocks.reinforced_glass,
				TRBlocks.alarm,
				TRBlocks.computercube,
				refined_iron_fence,
				fusion_coil,
				fusion_control_computer,
				nuke
        );
        return allBlocks;
    }

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		BlockTRPlanks planks = new BlockTRPlanks("rubber_planks", "rubber_planks");
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
				planks,
				new BlockTRWoodenSlabs.Half(Material.WOOD, "rubber_plank_slab"),
				new BlockTRWoodenSlabs.Double(Material.WOOD, "rubber_plank_double_slab"),
				new BlockTRWoodenStairs(planks, "rubber_plank_stair"),
				new BlockReinforcedGlass(),
				new BlockAlarm(),
				new BlockComputerCube(),
				new BlockRefinedIronFence(),
				new BlockFusionCoil(),
				new BlockFusionControlComputer(),
				new BlockNuke()
		};
		for (Block block: blocks) {
			event.getRegistry().register(block);
		}
		TRTileEntity.registerTileEntity();
	}
}
