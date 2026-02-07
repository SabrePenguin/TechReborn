package com.sabrepenguin.techreborn.util;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.itemblock.IMetaInformation;
import com.sabrepenguin.techreborn.items.materials.MaterialItem;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.*;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Tags.MODID)
public class ModelRegistryHandler {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Item item: TRItems.getAllItems()) {
			registerItemModels(item);
        }
		registerBlockModels();
    }

	private static void registerBlockModels() {
		propertiesMetaRegistration(TRBlocks.storage);
		propertiesMetaRegistration(TRBlocks.storage2);
		propertiesMetaRegistration(TRBlocks.ore);
		propertiesMetaRegistration(TRBlocks.ore2);
		propertiesMetaRegistration(TRBlocks.machine_casing);
		propertiesMetaRegistration(TRBlocks.machine_frame);

		propertiesRegistration(TRBlocks.iron_furnace);
		propertiesRegistration(TRBlocks.iron_alloy_furnace);
		propertiesRegistration(TRBlocks.computercube);
		propertiesRegistration(TRBlocks.alarm);
		propertiesRegistration(TRBlocks.fusion_coil);
		propertiesRegistration(TRBlocks.fusion_control_computer);
		propertiesRegistration(TRBlocks.nuke);
		propertiesRegistration(TRBlocks.lv_transformer);
		propertiesRegistration(TRBlocks.mv_transformer);
		propertiesRegistration(TRBlocks.hv_transformer);
		propertiesRegistration(TRBlocks.ev_transformer);
		propertiesRegistration(TRBlocks.quantum_tank);
		propertiesRegistration(TRBlocks.quantum_chest);
		propertiesRegistration(TRBlocks.creative_quantum_tank);
		propertiesRegistration(TRBlocks.creative_quantum_chest);
		propertiesRegistration(TRBlocks.chunk_loader);
		propertiesRegistration(TRBlocks.fluid_replicator);
		propertiesRegistration(TRBlocks.matter_fabricator);
		propertiesRegistration(TRBlocks.charge_o_mat);
		propertiesRegistration(TRBlocks.digital_chest);
		propertiesRegistration(TRBlocks.chemical_reactor);
		propertiesRegistration(TRBlocks.distillation_tower);
		propertiesRegistration(TRBlocks.implosion_compressor);
		propertiesRegistration(TRBlocks.industrial_blast_furnace);
		propertiesRegistration(TRBlocks.industrial_centrifuge);
		propertiesRegistration(TRBlocks.industrial_electrolyzer);
		propertiesRegistration(TRBlocks.industrial_grinder);
		propertiesRegistration(TRBlocks.industrial_sawmill);
		propertiesRegistration(TRBlocks.vacuum_freezer);

		itemBlockRegistration(TRBlocks.rubber_leaves);
		itemBlockRegistration(TRBlocks.rubber_plank_stair);
		itemBlockRegistration(TRBlocks.rubber_planks);
		itemBlockRegistration(TRBlocks.rubber_log);
		itemBlockRegistration(TRBlocks.rubber_sapling);
		itemBlockRegistration(TRBlocks.rubber_plank_slab);
		itemBlockRegistration(TRBlocks.refined_iron_fence);
		itemBlockRegistration(TRBlocks.reinforced_glass);

		itemBlockRegistration(TRBlocks.auto_crafting_table, true);
		propertiesRegistration(TRBlocks.electric_furnace);
		propertiesMetaRegistration(TRBlocks.player_detector);
		propertiesRegistration(TRBlocks.pump);
		propertiesRegistration(TRBlocks.rolling_machine);
		propertiesRegistration(TRBlocks.scrapboxinator);

		propertiesRegistration(TRBlocks.low_voltage_su);
		propertiesRegistration(TRBlocks.medium_voltage_su);
		propertiesRegistration(TRBlocks.high_voltage_su);
		propertiesRegistration(TRBlocks.adjustable_su);
		propertiesRegistration(TRBlocks.interdimensional_su);
		propertiesRegistration(TRBlocks.lapotronic_su);
		itemBlockRegistration(TRBlocks.lsu_storage, true);

		propertiesRegistration(TRBlocks.alloy_smelter);
		propertiesRegistration(TRBlocks.assembling_machine);
		propertiesRegistration(TRBlocks.compressor);
		propertiesRegistration(TRBlocks.extractor);
		propertiesRegistration(TRBlocks.grinder);
		propertiesRegistration(TRBlocks.plate_bending_machine);
		propertiesRegistration(TRBlocks.recycler);
		propertiesRegistration(TRBlocks.solid_canning_machine);
		propertiesRegistration(TRBlocks.wire_mill);
		propertiesRegistration(TRBlocks.lamp_incandescent);
		propertiesRegistration(TRBlocks.lamp_led);

		propertiesRegistration(TRBlocks.creative_solar_panel);
		propertiesRegistration(TRBlocks.diesel_generator);
		propertiesRegistration(TRBlocks.dragon_egg_syphon);
		propertiesRegistration(TRBlocks.gas_turbine);
		propertiesRegistration(TRBlocks.lightning_rod);
		propertiesRegistration(TRBlocks.magic_energy_absorber);
		propertiesRegistration(TRBlocks.magic_energy_converter);
		propertiesRegistration(TRBlocks.plasma_generator);
		propertiesRegistration(TRBlocks.semi_fluid_generator);
		propertiesRegistration(TRBlocks.solid_fuel_generator);
		propertiesRegistration(TRBlocks.thermal_generator);
		propertiesRegistration(TRBlocks.water_mill);
		propertiesRegistration(TRBlocks.wind_mill);
		dangerousPropertiesMetaRegistration(TRBlocks.solar_panel, false);
	}

	private static void propertiesMetaRegistration(Block block) {
		if (!(block instanceof IMetaInformation metaMaterial) || !(block instanceof INonStandardLocation property)) return;
		Item item = Item.getItemFromBlock(block);
		ResourceLocation customLocation = ModelRegistryUtils.getResourceLocation(block);
		ModelLoader.setCustomStateMapper(
				block, ModelRegistryUtils.createMapper(customLocation, property.getIgnoredProperties())
		);
		for (Pair<String, Integer> pair: metaMaterial.getMeta()) {
			ModelLoader.setCustomModelResourceLocation(
					item, pair.getRight(), new ModelResourceLocation(customLocation, "type=" + pair.getLeft())
			);
		}
	}

	/**
	 * WARNING: DANGEROUS AND POTENTIALLY BAD RESULTS. THIS DOES NOT ATTEMPT TO ENSURE EVERYTHING WORKS, AND EXISTS
	 * ENTIRELY FOR active=false ADDITION TO MY META BASED ITEMBLOCKS.
	 * <br>
	 * This function takes in a list of objects and will attempt to set the default values for an item.
	 * <pre><code>
	 * dangerousPropertiesMetaRegistration(TRBlocks.solar_panel, false, EnumFacing.NORTH);
	 * </code></pre>
	 * Would produce an item that cycles through all properties under the TYPE label
	 * @param block The standard block
	 * @param inputs A set of inputs that should implement a toString that works well. These should be in alphabetical
	 *               order of the expected parameters.
	 */
	private static void dangerousPropertiesMetaRegistration(Block block, Object... inputs) {
		List<Object> objectList = Arrays.asList(inputs);
		if (!(block instanceof IMetaInformation metaMaterial) || !(block instanceof INonStandardLocation property)) return;
		Item item = Item.getItemFromBlock(block);
		ResourceLocation customLocation = ModelRegistryUtils.getResourceLocation(block);
		ModelLoader.setCustomStateMapper(
				block, ModelRegistryUtils.createMapper(customLocation, property.getIgnoredProperties())
		);
		List<IProperty<?>> properties = Arrays.stream(property.getProperties())
				.filter(p -> !p.getName().equals("type"))
				.sorted(Comparator.comparing(IProperty::getName))
				.collect(Collectors.toList());
		if (properties.size() != objectList.size())
			throw new IllegalArgumentException("Block " +
					block.getRegistryName() +
					" dangerous itemblock registration expected " +
					properties.size() +
					" properties (excludes type) but got " +
					objectList.size() + " values instead.");
		TreeMap<String, String> treeMap = new TreeMap<>();
		for (int i = 0; i < properties.size(); i++) {
			Object input = objectList.get(i);
			String value = input instanceof IStringSerializable serializable ? serializable.getName() : String.valueOf(input);
			treeMap.put(properties.get(i).getName(), value);
		}
		for (Pair<String, Integer> pair: metaMaterial.getMeta()) {
			treeMap.put("type", pair.getLeft());
			String variantString = treeMap.entrySet()
					.stream()
					.map(entry -> entry.getKey() + "=" + entry.getValue())
					.collect(Collectors.joining(","));
			ModelLoader.setCustomModelResourceLocation(
					item, pair.getRight(), new ModelResourceLocation(customLocation, variantString)
			);
		}
	}

	private static void propertiesRegistration(Block block) {
		if (!(block instanceof INonStandardLocation property)) return;
		Item item = Item.getItemFromBlock(block);
		ResourceLocation customLocation = ModelRegistryUtils.getResourceLocation(block);
		ModelLoader.setCustomStateMapper(
				block, ModelRegistryUtils.createMapper(customLocation, property.getIgnoredProperties())
		);
		ModelLoader.setCustomModelResourceLocation(
				item, 0, new ModelResourceLocation(customLocation, "inventory")
		);
	}

	private static void itemBlockRegistration(Block block) {
		itemBlockRegistration(block, false);
	}

	private static void itemBlockRegistration(Block block, boolean differentLocation) {
		Item item = Item.getItemFromBlock(block);
		ResourceLocation customLocation = ModelRegistryUtils.getResourceLocation(block);
		if (differentLocation) {
			ModelLoader.setCustomStateMapper(block, ModelRegistryUtils.createEmptyMapper(customLocation));
		}
		ModelLoader.setCustomModelResourceLocation(
				item,
				0,
				new ModelResourceLocation(customLocation, "inventory")
		);
	}

	private static void registerItemModels(Item item) {
		String prefix = "";
		String postfix = "";
		if (item instanceof INonStandardLocation nonStandardLocation) {
			prefix = nonStandardLocation.getPrefix();
			postfix = nonStandardLocation.getPostfix();
		}
		ResourceLocation location = ModelRegistryUtils.fixLocation(item.getRegistryName(), prefix, postfix);

		if (item.getHasSubtypes()) {
			if (item instanceof MaterialItem material) {
				for (Pair<String, Integer> metadata: material.getMeta()) {
					ModelLoader.setCustomModelResourceLocation(
							item,
							metadata.getRight(),
							new ModelResourceLocation(location, "type=" + metadata.getLeft())
					);
				}
				return;
			}
		}
		ModelLoader.setCustomModelResourceLocation(
				item,
				0,
				new ModelResourceLocation(location, "inventory")
		);
	}
}
