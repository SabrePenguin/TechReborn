package com.sabrepenguin.techreborn.recipe;

import com.google.gson.JsonObject;
import com.sabrepenguin.techreborn.recipe.utils.RecipeUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SmeltingHandler implements ITRRecipeFactory {
	public void registerRecipe(JsonObject json, JsonContext context) {
		ItemStack result = RecipeUtils.getResult(json, context);
		ItemStack[] ingredients = CraftingHelper.getIngredient(json.get("inputs"), context).getMatchingStacks();
		float experience = json.has("experience") ? JsonUtils.getFloat(json, "experience") : 0.0f;
		for (ItemStack item: ingredients) {
			GameRegistry.addSmelting(item, result, experience);
		}
	}
}
