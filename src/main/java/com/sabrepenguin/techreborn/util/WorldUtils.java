package com.sabrepenguin.techreborn.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class WorldUtils {

	@SuppressWarnings("ConstantConditions")
	public static void dropItem(ItemStack itemStack, World world, BlockPos pos) {
		Random rand = new Random();
		float dX = rand.nextFloat() * 0.8F + 0.1F;
		float dY = rand.nextFloat() * 0.8F + 0.1F;
		float dZ = rand.nextFloat() * 0.8F + 0.1F;
		EntityItem entityItem = new EntityItem(world, ((float)pos.getX() + dX), ((float)pos.getY() + dY), ((float)pos.getZ() + dZ), itemStack.copy());
		if (itemStack.hasTagCompound()) {
			entityItem.getItem().setTagCompound(itemStack.getTagCompound().copy());
		}

		float factor = 0.05F;
		entityItem.motionX = rand.nextGaussian() * (double)factor;
		entityItem.motionY = rand.nextGaussian() * (double)factor + (double)0.2F;
		entityItem.motionZ = rand.nextGaussian() * (double)factor;
		if (!world.isRemote) {
			world.spawnEntity(entityItem);
		}
	}
}
