package com.sabrepenguin.techreborn.datagen.builders.conditions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class ModLoadedCondition implements ICondition {
	protected final String modid;
	public ModLoadedCondition(String modid) {
		this.modid = modid;
	}

	public static class ModLoadedConditionSerializer implements JsonSerializer<ModLoadedCondition> {

		@Override
		public JsonElement serialize(ModLoadedCondition src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject element = new JsonObject();
			element.addProperty("type", "forge:mod_loaded");
			element.addProperty("modid", src.modid);
			return element;
		}
	}
}
