package com.sabrepenguin.techreborn.items.tools;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.capability.stackhandler.ISideConfigTE;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemConfigurinator extends Item implements INonStandardLocation {
	public ItemConfigurinator() {
		this.setRegistryName(Tags.MODID, "configurinator");
		this.setTranslationKey(Tags.MODID + ".configurinator");
		this.setCreativeTab(TechReborn.RESOURCE_TAB);
	}

	@Override
	public String getPrefix() {
		return "tool";
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if (stack.getItem() == this) {
			if (playerIn.isSneaking()) {
				if (!worldIn.isRemote) {
					stack.setTagCompound(null);
				} else {
					playerIn.world.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.BLOCK_NOTE_HAT, SoundCategory.BLOCKS, 1.0f, 1.0f);
				}
				playerIn.swingArm(handIn);
				return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof ISideConfigTE sideConfigTE) {
			ItemStack stack = player.getHeldItem(hand);
			if (stack.getItem() != this)
				return EnumActionResult.PASS;
			if (!world.isRemote) {
				if (player.isSneaking()) {
					NBTTagCompound compound = new NBTTagCompound();
					compound.setTag("IOManager", sideConfigTE.getManager().writeToNBT());
					stack.setTagCompound(compound);
				} else {
					if (stack.hasTagCompound()) {
						NBTTagCompound compound = Objects.requireNonNull(stack.getTagCompound()).getCompoundTag("IOManager");
						if (!compound.isEmpty()) {
							sideConfigTE.getManager().readFromNBT(compound);
							sideConfigTE.updateTE();
						}
					}
				}
			} else {
				if (player.isSneaking()) {
					world.playSound(player, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 1.0f, 1.0f);
				} else {
					world.playSound(player, pos, SoundEvents.UI_BUTTON_CLICK, SoundCategory.BLOCKS, 1.0f, 1.0f);
				}
			}
			player.swingArm(hand);
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}
}
