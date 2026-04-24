package com.sabrepenguin.techreborn.items.reactor;

import com.sabrepenguin.techreborn.items.TRItems;
import ic2.api.info.Info;
import ic2.api.item.IHazmatLike;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayDeque;
import java.util.Queue;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemIC2ThoriumFuelRod extends ItemThoriumFuelRod implements IReactorComponent {
	public ItemIC2ThoriumFuelRod(String name, int count) {
		super(name, count);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (entityIn instanceof EntityLivingBase entity && !IHazmatLike.hasCompleteHazmat(entity)) {
			if (entity instanceof EntityPlayer player && player.isCreative())
				return;
			entity.addPotionEffect(new PotionEffect(Info.POTION_RADIATION, 200, 100));
		}
	}

	@Override
	public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulseStack,
									  int youX, int youY, int pulseX, int pulseY, boolean heatRun) {
		if (!heatRun) {
			reactor.addOutput(0.2f);
		}
		return true;
	}

	@Override
	public float influenceExplosion(ItemStack stack, IReactor reactor) {
		return (2.0f * count) / 5.0f;
	}

	@Override
	public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatRun) {
		if (!reactor.produceEnergy())
			return;

		final int basePulses = 1 + (count / 2);
		for (int i = 0; i < count; i++) {
			int pulses = basePulses;
			if (!heatRun) {
				for(int pulseI = 0; pulseI < pulses; pulseI++) {
					this.acceptUraniumPulse(stack, reactor, stack, x, y, x, y, false);
				}
				ReactorHelper.checkPulseable(reactor, x - 1, y, stack, x, y, false);
				ReactorHelper.checkPulseable(reactor, x + 1, y, stack, x, y, false);
				ReactorHelper.checkPulseable(reactor, x, y - 1, stack, x, y, false);
				ReactorHelper.checkPulseable(reactor, x, y + 1, stack, x, y, false);
			} else {
				pulses += ReactorHelper.checkPulseable(reactor, x - 1, y, stack, x, y, false) +
						ReactorHelper.checkPulseable(reactor, x + 1, y, stack, x, y, false) +
						ReactorHelper.checkPulseable(reactor, x, y - 1, stack, x, y, false) +
						ReactorHelper.checkPulseable(reactor, x, y + 1, stack, x, y, false);

				int heat = (int) ((ReactorHelper.triangularNumber(pulses) * 4) * 0.2f);

				Queue<ReactorHelper.ItemStackCoord> heatAcceptors = new ArrayDeque<>();
				ReactorHelper.checkHeatAcceptor(reactor, x - 1, y, heatAcceptors);
				ReactorHelper.checkHeatAcceptor(reactor, x + 1, y, heatAcceptors);
				ReactorHelper.checkHeatAcceptor(reactor, x, y - 1, heatAcceptors);
				ReactorHelper.checkHeatAcceptor(reactor, x, y + 1, heatAcceptors);

				while (!heatAcceptors.isEmpty() && heat > 0) {
					int dheat = heat / heatAcceptors.size();
					heat -= dheat;
					ReactorHelper.ItemStackCoord acceptor = heatAcceptors.remove();
					IReactorComponent acceptorComponent = (IReactorComponent) acceptor.stack().getItem();
					dheat = acceptorComponent.alterHeat(acceptor.stack(), reactor, acceptor.x(), acceptor.y(), dheat);
					heat += dheat;
				}

				if (heat > 0) reactor.addHeat(heat);
			}
		}
		if (!heatRun && getDamage(stack) >= this.getDamage(stack) - 1) {
			reactor.setItemAt(x, y, getDepletedStack());
		} else if (!heatRun) {
			setDamage(stack, getDamage(stack) + 1);
		}
	}

	@SuppressWarnings("ConstantConditions")
	private ItemStack getDepletedStack() {
		return switch (count) {
			case 2 -> new ItemStack(TRItems.depleted_dual_thorium_fuel_rod);
			case 4 -> new ItemStack(TRItems.depleted_quad_thorium_fuel_rod);
			default -> new ItemStack(TRItems.depleted_single_thorium_fuel_rod);
		};
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
		return heat;
	}


	@Override
	public boolean canBePlacedIn(ItemStack stack, IReactor reactor) {
		return true;
	}
}
