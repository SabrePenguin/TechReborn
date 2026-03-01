package com.sabrepenguin.techreborn.jei;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.jei.categories.AlloyCategory;
import com.sabrepenguin.techreborn.jei.wrappers.AlloyWrapper;
import com.sabrepenguin.techreborn.recipe.AlloyRecipe;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;

import java.util.Collection;
import java.util.stream.Collectors;

@JEIPlugin
public class TRRecipePlugin implements IModPlugin {

	public static final String ALLOY_UID = Tags.MODID + ".alloy.furnace";

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		registry.addRecipeCategories(new AlloyCategory(registry.getJeiHelpers().getGuiHelper(), ALLOY_UID));
	}

	@Override
	public void register(IModRegistry registry) {
		Collection<AlloyWrapper> alloyWrappers = RegistryHandler.instance().getAlloyRegistry().getRecipes().stream()
						.map(AlloyWrapper::new).collect(Collectors.toList());
		registry.addRecipes(alloyWrappers, ALLOY_UID);

		addDefaultCatalysts(registry);
		addCatalysts(registry);
	}

	@SuppressWarnings("ConstantConditions")
	public static void addDefaultCatalysts(IModRegistry registry) {
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.iron_furnace), VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.electric_furnace), VanillaRecipeCategoryUid.SMELTING);
	}

	@SuppressWarnings("ConstantConditions")
	public static void addCatalysts(IModRegistry registry) {
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.iron_alloy_furnace), ALLOY_UID);
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.alloy_smelter), ALLOY_UID);
	}
}
