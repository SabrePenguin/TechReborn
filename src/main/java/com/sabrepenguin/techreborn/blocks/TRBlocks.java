package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.machines.*;
import com.sabrepenguin.techreborn.blocks.machines.energy.BlockCreativeSolarPanel;
import com.sabrepenguin.techreborn.blocks.machines.energy.BlockLSUStorage;
import com.sabrepenguin.techreborn.blocks.machines.lighting.BlockLamp;
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
	public static final BlockOmnidirectionalStatic lv_transformer = null;
	public static final BlockOmnidirectionalStatic mv_transformer = null;
	public static final BlockOmnidirectionalStatic hv_transformer = null;
	public static final BlockOmnidirectionalStatic ev_transformer = null;
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
	public static final BlockHorizontalMachine electric_furnace = null;
	public static final BlockPlayerDetector player_detector = null;
	public static final BlockHorizontalMachine pump = null;
	public static final BlockHorizontalMachine rolling_machine = null;
	public static final BlockHorizontalMachine scrapboxinator = null;
	public static final BlockOmnidirectionalStatic low_voltage_su = null;
	public static final BlockOmnidirectionalStatic medium_voltage_su = null;
	public static final BlockOmnidirectionalStatic high_voltage_su = null;
	public static final BlockOmnidirectionalStatic adjustable_su = null;
	public static final BlockOmnidirectionalStatic interdimensional_su = null;
	public static final BlockOmnidirectionalStatic lapotronic_su = null;
	public static final BlockLSUStorage lsu_storage = null;
	public static final BlockHorizontalMachine alloy_smelter = null;
	public static final BlockHorizontalMachine assembling_machine = null;
	public static final BlockHorizontalMachine compressor = null;
	public static final BlockHorizontalMachine extractor = null;
	public static final BlockHorizontalMachine grinder = null;
	public static final BlockHorizontalMachine plate_bending_machine = null;
	public static final BlockHorizontalMachine recycler = null;
	public static final BlockHorizontalMachine solid_canning_machine = null;
	public static final BlockHorizontalMachine wire_mill = null;
	public static final BlockLamp lamp_incandescent = null;
	public static final BlockLamp lamp_led = null;
	public static final BlockCreativeSolarPanel creative_solar_panel = null;
	public static final BlockHorizontalMachine diesel_generator = null;
	public static final BlockHorizontalMachine dragon_egg_syphon = null;
	public static final BlockHorizontalMachine gas_turbine = null;
	public static final BlockHorizontalMachine lightning_rod = null;
	public static final BlockHorizontalMachine magic_energy_absorber = null;
	public static final BlockHorizontalMachine magic_energy_converter = null;
	public static final BlockHorizontalMachine plasma_generator = null;
	public static final BlockHorizontalMachine semi_fluid_generator = null;
	public static final BlockHorizontalMachine solid_fuel_generator = null;
	public static final BlockHorizontalMachine thermal_generator = null;
	public static final BlockHorizontalMachine water_mill = null;
	public static final BlockHorizontalMachine wind_mill = null;

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
				new BlockOmnidirectionalStatic("lv_transformer", "machines/energy"),
				new BlockOmnidirectionalStatic("mv_transformer", "machines/energy"),
				new BlockOmnidirectionalStatic("hv_transformer", "machines/energy"),
				new BlockOmnidirectionalStatic("ev_transformer", "machines/energy"),
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
				new BlockHorizontalMachine("electric_furnace", "machines/tier1"),
				new BlockPlayerDetector(),
				new BlockHorizontalMachine("pump", "machines/tier1"),
				new BlockHorizontalMachine("rolling_machine", "machines/tier1"),
				new BlockHorizontalMachine("scrapboxinator", "machines/tier1"),
				new BlockOmnidirectionalStatic("low_voltage_su", "machines/energy"),
				new BlockOmnidirectionalStatic("medium_voltage_su", "machines/energy"),
				new BlockOmnidirectionalStatic("high_voltage_su", "machines/energy"),
				new BlockOmnidirectionalStatic("adjustable_su", "machines/energy"),
				new BlockOmnidirectionalStatic("interdimensional_su", "machines/energy"),
				new BlockOmnidirectionalStatic("lapotronic_su", "machines/energy"),
				new BlockLSUStorage(),
				new BlockHorizontalMachine("alloy_smelter", "machines/tier1"),
				new BlockHorizontalMachine("assembling_machine", "machines/tier1"),
				new BlockHorizontalMachine("compressor", "machines/tier1"),
				new BlockHorizontalMachine("extractor", "machines/tier1"),
				new BlockHorizontalMachine("grinder", "machines/tier1"),
				new BlockHorizontalMachine("plate_bending_machine", "machines/tier1"),
				new BlockHorizontalMachine("recycler", "machines/tier1"),
				new BlockHorizontalMachine("solid_canning_machine", "machines/tier1"),
				new BlockHorizontalMachine("wire_mill", "machines/tier1"),
				new BlockLamp("lamp_incandescent", 14, BlockLamp.generateBox(0.25, 0.625)),
				new BlockLamp("lamp_led", 15, BlockLamp.generateBox(0.125, 0.0625)),
				new BlockCreativeSolarPanel(),
				new BlockHorizontalMachine("diesel_generator", "machines/generators"),
				new BlockHorizontalMachine("dragon_egg_syphon", "machines/generators"),
				new BlockHorizontalMachine("gas_turbine", "machines/generators"),
				new BlockHorizontalMachine("lightning_rod", "machines/generators"),
				new BlockHorizontalMachine("magic_energy_absorber", "machines/generators"),
				new BlockHorizontalMachine("magic_energy_converter", "machines/generators"),
				new BlockHorizontalMachine("plasma_generator", "machines/generators"),
				new BlockHorizontalMachine("semi_fluid_generator", "machines/generators"),
				new BlockHorizontalMachine("solid_fuel_generator", "machines/generators"),
				new BlockHorizontalMachine("thermal_generator", "machines/generators"),
				new BlockHorizontalMachine("water_mill", "machines/generators"),
				new BlockHorizontalMachine("wind_mill", "machines/generators"),
		};
		for (Block block: blocks) {
			event.getRegistry().register(block);
		}
		TRTileEntity.registerTileEntity();
	}
}
