package com.sabrepenguin.techreborn.recipe;

import com.google.gson.JsonObject;
import com.sabrepenguin.techreborn.recipe.registries.ITRRegistry;
import net.minecraftforge.common.crafting.JsonContext;

public interface ITRRecipeFactory {
	void registerRecipe(String recipeName, JsonObject json, JsonContext context, final ITRRegistry registry);
}
