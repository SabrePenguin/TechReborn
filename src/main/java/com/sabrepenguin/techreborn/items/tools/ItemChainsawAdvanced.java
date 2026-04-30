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
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemChainsawAdvanced extends ItemChainsaw {
	private static final EnumFacing[] SEARCH_ORDER = {EnumFacing.NORTH, EnumFacing.SOUTH,
			EnumFacing.EAST, EnumFacing.WEST, EnumFacing.UP};

	public ItemChainsawAdvanced() {
		super(ToolMaterial.DIAMOND, "advancedchainsaw", 250, 1000, TechRebornConfig.itemConfig.chainsaws.advancedChainsawMaxEnergy, 1.0f);
	}

	@Override
	public boolean hasResourceLocation() {
		return true;
	}

	@Override
	public ResourceLocation getResourceLocation() {
		return new ResourceLocation(Tags.MODID, "advanced_chainsaw");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (!ItemStackUtils.checkIsActive(stack)) {
			tooltip.add(TextFormatting.YELLOW + "Shear: " + TextFormatting.RED +
					I18n.format("techreborn.message.inactive"));
		} else {
			tooltip.add(TextFormatting.YELLOW + "Shear: " + TextFormatting.GREEN +
					I18n.format("techreborn.message.active"));
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
		if (!worldIn.isRemote && ItemStackUtils.checkIsActive(stack)) {
			if (state.getBlock() instanceof IShearable shearable) {
				if (shearable.isShearable(stack, worldIn, pos)) {
					List<ItemStack> results = shearable.onSheared(stack, worldIn, pos, 0);
					for (ItemStack result : results) {
						InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), result);
					}
					return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
				}
			}
		}
		Set<BlockPos> wood = new HashSet<>();
		findWood(worldIn, pos, wood, new HashSet<>());
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			wood.forEach(p -> breakBlock(worldIn, p, entityLiving, stack, pos, storage));
		}
		return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
	}

	private void findWood(World world, BlockPos pos, Set<BlockPos> wood, Set<BlockPos> leaves) {
		if (wood.size() >= 64)
			return;
		if (leaves.size() >= 128)
			return;
		for(EnumFacing facing: SEARCH_ORDER) {
			BlockPos newPos = pos.offset(facing);
			if (!wood.contains(newPos) && !leaves.contains(newPos)) {
				IBlockState state = world.getBlockState(newPos);
				if (state.getBlock().isWood(world, newPos)) {
					wood.add(newPos);
					findWood(world, newPos, wood, leaves);
				}
				if (state.getBlock().isLeaves(state, world, newPos)) {
					leaves.add(newPos);
					findWood(world, newPos, wood, leaves);
				}
			}
		}
	}

	private void breakBlock(World world, BlockPos pos, EntityLivingBase entity, ItemStack stack, BlockPos source, IEnergyStorage storage) {
		if (pos == source)
			return;
		if (storage.getEnergyStored() < energyCost)
			return;

		IBlockState state = world.getBlockState(pos);
		if (state.getBlockHardness(world, pos) == -1)
			return;
		if (!(entity instanceof EntityPlayer player))
			return;
		storage.extractEnergy(energyCost, false);
		state.getBlock().harvestBlock(world, player, pos, state, world.getTileEntity(pos), stack);
		world.setBlockToAir(pos);
		world.removeTileEntity(pos);
	}
}
