package com.sabrepenguin.techreborn.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import com.sabrepenguin.techreborn.recipe.utils.RecipeUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.Arrays;
import java.util.List;

public class AlloyHandler implements ITRRecipeFactory {
	@Override
	public void registerRecipe(JsonObject json, JsonContext context) {
		JsonArray ingredients = JsonUtils.getJsonArray(json, "ingredients");
		List<CountedIngredient> inputs = Arrays.asList(RecipeUtils.getCountedIngredients(ingredients));
		int recipeTime = JsonUtils.hasField(json, "recipe_time") ?
				JsonUtils.getInt(json, "recipe_time") : 200;
		int energyCost = JsonUtils.hasField(json, "fe_cost") ?
				JsonUtils.getInt(json, "fe_cost") : 24;
		ItemStack output = RecipeUtils.getResult(json, context);
		if (!inputs.isEmpty()) {
			RegistryHandler.instance()
					.getAlloyRegistry()
					.addRecipe(new AlloyRecipe(inputs, output, recipeTime, energyCost));
		}
	}
}
