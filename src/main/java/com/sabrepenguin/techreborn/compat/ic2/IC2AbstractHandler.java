package com.sabrepenguin.techreborn.compat.ic2;

import ic2.core.IC2;
import ic2.core.profile.Version;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public abstract class IC2AbstractHandler {
	protected static final IC2AbstractHandler instance = getNewInstance();

	public abstract boolean extractSap(EntityPlayer player, World world, BlockPos pos, EnumFacing side, IBlockState state,
							  List<ItemStack> stacks);

	private static IC2AbstractHandler getNewInstance() {
		if (IC2.version == Version.NEW) {
			return new IC2Experimental();
		} else {
			return new IC2Classic();
		}
	}

	public static IC2AbstractHandler getInstance() {
		return instance;
	}
}
