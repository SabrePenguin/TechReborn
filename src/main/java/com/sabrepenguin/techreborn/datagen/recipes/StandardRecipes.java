package com.sabrepenguin.techreborn.datagen.recipes;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.blocks.meta.BlockMachineCasing;
import com.sabrepenguin.techreborn.blocks.meta.BlockMachineFrame;
import com.sabrepenguin.techreborn.blocks.meta.BlockStorage;
import com.sabrepenguin.techreborn.blocks.meta.BlockStorage2;
import com.sabrepenguin.techreborn.datagen.builders.ReplaceableShapedBuilder;
import com.sabrepenguin.techreborn.datagen.builders.ShapedBuilder;
import com.sabrepenguin.techreborn.datagen.builders.ShapelessBuilder;
import com.sabrepenguin.techreborn.datagen.builders.ingredients.ItemIngredient;
import com.sabrepenguin.techreborn.datagen.builders.ingredients.ListIngredient;
import com.sabrepenguin.techreborn.datagen.builders.ingredients.OreDictIngredient;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.*;
import com.sabrepenguin.techreborn.util.handlers.OreHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;

public class StandardRecipes {
	private static final String RECIPE_DIR = "src/main/resources/assets/" + Tags.MODID + "/recipes";

	public static void initRecipes(File file) {
		compression(file);
		expansion(file);
		recipes(file);
		gear(file);
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
					.name("compressor")
					.pattern("S S")
					.pattern("SCS")
					.pattern("SMS")
					.define('C', "circuitBasic")
					.define('S', "stone")
					.define('M', "machineBlockBasic")
					.withOutput(IC2Duplicates.COMPRESSOR.getIngredient())
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
		}
	}

	@SuppressWarnings("ConstantConditions")
	private static void gear(File file) {
		File location = new File(file, "gear");
		NBTTagCompound energy = new NBTTagCompound();
		energy.setInteger("energy", 0);
		ItemStack cloak = new ItemStack(TRItems.cloakingdevice);
		cloak.setTagCompound(energy);
		new ShapedBuilder<>()
				.name("cloak")
				.pattern("CIC")
				.pattern("IOI")
				.pattern("CIC")
				.define('C', "ingotChrome")
				.define('I', "plateIridiumAlloy")
				.define('O', "ingotChrome")
				.withResult(cloak)
				.save(location);
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
}
