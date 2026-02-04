package com.sabrepenguin.techreborn.util;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.itemblock.IMetaMaterial;
import com.sabrepenguin.techreborn.items.materials.MaterialItem;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Tags.MODID)
public class ModelRegistryHandler {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Item item: TRItems.getAllItems()) {
			registerItemModels(item);
        }
		registerBlockModels();
//        for (Block block: TRBlocks.getAllBlocks()) {
//            registerBlockstateModels(block);
//        }
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
	}

	private static void registerBlockstateModels(Block block) {
		Item item = Item.getItemFromBlock(block);
		ResourceLocation customLocation = ModelRegistryUtils.getResourceLocation(block);
		if (block instanceof IMetaMaterial meta) {
			if (block instanceof INonStandardLocation property && property.getProperties().length > 0) {
				ModelLoader.setCustomStateMapper(
						block, ModelRegistryUtils.createMapper(customLocation, property.getIgnoredProperties())
				);
			}
			for (Pair<String, Integer> pair: meta.getMeta()) {
				ModelLoader.setCustomModelResourceLocation(
						item, pair.getRight(), new ModelResourceLocation(customLocation, "type=" + pair.getLeft())
				);
			}
			return;
		} else if (block instanceof INonStandardLocation location && location.getProperties().length > 0) {
			ModelLoader.setCustomStateMapper(
					block,
					ModelRegistryUtils.createMapper(customLocation, location.getIgnoredProperties())
			);
			ModelLoader.setCustomModelResourceLocation(
					item,
					0,
					new ModelResourceLocation(customLocation, "inventory")
			);
			return;
		}
		ModelLoader.setCustomModelResourceLocation(item,
				0,
				new ModelResourceLocation(customLocation, "inventory"));
	}

	private static void propertiesMetaRegistration(Block block) {
		if (!(block instanceof IMetaMaterial metaMaterial) || !(block instanceof INonStandardLocation property)) return;
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
