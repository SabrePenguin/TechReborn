package com.sabrepenguin.techreborn.items.tools;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.capability.energy.PoweredItemCapabilityProvider;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.items.ItemHelper;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import com.sabrepenguin.techreborn.util.InventoryUtils;
import com.sabrepenguin.techreborn.util.ItemStackUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
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
public class ItemOmniTool extends ItemPickaxe implements INonStandardLocation {
	private static final Random RANDOM = new Random();
	protected static final int maxCapacity = TechRebornConfig.itemConfig.omniTool.omniToolMaxEnergy;
	protected static final int cost = 100;
	protected static final int maxReceive = 1000;
	protected static final int hitCost = 125;

	public ItemOmniTool() {
		super(ToolMaterial.DIAMOND);
		ItemHelper.registerUnstackable(this, "omnitool");
		this.setHasSubtypes(true);
		this.setNoRepair();

		this.setHarvestLevel("shovel", ToolMaterial.DIAMOND.getHarvestLevel());
		this.setHarvestLevel("axe", ToolMaterial.DIAMOND.getHarvestLevel());
		this.setHarvestLevel("pickaxe", ToolMaterial.DIAMOND.getHarvestLevel());

		this.efficiency = 13f;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			ItemStackUtils.getFullAndEmptySubItems(this, items, maxCapacity);
		}
	}

	@Override
	public boolean canHarvestBlock(IBlockState blockIn) {
		return super.canHarvestBlock(blockIn) || Items.SHEARS.canHarvestBlock(blockIn) || Items.DIAMOND_SWORD.canHarvestBlock(blockIn);
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
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage.getEnergyStored() >= hitCost && attacker instanceof EntityPlayer player) {
				storage.extractEnergy(hitCost, false);
				target.attackEntityFrom(DamageSource.causePlayerDamage(player), 8f);
			}
		}
		return false;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack torch = InventoryUtils.findItem(player, "torch");
		if (!torch.isEmpty()) {

			// TODO I see why they had this disabled
			return torch.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
		}
		return EnumActionResult.PASS;
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
		return new ResourceLocation(Tags.MODID, "omni_tool");
	}
}
