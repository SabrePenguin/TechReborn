package com.sabrepenguin.techreborn.util;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.items.ItemBase;
import com.sabrepenguin.techreborn.items.ItemHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Tags.MODID)
public class ModelRegistryHandler {

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Item item: ItemHelper.getAllItems()) {
            registerModel(item);
        }
    }

    private static void registerModel(Item item) {
        if (item instanceof ItemBase baseItem && item.getHasSubtypes()) {
            String[] names = baseItem.getTypes();
            String prefix = baseItem.getPrefix();
            String postfix = baseItem.getPostfix();
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
        } else {
            ModelLoader.setCustomModelResourceLocation(
                    item,
                    0,
                    new ModelResourceLocation(item.getRegistryName(), "inventory")
            );
        }
    }
}
