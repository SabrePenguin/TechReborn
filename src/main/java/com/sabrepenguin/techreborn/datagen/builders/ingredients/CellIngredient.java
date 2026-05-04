package com.sabrepenguin.techreborn.datagen.builders.ingredients;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraftforge.fluids.Fluid;

import java.lang.reflect.Type;

public class CellIngredient implements IBasicIngredient {
	protected final Fluid fluid;
	public CellIngredient(Fluid fluid) {
		this.fluid = fluid;
	}

	public static class CellIngredientSerializer implements JsonSerializer<CellIngredient> {

		@Override
		public JsonElement serialize(CellIngredient src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject element = new JsonObject();
			element.addProperty("type", "techreborn:cell");
			element.addProperty("fluid", src.fluid.getName());
			return element;
		}
	}
}
