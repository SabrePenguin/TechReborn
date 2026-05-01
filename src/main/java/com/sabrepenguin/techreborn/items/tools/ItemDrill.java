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
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
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
public class ItemDrill extends ItemPickaxe implements INonStandardLocation {
	private static final Random RANDOM = new Random();
	protected final int cost;
	protected final int maxCapacity;
	protected final int maxReceive;
	protected final float unpoweredSpeed;

	protected ResourceLocation fileLocation;

	ItemDrill(ToolMaterial material, String name, int energyCost, int maxReceive,
					 int maxCapacity, float unpoweredSpeed, float poweredSpeed) {
		super(material);
		ItemHelper.registerUnstackable(this, name);
		this.setNoRepair();
		this.setHasSubtypes(true);

		this.setHarvestLevel("pickaxe", material.getHarvestLevel());
		this.setHarvestLevel("shovel", material.getHarvestLevel());

		this.cost = energyCost;
		this.maxCapacity = maxCapacity;
		this.maxReceive = maxReceive;
		this.unpoweredSpeed = unpoweredSpeed;
		this.efficiency = poweredSpeed;

		ItemHelper.addAnimatedItemProperty(this, energyCost);
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
			IEnergyStorage energyStorage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (energyStorage.getEnergyStored() < cost)
				return unpoweredSpeed;
			if (Items.WOODEN_PICKAXE.getDestroySpeed(stack, state) > 1.0f || Items.WOODEN_SHOVEL.getDestroySpeed(stack, state) > 1.0f)
				return efficiency;
		}
		return super.getDestroySpeed(stack, state);
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
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		return true;
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

	@Override
	public String getPrefix() {
		return "tool/drill";
	}

	@Override
	public boolean hasResourceLocation() {
		return fileLocation != null;
	}

	@Override
	public ResourceLocation getResourceLocation() {
		return fileLocation;
	}

	protected void setCustomFile(ResourceLocation location) {
		fileLocation = location;
	}

	public static ItemDrill diamondDrill() {
		ItemDrill drill = new ItemDrill(ToolMaterial.DIAMOND, "diamonddrill", 250,
				1000, TechRebornConfig.itemConfig.drills.diamondDrillMaxEnergy, 0.5f, 15);
		drill.setCustomFile(new ResourceLocation(Tags.MODID, "diamond_drill"));
		return drill;
	}
	public static ItemDrill steelDrill() {
		ItemDrill drill = new ItemDrill(ToolMaterial.IRON, "irondrill", 50,
				100, TechRebornConfig.itemConfig.drills.ironDrillMaxEnergy, 0.5f, 10);
		drill.setCustomFile(new ResourceLocation(Tags.MODID, "steel_drill"));
		return drill;
	}
}
