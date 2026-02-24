package com.sabrepenguin.techreborn.items.tools;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.capability.stackhandler.ISideConfigTE;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
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
			IBlockState state = world.getBlockState(pos);
			Block block = state.getBlock();
			String regName = block.getRegistryName().toString();
			ItemBlock.getItemFromBlock(block).getTranslationKey(stack);
			if (player.isSneaking()) {
				if (!world.isRemote) {
					NBTTagCompound compound = new NBTTagCompound();
					compound.setTag("IOManager", sideConfigTE.getManager().writeToNBT());
					compound.setString("Block", regName);
					stack.setTagCompound(compound);
				} else {
					world.playSound(player, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 1.0f, 1.0f);
				}
				player.swingArm(hand);
				return EnumActionResult.SUCCESS;
			} else {
				if (stack.hasTagCompound()) {
					NBTTagCompound stackCompound = stack.getTagCompound();
					if (stackCompound != null && stackCompound.getString("Block").equals(regName)) {
						if (!world.isRemote) {
							NBTTagCompound compound = stackCompound.getCompoundTag("IOManager");
							if (!compound.isEmpty()) {
								sideConfigTE.getManager().readFromNBT(compound);
								sideConfigTE.updateTE();
							}
						} else {
							world.playSound(player, pos, SoundEvents.UI_BUTTON_CLICK, SoundCategory.BLOCKS, 1.0f, 1.0f);
						}
						player.swingArm(hand);
						return EnumActionResult.SUCCESS;
					}
				}
			}
		}
		return EnumActionResult.PASS;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		String baseName = super.getItemStackDisplayName(stack);
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Block")) {
			NBTTagCompound stackCompound = stack.getTagCompound();
			String regName = stackCompound.getString("Block");

			Block block = Block.getBlockFromName(regName);
			if (block != null) {
				ItemStack blockStack = new ItemStack(block, 1);
				String blockName = blockStack.getDisplayName();
				return TextFormatting.AQUA + baseName + " (" + blockName + ")";
			}
		}
		return baseName;
	}
}
