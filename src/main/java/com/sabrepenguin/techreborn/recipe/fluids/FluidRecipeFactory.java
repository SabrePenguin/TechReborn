package com.sabrepenguin.techreborn.recipe.fluids;

import com.google.gson.JsonObject;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.common.crafting.JsonContext;

public class FluidRecipeFactory implements IRecipeFactory {
	@Override
	public IRecipe parse(JsonContext context, JsonObject json) {
		JsonObject modifiedJson = new JsonObject();
		json.entrySet().forEach(entry -> modifiedJson.add(entry.getKey(), entry.getValue()));
		modifiedJson.addProperty("type", "minecraft:crafting_shaped");

		IRecipe baseRecipe = CraftingHelper.getRecipe(modifiedJson, context);
		if (baseRecipe instanceof IShapedRecipe shapedRecipe)
			return new FluidRecipeWrapper(shapedRecipe);
		throw new RuntimeException("Not a shaped recipe");
	}
}
