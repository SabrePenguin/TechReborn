package com.sabrepenguin.techreborn.datagen.builders.ingredients;

import com.google.gson.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;

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
			if (src.stack.hasTagCompound()) {
				element.add("nbt", nbtToJson(src.stack.getTagCompound()));
			}
			return element;
		}

		private JsonElement nbtToJson(NBTBase nbt) {
			if (nbt instanceof NBTTagCompound tag) {
				JsonObject object = new JsonObject();
				for (String key: tag.getKeySet()) {
					object.add(key, nbtToJson(tag.getTag(key)));
				}
				return object;
			}
			if (nbt instanceof NBTTagList list) {
				JsonArray array = new JsonArray();
				for (int i = 0; i < list.tagCount(); i++) {
					array.add(nbtToJson(list.get(i)));
				}
				return array;
			}
			if (nbt instanceof NBTTagString string)
				return new JsonPrimitive(string.getString());
			if (nbt instanceof NBTTagDouble d)
				return new JsonPrimitive(d.getDouble());
			if (nbt instanceof NBTTagFloat f)
				return new JsonPrimitive(f.getFloat());
			if (nbt instanceof NBTPrimitive primitive)
				return new JsonPrimitive(primitive.getLong());
			return JsonNull.INSTANCE;
		}
	}
}
