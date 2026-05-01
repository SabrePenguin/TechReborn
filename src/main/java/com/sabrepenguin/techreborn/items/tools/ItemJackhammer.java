package com.sabrepenguin.techreborn.items.tools;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.capability.energy.PoweredItemCapabilityProvider;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.items.ItemHelper;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import com.sabrepenguin.techreborn.util.ItemStackUtils;
import com.sabrepenguin.techreborn.util.handlers.OreHandler;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
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
public class ItemJackhammer extends ItemPickaxe implements INonStandardLocation {
	private static final Random RANDOM = new Random();
	protected final int cost;
	protected final int maxReceive;
	protected final int maxCapacity;

	protected ResourceLocation fileLocation;

	public ItemJackhammer(ToolMaterial material, String name, int energyCost, int maxReceive,
						  int maxCapacity, float poweredSpeed) {
		super(material);
		ItemHelper.registerUnstackable(this, name);
		this.setNoRepair();
		this.setHasSubtypes(true);

		this.efficiency = poweredSpeed;
		this.cost = energyCost;
		this.maxReceive = maxReceive;
		this.maxCapacity = maxCapacity;
	}

	@Override
	public String getPrefix() {
		return "tool/jackhammer";
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

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			ItemStackUtils.getFullAndEmptySubItems(this, items, maxCapacity);
		}
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		if ((state.getBlock() == Blocks.STONE || OreHandler.hasOre("stone", ""))
				&& stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage.getEnergyStored() >= cost)
				return efficiency;
		}
		return 0.5f;
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

	public static ItemJackhammer diamondJackhammer() {
		ItemJackhammer diamondJackhammer = new ItemJackhammer(ToolMaterial.DIAMOND, "diamondjackhammer", 100, 1000,
				TechRebornConfig.itemConfig.jackhammers.diamondJackhammerMaxEnergy, 16f);
		diamondJackhammer.setCustomFile(new ResourceLocation(Tags.MODID, "diamond_jackhammer"));
		return diamondJackhammer;
	}

	public static ItemJackhammer steelJackhammer() {
		ItemJackhammer diamondJackhammer = new ItemJackhammer(ToolMaterial.DIAMOND, "steeljackhammer", 50, 100,
				TechRebornConfig.itemConfig.jackhammers.steelJackhammerMaxEnergy, 12f);
		diamondJackhammer.setCustomFile(new ResourceLocation(Tags.MODID, "steel_jackhammer"));
		return diamondJackhammer;
	}

	public static ItemJackhammer advancedJackhammer() {
		ItemJackhammer diamondJackhammer = new ItemJackhammer(ToolMaterial.IRON, "ironjackhammer", 250, 1000,
				TechRebornConfig.itemConfig.jackhammers.advancedJackhamerMaxEnergy, 60f);
		diamondJackhammer.setCustomFile(new ResourceLocation(Tags.MODID, "advanced_jackhammer"));
		return diamondJackhammer;
	}
}
