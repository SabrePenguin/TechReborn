package com.sabrepenguin.techreborn.items.reactor;

import com.sabrepenguin.techreborn.items.ItemHelper;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemThoriumFuelRod extends Item implements INonStandardLocation {

	protected final int count;

	public ItemThoriumFuelRod(String name, int count) {
		ItemHelper.registerItem(this, name);
		this.setMaxDamage(100000);
		this.setNoRepair();
		this.count = count;
	}

	@Override
	public String getPrefix() {
		return "reactor";
	}

	@Override
	public boolean isDamageable() {
		return true;
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("Time left: " + (getMaxDamage(stack) - getDamage(stack)) + " seconds");
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
