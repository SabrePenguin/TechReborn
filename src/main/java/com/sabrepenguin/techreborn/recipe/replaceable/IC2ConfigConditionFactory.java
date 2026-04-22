package com.sabrepenguin.techreborn.recipe.replaceable;

import com.google.gson.JsonObject;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.function.BooleanSupplier;

public class IC2ConfigConditionFactory implements IConditionFactory {
	private static final BooleanSupplier classicSupplier = () -> TechRebornConfig.compat.ic2.deduplicate && TechRebornConfig.compat.ic2.classic;
	private static final BooleanSupplier experimentalSupplier = () -> TechRebornConfig.compat.ic2.deduplicate && !TechRebornConfig.compat.ic2.classic;

	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		String target = JsonUtils.getString(json, "profile");
		if (target.equals("classic")) {
			return classicSupplier;
		} else if (target.equals("experimental")) {
			return experimentalSupplier;
		} else if (target.equals("classic_dedupe")) {
			return () -> !(TechRebornConfig.compat.ic2.deduplicate && TechRebornConfig.compat.ic2.classic);
		} else if (target.equals("dedupe")) {
			return () -> !(TechRebornConfig.compat.ic2.deduplicate);
		}
		throw new RuntimeException("Invalid condition");
	}
}
