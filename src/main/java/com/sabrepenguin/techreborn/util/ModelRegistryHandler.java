package com.sabrepenguin.techreborn.util;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.BlockBase;
import com.sabrepenguin.techreborn.blocks.IVariants;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.itemblock.IEnumMeta;
import com.sabrepenguin.techreborn.items.ItemBase;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.MaterialItem;
import com.sabrepenguin.techreborn.util.models.IPropertyBlockstate;
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

import java.util.List;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Tags.MODID)
public class ModelRegistryHandler {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Item item: TRItems.getAllItems()) {
			registerItemModels(item);
        }
        for (Block block: TRBlocks.getAllBlocks()) {
            registerBlockstateModel(block);
        }
    }

    private static void registerBlockstateModel(Block block) {
        Item item = Item.getItemFromBlock(block);
		if (block instanceof IEnumMeta meta) {
			String prefix = "";
			String postfix = "";
			if (block instanceof INonStandardLocation location) {
				prefix = location.getPrefix();
				postfix = location.getPostfix();
			}
			ResourceLocation fixed = ModelRegistryUtils.fixLocation(block.getRegistryName(), prefix, postfix);
			if (block instanceof IPropertyBlockstate property) {
				ModelLoader.setCustomStateMapper(
						block, ModelRegistryUtils.createMapper(fixed, property.getIgnoredProperties()));
			}
			for (Pair<String, Integer> i: meta.getMeta()) {
				ModelLoader.setCustomModelResourceLocation(
						item, i.getRight(), new ModelResourceLocation(fixed, "type=" + i.getLeft()));
			}
			return;
		}
        if (block instanceof BlockBase blockBase) {
            String prefix = "";
            String postfix = "";
            if (blockBase instanceof INonStandardLocation location) {
                prefix = location.getPrefix();
                postfix = location.getPostfix();
                if (location.getProperties().length > 0) {
                    ModelLoader.setCustomStateMapper(
                            block,
                            ModelRegistryUtils.createMapper(
                                    new ResourceLocation(block.getRegistryName().getNamespace(),
                                            prefix + block.getRegistryName().getPath() + postfix),
                                    location.getIgnoredProperties()
                            )
                    );
                    ModelLoader.setCustomModelResourceLocation(
                            item,
                            0,
                            new ModelResourceLocation(
                                    ModelRegistryUtils.fixLocation(block.getRegistryName(), prefix, postfix),
                                    "inventory"
                            )
                    );
                    return;
                }
            }
            if (block instanceof IVariants metaBlock) {
                List<MetadataHelper> types = metaBlock.getListMetadata();

                for (int i = 0; i < types.size(); i++) {
                    ModelLoader.setCustomModelResourceLocation(
                            item,
                            i,
                            new ModelResourceLocation(
                                    new ResourceLocation(block.getRegistryName().getNamespace(),
                                            prefix + block.getRegistryName().getPath() + postfix
                                    ),
                                    "type=" + i
                            )
                    );
                }
            } else {
                ModelLoader.setCustomModelResourceLocation(
                        item,
                        0,
                        new ModelResourceLocation(
                                new ResourceLocation(block.getRegistryName().getNamespace(),
                                        prefix + block.getRegistryName().getPath() + postfix),
                                "normal"
                        )
                );
            }
        }
    }

	private static void registerItemModels(Item item) {
		String prefix = "";
		String postfix = "";
		if (item instanceof INonStandardLocation nonStandardLocation) {
			prefix = nonStandardLocation.getPrefix();
			postfix = nonStandardLocation.getPostfix();
		}
		ResourceLocation location = ModelRegistryUtils.fixLocation(item.getRegistryName(), prefix, postfix);

		if (item instanceof ItemBase baseItem && baseItem.getHasSubtypes()) {
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
