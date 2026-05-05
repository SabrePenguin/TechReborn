package com.sabrepenguin.techreborn.datagen.recipes;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.BlockCable;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.blocks.fluids.TRFluids;
import com.sabrepenguin.techreborn.blocks.machines.energy.BlockSolarPanel;
import com.sabrepenguin.techreborn.blocks.meta.*;
import com.sabrepenguin.techreborn.datagen.builders.ReplaceableShapedBuilder;
import com.sabrepenguin.techreborn.datagen.builders.ReplaceableShapelessBuilder;
import com.sabrepenguin.techreborn.datagen.builders.ShapedBuilder;
import com.sabrepenguin.techreborn.datagen.builders.ShapelessBuilder;
import com.sabrepenguin.techreborn.datagen.builders.conditions.IC2Condition;
import com.sabrepenguin.techreborn.datagen.builders.conditions.ModLoadedCondition;
import com.sabrepenguin.techreborn.datagen.builders.ingredients.*;
import com.sabrepenguin.techreborn.items.ItemUpgrade;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.*;
import com.sabrepenguin.techreborn.util.handlers.OreHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;

public class StandardRecipes {

	public static void initRecipes(File file) {
		compression(file);
		expansion(file);
		recipes(file);
		upgradeRecipes(file);
		alloyRecipes(file);
		PartRecipes.parts(file);
		PartRecipes.cables(file);
		ToolRecipes.gear(file);
	}

	@SuppressWarnings("ConstantConditions")
	private static void recipes(File file) {
		machineRecipes(file);
		File out = new File(file, "misc");
		new ShapedBuilder<>()
				.name("rubber_stairs_r")
				.pattern("R  ")
				.pattern("RR ")
				.pattern("RRR")
				.define('R', new ItemStack(TRBlocks.rubber_planks))
				.withResult(new ItemStack(TRBlocks.rubber_plank_stair, 4))
				.save(out);
		new ShapedBuilder<>()
				.name("rubber_stairs_l")
				.pattern("  R")
				.pattern(" RR")
				.pattern("RRR")
				.define('R', new ItemStack(TRBlocks.rubber_planks))
				.withResult(new ItemStack(TRBlocks.rubber_plank_stair, 4))
				.save(out);
		// TODO: Slab fixes
		new ShapedBuilder<>()
				.name("reinforced_glass1")
				.pattern("GAG")
				.pattern("GGG")
				.pattern("GAG")
				.define('G', "blockGlass")
				.define('A', "plateAdvancedAlloy")
				.withResult(new ItemStack(TRBlocks.reinforced_glass, 7))
				.save(out);
		new ShapedBuilder<>()
				.name("reinforced_glass2")
				.pattern("GGG")
				.pattern("AGA")
				.pattern("GGG")
				.define('G', "blockGlass")
				.define('A', "plateAdvancedAlloy")
				.withResult(new ItemStack(TRBlocks.reinforced_glass, 7))
				.save(out);
		new ShapelessBuilder<>()
				.name("iridium_reinforced_stone")
				.requires("stone")
				.requires("plateIridiumAlloy")
				.withResult(new ItemStack(TRBlocks.storage2, 1, BlockStorage2.Storage2.IRIDIUM_REINFORCED_STONE.meta()))
				.save(out);
		new ShapelessBuilder<>()
				.name("iridium_reinforced_tungstensteel1")
				.requires(new ItemStack(TRBlocks.storage2, 1, BlockStorage2.Storage2.TUNGSTENSTEEL.meta()))
				.requires("plateIridium")
				.withResult(new ItemStack(TRBlocks.storage2, 1, BlockStorage2.Storage2.IRIDIUM_REINFORCED_TUNGSTENSTEEL.meta()))
				.save(out);
		new ShapelessBuilder<>()
				.name("iridium_reinforced_tungstensteel2")
				.requires(new ItemStack(TRBlocks.storage2, 1, BlockStorage2.Storage2.IRIDIUM_REINFORCED_STONE.meta()))
				.requires(new ItemStack(TRItems.ingot, 1, Ingot.IngotMeta.tungstensteel.metadata()))
				.withResult(new ItemStack(TRBlocks.storage2, 1, BlockStorage2.Storage2.IRIDIUM_REINFORCED_TUNGSTENSTEEL.meta()))
				.save(out);
		new ShapedBuilder<>()
				.name("cell")
				.pattern(" T ")
				.pattern("TPT")
				.pattern(" T ")
				.define('T', "ingotTin")
				.define('P', "paneGlass")
				.withResult(new ItemStack(TRItems.cell, 16))
				.save(out);
		new ReplaceableShapelessBuilder<>()
				.name("frequency_transmitter")
				.requires(IC2Duplicates.CABLE_ICOPPER.getIngredient())
				.requires("circuitBasic")
				.withOutput(IC2Duplicates.FREQ_TRANSMITTER.getIngredient())
				.save(out);
		new ShapedBuilder<>()
				.name("thorium_dual_rod")
				.withCondition(new ModLoadedCondition("ic2"))
				.pattern("RPR")
				.define('R', new ItemStack(TRItems.single_thorium_fuel_rod))
				.define('P', new ListIngredient()
						.addIngredient(new OreDictIngredient("plateCopper", 1))
						.addIngredient(new OreDictIngredient("plateLead", 1)))
				.withResult(new ItemStack(TRItems.dual_thorium_fuel_rod))
				.save(out);
		new ShapedBuilder<>()
				.name("thorium_quad_rod_copper")
				.withCondition(new ModLoadedCondition("ic2"))
				.pattern(" R ")
				.pattern("PPP")
				.pattern(" R ")
				.define('R', new ItemStack(TRItems.dual_thorium_fuel_rod))
				.define('P', "plateCopper")
				.withResult(new ItemStack(TRItems.quad_thorium_fuel_rod))
				.save(out);
		new ShapedBuilder<>()
				.name("thorium_quad_rod_lead")
				.withCondition(new ModLoadedCondition("ic2"))
				.pattern(" R ")
				.pattern("PPP")
				.pattern(" R ")
				.define('R', new ItemStack(TRItems.dual_thorium_fuel_rod))
				.define('P', "plateLead")
				.withResult(new ItemStack(TRItems.quad_thorium_fuel_rod))
				.save(out);
		new ShapedBuilder<>()
				.name("alarm")
				.pattern("ICI")
				.pattern("SRS")
				.pattern("ICI")
				.define('I', "ingotIron")
				.define('C', new ItemStack(TRBlocks.cable, 1, BlockCable.CableEnum.COPPER.metadata()))
				.define('S', IC2Duplicates.CABLE_COPPER.getIngredient())
				.define('R', "blockRedstone")
				.withResult(new ItemStack(TRBlocks.alarm))
				.save(out);
		new ShapedBuilder<>()
				.name("quantum_chest")
				.withCondition(new ModLoadedCondition("quantumstorage").invert())
				.pattern("DCD")
				.pattern("ATA")
				.pattern("DQD")
				.define('D', new ItemStack(TRItems.part, 1, Part.PartMeta.data_orb.metadata()))
				.define('C', new ItemStack(TRItems.part, 1, Part.PartMeta.computer_monitor.metadata()))
				.define('A', "machineBlockElite")
				.define('T', IC2Duplicates.COMPRESSOR.getIngredient())
				.define('Q', new ItemStack(TRBlocks.digital_chest))
				.withResult(new ItemStack(TRBlocks.quantum_chest))
				.save(out);
		new ShapedBuilder<>()
				.name("quantum_tank")
				.withCondition(new ModLoadedCondition("quantumstorage").invert())
				.pattern("EPE")
				.pattern("PCP")
				.pattern("EPE")
				.define('E', "circuitAdvanced")
				.define('P', "platePlatinum")
				.define('C', new ItemStack(TRBlocks.quantum_chest))
				.withResult(new ItemStack(TRBlocks.quantum_tank))
				.save(out);
		new ShapedBuilder<>()
				.name("incandescent_lamp")
				.pattern("GGG")
				.pattern("TCT")
				.pattern("GGG")
				.define('G', "paneGlass")
				.define('T', IC2Duplicates.CABLE_COPPER.getIngredient())
				.define('C', new ItemStack(TRItems.part, 1, Part.PartMeta.carbon_fiber.metadata()))
				.withResult(new ItemStack(TRBlocks.lamp_incandescent))
				.save(out);
		new ShapedBuilder<>()
				.name("led_lamp")
				.pattern("GGG")
				.pattern("TLT")
				.pattern("GGG")
				.define('G', "paneGlass")
				.define('T', IC2Duplicates.CABLE_TIN.getIngredient())
				.define('L', "dustGlowstone")
				.withResult(new ItemStack(TRBlocks.lamp_led))
				.save(out);
		new ShapedBuilder<>()
				.name("lapotronic_orb")
				.pattern("LLL")
				.pattern("LPL")
				.pattern("LLL")
				.define('L', "lapotronCrystal")
				.define('P', "plateIridiumAlloy")
				.withResult(new ItemStack(TRItems.lapotronicorb))
				.save(out);
		new ShapedBuilder<>()
				.name("lithium_battery")
				.pattern(" C ")
				.pattern("PFP")
				.pattern("PFP")
				.define('C', IC2Duplicates.CABLE_IGOLD.getIngredient())
				.define('P', "plateAluminum")
				.define('F', new CellIngredient(TRFluids.LITHIUM))
				.withResult(new ItemStack(TRItems.lithiumbattery))
				.save(out);
		new ShapedBuilder<>()
				.name("energy_crystal")
				.withCondition(IC2Condition.DeduplicateCondition())
				.pattern("RRR")
				.pattern("RDR")
				.pattern("RRR")
				.define('R', "dustRedstone")
				.define('D', new ListIngredient()
						.addIngredient(new OreDictIngredient("gemDiamond", 1))
						.addIngredient(new OreDictIngredient("gemRuby", 1)))
				.withResult(new ItemStack(TRItems.energycrystal))
				.save(out);
		new ShapedBuilder<>()
				.name("lapotronic_crystal")
				.withCondition(IC2Condition.DeduplicateCondition())
				.pattern("LCL")
				.pattern("LEL")
				.pattern("LCL")
				.define('L', new ItemStack(Items.DYE, 1, 4))
				.define('E', new ListIngredient()
						.addIngredient(new OreDictIngredient("energyCrystal", 1))
						.addIngredient(new OreDictIngredient("gemSapphire", 1)))
				.define('C', "circuitBasic")
				.withResult(new ItemStack(TRItems.lapotroncrystal))
				.save(out);
		new ShapedBuilder<>()
				.name("re_battery")
				.withCondition(IC2Condition.DeduplicateCondition())
				.pattern(" W ")
				.pattern("TRT")
				.pattern("TRT")
				.define('W', IC2Duplicates.CABLE_ICOPPER.getIngredient())
				.define('T', "ingotTin")
				.define('R', "dustRedstone")
				.withResult(new ItemStack(TRItems.rebattery))
				.save(out);
	}

