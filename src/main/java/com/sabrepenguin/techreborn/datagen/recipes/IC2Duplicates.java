package com.sabrepenguin.techreborn.datagen.recipes;

import com.sabrepenguin.techreborn.blocks.BlockCable;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.compat.ic2.IC2AbstractHandler;
import com.sabrepenguin.techreborn.datagen.builders.ingredients.ReplaceableIngredient;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.Ingot;
import com.sabrepenguin.techreborn.items.materials.Part;
import com.sabrepenguin.techreborn.items.materials.Plate;
import ic2.api.item.IC2Items;
import net.minecraft.item.ItemStack;

@SuppressWarnings("ConstantConditions")
public enum IC2Duplicates {
	GRINDER(new ItemStack(TRBlocks.grinder), getIC2Item("te", "macerator")),
	ELECTRICAL_FURNACE(new ItemStack(TRBlocks.electric_furnace), getIC2Item("te", "electric_furnace")),
	IRON_FURNACE(new ItemStack(TRBlocks.iron_furnace), getIC2Item("te", "iron_furnace")),
	GENERATOR(new ItemStack(TRBlocks.solid_fuel_generator), getIC2Item("te", "generator")),
	WATER_MILL(new ItemStack(TRBlocks.water_mill), getIC2Item("te", "water_generator")),
	EXTRACTOR(new ItemStack(TRBlocks.extractor), getIC2Item("te", "extractor")),
	RECYCLER(new ItemStack(TRBlocks.recycler), getIC2Item("te", "recycler")),
	COMPRESSOR(new ItemStack(TRBlocks.compressor), getIC2Item("te", "compressor")),
	BAT_BOX(new ItemStack(TRBlocks.low_voltage_su), getIC2Item("te", "batbox")),
	MFE(new ItemStack(TRBlocks.medium_voltage_su), getIC2Item("te", "mfe")),
	MFSU(new ItemStack(TRBlocks.high_voltage_su), getIC2Item("te", "mfsu")),
	LVT(new ItemStack(TRBlocks.lv_transformer), getIC2Item("te", "lv_transformer")),
	MVT(new ItemStack(TRBlocks.mv_transformer), getIC2Item("te", "mv_transformer")),
	HVT(new ItemStack(TRBlocks.hv_transformer), getIC2Item("te", "hv_transformer")),
	CABLE_COPPER(new ItemStack(TRBlocks.cable, 1, BlockCable.CableEnum.COPPER.metadata()), getIC2Item("cable", "type:copper,insulation:0")),
	CABLE_GLASSFIBER(new ItemStack(TRBlocks.cable, 1, BlockCable.CableEnum.GLASSFIBER.metadata()), getIC2Item("cable", "type:glass,insulation:0")),
	CABLE_GOLD(new ItemStack(TRBlocks.cable, 1, BlockCable.CableEnum.GOLD.metadata()), getIC2Item("cable", "type:gold,insulation:0")),
	CABLE_HV(new ItemStack(TRBlocks.cable, 1, BlockCable.CableEnum.HV.metadata()), getIC2Item("cable", "type:iron,insulation:0")),
	CABLE_ICOPPER(new ItemStack(TRBlocks.cable, 1, BlockCable.CableEnum.INSULATEDCOPPER.metadata()), getIC2Item("cable", "type:copper,insulation:1")),
	CABLE_IGOLD(new ItemStack(TRBlocks.cable, 1, BlockCable.CableEnum.INSULATEDGOLD.metadata()), getIC2Item("cable", "type:gold,insulation:1")),
	CABLE_IHV(new ItemStack(TRBlocks.cable, 1, BlockCable.CableEnum.INSULATEDHV.metadata()), getIC2Item("cable", "type:iron,insulation:1")),
	CABLE_IIHV(new ItemStack(TRBlocks.cable, 1, BlockCable.CableEnum.TIN.metadata()), getIC2Item("cable", "type:iron,insulation:2")),
	CABLE_TIN(new ItemStack(TRBlocks.cable, 1, BlockCable.CableEnum.TIN.metadata()), getIC2Item("cable", "type:tin,insulation:0")),
	MIXED_METAL(new ItemStack(TRItems.ingot, 1, Ingot.IngotMeta.mixed_metal.metadata()), getIC2Item("ingot", "alloy")),
	CARBON_FIBER(new ItemStack(TRItems.part, 1, Part.PartMeta.carbon_fiber.metadata()), getIC2Item("crafting", "carbon_fibre")),
	CARBON_MESH(new ItemStack(TRItems.part, 1, Part.PartMeta.carbon_mesh.metadata()), getIC2Item("crafting", "carbon_mesh")),
	NEUTRON_REFLECTOR(new ItemStack(TRItems.part, 1, Part.PartMeta.neutron_reflector.metadata()), getIC2Item("neutron_reflector", null)),
	THICK_NEUTRON_REFLECTOR(new ItemStack(TRItems.part, 1, Part.PartMeta.thick_neutron_reflector.metadata()), getIC2Item("thick_neutron_reflector", null)),
	IRIDIUM_NEUTRON_REFLECTOR(new ItemStack(TRItems.iridium_neutron_reflector), getIC2Item("iridium_reflector")),
	SCRAP(new ItemStack(TRItems.part, 1, Part.PartMeta.scrap.metadata()), getIC2Item("crafting", "scrap")),
	SCRAP_BOX(new ItemStack(TRItems.scrapbox), getIC2Item("crafting", "scrap_box")),
	FREQ_TRANSMITTER(new ItemStack(TRItems.frequencytransmitter), getIC2Item("frequency_transmitter")),
	RUBBER_WOOD(new ItemStack(TRBlocks.rubber_log), getIC2Item("rubber_wood")),
	RE_BATTERY(new ItemStack(TRItems.rebattery), getIC2Item("re_battery")),
	ENERGY_CRYSTAL(new ItemStack(TRItems.energycrystal), getIC2Item("energy_crystal")),
	LAPATRON_CRYSTAL(new ItemStack(TRItems.lapotroncrystal), getIC2Item("lapotron_crystal")),
	CARBON_PLATE(new ItemStack(TRItems.plates, 1, Plate.PlateMeta.carbon.metadata()), getIC2Item("crafting", "carbon_plate")),
	IRON_FENCE(new ItemStack(TRBlocks.refined_iron_fence), IC2Duplicates.getIC2Item("fence", "iron")),

//	REFINED_IRON(new ItemStack(TRItems.ingot, 1, Ingot.IngotMeta.refined_iron.metadata()), true),
//	BASIC_MACHINE_FRAME(new ItemStack(TRBlocks.machine_frame, 1, BlockMachineFrame.Frame.BASIC.meta()), true),
//	ADVANCED_MACHINE_FRAME(new ItemStack(TRBlocks.machine_frame, 1, BlockMachineFrame.Frame.ADVANCED.meta()), true),
//
//	ADVANCED_ALLOY(new ItemStack(TRItems.plates, 1, Plate.PlateMeta.advanced_alloy.metadata()), true)
	;

	final ItemStack ic2Stack;
	final ItemStack trStack;
	final Boolean classic;
	
	IC2Duplicates(ItemStack stack, ItemStack ic2stack) {
		this.trStack = stack;
		this.ic2Stack = ic2stack;
		this.classic = null;
	}

	static ItemStack getIC2ItemAlt(String classic, String exp) {
		if (IC2AbstractHandler.isClassic())
			return getIC2Item(classic);
		else
			return getIC2Item(exp);
	}

	static ItemStack getIC2Item(String item) {
		return getIC2Item(item, null);
	}

	static ItemStack getIC2Item(String item, String variant) {
		ItemStack result = IC2Items.getItem(item, variant);

		if (result == null || result.isEmpty() || result.getItem().getRegistryName().toString().equals("ic2:itemnouse"))
			throw new RuntimeException("Not a valid item: " + item + ":" + variant);
		return result;
	}

	public ReplaceableIngredient getIngredient() {
		return new ReplaceableIngredient(this.trStack, this.ic2Stack);
	}
}
