package com.sabrepenguin.techreborn.items.armor;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.capability.energy.PoweredItemCapabilityProvider;
import com.sabrepenguin.techreborn.capability.energy.SettableEnergyStorage;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.items.ItemHelper;
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
public class ItemLapotronPack extends ItemArmor implements INonStandardLocation {

	public ItemLapotronPack() {
		super(ArmorMaterial.DIAMOND, 7, EntityEquipmentSlot.CHEST);
		ItemHelper.registerUnstackable(this, "lapotronpack");
		this.setHasSubtypes(true);
	}

	public void setEnergy(ItemStack stack, int energy) {
		if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
			IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
			if (storage instanceof SettableEnergyStorage energyStorage) {
				energyStorage.setEnergy(energy);
			}
		}
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		ItemLithiumBatpack.distributeToInventory(world, player, itemStack, TechRebornConfig.itemConfig.lapotronPack.transferLimit);
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
		return Tags.MODID + ":textures/models/lapotronpack.png";
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
		int maxTransfer = TechRebornConfig.itemConfig.lapotronPack.transferLimit;
		return new PoweredItemCapabilityProvider(stack, TechRebornConfig.itemConfig.lapotronPack.maxEnergy, maxTransfer, maxTransfer);
	}

	// INonStandardLocation

	@Override
	public String getPrefix() {
		return "armor";
	}
}