	@SuppressWarnings("ConstantConditions")
	private static void machineRecipes(File file) {
		File location = new File(file, "machines");
		{
			File simple = new File(location, "simple");
			new ShapedBuilder<>()
					.name("iron_alloy_furnace")
					.pattern("III")
					.pattern("F F")
					.pattern("III")
					.define('I', "ingotRefinedIron")
					.define('F', IC2Duplicates.IRON_FURNACE.getIngredient())
					.withResult(new ItemStack(TRBlocks.iron_alloy_furnace))
					.save(simple);
			new ReplaceableShapedBuilder<>()
					.name("iron_furnace")
					.pattern("III")
					.pattern("I I")
					.pattern("III")
					.define('I', "ingotIron")
					.withOutput(IC2Duplicates.IRON_FURNACE.getIngredient())
					.save(simple);
			new ReplaceableShapedBuilder<>()
					.name("iron_furnace_upgrade")
					.pattern(" I ")
					.pattern("I I")
					.pattern("IFI")
					.define('I', "ingotIron")
					.define('F', new ItemStack(Blocks.FURNACE))
					.withOutput(IC2Duplicates.IRON_FURNACE.getIngredient())
					.save(simple);
		}
		{
			File generators = new File(location, "generators");
			new ShapedBuilder<>()
					.name("wind_mill_v")
					.pattern(" P ")
					.pattern(" G ")
					.pattern(" P ")
					.define('P', "plateMagnalium")
					.define('G', IC2Duplicates.GENERATOR.getIngredient())
					.withResult(new ItemStack(TRBlocks.wind_mill))
					.save(generators);
			new ShapedBuilder<>()
					.name("wind_mill_h")
					.pattern("PGP")
					.define('P', "plateMagnalium")
					.define('G', IC2Duplicates.GENERATOR.getIngredient())
					.withResult(new ItemStack(TRBlocks.wind_mill))
					.save(generators);
			new ShapedBuilder<>()
					.name("lightning_rod")
					.pattern("CAC")
					.pattern("ACA")
					.pattern("CAC")
					.define('A', new ItemStack(TRBlocks.machine_casing, 1, BlockMachineCasing.Casing.ADVANCED.meta()))
					.define('C', "circuitMaster")
					.withResult(new ItemStack(TRBlocks.lightning_rod))
					.save(generators);
			new ReplaceableShapelessBuilder<>()
					.name("generator")
					.requires("reBattery")
					.requires("machineBlockBasic")
					.withOutput(IC2Duplicates.GENERATOR.getIngredient())
					.save(generators);
			new ReplaceableShapedBuilder<>()
					.name("water_mill")
					.pattern("SWS")
					.pattern("WGW")
					.pattern("SWS")
					.define('S', "stickWood")
					.define('W', "plankWood")
					.define('G', IC2Duplicates.GENERATOR.getIngredient())
					.withOutput(IC2Duplicates.WATER_MILL.getIngredient())
					.save(generators);
			new ShapedBuilder<>()
					.name("solar_panel_basic")
					.pattern("DLD")
					.pattern("LDL")
					.pattern("CGC")
					.define('D', "dustCoal")
					.define('L', "paneGlass")
					.define('C', "circuitBasic")
					.define('G', IC2Duplicates.GENERATOR.getIngredient())
					.withResult(new ItemStack(TRBlocks.solar_panel, 1, BlockSolarPanel.SolarPanelEnum.BASIC.metadata()))
					.save(generators);
			new ShapedBuilder<>()
					.name("solar_panel_hybrid")
					.pattern("SSS")
					.pattern("STS")
					.pattern("SSS")
					.define('S', new ItemStack(TRBlocks.solar_panel, 1, BlockSolarPanel.SolarPanelEnum.BASIC.metadata()))
					.define('T', new ItemStack(TRBlocks.lv_transformer))
					.withResult(new ItemStack(TRBlocks.solar_panel, 1, BlockSolarPanel.SolarPanelEnum.HYBRID.metadata()))
					.save(generators);
			new ShapedBuilder<>()
					.name("solar_panel_advanced")
					.pattern("SSS")
					.pattern("STS")
					.pattern("SSS")
					.define('S', new ItemStack(TRBlocks.solar_panel, 1, BlockSolarPanel.SolarPanelEnum.HYBRID.metadata()))
					.define('T', new ItemStack(TRBlocks.mv_transformer))
					.withResult(new ItemStack(TRBlocks.solar_panel, 1, BlockSolarPanel.SolarPanelEnum.ADVANCED.metadata()))
					.save(generators);
			new ShapedBuilder<>()
					.name("solar_panel_ultimate")
					.pattern("SSS")
					.pattern("STS")
					.pattern("SSS")
					.define('S', new ItemStack(TRBlocks.solar_panel, 1, BlockSolarPanel.SolarPanelEnum.ADVANCED.metadata()))
					.define('T', new ItemStack(TRBlocks.hv_transformer))
					.withResult(new ItemStack(TRBlocks.solar_panel, 1, BlockSolarPanel.SolarPanelEnum.ULTIMATE.metadata()))
					.save(generators);
			new ShapedBuilder<>()
					.name("solar_panel_quantum")
					.pattern("SSS")
					.pattern("STS")
					.pattern("SSS")
					.define('S', new ItemStack(TRBlocks.solar_panel, 1, BlockSolarPanel.SolarPanelEnum.ULTIMATE.metadata()))
					.define('T', new ItemStack(TRBlocks.ev_transformer))
					.withResult(new ItemStack(TRBlocks.solar_panel, 1, BlockSolarPanel.SolarPanelEnum.QUANTUM.metadata()))
					.save(generators);
			new ShapedBuilder<>()
					.name("diesel_generator_bronze")
					.pattern("III")
					.pattern("IHI")
					.pattern("CGC")
					.define('I', "plateBronze")
					.define('H', "glassReinforced")
					.define('C', "circuitBasic")
					.define('G', IC2Duplicates.GENERATOR.getIngredient())
					.withResult(new ItemStack(TRBlocks.diesel_generator))
					.save(generators);
			new ShapedBuilder<>()
					.name("diesel_generator_brass")
					.pattern("III")
					.pattern("IHI")
					.pattern("CGC")
					.define('I', "plateBrass")
					.define('H', "glassReinforced")
					.define('C', "circuitBasic")
					.define('G', IC2Duplicates.GENERATOR.getIngredient())
					.withResult(new ItemStack(TRBlocks.diesel_generator))
					.save(generators);
			new ShapedBuilder<>()
					.name("semi_fluid_generator_ri")
					.pattern("III")
					.pattern("IHI")
					.pattern("CGC")
					.define('I', "plateRefinedIron")
					.define('H', "glassReinforced")
					.define('C', "circuitBasic")
					.define('G', IC2Duplicates.GENERATOR.getIngredient())
					.withResult(new ItemStack(TRBlocks.semi_fluid_generator))
					.save(generators);
			new ShapedBuilder<>()
					.name("semi_fluid_generator_a")
					.pattern("III")
					.pattern("IHI")
					.pattern("CGC")
					.define('I', "plateAluminum")
					.define('H', "glassReinforced")
					.define('C', "circuitBasic")
					.define('G', IC2Duplicates.GENERATOR.getIngredient())
					.withResult(new ItemStack(TRBlocks.semi_fluid_generator))
					.save(generators);
			new ShapedBuilder<>()
					.name("gas_turbine_invar")
					.pattern("IAI")
					.pattern("WGW")
					.pattern("IAI")
					.define('I', "plateInvar")
					.define('A', "circuitAdvanced")
					.define('W', new ItemStack(TRBlocks.wind_mill))
					.define('G', "glassReinforced")
					.withResult(new ItemStack(TRBlocks.gas_turbine))
					.save(generators);
			new ShapedBuilder<>()
					.name("gas_turbine_aluminum")
					.pattern("IAI")
					.pattern("WGW")
					.pattern("IAI")
					.define('I', "plateAluminum")
					.define('A', "circuitAdvanced")
					.define('W', new ItemStack(TRBlocks.wind_mill))
					.define('G', "glassReinforced")
					.withResult(new ItemStack(TRBlocks.gas_turbine))
					.save(generators);
			new ShapedBuilder<>()
					.name("thermal_generator")
					.pattern("III")
					.pattern("IHI")
					.pattern("CGC")
					.define('I', "plateInvar")
					.define('H', "glassReinforced")
					.define('C', "circuitAdvanced")
					.define('G', IC2Duplicates.GENERATOR.getIngredient())
					.withResult(new ItemStack(TRBlocks.thermal_generator))
					.save(generators);
		}
		{
			File processing = new File(location, "processing");

			new ShapedBuilder<>()
					.name("casing_standard")
					.pattern("XXX")
					.pattern("CAC")
					.pattern("XXX")
					.define('X', "ingotRefinedIron")
					.define('C', "circuitBasic")
					.define('A', "machineBlockBasic")
					.withResult(new ItemStack(TRBlocks.machine_casing, 4, BlockMachineCasing.Casing.STANDARD.meta()))
					.save(processing);
			new ShapedBuilder<>()
					.name("casing_standard_iron")
					.pattern("XXX")
					.pattern("CAC")
					.pattern("XXX")
					.define('X', "plateIron")
					.define('C', "circuitBasic")
					.define('A', "machineBlockBasic")
					.withResult(new ItemStack(TRBlocks.machine_casing, 4, BlockMachineCasing.Casing.STANDARD.meta()))
					.save(processing);
			new ShapedBuilder<>()
					.name("casing_standard_aluminum")
					.pattern("XXX")
					.pattern("CAC")
					.pattern("XXX")
					.define('X', "plateAluminum")
					.define('C', "circuitBasic")
					.define('A', "machineBlockBasic")
					.withResult(new ItemStack(TRBlocks.machine_casing, 4, BlockMachineCasing.Casing.STANDARD.meta()))
					.save(processing);
			new ShapedBuilder<>()
					.name("casing_reinforced")
					.pattern("XXX")
					.pattern("CAC")
					.pattern("XXX")
					.define('X', "plateSteel")
					.define('C', "circuitAdvanced")
					.define('A', new ListIngredient()
							.addIngredient(new ItemIngredient(new ItemStack(TRBlocks.machine_casing, 1, BlockMachineCasing.Casing.STANDARD.meta())))
							.addIngredient(new OreDictIngredient("machineBlockAdvanced", 1)))
					.withResult(new ItemStack(TRBlocks.machine_casing, 4, BlockMachineCasing.Casing.REINFORCED.meta()))
					.save(processing);
			new ShapedBuilder<>()
					.name("casing_advanced")
					.pattern("XXX")
					.pattern("CAC")
					.pattern("XXX")
					.define('X', "plateChrome")
					.define('C', "circuitElite")
					.define('A', new ListIngredient()
							.addIngredient(new ItemIngredient(new ItemStack(TRBlocks.machine_casing, 1, BlockMachineCasing.Casing.REINFORCED.meta())))
							.addIngredient(new OreDictIngredient("machineBlockElite", 1)))
					.withResult(new ItemStack(TRBlocks.machine_casing, 4, BlockMachineCasing.Casing.ADVANCED.meta()))
					.save(processing);
			new ShapedBuilder<>()
					.name("alloy_smelter")
					.pattern(" C ")
					.pattern("FMF")
					.define('C', "circuitBasic")
					.define('F', new ItemStack(TRBlocks.iron_furnace))
					.define('M', new ItemStack(TRBlocks.machine_frame, 1, BlockMachineFrame.Frame.BASIC.meta()))
					.withResult(new ItemStack(TRBlocks.alloy_smelter))
					.save(processing);
			new ReplaceableShapedBuilder<>()
					.name("compressor")
					.pattern("S S")
					.pattern("SCS")
					.pattern("SMS")
					.define('C', "circuitBasic")
					.define('S', "stone")
					.define('M', "machineBlockBasic")
					.withOutput(IC2Duplicates.COMPRESSOR.getIngredient())
					.save(processing);
			new ReplaceableShapedBuilder<>()
					.name("electrical_furnace")
					.pattern(" C ")
					.pattern("RFR")
					.define('C', "circuitBasic")
					.define('R', "dustRedstone")
					.define('F', IC2Duplicates.IRON_FURNACE.getIngredient())
					.withOutput(IC2Duplicates.ELECTRICAL_FURNACE.getIngredient())
					.save(processing);
			new ReplaceableShapedBuilder<>()
					.name("extractor")
					.pattern("TMT")
					.pattern("TCT")
					.define('T', new ItemStack(TRItems.treetap, 1, OreDictionary.WILDCARD_VALUE))
					.define('M', "machineBlockBasic")
					.define('C', "circuitBasic")
					.withOutput(IC2Duplicates.EXTRACTOR.getIngredient())
					.save(processing);
			new ReplaceableShapedBuilder<>()
					.name("recycler")
					.pattern(" B ")
					.pattern("DCD")
					.pattern("GDG")
					.define('B', "circuitBasic")
					.define('D', "dirt")
					.define('C', IC2Duplicates.COMPRESSOR.getIngredient())
					.define('G', "glowstoneDust")
					.withOutput(IC2Duplicates.RECYCLER.getIngredient())
					.save(processing);
			new ReplaceableShapedBuilder<>()
					.name("grinder")
					.pattern("FFF")
					.pattern("SMS")
					.pattern(" C ")
					.define('F', new ItemStack(Items.FLINT))
					.define('S', new ItemStack(Blocks.COBBLESTONE, 1, 0))
					.define('M', new ItemStack(TRBlocks.machine_frame, 1, BlockMachineFrame.Frame.BASIC.meta()))
					.define('C', "circuitBasic")
					.withOutput(IC2Duplicates.GRINDER.getIngredient())
					.save(processing);
			new ShapedBuilder<>()
					.name("wire_mill")
					.pattern("PEP")
					.pattern("CBC")
					.pattern("PSP")
					.define('P', "plateBrass")
					.define('E', IC2Duplicates.EXTRACTOR.getIngredient())
					.define('C', "circuitBasic")
					.define('B', "machineBlockBasic")
					.define('S', new ListIngredient()
							.addIngredient(new ItemIngredient(new ItemStack(Blocks.PISTON)))
							.addIngredient(new ItemIngredient(new ItemStack(Blocks.STICKY_PISTON))))
					.withResult(new ItemStack(TRBlocks.wire_mill))
					.save(processing);
			new ShapedBuilder<>()
					.name("chemical_reactor")
					.pattern("IEI")
					.pattern("CPC")
					.pattern("IEI")
					.define('I', "plateInvar")
					.define('E', IC2Duplicates.EXTRACTOR.getIngredient())
					.define('C', "circuitAdvanced")
					.define('P', IC2Duplicates.COMPRESSOR.getIngredient())
					.withResult(new ItemStack(TRBlocks.chemical_reactor))
					.save(processing);
			new ShapedBuilder<>()
					.name("industrial_electrolyzer")
					.pattern("IEI")
					.pattern("CAC")
					.pattern("IEI")
					.define('I', "plateIron")
					.define('E', IC2Duplicates.EXTRACTOR.getIngredient())
					.define('C', "circuitAdvanced")
					.define('A', "machineBlockAdvanced")
					.withResult(new ItemStack(TRBlocks.industrial_electrolyzer))
					.save(processing);
			new ShapedBuilder<>()
					.name("industrial_centrifuge")
					.pattern("ICI")
					.pattern("AEA")
					.pattern("ICI")
					.define('I', "ingotRefinedIron")
					.define('E', IC2Duplicates.EXTRACTOR.getIngredient())
					.define('C', "circuitAdvanced")
					.define('A', "machineBlockAdvanced")
					.withResult(new ItemStack(TRBlocks.industrial_centrifuge))
					.save(processing);
			new ShapedBuilder<>()
					.name("industrial_centrifuge_aluminum")
					.pattern("ICI")
					.pattern("AEA")
					.pattern("ICI")
					.define('I', "plateAluminum")
					.define('E', IC2Duplicates.EXTRACTOR.getIngredient())
					.define('C', "circuitAdvanced")
					.define('A', "machineBlockAdvanced")
					.withResult(new ItemStack(TRBlocks.industrial_centrifuge))
					.save(processing);
			new ShapedBuilder<>()
					.name("industrial_sawmill")
					.pattern("IAI")
					.pattern("SSS")
					.pattern("ACA")
					.define('I', "ingotRefinedIron")
					.define('S', new ItemStack(TRItems.part, 1, Part.PartMeta.diamond_saw_blade.metadata()))
					.define('A', "circuitAdvanced")
					.define('C', "machineBlockAdvanced")
					.withResult(new ItemStack(TRBlocks.industrial_sawmill))
					.save(processing);
			new ShapedBuilder<>()
					.name("industrial_blast_furnace")
					.pattern("CHC")
					.pattern("HBH")
					.pattern("FHF")
					.define('C', "circuitAdvanced")
					.define('H', new ItemStack(TRItems.part, 1, Part.PartMeta.cupronickel_heating_coil.metadata()))
					.define('F', IC2Duplicates.ELECTRICAL_FURNACE.getIngredient())
					.define('B', "machineBlockAdvanced")
					.withResult(new ItemStack(TRBlocks.industrial_blast_furnace))
					.save(processing);
			new ShapedBuilder<>()
					.name("industrial_grinder")
					.pattern("ECG")
					.pattern("HHH")
					.pattern("CBC")
					.define('E', new ItemStack(TRBlocks.industrial_electrolyzer))
					.define('C', "circuitAdvanced")
					.define('G', IC2Duplicates.GRINDER.getIngredient())
					.define('H', "craftingDiamondGrinder")
					.define('B', "machineBlockAdvanced")
					.withResult(new ItemStack(TRBlocks.industrial_grinder))
					.save(processing);
			new ShapedBuilder<>()
					.name("implosion_compressor")
					.pattern("ABA")
					.pattern("CPC")
					.pattern("ABA")
					.define('A', new ItemStack(TRItems.ingot, 1, Ingot.IngotMeta.advanced_alloy.metadata()))
					.define('B', "machineBlockAdvanced")
					.define('C', "circuitAdvanced")
					.define('P', IC2Duplicates.COMPRESSOR.getIngredient())
					.withResult(new ItemStack(TRBlocks.implosion_compressor))
					.save(processing);
			new ShapedBuilder<>()
					.name("vacuum_freezer")
					.pattern("SPS")
					.pattern("CGC")
					.pattern("SPS")
					.define('S', "plateSteel")
					.define('P', IC2Duplicates.EXTRACTOR.getIngredient())
					.define('C', "circuitAdvanced")
					.define('G', "glassReinforced")
					.withResult(new ItemStack(TRBlocks.vacuum_freezer))
					.save(processing);
			new ShapedBuilder<>()
					.name("distillation_tower")
					.pattern("CMC")
					.pattern("PBP")
					.pattern("EME")
					.define('C', new ItemStack(TRBlocks.industrial_centrifuge))
					.define('M', "circuitMaster")
					.define('P', IC2Duplicates.EXTRACTOR.getIngredient())
					.define('B', "machineBlockElite")
					.define('E', new ItemStack(TRBlocks.industrial_electrolyzer))
					.withResult(new ItemStack(TRBlocks.distillation_tower))
					.save(processing);
			new ShapedBuilder<>()
					.name("plate_bending_machine")
					.pattern("PCP")
					.pattern("MBM")
					.pattern("PCP")
					.define('P', new ListIngredient()
							.addIngredient(new ItemIngredient(new ItemStack(Blocks.PISTON)))
							.addIngredient(new ItemIngredient(new ItemStack(Blocks.STICKY_PISTON))))
					.define('C', "circuitBasic")
					.define('M', IC2Duplicates.COMPRESSOR.getIngredient())
					.define('B', "machineBlockBasic")
					.withResult(new ItemStack(TRBlocks.plate_bending_machine))
					.save(processing);
			new ShapedBuilder<>()
					.name("assembling_machine")
					.pattern("CIC")
					.pattern("PBP")
					.pattern("CPC")
					.define('C', "circuitBasic")
					.define('I', new ItemStack(Blocks.PISTON))
					.define('P', "plateRefinedIron")
					.define('B', "machineBlockBasic")
					.withResult(new ItemStack(TRBlocks.assembling_machine))
					.save(processing);
			new ShapedBuilder<>()
					.name("assembling_machine_aluminum")
					.pattern("CIC")
					.pattern("PBP")
					.pattern("CPC")
					.define('C', "circuitBasic")
					.define('I', new ItemStack(Blocks.PISTON))
					.define('P', "plateAluminum")
					.define('B', "machineBlockBasic")
					.withResult(new ItemStack(TRBlocks.assembling_machine))
					.save(processing);
			new ShapedBuilder<>()
					.name("rolling_machine")
					.pattern("PCP")
					.pattern("MBM")
					.pattern("PCP")
					.define('P', new ItemStack(Blocks.PISTON))
					.define('C', "circuitAdvanced")
					.define('M', IC2Duplicates.COMPRESSOR.getIngredient())
					.define('B', "machineBlockBasic")
					.withResult(new ItemStack(TRBlocks.rolling_machine))
					.save(processing);
			new ShapedBuilder<>()
					.name("auto_crafting_table")
					.pattern("CPC")
					.pattern("PWP")
					.pattern("CPC")
					.define('P', "plateIron")
					.define('C', "circuitAdvanced")
					.define('W', "workbench")
					.withResult(new ItemStack(TRBlocks.auto_crafting_table))
					.save(processing);
			new ShapedBuilder<>()
					.name("charge_o_mat")
					.pattern("ETE")
					.pattern("COC")
					.pattern("EAE")
					.define('E', "circuitMaster")
					.define('T', "energyCrystal")
					.define('C', "chest")
					.define('O', new ItemStack(TRItems.lapotronicorb))
					.define('A', "machineBlockAdvanced")
					.withResult(new ItemStack(TRBlocks.charge_o_mat))
					.save(processing);
			new ShapedBuilder<>()
					.name("interdimensional_su")
					.pattern("PAP")
					.pattern("ACA")
					.pattern("PAP")
					.define('P', "plateIridiumAlloy")
					.define('C', "chestEnder")
					.define('A', new ItemStack(TRBlocks.adjustable_su))
					.withResult(new ItemStack(TRBlocks.interdimensional_su))
					.save(processing);
			new ShapedBuilder<>()
					.name("adjustable_su")
					.pattern("LLL")
					.pattern("LCL")
					.pattern("LLL")
					.define('L', new ItemStack(TRItems.lapotronicorb))
					.define('C', "energyCrystal")
					.withResult(new ItemStack(TRBlocks.adjustable_su))
					.save(processing);
			new ShapedBuilder<>()
					.name("lapotronic_su")
					.pattern(" L ")
					.pattern("CBC")
					.pattern(" M ")
					.define('L', IC2Duplicates.LVT.getIngredient())
					.define('C', "circuitAdvanced")
					.define('M', IC2Duplicates.MVT.getIngredient())
					.define('B', new ItemStack(TRBlocks.lsu_storage))
					.withResult(new ItemStack(TRBlocks.lapotronic_su))
					.save(processing);
			new ShapedBuilder<>()
					.name("lsu_storage")
					.pattern("LLL")
					.pattern("LCL")
					.pattern("LLL")
					.define('L', "blockLapis")
					.define('C', "circuitBasic")
					.withResult(new ItemStack(TRBlocks.lsu_storage))
					.save(processing);
			new ShapedBuilder<>()
					.name("scrapboxinator")
					.pattern("ICI")
					.pattern("DSD")
					.pattern("ICI")
					.define('S', new ItemStack(TRItems.scrapbox))
					.define('C', "circuitBasic")
					.define('I', "plateIron")
					.define('D', "dirt")
					.withResult(new ItemStack(TRBlocks.scrapboxinator))
					.save(processing);
			new ShapedBuilder<>()
					.name("fusion_control_computer")
					.pattern("CCC")
					.pattern("PTP")
					.pattern("CCC")
					.define('P', "energyCrystal")
					.define('T', new ItemStack(TRBlocks.fusion_coil))
					.define('C', "circuitMaster")
					.withResult(new ItemStack(TRBlocks.fusion_control_computer))
					.save(processing);
			new ShapedBuilder<>()
					.name("fusion_coil")
					.pattern("CSC")
					.pattern("NAN")
					.pattern("CRC")
					.define('A', new ItemStack(TRBlocks.machine_casing, 1, BlockMachineCasing.Casing.ADVANCED.meta()))
					.define('N', new ItemStack(TRItems.part, 1, Part.PartMeta.nichrome_heating_coil.metadata()))
					.define('C', "circuitMaster")
					.define('S', "craftingSuperconductor")
					.define('R', IC2Duplicates.IRIDIUM_NEUTRON_REFLECTOR.getIngredient())
					.withResult(new ItemStack(TRBlocks.fusion_coil))
					.save(processing);
			new ShapedBuilder<>()
					.name("digital_chest_aluminum")
					.pattern("PPP")
					.pattern("PDP")
					.pattern("PCP")
					.define('P', "plateAluminum")
					.define('D', new ItemStack(TRItems.part, 1, Part.PartMeta.data_orb.metadata()))
					.define('C', new ItemStack(TRItems.part, 1, Part.PartMeta.computer_monitor.metadata()))
					.withResult(new ItemStack(TRBlocks.digital_chest))
					.save(processing);
			new ShapedBuilder<>()
					.name("digital_chest_steel")
					.pattern("PPP")
					.pattern("PDP")
					.pattern("PCP")
					.define('P', "plateSteel")
					.define('D', new ItemStack(TRItems.part, 1, Part.PartMeta.data_orb.metadata()))
					.define('C', new ItemStack(TRItems.part, 1, Part.PartMeta.computer_monitor.metadata()))
					.withResult(new ItemStack(TRBlocks.digital_chest))
					.save(processing);
			new ShapedBuilder<>()
					.name("matter_fabricator")
					.pattern("ETE")
					.pattern("AOA")
					.pattern("ETE")
					.define('E', "circuitMaster")
					.define('T', IC2Duplicates.EXTRACTOR.getIngredient())
					.define('A', "machineBlockElite")
					.define('O', new ItemStack(TRItems.lapotronicorb))
					.withResult(new ItemStack(TRBlocks.matter_fabricator))
					.save(processing);
			new ShapedBuilder<>()
					.name("computer_cube")
					.pattern("OMC")
					.pattern("MFM")
					.pattern("CMO")
					.define('O', new ItemStack(TRItems.part, 1, Part.PartMeta.data_orb.metadata()))
					.define('M', new ItemStack(TRItems.part, 1, Part.PartMeta.computer_monitor.metadata()))
					.define('C', "circuitMaster")
					.define('F', "machineBlockAdvanced")
					.withResult(new ItemStack(TRBlocks.computercube))
					.save(processing);
			new ShapedBuilder<>()
					.name("player_detector")
					.pattern(" D ")
					.pattern("CFC")
					.pattern(" D ")
					.define('D', "circuitStorage")
					.define('C', "circuitAdvanced")
					.define('F', new ItemStack(TRBlocks.computercube))
					.withResult(new ItemStack(TRBlocks.player_detector, 1, OreDictionary.WILDCARD_VALUE))
					.save(processing);
			new ShapedBuilder<>()
					.name("dragon_egg_syphon")
					.pattern("CTC")
					.pattern("PSP")
					.pattern("CBC")
					.define('C', "circuitMaster")
					.define('T', IC2Duplicates.MFE.getIngredient())
					.define('P', "plateIridiumAlloy")
					.define('S', "craftingSuperconductor")
					.define('B', new ItemStack(TRItems.lapotronicorb))
					.withResult(new ItemStack(TRBlocks.dragon_egg_syphon))
					.save(processing);
			new ShapedBuilder<>()
					.name("plasma_generator")
					.pattern("PPP")
					.pattern("PTP")
					.pattern("CGC")
					.define('P', "plateTungstensteel")
					.define('T', IC2Duplicates.HVT.getIngredient())
					.define('C', "circuitMaster")
					.define('G', IC2Duplicates.GENERATOR.getIngredient())
					.withResult(new ItemStack(TRBlocks.plasma_generator))
					.save(processing);
			new ShapedBuilder<>()
					.name("canning_machine")
					.pattern("TCT")
					.pattern("TBT")
					.pattern("TTT")
					.define('T', "ingotTin")
					.define('C', "circuitBasic")
					.define('B', "machineBlockBasic")
					.withResult(new ItemStack(TRBlocks.solid_canning_machine))
					.save(processing);
			new ShapedBuilder<>()
					.name("fluid_replicator")
					.pattern("PCP")
					.pattern("CFC")
					.pattern("ESR")
					.define('P', "plateTungstensteel")
					.define('C', "circuitMaster")
					.define('F', "machineBlockElite")
					.define('E', new ItemStack(TRBlocks.industrial_electrolyzer))
					.define('R', new ItemStack(TRBlocks.chemical_reactor))
					.define('S', "craftingSuperconductor")
					.withResult(new ItemStack(TRBlocks.fluid_replicator))
					.save(processing);
		}
		{
			File transformers = new File(file, "transformers");
			new ShapedBuilder<>()
					.name("ev_transformer")
					.pattern(" H ")
					.pattern("CML")
					.pattern(" H ")
					.define('H', IC2Duplicates.CABLE_IHV.getIngredient())
					.define('C', "circuitAdvanced")
					.define('M', IC2Duplicates.HVT.getIngredient())
					.define('L', IC2Duplicates.ENERGY_CRYSTAL.getIngredient())
					.withResult(new ItemStack(TRBlocks.ev_transformer))
					.save(transformers);
			new ReplaceableShapedBuilder<>()
					.name("lv_transformer")
					.withCondition(IC2Condition.DeduplicateCondition())
					.pattern("PWP")
					.pattern("CCC")
					.pattern("PPP")
					.define('P', "plankWood")
					.define('W', IC2Duplicates.CABLE_TIN.getIngredient())
					.define('C', "ingotCopper")
					.withOutput(IC2Duplicates.LVT.getIngredient())
					.save(transformers);
			new ReplaceableShapedBuilder<>()
					.name("mv_transformer")
					.withCondition(IC2Condition.DeduplicateCondition())
					.pattern("G")
					.pattern("M")
					.pattern("G")
					.define('G', IC2Duplicates.CABLE_ICOPPER.getIngredient())
					.define('M', "machineBlockBasic")
					.withOutput(IC2Duplicates.MVT.getIngredient())
					.save(transformers);
			new ReplaceableShapedBuilder<>()
					.name("hv_transformer")
					.withCondition(IC2Condition.DeduplicateCondition())
					.pattern(" H ")
					.pattern("CML")
					.pattern(" H ")
					.define('H', IC2Duplicates.CABLE_GOLD.getIngredient())
					.define('C', "circuitBasic")
					.define('M', IC2Duplicates.MVT.getIngredient())
					.define('L', new ItemStack(TRItems.lithiumbattery))
					.withOutput(IC2Duplicates.HVT.getIngredient())
					.save(transformers);
			new ReplaceableShapedBuilder<>()
					.name("batbox")
					.withCondition(IC2Condition.DeduplicateCondition())
					.pattern("WCW")
					.pattern("BBB")
					.pattern("WWW")
					.define('W', "plankWood")
					.define('C', IC2Duplicates.CABLE_ICOPPER.getIngredient())
					.define('B', "reBattery")
					.withOutput(IC2Duplicates.BAT_BOX.getIngredient())
					.save(transformers);
			new ReplaceableShapedBuilder<>()
					.name("mfe")
					.withCondition(IC2Condition.DeduplicateCondition())
					.pattern("GEG")
					.pattern("EME")
					.pattern("GEG")
					.define('G', IC2Duplicates.CABLE_IGOLD.getIngredient())
					.define('E', "energyCrystal")
					.define('M', "machineBlockBasic")
					.withOutput(IC2Duplicates.MFE.getIngredient())
					.save(transformers);
			new ReplaceableShapedBuilder<>()
					.name("mfsu")
					.withCondition(IC2Condition.DeduplicateCondition())
					.pattern("LAL")
					.pattern("LML")
					.pattern("LOL")
					.define('L', "lapotronCrystal")
					.define('A', "circuitAdvanced")
					.define('M', IC2Duplicates.MFE.getIngredient())
					.define('O', "machineBlockAdvanced")
					.withOutput(IC2Duplicates.MFSU.getIngredient())
					.save(transformers);
		}
	}

