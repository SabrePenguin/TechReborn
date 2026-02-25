package com.sabrepenguin.techreborn.jei;

import com.sabrepenguin.techreborn.blocks.TRBlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class TRRecipePlugin implements IModPlugin {
	@Override
	public void register(IModRegistry registry) {
		addDefaultCatalysts(registry);
	}

	@SuppressWarnings("ConstantConditions")
	public static void addDefaultCatalysts(IModRegistry registry) {
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.iron_furnace), VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.electric_furnace), VanillaRecipeCategoryUid.SMELTING);
	}
}
