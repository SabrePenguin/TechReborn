package com.sabrepenguin.techreborn.datagen.builders.ingredients;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.item.ItemStack;

import java.lang.reflect.Type;
import java.util.Objects;

public class ReplaceableIngredient implements IBasicIngredient {

	protected final ItemStack original;
	protected final ItemStack replacement;
	protected int count;

	public ReplaceableIngredient(ItemStack original, ItemStack replacement) {
		this.original = original;
		this.replacement = replacement;
	}

	public ReplaceableIngredient count(int count) {
		this.count = count;
		return this;
	}

	public static final class ReplaceableIngredientSerializer implements JsonSerializer<ReplaceableIngredient> {

		@Override
		public JsonElement serialize(ReplaceableIngredient src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject element = new JsonObject();
			element.addProperty("type", "techreborn:replaceable");
			{
				JsonObject original = new JsonObject();
				original.addProperty("item", Objects.requireNonNull(src.original.getItem().getRegistryName()).toString());
				if (src.original.getHasSubtypes()) {
					original.addProperty("data", src.original.getMetadata());
				}
				if (src.original.getCount() > 1 && src.count == 0)
					original.addProperty("count", src.original.getCount());
				else if (src.count != 0)
					original.addProperty("count", src.count);
				element.add("original", original);
			}
			{
				JsonObject replacement = new JsonObject();
				replacement.addProperty("item", Objects.requireNonNull(src.replacement.getItem().getRegistryName()).toString());
				if (src.replacement.getHasSubtypes()) {
					replacement.addProperty("data", src.replacement.getMetadata());
				}
				if (src.replacement.getCount() > 1 && src.count == 0)
					replacement.addProperty("count", src.replacement.getCount());
				else if (src.count != 0)
					replacement.addProperty("count", src.count);
				element.add("replacement", replacement);
			}
			return element;
		}
	}
}