	@SuppressWarnings("ConstantConditions")
	private static void upgradeRecipes(File file) {
		File location = new File(file, "upgrades");
		new ShapedBuilder<>()
				.name("energy_storage")
				.pattern("PPP")
				.pattern("WBW")
				.pattern("PCP")
				.define('P', "plankWood")
				.define('W', IC2Duplicates.CABLE_ICOPPER.getIngredient())
				.define('B', "reBattery")
				.define('C', "circuitBasic")
				.withResult(new ItemStack(TRItems.upgrades, 1, ItemUpgrade.UpgradeEnum.ENERGY_STORAGE.metadata()))
				.save(location);
		new ShapedBuilder<>()
				.name("overclock")
				.pattern("TTT")
				.pattern("WCW")
				.define('T', new ItemStack(TRItems.part, 1, Part.PartMeta.coolant_simple.metadata()))
				.define('W', IC2Duplicates.CABLE_ICOPPER.getIngredient())
				.define('C', "circuitBasic")
				.withResult(new ItemStack(TRItems.upgrades, 1, ItemUpgrade.UpgradeEnum.OVERCLOCK.metadata()))
				.save(location);
		new ShapedBuilder<>()
				.name("overclock_helium")
				.pattern("TTT")
				.pattern("WCW")
				.define('T', new ItemStack(TRItems.part, 1, Part.PartMeta.helium_coolant_simple.metadata()))
				.define('W', IC2Duplicates.CABLE_ICOPPER.getIngredient())
				.define('C', "circuitBasic")
				.withResult(new ItemStack(TRItems.upgrades, 2, ItemUpgrade.UpgradeEnum.OVERCLOCK.metadata()))
				.save(location);
		new ShapedBuilder<>()
				.name("overclock_nak")
				.pattern("TTT")
				.pattern("WCW")
				.define('T', new ItemStack(TRItems.part, 1, Part.PartMeta.nak_coolant_simple.metadata()))
				.define('W', IC2Duplicates.CABLE_ICOPPER.getIngredient())
				.define('C', "circuitBasic")
				.withResult(new ItemStack(TRItems.upgrades, 2, ItemUpgrade.UpgradeEnum.OVERCLOCK.metadata()))
				.save(location);
		new ShapedBuilder<>()
				.name("transformer")
				.pattern("GGG")
				.pattern("WTW")
				.pattern("GCG")
				.define('G', "blockGlass")
				.define('W', IC2Duplicates.CABLE_IGOLD.getIngredient())
				.define('T', IC2Duplicates.MVT.getIngredient())
				.define('C', "circuitBasic")
				.withResult(new ItemStack(TRItems.upgrades, 1, ItemUpgrade.UpgradeEnum.TRANSFORMER.metadata()))
				.save(location);
		new ShapedBuilder<>()
				.name("superconductor")
				.pattern("SOS")
				.pattern("CMC")
				.pattern("SOS")
				.define('S', new ItemStack(TRItems.part, 1, Part.PartMeta.enhanced_super_conductor.metadata()))
				.define('O', new ItemStack(TRItems.part, 1, Part.PartMeta.data_orb.metadata()))
				.define('C', new ItemStack(TRBlocks.cable, 1, BlockCable.CableEnum.SUPERCONDUCTOR.metadata()))
				.define('M', new ItemStack(TRBlocks.machine_frame, 1, BlockMachineFrame.Frame.HIGHLY_ADVANCED.meta()))
				.withResult(new ItemStack(TRItems.upgrades, 1, ItemUpgrade.UpgradeEnum.SUPERCONDUCTOR.metadata()))
				.save(location);
	}

