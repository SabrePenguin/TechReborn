package com.sabrepenguin.techreborn.recipe.fluids;

import com.google.gson.JsonObject;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import org.jetbrains.annotations.NotNull;

public class FluidIngredientFactory implements IIngredientFactory {
	@Override
	public @NotNull Ingredient parse(JsonContext context, JsonObject json) {
		String fluidName = JsonUtils.getString(json, "fluid");
		int amount = JsonUtils.getInt(json, "amount", 1000);
		Fluid fluid = FluidRegistry.getFluid(fluidName);

		return new FluidIngredient(fluid, amount);
	}
}
