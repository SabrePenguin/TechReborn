package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.machines.*;
import com.sabrepenguin.techreborn.blocks.machines.tier1.*;
import com.sabrepenguin.techreborn.blocks.machines.tier2.*;
import com.sabrepenguin.techreborn.blocks.machines.tier3.*;
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
	public static final BlockTransformer lv_transformer = null;
	public static final BlockTransformer mv_transformer = null;
	public static final BlockTransformer hv_transformer = null;
	public static final BlockTransformer ev_transformer = null;
	public static final BlockQuantumTank quantum_tank = null;
	public static final BlockQuantumChest quantum_chest = null;
	public static final BlockCreativeQuantumTank creative_quantum_tank = null;
	public static final BlockCreativeQuantumChest creative_quantum_chest = null;
	public static final BlockFluidReplicator fluid_replicator = null;
	public static final BlockChunkLoader chunk_loader = null;
	public static final BlockMatterFabricator matter_fabricator = null;
	public static final BlockChargeOMat charge_o_mat = null;
	public static final BlockChemicalReactor chemical_reactor = null;
	public static final BlockDigitalChest digital_chest = null;
	public static final BlockDistillationTower distillation_tower = null;
	public static final BlockImplosionCompressor implosion_compressor = null;
	public static final BlockIndustrialBlastFurnace industrial_blast_furnace = null;
	public static final BlockIndustrialCentrifuge industrial_centrifuge = null;
	public static final BlockIndustrialElectrolyzer industrial_electrolyzer = null;
	public static final BlockIndustrialGrinder industrial_grinder = null;
	public static final BlockIndustrialSawmill industrial_sawmill = null;
	public static final BlockVacuumFreezer vacuum_freezer = null;
	public static final BlockAutoCraftingTable auto_crafting_table = null;
	public static final HorizontalMachineGuiBlock electric_furnace = null;
	public static final BlockPlayerDetector player_detector = null;
	public static final HorizontalMachineGuiBlock pump = null;
	public static final HorizontalMachineGuiBlock rolling_machine = null;
	public static final HorizontalMachineGuiBlock scrapboxinator = null;

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
				new BlockNuke(),
				new BlockTransformer("lv_transformer"),
				new BlockTransformer("mv_transformer"),
				new BlockTransformer("hv_transformer"),
				new BlockTransformer("ev_transformer"),
				new BlockQuantumTank(),
				new BlockQuantumChest(),
				new BlockCreativeQuantumTank(),
				new BlockCreativeQuantumChest(),
				new BlockChunkLoader(),
				new BlockFluidReplicator(),
				new BlockMatterFabricator(),
				new BlockChargeOMat(),
				new BlockChemicalReactor(),
				new BlockDigitalChest(),
				new BlockDistillationTower(),
				new BlockImplosionCompressor(),
				new BlockIndustrialBlastFurnace(),
				new BlockIndustrialCentrifuge(),
				new BlockIndustrialElectrolyzer(),
				new BlockIndustrialGrinder(),
				new BlockIndustrialSawmill(),
				new BlockVacuumFreezer(),
				new BlockAutoCraftingTable(),
				new HorizontalMachineGuiBlock("electric_furnace", "machines/tier1"),
				new BlockPlayerDetector(),
				new HorizontalMachineGuiBlock("pump", "machines/tier1"),
				new HorizontalMachineGuiBlock("rolling_machine", "machines/tier1"),
				new HorizontalMachineGuiBlock("scrapboxinator", "machines/tier1"),
		};
		for (Block block: blocks) {
			event.getRegistry().register(block);
		}
		TRTileEntity.registerTileEntity();
	}
}
