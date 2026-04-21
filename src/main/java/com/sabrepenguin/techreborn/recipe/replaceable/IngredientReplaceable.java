package com.sabrepenguin.techreborn.recipe.replaceable;

import com.google.gson.JsonObject;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.util.ModLoadedUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;
import org.jetbrains.annotations.NotNull;

public class IngredientReplaceable implements IIngredientFactory {
	@Override
	public @NotNull Ingredient parse(JsonContext context, JsonObject json) {
		Ingredient original = CraftingHelper.getIngredient(JsonUtils.getJsonObject(json, "original"));
		Ingredient replacement = null;
		if (TechRebornConfig.compat.ic2.deduplicate && ModLoadedUtil.IC2_LOADED) {
			replacement = CraftingHelper.getIngredient(JsonUtils.getJsonObject(json, "replacement"));
		}

		return replacement != null ? replacement : original;
	}
}
