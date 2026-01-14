package com.sabrepenguin.techreborn.items.armor;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.capability.ItemEnergyStorage;
import com.sabrepenguin.techreborn.capability.ItemPowerProvider;
import com.sabrepenguin.techreborn.capability.PoweredItemCapability;
import com.sabrepenguin.techreborn.items.IEnergy;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
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

public class ItemCloak extends ItemArmor implements INonStandardLocation, IEnergy {

	private static final int COST = 10;
	private static final int INPUT = 100;
    private static final int MAX = 40_000_000;

	public ItemCloak(ArmorMaterial material) {
		super(material, 0, EntityEquipmentSlot.CHEST);
		this.setRegistryName(Tags.MODID, "cloakingdevice");
		this.setTranslationKey(Tags.MODID + ".cloakingdevice");
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
	}

	@Override
	public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return Tags.MODID + ":textures/models/cloaking.png";
	}

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            ItemStack full = new ItemStack(this);
            if (full.hasCapability(CapabilityEnergy.ENERGY, null)) {
                IEnergyStorage storage = full.getCapability(CapabilityEnergy.ENERGY, null);
                if (storage != null) {
                    this.setEnergy(full, MAX);
                    items.add(full);
                }
            }
            ItemStack empty = new ItemStack(this);
            this.setEnergy(empty, 0);
            items.add(empty);
        }
        super.getSubItems(tab, items);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage != null) {
                double maxAmount = storage.getMaxEnergyStored();
                double energyDif = maxAmount - storage.getEnergyStored();
                return energyDif/maxAmount;
            }
        }
        return super.getDurabilityForDisplay(stack);
    }

    public void setEnergy(ItemStack stack, int energy) {
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
            if (storage instanceof ItemEnergyStorage energyStorage) {
                energyStorage.setEnergyStored(energy);
            }
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public String getPrefix() {
        return "armor/";
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new PoweredItemCapability(stack, this);
    }

    @Override
    public int getCapacity() {
        return MAX;
    }

    @Override
    public int getInputTransfer() {
        return INPUT;
    }

    @Override
    public int getOutputTransfer() {
        return COST;
    }
}
