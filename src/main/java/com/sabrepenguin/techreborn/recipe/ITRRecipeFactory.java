package com.sabrepenguin.techreborn.recipe;

import com.google.gson.JsonObject;
import net.minecraftforge.common.crafting.JsonContext;

public interface ITRRecipeFactory {
	void registerRecipe(JsonObject json, JsonContext context, final BasicRegistry registry);
}
