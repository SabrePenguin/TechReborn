package com.sabrepenguin.techreborn.datagen.builders.conditions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.sabrepenguin.techreborn.Tags;

import java.lang.reflect.Type;

public class IC2Condition implements ICondition {
	private final String profile;

	public IC2Condition(String profile) {
		this.profile = profile;
	}

	public static final class IC2ConditionSerializer implements JsonSerializer<IC2Condition> {

		@Override
		public JsonElement serialize(IC2Condition src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject json = new JsonObject();
			json.addProperty("type", Tags.MODID + ":ic2_condition");
			json.addProperty("profile", src.profile);
			return json;
		}
	}
}
