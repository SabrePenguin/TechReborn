package com.sabrepenguin.techreborn.recipe.replaceable;

import com.google.gson.JsonObject;
import com.sabrepenguin.techreborn.compat.ic2.IC2AbstractHandler;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.util.ModLoadedUtil;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

import java.util.function.BooleanSupplier;

public class IC2ConfigConditionFactory implements IConditionFactory {

	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		String target = JsonUtils.getString(json, "profile");
		return switch (target) {
			case "classic" -> () -> ModLoadedUtil.IC2_LOADED && IC2AbstractHandler.isClassic();
			case "experimental" -> () -> ModLoadedUtil.IC2_LOADED && !IC2AbstractHandler.isClassic();
			case "classic_dedupe" ->
					() -> !(TechRebornConfig.compat.ic2.deduplicate && TechRebornConfig.compat.ic2.classic);
			case "exp_dedupe" ->
					() -> !(TechRebornConfig.compat.ic2.deduplicate && !TechRebornConfig.compat.ic2.classic);
			case "dedupe" -> () -> !(TechRebornConfig.compat.ic2.deduplicate);
			default -> throw new RuntimeException("Invalid condition");
		};
	}
}
