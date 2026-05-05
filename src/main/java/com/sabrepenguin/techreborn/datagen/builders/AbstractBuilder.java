package com.sabrepenguin.techreborn.datagen.builders;

import com.google.gson.*;
import com.sabrepenguin.techreborn.datagen.builders.conditions.ConfigCondition;
import com.sabrepenguin.techreborn.datagen.builders.conditions.IC2Condition;
import com.sabrepenguin.techreborn.datagen.builders.conditions.ICondition;
import com.sabrepenguin.techreborn.datagen.builders.conditions.ModLoadedCondition;
import com.sabrepenguin.techreborn.datagen.builders.ingredients.*;
import net.minecraft.item.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBuilder<T extends AbstractBuilder<T>>  {
	protected static final Gson GSON = new GsonBuilder().setPrettyPrinting()
			.registerTypeAdapter(ItemIngredient.class, new ItemIngredient.ItemIngSerializer())
			.registerTypeAdapter(OreDictIngredient.class, new OreDictIngredient.OredictSerializer())
			.registerTypeAdapter(FluidIngredient.class, new FluidIngredient.FluidIngSerializer())
			.registerTypeAdapter(ReplaceableIngredient.class, new ReplaceableIngredient.ReplaceableIngredientSerializer())
			.registerTypeAdapter(ListIngredient.class, new ListIngredient.ListIngredientSerializer())
			.registerTypeAdapter(CellIngredient.class, new CellIngredient.CellIngredientSerializer())
			.registerTypeAdapter(ShapedBuilder.class, new ShapedBuilder.ShapedBuilderSerializer<>())
			.registerTypeAdapter(ShapelessBuilder.class, new ShapelessBuilder.ShapelessBuilderSerializer<>())
			.registerTypeAdapter(ReplaceableShapedBuilder.class, new ReplaceableShapedBuilder.ReplaceableShapedBuilderSerializer<>())
			.registerTypeAdapter(ReplaceableShapelessBuilder.class, new ReplaceableShapelessBuilder.ReplaceableShapelessBuilderSerializer<>())
			.registerTypeAdapter(IC2Condition.class, new IC2Condition.IC2ConditionSerializer())
			.registerTypeAdapter(ModLoadedCondition.class, new ModLoadedCondition.ModLoadedConditionSerializer())
			.registerTypeAdapter(ConfigCondition.class, new ConfigCondition.ConfigConditionSerializer())
			.create();
	protected transient String name;
	protected String type = "minecraft:crafting_shapeless";
	protected ItemIngredient result;
	protected List<ICondition> conditions = new ArrayList<>();

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

	public T withCondition(ICondition condition) {
		conditions.add(condition);
		return self();
	}

	public abstract String save(File folder);

	protected T self() {
		return getThis();
	}

	protected abstract T getThis();
}
