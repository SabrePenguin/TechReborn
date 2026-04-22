package com.sabrepenguin.techreborn.recipe.replaceable;

import com.google.gson.JsonObject;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.util.ModLoadedUtil;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class ReplaceableShapelessRecipeFactory implements IRecipeFactory {
	@Override
	public IRecipe parse(JsonContext context, JsonObject json) {
		JsonObject modifiedJson = new JsonObject();
		json.entrySet().forEach(entry -> modifiedJson.add(entry.getKey(), entry.getValue()));
		modifiedJson.addProperty("type", "minecraft:crafting_shapeless");

		JsonObject resultJson = json.getAsJsonObject("result");
		JsonObject out = resultJson.getAsJsonObject("original");
		if (TechRebornConfig.compat.ic2.deduplicate && ModLoadedUtil.IC2_LOADED) {
			out = resultJson.getAsJsonObject("replacement");
		}
		modifiedJson.add("result", out);

		return CraftingHelper.getRecipe(modifiedJson, context);
	}
}