	@SuppressWarnings("ConstantConditions")
	private static void uuMatter(File file) {
		File uu = new File(file, "uumatter");
		ItemStack uuMatter = new ItemStack(TRItems.uumatter);
		new ShapedBuilder<>()
				.name("logs")
				.pattern(" U ")
				.pattern("   ")
				.pattern("   ")
				.define('U', uuMatter)
				.withResult(new ItemStack(Blocks.LOG, 8))
				.save(uu);
		new ShapedBuilder<>()
				.name("stone")
				.pattern("   ")
				.pattern(" U ")
				.pattern("   ")
				.define('U', uuMatter)
				.withResult(new ItemStack(Blocks.STONE, 16))
				.save(uu);
		new ShapedBuilder<>()
				.name("snow")
				.pattern("U U")
				.pattern("   ")
				.pattern("   ")
				.define('U', uuMatter)
				.withResult(new ItemStack(Blocks.SNOW, 16))
				.save(uu);
		new ShapedBuilder<>()
				.name("grass")
				.pattern("   ")
				.pattern("U  ")
				.pattern("U  ")
				.define('U', uuMatter)
				.withResult(new ItemStack(Blocks.GRASS, 16))
				.save(uu);
		new ShapedBuilder<>()
				.name("obsidian")
				.pattern("U U")
				.pattern("U U")
				.pattern("   ")
				.define('U', uuMatter)
				.withResult(new ItemStack(Blocks.OBSIDIAN, 12))
				.save(uu);
		new ShapedBuilder<>()
				.name("glass")
				.pattern(" U ")
				.pattern("U U")
				.pattern(" U ")
				.define('U', uuMatter)
				.withResult(new ItemStack(Blocks.GLASS, 32))
				.save(uu);
		new ShapedBuilder<>()
				.name("dye")
				.pattern("UU ")
				.pattern("  U")
				.pattern("UU ")
				.define('U', uuMatter)
				.withResult(new ItemStack(Items.DYE, 32, 3))
				.save(uu);
		new ShapedBuilder<>()
				.name("glowstone")
				.pattern(" U ")
				.pattern("U U")
				.pattern("UUU")
				.define('U', uuMatter)
				.withResult(new ItemStack(Blocks.GLOWSTONE, 8))
				.save(uu);
		new ShapedBuilder<>()
				.name("cactus")
				.pattern(" U ")
				.pattern("UUU")
				.pattern("U U")
				.define('U', uuMatter)
				.withResult(new ItemStack(Blocks.CACTUS, 48))
				.save(uu);
		new ShapedBuilder<>()
				.name("sugarcane")
				.pattern("U U")
				.pattern("U U")
				.pattern("U U")
				.define('U', uuMatter)
				.withResult(new ItemStack(Items.REEDS, 48))
				.save(uu);
		new ShapedBuilder<>()
				.name("vines")
				.pattern("U  ")
				.pattern("U  ")
				.pattern("U  ")
				.define('U', uuMatter)
				.withResult(new ItemStack(Blocks.VINE, 24))
				.save(uu);
		new ShapedBuilder<>()
				.name("snowball")
				.pattern("   ")
				.pattern("   ")
				.pattern("UUU")
				.define('U', uuMatter)
				.withResult(new ItemStack(Items.SNOWBALL, 16))
				.save(uu);
		new ShapedBuilder<>()
				.name("clay_ball")
				.pattern("UU ")
				.pattern("U  ")
				.pattern("UU ")
				.define('U', uuMatter)
				.withResult(new ItemStack(Items.CLAY_BALL, 48))
				.save(uu);
		new ShapedBuilder<>()
				.name("water_lily")
				.pattern("U U")
				.pattern(" U ")
				.pattern(" U ")
				.define('U', uuMatter)
				.withResult(new ItemStack(Blocks.WATERLILY, 64))
				.save(uu);
		new ShapedBuilder<>()
				.name("gunpowder")
				.pattern("UUU")
				.pattern("U  ")
				.pattern("UUU")
				.define('U', uuMatter)
				.withResult(new ItemStack(Items.GUNPOWDER, 15))
				.save(uu);
		new ShapedBuilder<>()
				.name("bone")
				.pattern("U  ")
				.pattern("UU ")
				.pattern("U  ")
				.define('U', uuMatter)
				.withResult(new ItemStack(Items.BONE, 32))
				.save(uu);
		new ShapedBuilder<>()
				.name("feather")
				.pattern(" U ")
				.pattern(" U ")
				.pattern("U U")
				.define('U', uuMatter)
				.withResult(new ItemStack(Items.FEATHER, 32))
				.save(uu);
		new ShapedBuilder<>()
				.name("dye")
				.pattern(" UU")
				.pattern(" UU")
				.pattern(" U ")
				.define('U', uuMatter)
				.withResult(new ItemStack(Items.DYE, 48))
				.save(uu);
		new ShapedBuilder<>()
				.name("ender_pearl")
				.pattern("UUU")
				.pattern("U U")
				.pattern(" U ")
				.define('U', uuMatter)
				.withResult(new ItemStack(Items.ENDER_PEARL, 1))
				.save(uu);
		new ShapedBuilder<>()
				.name("coal")
				.pattern("  U")
				.pattern("U  ")
				.pattern("  U")
				.define('U', uuMatter)
				.withResult(new ItemStack(Items.COAL, 5, 0))
				.save(uu);
		new ShapedBuilder<>()
				.name("iron_ore")
				.pattern("U U")
				.pattern(" U ")
				.pattern("U U")
				.define('U', uuMatter)
				.withResult(new ItemStack(Blocks.IRON_ORE, 2))
				.save(uu);
		new ShapedBuilder<>()
				.name("gold_ore")
				.pattern(" U ")
				.pattern("UUU")
				.pattern(" U ")
				.define('U', uuMatter)
				.withResult(new ItemStack(Blocks.GOLD_ORE, 2))
				.save(uu);
		new ShapedBuilder<>()
				.name("redstone")
				.pattern("   ")
				.pattern(" U ")
				.pattern("UUU")
				.define('U', uuMatter)
				.withResult(new ItemStack(Items.REDSTONE, 24))
				.save(uu);
		new ShapedBuilder<>()
				.name("lapis")
				.pattern(" U ")
				.pattern(" U ")
				.pattern(" UU")
				.define('U', uuMatter)
				.withResult(new ItemStack(Items.DYE, 9, 4))
				.save(uu);
		new ShapedBuilder<>()
				.name("emerald_ore")
				.pattern("UU ")
				.pattern("U U")
				.pattern(" UU")
				.define('U', uuMatter)
				.withResult(new ItemStack(Blocks.EMERALD_ORE, 1))
				.save(uu);
		new ShapedBuilder<>()
				.name("emerald")
				.pattern("UUU")
				.pattern("UUU")
				.pattern(" U ")
				.define('U', uuMatter)
				.withResult(new ItemStack(Items.EMERALD, 2))
				.save(uu);
		new ShapedBuilder<>()
				.name("diamond")
				.pattern("UUU")
				.pattern("UUU")
				.pattern("UUU")
				.define('U', uuMatter)
				.withResult(new ItemStack(Items.DIAMOND, 1))
				.save(uu);
		new ShapedBuilder<>()
				.name("tin_dust")
				.pattern("   ")
				.pattern("U U")
				.pattern("  U")
				.define('U', uuMatter)
				.withResult(new ItemStack(TRItems.dust, 10, Dust.MetaDust.tin.metadata()))
				.save(uu);
		new ShapedBuilder<>()
				.name("copper_dust")
				.pattern("  U")
				.pattern("U U")
				.pattern("   ")
				.define('U', uuMatter)
				.withResult(new ItemStack(TRItems.dust, 10, Dust.MetaDust.copper.metadata()))
				.save(uu);
		new ShapedBuilder<>()
				.name("lead_dust")
				.pattern("UUU")
				.pattern("UUU")
				.pattern("U  ")
				.define('U', uuMatter)
				.withResult(new ItemStack(TRItems.dust, 14, Dust.MetaDust.lead.metadata()))
				.save(uu);
		new ShapedBuilder<>()
				.name("platinum_dust")
				.pattern(" U ")
				.pattern("UUU")
				.pattern("UUU")
				.define('U', uuMatter)
				.withResult(new ItemStack(TRItems.dust, 1, Dust.MetaDust.platinum.metadata()))
				.save(uu);
		new ShapedBuilder<>()
				.name("tungsten_dust")
				.pattern("U  ")
				.pattern("UUU")
				.pattern("UUU")
				.define('U', uuMatter)
				.withResult(new ItemStack(TRItems.dust, 1, Dust.MetaDust.tungsten.metadata()))
				.save(uu);
		new ShapedBuilder<>()
				.name("titanium_dust")
				.pattern("UUU")
				.pattern(" U ")
				.pattern(" U ")
				.define('U', uuMatter)
				.withResult(new ItemStack(TRItems.dust, 2, Dust.MetaDust.titanium.metadata()))
				.save(uu);
		new ShapedBuilder<>()
				.name("aluminum_dust")
				.pattern(" U ")
				.pattern(" U ")
				.pattern("UUU")
				.define('U', uuMatter)
				.withResult(new ItemStack(TRItems.dust, 16, Dust.MetaDust.aluminum.metadata()))
				.save(uu);
		new ShapedBuilder<>()
				.name("iridium_ore")
				.pattern("UUU")
				.pattern(" U ")
				.pattern("UUU")
				.define('U', uuMatter)
				.withResult(new ItemStack(TRBlocks.ore, 1, OreBlock.Ore.IRIDIUM.meta()))
				.save(uu);
	}

