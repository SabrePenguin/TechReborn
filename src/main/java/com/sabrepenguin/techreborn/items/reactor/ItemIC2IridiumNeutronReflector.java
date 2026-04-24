package com.sabrepenguin.techreborn.items.reactor;

import com.sabrepenguin.techreborn.items.ItemHelper;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemIC2IridiumNeutronReflector extends Item implements IReactorComponent, INonStandardLocation {
	public ItemIC2IridiumNeutronReflector() {
		ItemHelper.registerItem(this, "iridium_neutron_reflector");
	}

	@Override
	public String getPrefix() {
		return "reactor";
	}

	@Override
	public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatRun) {
	}

	@Override
	public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulseStack,
									  int x, int y, int pulseX, int pulseY, boolean heatRun) {
		if (!heatRun) {
			IReactorComponent component = (IReactorComponent) pulseStack.getItem();
			component.acceptUraniumPulse(pulseStack, reactor, stack, pulseX, pulseY, x, y, false);
		}
		return true;
	}

	@Override
	public boolean canStoreHeat(ItemStack stack, IReactor reactor, int x, int y) {
		return false;
	}

	@Override
	public int getMaxHeat(ItemStack stack, IReactor reactor, int x, int y) {
		return 0;
	}

	@Override
	public int getCurrentHeat(ItemStack stack, IReactor reactor, int x, int y) {
		return 0;
	}

	@Override
	public int alterHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
		return 0;
	}

	@Override
	public float influenceExplosion(ItemStack stack, IReactor reactor) {
		return -1;
	}

	@Override
	public boolean canBePlacedIn(ItemStack stack, IReactor reactor) {
		return true;
	}
}
