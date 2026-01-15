package com.sabrepenguin.techreborn.util;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.BlockBase;
import com.sabrepenguin.techreborn.blocks.IVariants;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.items.ItemBase;
import com.sabrepenguin.techreborn.items.materials.ItemMaterial;
import com.sabrepenguin.techreborn.items.MetadataItem;
import com.sabrepenguin.techreborn.items.TRItems;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Tags.MODID)
public class ModelRegistryHandler {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Item item: TRItems.getAllItems()) {
            registerItemModel(item);
        }
        for (Block block: TRBlocks.getAllBlocks()) {
            registerBlockstateModel(block);
        }
    }

    private static void registerBlockstateModel(Block block) {
        Item item = Item.getItemFromBlock(block);
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

    private static void registerItemModel(Item item) {
        if (item instanceof INonStandardLocation nonStandardItem) {
            ModelLoader.setCustomModelResourceLocation(
                    item,
                    0,
                    new ModelResourceLocation(
                            ModelRegistryUtils.fixLocation(item.getRegistryName(), nonStandardItem.getPrefix(), nonStandardItem.getPostfix()),
                            "inventory")
            );
        }
        else if (item instanceof ItemBase baseItem && item.getHasSubtypes()) {
            String[] names = baseItem.getTypes();
            String prefix = baseItem.getPrefix();
            String postfix = baseItem.getPostfix();
            if (item instanceof ItemMaterial material) {
                for(MetadataItem mitem: material.getItems()) {
                    ModelLoader.setCustomModelResourceLocation(
                            item,
                            mitem.meta(),
                            new ModelResourceLocation(
                                    new ResourceLocation(item.getRegistryName().getNamespace(),
                                            "items/materials/" + material.getBlockstateJson()),
//                                            prefix + mitem.name() + postfix),
//                                    "inventory"
                                    "type=" + mitem.name()
                            )
                    );

                }
                return;
            }
            for(int i = 0; i < names.length; i++) {
                if (names[i].equals(MetadataHelper.META_PLACEHOLDER))
                    continue;
                ModelLoader.setCustomModelResourceLocation(
                        item,
                        i,
                        new ModelResourceLocation(
                                new ResourceLocation(item.getRegistryName().getNamespace(),
                                prefix + names[i] + postfix),
                                "inventory"
                        )
                );
            }
        } else if (item instanceof ItemBase base) {
            ModelLoader.setCustomModelResourceLocation(
                    item,
                    0,
                    new ModelResourceLocation(
                            new ResourceLocation(item.getRegistryName().getNamespace(),
                                    base.getPrefix() + item.getRegistryName().getPath() + base.getPostfix()
                                    ),
                            "inventory")
            );
        } else {
            ModelLoader.setCustomModelResourceLocation(
                    item,
                    0,
                    new ModelResourceLocation(item.getRegistryName(), "inventory")
            );
        }
    }
}