	@SuppressWarnings("ConstantConditions")
	private static void compression(File file) {
		for (BlockStorage.Storage e: BlockStorage.Storage.values()) {
			if (OreHandler.hasOre("gem", e.getName())) {
				cube("gem", e.getName(), "_block", new ItemStack(TRBlocks.storage, 1, e.meta()), new File(file, "block"));
			}
			if (OreHandler.hasOre("ingot", e.getName())) {
				cube("ingot", e.getName(), "_block", new ItemStack(TRBlocks.storage, 1, e.meta()), new File(file, "block"));
			}
		}
		for (BlockStorage2.Storage2 e: BlockStorage2.Storage2.values()) {
			if (OreHandler.hasOre("gem", e.getName())) {
				cube("gem", e.getName(), "_block", new ItemStack(TRBlocks.storage2, 1, e.meta()), new File(file, "block"));
			}
			if (OreHandler.hasOre("ingot", e.getName())) {
				cube("ingot", e.getName(), "_block", new ItemStack(TRBlocks.storage2, 1, e.meta()), new File(file, "block"));
			}
		}
		for (Ingot.IngotMeta e: Ingot.IngotMeta.values()) {
			if (OreHandler.hasOre("nugget", e.getName())) {
				cube("nugget", e.getName(), "_ingotn", new ItemStack(TRItems.ingot, 1, e.metadata()), new File(file, "ingot"));
			}
		}
		cube("nugget", "iron", "_ingotn", new ItemStack(Items.IRON_INGOT), new File(file, "ingot"));
		cube("nugget", "diamond", "_gemn", new ItemStack(Items.DIAMOND), new File(file, "gem"));
		for (Dust.MetaDust e: Dust.MetaDust.values()) {
			if (OreHandler.hasOre("dustSmall", e.getName())) {
				smallcube("dustSmall", e.getName(), "_dust", new ItemStack(TRItems.dust, 1, e.metadata()), new File(file, "dust"));
			}
		}
		smallcube("dustSmall", "glowstone", "_dust", new ItemStack(Items.GLOWSTONE_DUST), new File(file, "dust"));
		smallcube("dustSmall", "redstone", "_dust", new ItemStack(Items.REDSTONE), new File(file, "dust"));
	}

