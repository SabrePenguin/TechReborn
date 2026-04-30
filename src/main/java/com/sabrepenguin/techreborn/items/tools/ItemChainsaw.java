package com.sabrepenguin.techreborn.items.tools;

import com.sabrepenguin.techreborn.capability.energy.PoweredItemCapabilityProvider;
import com.sabrepenguin.techreborn.items.ItemHelper;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import com.sabrepenguin.techreborn.util.ItemStackUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
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
public abstract class ItemChainsaw extends ItemAxe implements INonStandardLocation {
	private static final Random RANDOM = new Random();
	protected final int energyCost;
	protected final float poweredSpeed = 20f;
	protected final int maxCapacity;
	protected final int maxReceive;

	public ItemChainsaw(ToolMaterial material, String name, int energyCost, int maxReceive, int maxCapacity, float unpoweredSpeed) {
		super(material);
		ItemHelper.registerUnstackable(this, name);
		this.setHasSubtypes(true);
		this.setNoRepair();

		this.setHarvestLevel("axe", material.getHarvestLevel());

		this.efficiency = unpoweredSpeed;
		this.energyCost = energyCost;
		this.maxCapacity = maxCapacity;
		this.maxReceive = maxReceive;
		ItemHelper.addAnimatedItemProperty(this, energyCost);
	}

	@Override
	public String getPrefix() {
		return "tool/chainsaw";
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			ItemStackUtils.getFullAndEmptySubItems(this, items, maxCapacity);
		}
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage.getEnergyStored() >= energyCost && state.getBlock().isToolEffective("axe", state) || state.getMaterial() == Material.WOOD) {
				return this.poweredSpeed;
			}
		}
		return super.getDestroySpeed(stack, state);
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		if (RANDOM.nextInt(EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack) + 1) == 0) {
			if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
				IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
				storage.extractEnergy(energyCost, false);
			}
		}
		return true;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		return true;
	}

	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		return new PoweredItemCapabilityProvider(stack, maxCapacity, maxReceive, energyCost);
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
	public int getItemEnchantability() {
		return 20;
	}
}
