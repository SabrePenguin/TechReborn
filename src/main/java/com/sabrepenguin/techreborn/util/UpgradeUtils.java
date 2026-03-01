package com.sabrepenguin.techreborn.util;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.items.ItemUpgrade;
import com.sabrepenguin.techreborn.items.TRItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.IItemHandler;

@Mod.EventBusSubscriber(modid = Tags.MODID)
public class UpgradeUtils {

	public static int config_revision = 0;

	//TODO: Move to config
	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(Tags.MODID)) {
//			ConfigManager.sync(Tags.MODID, Config.Type.INSTANCE);
			UpgradeUtils.config_revision++;
		}
	}

	//TODO: Config of multipliers
	public static float overclock_processing = 0.25f;
	public static float overclock_energy_multiplier = 0.75f;
	public static float transformer_multiplier = 4f;
	public static int storage_increase = 160_000;
	private final static boolean exponential = false;

	public static float getProcessingTimeMultiplier(IItemHandler handler) {
		float base = 1;
		for (int i = 0; i < handler.getSlots(); i++) {
			ItemStack upgrade = handler.getStackInSlot(i);
			Item item = upgrade.getItem();
			int meta = upgrade.getMetadata();
			if (item == TRItems.upgrades) {
				if (meta == ItemUpgrade.UpgradeEnum.OVERCLOCK.metadata()) {
					if (exponential) {
						base *= (1f - overclock_processing);
					} else {
						base -= overclock_processing;
					}
					if (base < 0.01)
						base = 0.01f;
				}
			}
		}
		return base;
	}

	public static int getProcessingTimeMultiplier(IItemHandler handler, int baseAmount) {
		float base = 1;
		for (int i = 0; i < handler.getSlots(); i++) {
			ItemStack upgrade = handler.getStackInSlot(i);
			Item item = upgrade.getItem();
			int meta = upgrade.getMetadata();
			if (item == TRItems.upgrades) {
				if (meta == ItemUpgrade.UpgradeEnum.OVERCLOCK.metadata()) {
					if (exponential) {
						base *= (1f - overclock_processing);
					} else {
						base -= overclock_processing;
					}
					if (base < 0.01)
						base = 0.01f;
				}
			}
		}
		return (int) (base * baseAmount);
	}

	public static int getTotalCostMultiplier(IItemHandler handler, int energyCost) {
		float base = 1;
		for (int i = 0; i < handler.getSlots(); i++) {
			ItemStack upgrade = handler.getStackInSlot(i);
			Item item = upgrade.getItem();
			int meta = upgrade.getMetadata();
			if (item == TRItems.upgrades) {
				if (meta == ItemUpgrade.UpgradeEnum.OVERCLOCK.metadata()) {
					base += overclock_energy_multiplier;
				}
			}
		}
		return (int) (base * energyCost);
	}

	public static float getTotalCostMultiplier(IItemHandler handler) {
		float base = 1;
		for (int i = 0; i < handler.getSlots(); i++) {
			ItemStack upgrade = handler.getStackInSlot(i);
			Item item = upgrade.getItem();
			int meta = upgrade.getMetadata();
			if (item == TRItems.upgrades) {
				if (meta == ItemUpgrade.UpgradeEnum.OVERCLOCK.metadata()) {
					base += overclock_energy_multiplier;
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
	public static float getEnergyTransferMultiplier(IItemHandler handler) {
		float base = 1;
		for (int i = 0; i < handler.getSlots(); i++) {
			ItemStack upgrade = handler.getStackInSlot(i);
			Item item = upgrade.getItem();
			int meta = upgrade.getMetadata();
			if (item == TRItems.upgrades) {
				if (meta == ItemUpgrade.UpgradeEnum.TRANSFORMER.metadata()) {
					base *= transformer_multiplier;
				} else if (meta == ItemUpgrade.UpgradeEnum.OVERCLOCK.metadata()) {
					base *= 2;
				}
			}
		}
		return base;
	}

	public static int getTotalEnergyStorageIncrease(IItemHandler handler, int base) {
		int total = 0;
		for (int i = 0; i < handler.getSlots(); i++) {
			ItemStack upgrade = handler.getStackInSlot(i);
			Item item = upgrade.getItem();
			int meta = upgrade.getMetadata();
			if (item == TRItems.upgrades) {
				if (meta == ItemUpgrade.UpgradeEnum.ENERGY_STORAGE.metadata()) {
					total += storage_increase;
				} else if (meta == ItemUpgrade.UpgradeEnum.OVERCLOCK.metadata()) {
					total += base;
				}
			}
		}
		return base + total;
	}
}
