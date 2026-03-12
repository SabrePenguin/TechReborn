package com.sabrepenguin.techreborn.recipe;

import com.google.gson.JsonObject;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.JsonContext;


public class IndustrialGrinderHandler implements ITRRecipeFactory {
	@Override
	public void registerRecipe(JsonObject json, JsonContext context, final BasicRegistry registry) {
		JsonObject outputs = JsonUtils.getJsonObject(json, "result");
//		List<ItemStack> out = Arrays.asList(CraftingHelper.get(outputs), context)
	}
}
