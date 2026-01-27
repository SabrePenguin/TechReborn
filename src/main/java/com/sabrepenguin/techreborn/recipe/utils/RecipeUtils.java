package com.sabrepenguin.techreborn.recipe.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public class RecipeUtils {
	public static ItemStack getResult(JsonObject json, JsonContext context) {
		JsonObject result = JsonUtils.getJsonObject(json, "result");
		return CraftingHelper.getItemStack(result, context);
	}

	public static ItemStack[] getMultiResult(JsonObject json, JsonContext context) {
		JsonObject result = JsonUtils.getJsonObject(json, "result");

		return null;
	}

	public static CountedIngredient[] getCountedIngredients(JsonArray json) {
		CountedIngredient[] ingredients = new CountedIngredient[json.size()];
		for(int i = 0; i < json.size(); i++) {
			JsonObject ingredient = json.get(i).getAsJsonObject();
			int count = JsonUtils.hasField(ingredient, "count") ? JsonUtils.getInt(ingredient, "count") : 1;
			if (JsonUtils.hasField(ingredient, "type")) {
				if (JsonUtils.getString(ingredient, "type").equals("forge:ore_dict")) {
					ingredients[i] = new CountedIngredient(count, JsonUtils.getString(ingredient, "ore"));
					continue;
				}
			} else {
				int data = JsonUtils.hasField(ingredient, "data") ? JsonUtils.getInt(ingredient, "data"): 0;
				if (JsonUtils.hasField(ingredient, "item")) {
					Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(JsonUtils.getString(ingredient, "item")));
					if (item == null)
						throw new JsonSyntaxException("Unknown item " + JsonUtils.getString(ingredient, "item"));
					ingredients[i] = new CountedIngredient(count, new ItemStack(item, 1, data));
					continue;
				}
			}
			throw new JsonSyntaxException("Invalid ingredient " + ingredient);
		}
		return ingredients;
	}

	public static boolean checkRecipeValid(IItemHandler handler, List<CountedIngredient> ingredientList) {
		if (handler.getSlots() < ingredientList.size()) return false;
		List<CountedIngredient> copy = new ArrayList<>(ingredientList);
		for (int i = 0; i < handler.getSlots(); i++) {
			int finalI = i;
			copy.stream().filter(ingredient -> ingredient.matches(handler.getStackInSlot(finalI))).findFirst().ifPresent(copy::remove);
		}
		return copy.isEmpty();
	}

	public static void applyRecipeToHandler(IItemHandler handler, List<CountedIngredient> ingredientList) {
		List<CountedIngredient> copy = new ArrayList<>(ingredientList);
		for (int i = 0; i < handler.getSlots(); i++) {
			int finalI = i;
			copy.stream()
					.filter(ingredient -> ingredient.matches(handler.getStackInSlot(finalI)))
					.findFirst()
					.map(ingredient -> handler.extractItem(finalI, ingredient.getCount(), false));
		}
	}
}