	@SuppressWarnings("ConstantConditions")
	private static void expansion(File file) {
		for (Ingot.IngotMeta e: Ingot.IngotMeta.values()) {
			if (OreHandler.hasOre("block", e.getName())) {
				one_to_x("block", e.getName(), "_ingotb", new ItemStack(TRItems.ingot, 9, e.metadata()), new File(file, "ingot"));
			}
		}
		new ShapelessBuilder<>()
				.name("refined_iron_ingotb")
				.requires(new ItemStack(TRBlocks.storage2, 1, BlockStorage2.Storage2.REFINED_IRON.meta()))
				.withResult(new ItemStack(TRItems.ingot, 9, Ingot.IngotMeta.refined_iron.metadata()))
				.save(new File(file, "ingot"));
		for (Nugget.NuggetMeta e: Nugget.NuggetMeta.values()) {
			if (OreHandler.hasOre("ingot", e.getName())) {
				one_to_x("ingot", e.getName(), "_nugget", new ItemStack(TRItems.nuggets, 9, e.metadata()), new File(file, "nugget"));
			}
		}
		one_to_x("gem", "diamond", "_nugget", new ItemStack(TRItems.nuggets, 9, Nugget.NuggetMeta.diamond.metadata()), new File(file, "nugget"));
		for (Gem.GemMeta e: Gem.GemMeta.values()) {
			if (OreHandler.hasOre("block", e.getName())) {
				one_to_x("block", e.getName(), "_gemb", new ItemStack(TRItems.gem, 9, e.metadata()), new File(file, "gem"));
			}
		}
		for (DustSmall.DustSmallMeta e: DustSmall.DustSmallMeta.values()) {
			if (OreHandler.hasOre("dust", e.name())) {
				one_to_x("dust", e.getName(), "_smalldust", new ItemStack(TRItems.smalldust, 4, e.metadata()), new File(file, "smalldust"));
			}
		}
	}

