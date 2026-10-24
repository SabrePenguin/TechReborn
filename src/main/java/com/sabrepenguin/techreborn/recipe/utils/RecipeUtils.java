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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
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

	public static List<ItemStack> applyRecipeToHandler(IItemHandler handler, List<CountedIngredient> ingredientList) {
		List<CountedIngredient> remainder = new ArrayList<>(ingredientList);
		List<ItemStack> leftoverStacks = new ArrayList<>();
		for (int i = 0; i < handler.getSlots(); i++) {
			ItemStack slotStack = handler.getStackInSlot(i);
			if (slotStack.isEmpty())
				continue;
			CountedIngredient match = null;
			for (CountedIngredient ingredient: remainder) {
				if (ingredient.matches(slotStack)) {
					match = ingredient;
					break;
				}
			}
			if (match != null) {
				remainder.remove(match);
				ItemStack container = ItemStack.EMPTY;
				if (slotStack.getItem().hasContainerItem(slotStack)) {
					container = slotStack.getItem().getContainerItem(slotStack);
				}
				handler.extractItem(i, match.getCount(), false);
				if (!container.isEmpty()) {
					if (handler.getStackInSlot(i).isEmpty()) {
						ItemStack remains = handler.insertItem(i, container, false);
						if (!remains.isEmpty()) {
							leftoverStacks.add(remains);
						}
					} else {
						leftoverStacks.add(container);
					}
				}
			}
		}
		return leftoverStacks;
	}

	public static List<FluidStack> applyRecipeToHandler(IFluidHandler handler, List<FluidStack> fluidInputs) {
		List<FluidStack> remainder = new ArrayList<>(fluidInputs);
		List<FluidStack> leftoverStacks = new ArrayList<>();
		for (IFluidTankProperties tank: handler.getTankProperties()) {
			FluidStack contents = tank.getContents();
			if (contents == null || contents.amount == 0) {
				continue;
			}
			FluidStack match = null;
			for (FluidStack ingredient: remainder) {
				if (ingredient.isFluidEqual(contents)) {
					match = ingredient;
					break;
				}
			}
			if (match != null) {
				remainder.remove(match);
				handler.drain(match, true);
			}
		}
		return  leftoverStacks;
	}
}
