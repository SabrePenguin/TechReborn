package com.sabrepenguin.techreborn.items.tools;

import com.sabrepenguin.techreborn.items.ItemHelper;
import com.sabrepenguin.techreborn.util.ChatUtils;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemDebug extends Item implements INonStandardLocation {
	public ItemDebug() {
		ItemHelper.registerItem(this, "debug");
	}

	@Override
	public String getPrefix() {
		return "tool";
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState state = worldIn.getBlockState(pos);
		Block block = state.getBlock();
		if (block == null || block.isAir(state, worldIn, pos))
			return EnumActionResult.PASS;
		TileEntity te = worldIn.getTileEntity(pos);
		if (!worldIn.isRemote) {
			ITextComponent message = new TextComponentString("");
			Style blueStyle = new Style().setColor(TextFormatting.BLUE);
			Style greenStyle = new Style().setColor(TextFormatting.GREEN);
			TextComponentTranslation blockName = new TextComponentTranslation(block.getTranslationKey() + ".name");
			message.appendSibling(new TextComponentString("Block: ").setStyle(blueStyle))
					.appendSibling(blockName.setStyle(greenStyle))
					.appendText("\n")
					.appendSibling(new TextComponentString("State: ").setStyle(blueStyle))
					.appendSibling(new TextComponentString(state.toString()).setStyle(greenStyle));

			if (te != null && te.hasCapability(CapabilityEnergy.ENERGY, facing)) {
				IEnergyStorage storage = te.getCapability(CapabilityEnergy.ENERGY, facing);
				message.appendText("\n")
						.appendSibling(new TextComponentString("Power: ").setStyle(blueStyle))
						.appendSibling(new TextComponentString(storage.getEnergyStored() + " FE").setStyle(greenStyle));
			}
			ChatUtils.sendUpdatingMessage(message, ChatUtils.DEBUG_TOOL);
		}
		return EnumActionResult.SUCCESS;
	}
}
