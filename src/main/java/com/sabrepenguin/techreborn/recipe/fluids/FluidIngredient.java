package com.sabrepenguin.techreborn.recipe.fluids;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.Nullable;

public class FluidIngredient extends Ingredient {
	private final Fluid fluid;
	private final int amount;

	public FluidIngredient(Fluid fluid, int amount) {
		super(createBucket(fluid));
		this.fluid = fluid;
		this.amount = amount;
	}

	private static ItemStack[] createBucket(Fluid fluid) {
		ItemStack bucket = FluidUtil.getFilledBucket(new FluidStack(fluid, 1000));
		if (bucket.isEmpty()) {
			return new ItemStack[0];
		}
		return new ItemStack[] {bucket};
	}

	@Override
	public boolean apply(@Nullable ItemStack stack) {
		if (stack == null || stack.isEmpty()) return false;
		IFluidHandlerItem handler = FluidUtil.getFluidHandler(stack);
		if (handler != null) {
			FluidStack fluid = handler.drain(new FluidStack(this.fluid, this.amount), false);
			return fluid != null && fluid.amount >= this.amount;
		}
		return false;
	}

	public Fluid getFluid() {
		return this.fluid;
	}

	public int getAmount() {
		return this.amount;
	}
}
