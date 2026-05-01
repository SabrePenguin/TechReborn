package com.sabrepenguin.techreborn.items.tools;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.capability.energy.PoweredItemCapabilityProvider;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.items.ItemHelper;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import com.sabrepenguin.techreborn.util.ItemStackUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemRockCutter extends ItemPickaxe implements INonStandardLocation {
	private static final Random RANDOM = new Random();
	protected static final int maxCapacity = TechRebornConfig.itemConfig.rockCutter.rockCutterMaxEnergy;
	protected static final int maxReceive = 1;
	protected static final int cost = 500;

	public ItemRockCutter() {
		super(ToolMaterial.DIAMOND);
		ItemHelper.registerUnstackable(this, "rockcutter");
		this.setHasSubtypes(true);
		this.setNoRepair();

		this.efficiency = 16f;
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage.getEnergyStored() >= cost)
				return efficiency;
		}
		return 2f;
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		if (RANDOM.nextInt(EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack) + 1) == 0) {
			if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
				IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
				storage.extractEnergy(cost, false);
			}
		}
		return true;
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	public int getHarvestLevel(ItemStack stack, String toolClass, @Nullable EntityPlayer player, @Nullable IBlockState blockState) {
		if (!stack.isItemEnchanted()) {
			stack.addEnchantment(Enchantments.SILK_TOUCH, 1);
		}
		return super.getHarvestLevel(stack, toolClass, player, blockState);
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			ItemStack empty = new ItemStack(this);
			empty.addEnchantment(Enchantments.SILK_TOUCH, 1);
			items.add(empty);
			ItemStack full = new ItemStack(this);
			full.addEnchantment(Enchantments.SILK_TOUCH, 1);
			if (full.hasCapability(CapabilityEnergy.ENERGY, null)) {
				IEnergyStorage storage = full.getCapability(CapabilityEnergy.ENERGY, null);
				if (storage != null) {
					ItemStackUtils.setEnergy(full, maxCapacity);
					items.add(full);
				}
			}
		}
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		if (!stack.isItemEnchanted()) {
			stack.addEnchantment(Enchantments.SILK_TOUCH, 1);
		}
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return ItemStackUtils.getItemStackDurabilityForDisplay(stack);
	}

	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		return new PoweredItemCapabilityProvider(stack, maxCapacity, maxReceive, cost);
	}

	@Override
	public boolean shouldCauseBlockBreakReset(ItemStack oldStack, ItemStack newStack) {
		return !oldStack.isItemEqual(newStack);
	}

	// INonStandardLocation

	@Override
	public String getPrefix() {
		return "tool";
	}

	@Override
	public boolean hasResourceLocation() {
		return true;
	}

	@Override
	public ResourceLocation getResourceLocation() {
		return new ResourceLocation(Tags.MODID, "rock_cutter");
	}
}
