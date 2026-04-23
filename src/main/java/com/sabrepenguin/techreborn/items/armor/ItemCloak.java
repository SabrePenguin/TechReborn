package com.sabrepenguin.techreborn.items.armor;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.capability.energy.PoweredItemCapabilityProvider;
import com.sabrepenguin.techreborn.capability.energy.SettableEnergyStorage;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemCloak extends ItemArmor implements INonStandardLocation {
    private static final int MAX = 160_000_000;

	public ItemCloak(ArmorMaterial material) {
		super(material, 0, EntityEquipmentSlot.CHEST);
		this.setRegistryName(Tags.MODID, "cloakingdevice");
		this.setTranslationKey(Tags.MODID + ".cloakingdevice");
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
		this.setCreativeTab(TechReborn.RESOURCE_TAB);
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
			if (storage instanceof SettableEnergyStorage energyStorage) {
				energyStorage.setEnergy(energy);
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
        return new PoweredItemCapabilityProvider(stack, MAX, 400, 0);
    }
}
