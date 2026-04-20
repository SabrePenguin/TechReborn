package com.sabrepenguin.techreborn.datagen.builders.ingredients;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class FluidIngredient implements IBasicIngredient {
	private final String fluid;
	public FluidIngredient(String fluid) {
		this.fluid = fluid;
	}

	public static class FluidIngSerializer implements JsonSerializer<FluidIngredient> {

		@Override
		public JsonElement serialize(FluidIngredient src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject element = new JsonObject();
			element.addProperty("type", "techreborn:fluid_container");
			element.addProperty("fluid", src.fluid);
			element.addProperty("amount", 1000);
			return element;
		}
	}
}
