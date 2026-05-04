package com.sabrepenguin.techreborn.datagen.builders.conditions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.sabrepenguin.techreborn.Tags;

import java.lang.reflect.Type;

public class IC2Condition implements ICondition {
	private final Condition profile;

	public IC2Condition(String profile) {
		this.profile = Condition.fromString(profile);
	}

	public IC2Condition(Condition condition) {
		this.profile = condition;
	}

	public static IC2Condition ClassicCondition() {
		return new IC2Condition(Condition.CLASSIC_DEDUPE);
	}

	public static IC2Condition ExperimentalCondition() {
		return new IC2Condition(Condition.EXP_DEDUPE);
	}

	public static IC2Condition DeduplicateCondition() {
		return new IC2Condition(Condition.DEDUPE);
	}

	public static final class IC2ConditionSerializer implements JsonSerializer<IC2Condition> {

		@Override
		public JsonElement serialize(IC2Condition src, Type typeOfSrc, JsonSerializationContext context) {
			JsonObject json = new JsonObject();
			json.addProperty("type", Tags.MODID + ":ic2_condition");
			json.addProperty("profile", src.profile.toString());
			return json;
		}
	}

	public enum Condition {
		DEDUPE,
		CLASSIC_DEDUPE,
		EXP_DEDUPE;

		public static Condition fromString(String string) {
			if (string.equals("dedupe"))
				return DEDUPE;
			if (string.equals("classic_dedupe"))
				return CLASSIC_DEDUPE;
			if (string.equals("exp_dedupe"))
				return EXP_DEDUPE;
			throw new RuntimeException("Not a valid condition");
		}


		@Override
		public String toString() {
			return switch (this) {
				case DEDUPE -> "dedupe";
				case CLASSIC_DEDUPE -> "classic_dedupe";
				case EXP_DEDUPE -> "exp_dedupe";
			};
		}
	}
}
