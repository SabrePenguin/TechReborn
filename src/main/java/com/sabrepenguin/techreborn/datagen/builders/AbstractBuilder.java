package com.sabrepenguin.techreborn.datagen.builders;

import com.google.gson.*;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.lang.reflect.Type;

public abstract class AbstractBuilder<T extends AbstractBuilder<T>>  {
	protected static final Gson GSON;
	static {
		GsonBuilder gson = new GsonBuilder().setPrettyPrinting();
		gson.registerTypeAdapter(ItemIng.class, new ItemIng.ItemIngSerializer());
		gson.registerTypeAdapter(Oredict.class, new Oredict.OredictSerializer());
		gson.registerTypeAdapter(FluidIng.class, new FluidIng.FluidIngSerializer());
		gson.registerTypeAdapter(ShapedBuilder.class, new ShapedBuilder.ShapedBuilderSerializer());
		gson.registerTypeAdapter(ShapelessBuilder.class, new ShapelessBuilder.ShapelessBuilderSerializer());
		GSON = gson.create();
	}
	protected transient String name;
	protected String type = "minecraft:crafting_shapeless";
	protected ItemIng result;

	public T name(String name) {
		if (!name.endsWith(".json"))
			name = name + ".json";
		this.name = name;
		return self();
	}

	public T withType(String type) {
		this.type = type;
		return self();
	}

	public T withResult(ItemStack output) {
		this.result = new ItemIng(output);
		return self();
	}

	public abstract String save(File folder);

	@SuppressWarnings("unchecked")
	protected T self() {
		return (T) this;
	}

	protected static class Oredict extends BasicIngredient {
		private final String name;
		private final int count;
		public Oredict(String name, int count) {
			this.name = name;
			this.count = count;
		}

		private static class OredictSerializer implements JsonSerializer<Oredict> {

			@Override
			public JsonElement serialize(Oredict src, Type typeOfSrc, JsonSerializationContext context) {
				JsonObject element = new JsonObject();
				element.addProperty("type", "forge:ore_dict");
				element.addProperty("ore", src.name);
				if (src.count > 1)
					element.addProperty("count", src.count);
				return element;
			}
		}
	}

	protected static class ItemIng extends BasicIngredient {
		private final ItemStack stack;
		public ItemIng(ItemStack ingredient) {
			stack = ingredient;
		}

		private static class ItemIngSerializer implements JsonSerializer<ItemIng> {

			@Override
			public JsonElement serialize(ItemIng src, Type typeOfSrc, JsonSerializationContext context) {
				JsonObject element = new JsonObject();
				element.addProperty("item", src.stack.getItem().getRegistryName().toString());
				if (src.stack.getHasSubtypes()) {
					element.addProperty("data", src.stack.getMetadata());
				}
				if (src.stack.getCount() > 1)
					element.addProperty("count", src.stack.getCount());
				return element;
			}
		}
	}

	protected static class FluidIng extends BasicIngredient {
		private final String fluid;
		public FluidIng(String fluid) {
			this.fluid = fluid;
		}

		private static class FluidIngSerializer implements JsonSerializer<FluidIng> {

			@Override
			public JsonElement serialize(FluidIng src, Type typeOfSrc, JsonSerializationContext context) {
				JsonObject element = new JsonObject();
				element.addProperty("type", "techreborn:fluid_container");
				element.addProperty("fluid", src.fluid);
				element.addProperty("amount", 1000);
				return element;
			}
		}
	}

	protected abstract static class BasicIngredient {
	}
}
