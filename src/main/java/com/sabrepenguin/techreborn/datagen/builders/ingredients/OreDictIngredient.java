package com.sabrepenguin.techreborn.datagen.builders.ingredients;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class OreDictIngredient implements IBasicIngredient {
	private final String name;
	private final int count;
	public OreDictIngredient(String name, int count) {
		this.name = name;
		this.count = count;
	}

	public static class OredictSerializer implements JsonSerializer<OreDictIngredient> {

		@Override
		public JsonElement serialize(OreDictIngredient src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject element = new JsonObject();
			element.addProperty("type", "forge:ore_dict");
			element.addProperty("ore", src.name);
			if (src.count > 1)
				element.addProperty("count", src.count);
			return element;
		}
	}
}
