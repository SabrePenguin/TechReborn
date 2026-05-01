package com.sabrepenguin.techreborn.items.tools;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sabrepenguin.techreborn.capability.energy.PoweredItemCapabilityProvider;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.items.ItemHelper;
import com.sabrepenguin.techreborn.util.ChatUtils;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import com.sabrepenguin.techreborn.util.ItemStackUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemNanosaber extends ItemSword implements INonStandardLocation {
	protected static final int maxCapacity = TechRebornConfig.itemConfig.nanosaber.nanosaberMaxEnergy;
	protected static final int activeDamage = TechRebornConfig.itemConfig.nanosaber.nanosaberDamageWhenActive;
	protected final int cost = 250;
	protected final int maxReceive = 1000;

	@SuppressWarnings("ConstantConditions")
	public ItemNanosaber() {
		super(ToolMaterial.DIAMOND);
		ItemHelper.registerUnstackable(this, "nanosaber");
		this.setNoRepair();
		this.setHasSubtypes(true);

		this.addPropertyOverride(new ResourceLocation("techreborn:active"), (stack, worldIn, entityIn) -> {
			if (ItemStackUtils.checkIsActive(stack)) {
				if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
					IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
					if (storage.getMaxEnergyStored() - storage.getEnergyStored() >= 0.9 * storage.getMaxEnergyStored()) {
						return 0.5f;
					}
					return 1;
				}
			}
			return 0;
		});
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (!ItemStackUtils.checkIsActive(stack))
			return true;

		if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage.getEnergyStored() >= cost) {
				storage.extractEnergy(cost, false);
				return true;
			}
		}
		return false;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		if (slot != EntityEquipmentSlot.MAINHAND)
			return super.getAttributeModifiers(slot, stack);

		int damage = 4;

		if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage.getEnergyStored() >= cost) {
				if (ItemStackUtils.checkIsActive(stack))
					damage = activeDamage;
			}
		}

		Multimap<String, AttributeModifier> multimap = HashMultimap.create();

		multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
				new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double) damage - 1, 0));
		multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
				new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4, 0));
		return multimap;
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
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (!ItemStackUtils.checkIsActive(stack)) {
			tooltip.add(TextFormatting.GRAY + I18n.format("techreborn.message.inactive"));
		} else {
			tooltip.add(TextFormatting.GRAY + I18n.format("techreborn.message.active"));
		}
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			ItemStackUtils.getFullAndEmptySubItems(this, items, maxCapacity);
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
}
