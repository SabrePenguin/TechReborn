package com.sabrepenguin.techreborn.jei;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.jei.categories.AlloyCategory;
import com.sabrepenguin.techreborn.jei.categories.OneToOneCategory;
import com.sabrepenguin.techreborn.jei.categories.SingleItemCategory;
import com.sabrepenguin.techreborn.jei.wrappers.AlloyWrapper;
import com.sabrepenguin.techreborn.jei.wrappers.OneToOneRecipeWrapper;
import com.sabrepenguin.techreborn.jei.wrappers.FluidRecipeJEIWrapper;
import com.sabrepenguin.techreborn.jei.wrappers.SingleItemRecipeWrapper;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.fluids.FluidRecipeWrapper;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@JEIPlugin
public class TRRecipePlugin implements IModPlugin {

	public static final String ALLOY_UID = Tags.MODID + ".alloy.furnace";
	public static final String GRINDER_UID = "tile." + Tags.MODID + ".grinder.name";
	public static final String EXTRACTOR_UID = "tile." + Tags.MODID + ".extractor.name";
	public static final String PLATE_BENDING_UID = "tile." + Tags.MODID + ".plate_bending_machine.name";
	public static final String RECYCLER_UID = "tile." + Tags.MODID + ".recycler.name";
	public static final String WIRE_MILL_UID = "tile." + Tags.MODID + ".wire_mill.name";
	public static final String COMPRESSOR_UID = "tile." + Tags.MODID + ".compressor.name";

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IGuiHelper helper = registry.getJeiHelpers().getGuiHelper();
		JEITextures.init(helper);
		registry.addRecipeCategories(new AlloyCategory(helper, ALLOY_UID));
		registry.addRecipeCategories(new OneToOneCategory(helper, GRINDER_UID));
		registry.addRecipeCategories(new OneToOneCategory(helper, EXTRACTOR_UID));
		registry.addRecipeCategories(new OneToOneCategory(helper, PLATE_BENDING_UID));
		registry.addRecipeCategories(new SingleItemCategory(helper, RECYCLER_UID));
		registry.addRecipeCategories(new OneToOneCategory(helper, WIRE_MILL_UID));
		registry.addRecipeCategories(new OneToOneCategory(helper, COMPRESSOR_UID));
	}

	@Override
	public void register(IModRegistry registry) {
		registry.handleRecipes(
				FluidRecipeWrapper.class,
				FluidRecipeJEIWrapper::new,
				VanillaRecipeCategoryUid.CRAFTING
		);

		RegistryHandler handler = RegistryHandler.instance();

		Collection<AlloyWrapper> alloyWrappers = handler.getAlloyRegistry().getRecipes().stream()
						.map(AlloyWrapper::new).collect(Collectors.toList());
		registry.addRecipes(alloyWrappers, ALLOY_UID);

		Collection<OneToOneRecipeWrapper> grinderWrappers = handler.getGrinderRegistry().getRecipes().stream()
						.map(OneToOneRecipeWrapper::new).collect(Collectors.toList());
		registry.addRecipes(grinderWrappers, GRINDER_UID);
		registry.addRecipes(
				handler.getExtractorRegistry().getRecipes().stream().map(OneToOneRecipeWrapper::new).collect(Collectors.toList()),
				EXTRACTOR_UID);
		registry.addRecipes(
				handler.getPlateBenderRegistry().getRecipes().stream().map(OneToOneRecipeWrapper::new).collect(Collectors.toList()),
				PLATE_BENDING_UID);
		registry.addRecipes(Arrays.asList(new SingleItemRecipeWrapper(SingleItemRecipeWrapper.SCRAP_INPUT, SingleItemRecipeWrapper.SCRAP, 45, 8)), RECYCLER_UID);
		registry.addRecipes(
				handler.getWireMillRegistry().getRecipes().stream().map(OneToOneRecipeWrapper::new).collect(Collectors.toList()),
				WIRE_MILL_UID);
		registry.addRecipes(
				handler.getCompressorRegistry().getRecipes().stream().map(OneToOneRecipeWrapper::new).collect(Collectors.toList()),
				COMPRESSOR_UID);
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
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.grinder), GRINDER_UID);
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.extractor), EXTRACTOR_UID);
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.plate_bending_machine), PLATE_BENDING_UID);
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.recycler), RECYCLER_UID);
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.wire_mill), WIRE_MILL_UID);
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.compressor), COMPRESSOR_UID);
	}
}
