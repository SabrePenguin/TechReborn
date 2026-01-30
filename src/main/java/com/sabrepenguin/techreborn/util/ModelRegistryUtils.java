package com.sabrepenguin.techreborn.util;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class ModelRegistryUtils {
    public static IStateMapper createMapper(ResourceLocation location, IProperty<?>[] ignored) {
        return new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap(state.getProperties());
                for (IProperty<?> property: ignored) {
                    map.remove(property);
                }
                return new ModelResourceLocation(location, getPropertyString(map));
            }
        };
    }

	public static ResourceLocation getResourceLocation(Item item) {
		if (item instanceof INonStandardLocation location) {
			return getResourceLocation(location, item.getRegistryName());
		}
		return item.getRegistryName();
	}

	public static ResourceLocation getResourceLocation(Block block) {
		if (block instanceof INonStandardLocation location) {
			return getResourceLocation(location, block.getRegistryName());
		}
		return block.getRegistryName();
	}

	private static ResourceLocation getResourceLocation(INonStandardLocation location, ResourceLocation original) {
		ResourceLocation customLoc = location.hasResourceLocation() ? location.getResourceLocation() : original;
		return new ResourceLocation(customLoc.getNamespace(),
				location.getPrefix() +
						customLoc.getPath() +
						location.getPostfix());
	}

    public static ResourceLocation fixLocation(ResourceLocation original, String prefix, String postfix) {
        return new ResourceLocation(original.getNamespace(), prefix + original.getPath() + postfix);
    }
}
