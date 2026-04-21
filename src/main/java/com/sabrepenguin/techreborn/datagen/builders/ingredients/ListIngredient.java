package com.sabrepenguin.techreborn.datagen.builders.ingredients;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.sabrepenguin.techreborn.TechReborn;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListIngredient implements IBasicIngredient {
	List<IBasicIngredient> ingredients = new ArrayList<>();

	public ListIngredient addIngredient(IBasicIngredient ingredient) {
		this.ingredients.add(ingredient);
		return this;
	}

	public static class ListIngredientSerializer implements JsonSerializer<ListIngredient> {

		@Override
		public JsonElement serialize(ListIngredient src, Type typeOfSrc, JsonSerializationContext context) {
			TechReborn.LOGGER.warn("Serializing a ListIngredient");
			JsonArray array = new JsonArray();
			for (IBasicIngredient ingredient: src.ingredients) {
				TechReborn.LOGGER.warn("Running ingredient");
				array.add(context.serialize(ingredient));
			}
			return array;
		}
	}
}
