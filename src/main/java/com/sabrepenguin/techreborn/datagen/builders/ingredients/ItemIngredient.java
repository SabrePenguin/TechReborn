package com.sabrepenguin.techreborn.datagen.builders.ingredients;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Type;

public class ItemIngredient implements IBasicIngredient {
	private final ItemStack stack;
	public ItemIngredient(ItemStack ingredient) {
		stack = ingredient;
	}

	public static class ItemIngSerializer implements JsonSerializer<ItemIngredient> {

		@Override
		public JsonElement serialize(ItemIngredient src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject element = new JsonObject();
			element.addProperty("item", src.stack.getItem().getRegistryName().toString());
			if (src.stack.getHasSubtypes()) {
				element.addProperty("data", src.stack.getMetadata());
			}
			if (src.stack.getCount() > 1)
				element.addProperty("count", src.stack.getCount());
			return element;
		}
	}
}
