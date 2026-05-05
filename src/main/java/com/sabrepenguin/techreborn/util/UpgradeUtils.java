package com.sabrepenguin.techreborn.util;

import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.items.ItemUpgrade;
import com.sabrepenguin.techreborn.items.TRItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class UpgradeUtils {

	@SuppressWarnings("ConstantConditions")
	public static float getProcessingTimeMultiplier(IItemHandler handler) {
		float base = 1;
		for (int i = 0; i < handler.getSlots(); i++) {
			ItemStack upgrade = handler.getStackInSlot(i);
			Item item = upgrade.getItem();
			int meta = upgrade.getMetadata();
			if (item == TRItems.upgrades) {
				if (meta == ItemUpgrade.UpgradeEnum.OVERCLOCK.metadata()) {
					if (TechRebornConfig.itemConfig.upgrades.exponential) {
						base *= (1f - TechRebornConfig.itemConfig.upgrades.overclockProcessing);
					} else {
						base -= TechRebornConfig.itemConfig.upgrades.overclockProcessing;
					}
					if (base < 0.01)
						base = 0.01f;
				}
			}
		}
		return base;
	}

	@SuppressWarnings("ConstantConditions")
	public static int getProcessingTimeMultiplier(IItemHandler handler, int baseAmount) {
		float base = 1;
		for (int i = 0; i < handler.getSlots(); i++) {
			ItemStack upgrade = handler.getStackInSlot(i);
			Item item = upgrade.getItem();
			int meta = upgrade.getMetadata();
			if (item == TRItems.upgrades) {
				if (meta == ItemUpgrade.UpgradeEnum.OVERCLOCK.metadata()) {
					if (TechRebornConfig.itemConfig.upgrades.exponential) {
						base *= (1f - TechRebornConfig.itemConfig.upgrades.overclockProcessing);
					} else {
						base -= TechRebornConfig.itemConfig.upgrades.overclockProcessing;
					}
					if (base < 0.01)
						base = 0.01f;
				}
			}
		}
		return (int) (base * baseAmount);
	}

	@SuppressWarnings("ConstantConditions")
	public static int getTotalCostMultiplier(IItemHandler handler, int energyCost) {
		float base = 1;
		for (int i = 0; i < handler.getSlots(); i++) {
			ItemStack upgrade = handler.getStackInSlot(i);
			Item item = upgrade.getItem();
			int meta = upgrade.getMetadata();
			if (item == TRItems.upgrades) {
				if (meta == ItemUpgrade.UpgradeEnum.OVERCLOCK.metadata()) {
					base += TechRebornConfig.itemConfig.upgrades.overclockEnergyMultiplier;
				}
			}
		}
		return (int) (base * energyCost);
	}

	@SuppressWarnings("ConstantConditions")
	public static float getTotalCostMultiplier(IItemHandler handler) {
		float base = 1;
		for (int i = 0; i < handler.getSlots(); i++) {
			ItemStack upgrade = handler.getStackInSlot(i);
			Item item = upgrade.getItem();
			int meta = upgrade.getMetadata();
			if (item == TRItems.upgrades) {
				if (meta == ItemUpgrade.UpgradeEnum.OVERCLOCK.metadata()) {
					base += TechRebornConfig.itemConfig.upgrades.overclockEnergyMultiplier;
				}
			}
		}
		return base;
	}

	/**
	 * Gets the total multiplier based on the upgrades in the passed-in inventory
	 * @param handler The inventory containing upgrades
	 * @return The total multiplier for the energy
	 */
	@SuppressWarnings("ConstantConditions")
	public static float getEnergyTransferMultiplier(IItemHandler handler) {
		float base = 1;
		for (int i = 0; i < handler.getSlots(); i++) {
			ItemStack upgrade = handler.getStackInSlot(i);
			Item item = upgrade.getItem();
			int meta = upgrade.getMetadata();
			if (item == TRItems.upgrades) {
				if (meta == ItemUpgrade.UpgradeEnum.TRANSFORMER.metadata()) {
					base *= TechRebornConfig.itemConfig.upgrades.transformerMultiplier;
				} else if (meta == ItemUpgrade.UpgradeEnum.OVERCLOCK.metadata()) {
					base *= 2;
				}
			}
		}
		return base;
	}

	@SuppressWarnings("ConstantConditions")
	public static int getTotalEnergyStorageIncrease(IItemHandler handler, int base) {
		int total = 0;
		for (int i = 0; i < handler.getSlots(); i++) {
			ItemStack upgrade = handler.getStackInSlot(i);
			Item item = upgrade.getItem();
			int meta = upgrade.getMetadata();
			if (item == TRItems.upgrades) {
				if (meta == ItemUpgrade.UpgradeEnum.ENERGY_STORAGE.metadata()) {
					total += TechRebornConfig.itemConfig.upgrades.storageIncrease;
				} else if (meta == ItemUpgrade.UpgradeEnum.OVERCLOCK.metadata()) {
					total += base;
				}
			}
		}
		return base + total;
	}
}
