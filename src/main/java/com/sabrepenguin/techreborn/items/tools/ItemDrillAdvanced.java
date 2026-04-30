package com.sabrepenguin.techreborn.items.tools;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.util.ChatUtils;
import com.sabrepenguin.techreborn.util.ItemStackUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemDrillAdvanced extends ItemDrill {
	public ItemDrillAdvanced() {
		super(ToolMaterial.DIAMOND, "advanceddrill", 250, 1000,
				TechRebornConfig.itemConfig.drills.advancedDrillMaxEnergy, 2.0f, 10f);
		this.setCustomFile(new ResourceLocation(Tags.MODID, "advanced_drill"));
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (!ItemStackUtils.checkIsActive(stack)) {
			tooltip.add(TextFormatting.RED + I18n.format("techreborn.message.inactive"));
		} else {
			tooltip.add(TextFormatting.GREEN + I18n.format("techreborn.message.active"));
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		final ItemStack stack = playerIn.getHeldItem(handIn);
		if (playerIn.isSneaking()) {
			if (ItemStackUtils.checkIsActive(stack)) {
				ItemStackUtils.setActive(stack, false);
				if (worldIn.isRemote) {
					ChatUtils.sendUpdatingMessage(
							new TextComponentString(
									TextFormatting.GRAY + I18n.format("techreborn.message.set_to") + " " +
											TextFormatting.GOLD + I18n.format("techreborn.message.inactive")
							),
							ChatUtils.ACTIVE_TOOL
					);
				}
			} else {
				ItemStackUtils.setActive(stack, true);
				if (worldIn.isRemote) {
					ChatUtils.sendUpdatingMessage(
							new TextComponentString(
									TextFormatting.GRAY + I18n.format("techreborn.message.set_to") + " " +
											TextFormatting.GOLD + I18n.format("techreborn.message.active")
							),
							ChatUtils.ACTIVE_TOOL
					);
				}
			}
			return new ActionResult<>(EnumActionResult.SUCCESS, stack);
		}
		return new ActionResult<>(EnumActionResult.PASS, stack);
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		if (entityLiving instanceof EntityPlayer player) {
			if (ItemStackUtils.checkIsActive(stack)) {
				destroyBlocks(worldIn, player, pos, stack, state);
			}
		}
		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}

	@SuppressWarnings("ConstantConditions")
	private void destroyBlocks(World world, EntityPlayer player, BlockPos pos, ItemStack stack, IBlockState state) {
		RayTraceResult result = rayTrace(world, player, false);
		if (result == null || result.sideHit == null)
			return;
		if (!stack.hasCapability(CapabilityEnergy.ENERGY, null))
			return;
		IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
		EnumFacing facing = result.sideHit;
		float hardness = state.getPlayerRelativeBlockHardness(player, world, pos);
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (storage.getEnergyStored() < cost)
					break;
				BlockPos offset = switch (facing) {
					case DOWN, UP -> pos.add(i, 0, j);
					case NORTH, SOUTH -> pos.add(i, j, 0);
					case WEST, EAST -> pos.add(0, i, j);
				};
				if (shouldBreak(world, player, pos, offset, hardness)) {
					storage.extractEnergy(cost, false);
					IBlockState currentState = world.getBlockState(offset);
					currentState.getBlock().removedByPlayer(currentState, world, offset, player, true);
					currentState.getBlock().harvestBlock(world, player, offset, currentState, world.getTileEntity(offset), stack);
					world.setBlockToAir(offset);
					world.removeTileEntity(offset);
				}
			}
		}
	}

	private boolean shouldBreak(World world, EntityPlayer player, BlockPos center, BlockPos offset, float centerHardness) {
		if (center.equals(offset))
			return false;
		IBlockState blockState = world.getBlockState(offset);
		if (world.isAirBlock(offset))
			return false;
		if (blockState.getMaterial().isLiquid())
			return false;
		if (blockState.getBlockHardness(world, offset) < 0)
			return false;
		float blockHardness = blockState.getPlayerRelativeBlockHardness(player, world, offset);
		if (blockHardness <= 0f)
			return false;
		return !(centerHardness / blockHardness > 10);
	}
}
