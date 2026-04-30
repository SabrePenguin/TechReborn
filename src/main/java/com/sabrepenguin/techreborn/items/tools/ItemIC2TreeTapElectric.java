package com.sabrepenguin.techreborn.items.tools;

import com.sabrepenguin.techreborn.compat.ic2.IC2AbstractHandler;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemIC2TreeTapElectric extends ItemTreeTapElectric {
	protected final IC2AbstractHandler handler;

	public ItemIC2TreeTapElectric() {
		super();
		handler = IC2AbstractHandler.getInstance();
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState state = worldIn.getBlockState(pos);
		ItemStack stack = player.getHeldItem(hand);
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage.getEnergyStored() >= cost) {
				if (handler.extractSap(player, worldIn, pos, facing, state, null) && !worldIn.isRemote) {
					storage.extractEnergy(cost, false);
					return EnumActionResult.SUCCESS;
				}
			}
		}
		return EnumActionResult.PASS;
	}
}
