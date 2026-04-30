package com.sabrepenguin.techreborn.compat.ic2;

import ic2.api.item.IC2Items;
import ic2.core.item.tool.ItemTreetap;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class IC2Experimental extends IC2AbstractHandler {
	@Override
	public boolean extractSap(EntityPlayer player, World world, BlockPos pos, EnumFacing side, IBlockState state, List<ItemStack> stacks) {
		if (state.getBlock() != Block.getBlockFromItem(IC2Items.getItem("rubber_wood").getItem())) {
			return false;
		}
		return ItemTreetap.attemptExtract(player, world, pos, side, state, stacks);
	}
}
