package com.sabrepenguin.techreborn.items;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class ItemHelper {

	public static void registerItem(Item item, String name) {
		item.setRegistryName(Tags.MODID, name);
		item.setTranslationKey(Tags.MODID + "." + name);
		item.setCreativeTab(TechReborn.RESOURCE_TAB);
	}

	public static void registerUnstackable(Item item, String name) {
		registerItem(item, name);
		item.setMaxStackSize(1);
	}

	public static void addAnimatedItemProperty(Item item, int energyCost) {
		item.addPropertyOverride(new ResourceLocation("techreborn:animated"), (stack, worldIn, entityIn) -> {
			if (!stack.isEmpty() && stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
				IEnergyStorage storage = stack.getCapability(CapabilityEnergy.ENERGY, null);
				if (storage.getEnergyStored() >= energyCost && entityIn != null && entityIn.getHeldItemMainhand().isItemEqual(stack))
					return 1;
			}
			return 0;
		});
	}
}
