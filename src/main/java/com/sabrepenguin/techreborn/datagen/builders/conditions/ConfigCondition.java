package com.sabrepenguin.techreborn.datagen.builders.conditions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class ConfigCondition implements ICondition {
	protected final String condition;

	public ConfigCondition(String condition) {
		this.condition = condition;
	}

	public static class ConfigConditionSerializer implements JsonSerializer<ConfigCondition> {

		@Override
		public JsonElement serialize(ConfigCondition src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject element = new JsonObject();
			element.addProperty("type", "techreborn:config");
			element.addProperty("config", src.condition);
			return element;
		}
	}
}
