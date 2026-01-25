package com.sabrepenguin.techreborn.recipe;

import com.google.gson.JsonObject;
import com.sabrepenguin.techreborn.recipe.utils.RecipeUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.crafting.JsonContext;

public class SmeltingHandler implements ITRRecipeFactory {
	public void registerRecipe(JsonObject json, JsonContext context) {
		ItemStack result = RecipeUtils.getResult(json, context);
	}
}
