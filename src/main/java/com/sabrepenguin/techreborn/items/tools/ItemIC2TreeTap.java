package com.sabrepenguin.techreborn.items.tools;

import com.sabrepenguin.techreborn.compat.ic2.IC2AbstractHandler;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemIC2TreeTap extends ItemTreeTap {
	protected final IC2AbstractHandler handler;

	public ItemIC2TreeTap() {
		super();
		handler = IC2AbstractHandler.getInstance();
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState state = worldIn.getBlockState(pos);
		if (handler.extractSap(player, worldIn, pos, facing, state, null)) {
			if (!worldIn.isRemote) {
				player.getHeldItem(hand).damageItem(1, player);
			}
			if (player instanceof EntityPlayerMP) {
				//TODO: Advancement?
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
}
