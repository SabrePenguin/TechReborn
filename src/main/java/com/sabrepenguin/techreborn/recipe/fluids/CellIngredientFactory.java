package com.sabrepenguin.techreborn.recipe.fluids;

import com.google.gson.JsonObject;
import com.sabrepenguin.techreborn.items.ItemCell;
import com.sabrepenguin.techreborn.items.TRItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CellIngredientFactory implements IIngredientFactory {
	@Override
	public @NotNull Ingredient parse(JsonContext context, JsonObject json) {
		String fluidName = JsonUtils.getString(json, "fluid");
		Fluid fluid = FluidRegistry.getFluid(fluidName);
		if (fluid == null) {
			throw new RuntimeException("Recipe used a fluid that doesn't exist");
		}
		ItemStack out = new ItemStack(TRItems.cell, 1, 0);
		IFluidHandler handler = new FluidHandlerItemStack(out, 1000);
		handler.fill(new FluidStack(fluid, 1000), true);
		return new IngredientCell(out);
	}

	public static final class IngredientCell extends Ingredient {
		public IngredientCell(ItemStack... stacks) {
			super(stacks);
		}

		@Override
		public boolean apply(@Nullable ItemStack stack) {
			if (super.apply(stack))
				return ItemCell.isItemEqual(stack, this.getMatchingStacks()[0]);
			return false;
		}
	}
}
