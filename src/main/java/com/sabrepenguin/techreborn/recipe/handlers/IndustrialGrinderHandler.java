package com.sabrepenguin.techreborn.recipe.handlers;

import com.google.gson.JsonObject;
import com.sabrepenguin.techreborn.recipe.registries.BasicRegistry;
import com.sabrepenguin.techreborn.recipe.ITRRecipeFactory;
import com.sabrepenguin.techreborn.recipe.registries.ITRRegistry;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.JsonContext;


public class IndustrialGrinderHandler implements ITRRecipeFactory {
	@Override
	public void registerRecipe(String recipeName, JsonObject json, JsonContext context, final ITRRegistry registry) {
		JsonObject outputs = JsonUtils.getJsonObject(json, "result");
//		List<ItemStack> out = Arrays.asList(CraftingHelper.get(outputs), context)
	}
}
