package com.sabrepenguin.techreborn.datagen.builders;

import com.google.gson.*;
import com.sabrepenguin.techreborn.datagen.builders.ingredients.FluidIngredient;
import com.sabrepenguin.techreborn.datagen.builders.ingredients.ItemIngredient;
import com.sabrepenguin.techreborn.datagen.builders.ingredients.OreDictIngredient;
import net.minecraft.item.ItemStack;

import java.io.File;

public abstract class AbstractBuilder<T extends AbstractBuilder<T>>  {
	protected static final Gson GSON;
	static {
		GsonBuilder gson = new GsonBuilder().setPrettyPrinting();
		gson.registerTypeAdapter(ItemIngredient.class, new ItemIngredient.ItemIngSerializer());
		gson.registerTypeAdapter(OreDictIngredient.class, new OreDictIngredient.OredictSerializer());
		gson.registerTypeAdapter(FluidIngredient.class, new FluidIngredient.FluidIngSerializer());
		gson.registerTypeAdapter(ShapedBuilder.class, new ShapedBuilder.ShapedBuilderSerializer());
		gson.registerTypeAdapter(ShapelessBuilder.class, new ShapelessBuilder.ShapelessBuilderSerializer());
		GSON = gson.create();
	}
	protected transient String name;
	protected String type = "minecraft:crafting_shapeless";
	protected ItemIngredient result;

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
		this.result = new ItemIngredient(output);
		return self();
	}

	public abstract String save(File folder);

	@SuppressWarnings("unchecked")
	protected T self() {
		return (T) this;
	}
}
