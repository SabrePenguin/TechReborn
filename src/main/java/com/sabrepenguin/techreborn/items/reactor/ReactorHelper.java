package com.sabrepenguin.techreborn.items.reactor;

import com.github.bsideup.jabel.Desugar;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import net.minecraft.item.ItemStack;

import java.util.Collection;

public class ReactorHelper {
	public static int triangularNumber(int x) {
		return (x * x + x) / 2;
	}

	public static int checkPulseable(IReactor reactor, int x, int y, ItemStack stack, int mex, int mey,
									 boolean heatRun) {
		ItemStack other = reactor.getItemAt(x, y);

		if (other != null && other.getItem() instanceof IReactorComponent) {
			if (((IReactorComponent) other.getItem()).acceptUraniumPulse(other, reactor, stack, x, y, mex, mey,
					heatRun))
				return 1;
		}

		return 0;
	}

	public static void checkHeatAcceptor(IReactor reactor, int x, int y, Collection<ItemStackCoord> heatAcceptors) {
		ItemStack stack = reactor.getItemAt(x, y);

		if (stack != null && stack.getItem() instanceof IReactorComponent) {
			if (((IReactorComponent) stack.getItem()).canStoreHeat(stack, reactor, x, y))
				heatAcceptors.add(new ItemStackCoord(stack, x, y));
		}
	}

	@Desugar
	public static record ItemStackCoord(ItemStack stack, int x, int y) {}
}
