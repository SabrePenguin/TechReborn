package com.sabrepenguin.techreborn.items.armor;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.capability.energy.PoweredItemCapabilityProvider;
import com.sabrepenguin.techreborn.capability.energy.SettableEnergyStorage;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import com.sabrepenguin.techreborn.util.ItemStackUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemLithiumBatpack extends ItemArmor implements INonStandardLocation {

	public ItemLithiumBatpack() {
		super(ArmorMaterial.DIAMOND, 7, EntityEquipmentSlot.CHEST);
		this.setRegistryName(Tags.MODID, "lithiumbatpack");
		this.setTranslationKey(Tags.MODID + ".lithiumbatpack");
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
		this.setCreativeTab(TechReborn.RESOURCE_TAB);
	}

	public void setEnergy(ItemStack stack, int energy) {
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage instanceof SettableEnergyStorage energyStorage) {
				energyStorage.setEnergy(energy);
			}
		}
	}

	@SuppressWarnings("ConstantConditions")
	public static void distributeToInventory(World world, EntityPlayer player, ItemStack stack, int transfer) {
		if (world.isRemote)
			return;
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
				if (storage.getEnergyStored() == 0)
					break;
				ItemStack target = player.inventory.getStackInSlot(i);
				if (!target.isEmpty() && target.hasCapability(CapabilityEnergy.ENERGY, null)) {
					IEnergyStorage targetStorage = target.getCapability(CapabilityEnergy.ENERGY, null);
					if (targetStorage.getEnergyStored() == targetStorage.getMaxEnergyStored())
						continue;
					int extracted = storage.extractEnergy(transfer, true);
					if (extracted > 0) {
						int inserted = targetStorage.receiveEnergy(extracted, false);
						storage.extractEnergy(inserted, false);
					}
				}
			}
		}
	}

	// ItemArmor

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		distributeToInventory(world, player, itemStack, TechRebornConfig.itemConfig.lithiumBatpack.transferLimit);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			ItemStack empty = new ItemStack(this);
			this.setEnergy(empty, 0);
			items.add(empty);
			ItemStack full = new ItemStack(this);
			if (full.hasCapability(CapabilityEnergy.ENERGY, null)) {
				IEnergyStorage storage = full.getCapability(CapabilityEnergy.ENERGY, null);
				if (storage != null) {
					this.setEnergy(full, TechRebornConfig.itemConfig.lapotronPack.maxEnergy);
					items.add(full);
				}
			}
		}
	}

	@Override
	public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return Tags.MODID + ":textures/models/lithiumbatpack.png";
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return ItemStackUtils.getItemStackDurabilityForDisplay(stack);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
		int maxTransfer = TechRebornConfig.itemConfig.lithiumBatpack.transferLimit;
		return new PoweredItemCapabilityProvider(stack, TechRebornConfig.itemConfig.lithiumBatpack.maxEnergy, maxTransfer, maxTransfer);
	}

	// INonStandardLocation

	@Override
	public String getPrefix() {
		return "armor";
	}
}
