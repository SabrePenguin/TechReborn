package com.sabrepenguin.techreborn.recipe.misc;

import com.google.gson.JsonObject;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.function.BooleanSupplier;

public class ConfigConditionFactory implements IConditionFactory {
	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		String config = JsonUtils.getString(json, "config");
		if (config.equals("itemconfig.loadarmor"))
			return () -> TechRebornConfig.itemConfig.loadArmor;
		throw new RuntimeException("Not a valid condition: " + config);
	}
}