	private static void one_to_x(String type, String name, String fileName, ItemStack out, File recipeDir) {
		new ShapelessBuilder<>()
				.name(name + fileName)
				.requires(OreHandler.toOre(type, name))
				.withResult(out)
				.save(recipeDir);
	}

	private static void smallcube(String type, String name, String fileName, ItemStack out, File recipeDir) {
		new ShapedBuilder<>()
				.name(name + fileName)
				.define('I', OreHandler.toOre(type, name))
				.pattern("II")
				.pattern("II")
				.withResult(out)
				.save(recipeDir);
	}

	private static void cube(String type, String name, String fileName, ItemStack out, File recipeDir) {
		new ShapedBuilder<>()
				.name(name + fileName)
				.define('I', OreHandler.toOre(type, name))
				.pattern("III")
				.pattern("III")
				.pattern("III")
				.withResult(out)
				.save(recipeDir);
	}

	public static void alloyRecipes(File file) {
		File location = new File(file, "alloy");
		IBasicIngredient refinedIron = new OreDictIngredient("ingotRefinedIron", 1);
		IBasicIngredient nickel = new OreDictIngredient("ingotNickel", 1);
		IBasicIngredient invar = new OreDictIngredient("ingotInvar", 1);
		IBasicIngredient titanium = new OreDictIngredient("ingotTitanium", 1);
		IBasicIngredient tungsten = new OreDictIngredient("ingotTungsten", 1);
		IBasicIngredient tungstensteel = new OreDictIngredient("ingotTungstensteel", 1);
		IBasicIngredient bronze = new OreDictIngredient("ingotBronze", 1);
		IBasicIngredient brass = new OreDictIngredient("ingotBrass", 1);
		IBasicIngredient tin = new OreDictIngredient("ingotTin", 1);
		IBasicIngredient zinc = new OreDictIngredient("ingotZinc", 1);
		IBasicIngredient aluminum = new ListIngredient()
				.addIngredient(new OreDictIngredient("ingotAluminum", 1))
				.addIngredient(new OreDictIngredient("ingotAluminium", 1));
		ReplaceableIngredient metal = IC2Duplicates.MIXED_METAL.getIngredient();
		layered(location, "alloy_rbot", refinedIron, bronze, tin, metal.count(2));
		layered(location, "alloy_rboz", refinedIron, bronze, zinc, metal);
		layered(location, "alloy_rbat", refinedIron, brass, tin, metal);
		layered(location, "alloy_rbaz", refinedIron, brass, zinc, metal);
		layered(location, "alloy_nbot", nickel, bronze, tin, metal.count(3));
		layered(location, "alloy_nboz", nickel, bronze, zinc, metal);
		layered(location, "alloy_nbat", nickel, brass, tin, metal);
		layered(location, "alloy_nbaz", nickel, brass, zinc, metal);
		layered(location, "alloy_nboa", nickel, bronze, aluminum, metal.count(4));
		layered(location, "alloy_nbaa", nickel, brass, aluminum, metal);
		layered(location, "alloy_ibot", invar, bronze, tin, metal.count(4));
		layered(location, "alloy_iboz", invar, bronze, zinc, metal);
		layered(location, "alloy_ibat", invar, brass, tin, metal);
		layered(location, "alloy_ibaz", invar, brass, zinc, metal);
		layered(location, "alloy_iboa", invar, bronze, aluminum, metal.count(5));
		layered(location, "alloy_ibaa", invar, brass, aluminum, metal);
		layered(location, "alloy_tbot", titanium, bronze, tin, metal.count(5));
		layered(location, "alloy_tboz", titanium, bronze, zinc, metal);
		layered(location, "alloy_tbat", titanium, brass, tin, metal);
		layered(location, "alloy_tbaz", titanium, brass, zinc, metal);
		layered(location, "alloy_wbot", tungsten, bronze, tin, metal);
		layered(location, "alloy_wboz", tungsten, bronze, zinc, metal);
		layered(location, "alloy_wbat", tungsten, brass, tin, metal);
		layered(location, "alloy_wbaz", tungsten, brass, zinc, metal);
		layered(location, "alloy_iboa", titanium, bronze, aluminum, metal.count(6));
		layered(location, "alloy_ibaa", titanium, brass, aluminum, metal);
		layered(location, "alloy_wboa", tungsten, bronze, aluminum, metal);
		layered(location, "alloy_wbaa", tungsten, brass, aluminum, metal);
		layered(location, "alloy_tsbot", tungstensteel, bronze, tin, metal.count(8));
		layered(location, "alloy_tsboz", tungstensteel, bronze, zinc, metal);
		layered(location, "alloy_tsbat", tungstensteel, brass, tin, metal);
		layered(location, "alloy_tsbaz", tungstensteel, brass, zinc, metal);
		layered(location, "alloy_tsboa", tungstensteel, bronze, aluminum, metal.count(9));
		layered(location, "alloy_tsbaa", tungstensteel, brass, aluminum, metal);
	}

	private static void layered(File file, String name, IBasicIngredient top, IBasicIngredient middle, IBasicIngredient bottom, IBasicIngredient output) {
		new ReplaceableShapedBuilder<>()
				.name(name)
				.withCondition(new IC2Condition("dedupe"))
				.pattern("TTT")
				.pattern("MMM")
				.pattern("BBB")
				.define('T', top)
				.define('M', middle)
				.define('B', bottom)
				.withOutput(output)
				.save(file);
	}
}
