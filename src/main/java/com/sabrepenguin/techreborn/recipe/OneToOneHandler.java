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

public class OneToOneHandler implements ITRRecipeFactory {
	@Override
	public void registerRecipe(String recipeName, JsonObject json, JsonContext context, final BasicRegistry registry) throws RecipeLoadingException {
		JsonArray ingredients = JsonUtils.getJsonArray(json, "ingredients");
		List<CountedIngredient> inputs = Arrays.asList(RecipeUtils.getCountedIngredients(ingredients));
		if (inputs.size() != 1) {
			throw new RecipeLoadingException("Input size is larger than 1 in a type that only supports 1 input");
		}
		int recipeTime = JsonUtils.hasField(json, "recipe_time") ?
				JsonUtils.getInt(json, "recipe_time") : 300;
		int energyCost = JsonUtils.hasField(json, "fe_cost") ?
				JsonUtils.getInt(json, "fe_cost") : 2;
		ItemStack output = RecipeUtils.getResult(json, context);
		registry.addRecipe(recipeName, new BasicOutputRecipe(inputs, output, recipeTime, energyCost));
	}
}
