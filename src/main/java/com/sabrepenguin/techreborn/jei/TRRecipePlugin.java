package com.sabrepenguin.techreborn.jei;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.jei.categories.*;
import com.sabrepenguin.techreborn.jei.wrappers.*;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.fluids.FluidRecipeWrapper;
import com.sabrepenguin.techreborn.util.ModLoadedUtil;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@JEIPlugin
public class TRRecipePlugin implements IModPlugin {

	public static final String ALLOY_UID = "tile." + Tags.MODID + ".alloy_smelter.name";
	public static final String GRINDER_UID = "tile." + Tags.MODID + ".grinder.name";
	public static final String EXTRACTOR_UID = "tile." + Tags.MODID + ".extractor.name";
	public static final String PLATE_BENDING_UID = "tile." + Tags.MODID + ".plate_bending_machine.name";
	public static final String RECYCLER_UID = "tile." + Tags.MODID + ".recycler.name";
	public static final String WIRE_MILL_UID = "tile." + Tags.MODID + ".wire_mill.name";
	public static final String COMPRESSOR_UID = "tile." + Tags.MODID + ".compressor.name";
	public static final String ASSEMBLING_MACHINE_UID = "tile." + Tags.MODID + ".assembling_machine.name";
	public static final String SCRAPBOXINATOR_UID = "tile." + Tags.MODID + ".scrapboxinator.name";
	public static final String SOLID_CANNING_MACHINE_UID = "tile." + Tags.MODID + ".solid_canning_machine.name";
	public static final String CHEMICAL_REACTOR_UID = "tile." + Tags.MODID + ".chemical_reactor.name";
	public static final String ROLLING_MACHINE_UID = "tile." + Tags.MODID + ".rolling_machine.name";
	public static final String INDUSTRIAL_CENTRIFUGE_UID = "tile." + Tags.MODID + ".industrial_centrifuge.name";
	public static final String INDUSTRIAL_ELECTROLYZER_UID = "tile." + Tags.MODID + ".industrial_electrolyzer.name";
	public static final String DISTILLATION_TOWER_UID = "tile." + Tags.MODID + ".distillation_tower.name";
	public static final String SAWMILL_UID = "tile." + Tags.MODID + ".industrial_sawmill.name";

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IGuiHelper helper = registry.getJeiHelpers().getGuiHelper();
		JEITextures.init(helper);
		registry.addRecipeCategories(new TwoToOneCategory(helper, ALLOY_UID));
		registry.addRecipeCategories(new OneToOneCategory(helper, GRINDER_UID));
		registry.addRecipeCategories(new OneToOneCategory(helper, EXTRACTOR_UID));
		registry.addRecipeCategories(new OneToOneCategory(helper, PLATE_BENDING_UID));
		registry.addRecipeCategories(new SingleItemCategory(helper, RECYCLER_UID, 58));
		registry.addRecipeCategories(new OneToOneCategory(helper, WIRE_MILL_UID));
		registry.addRecipeCategories(new OneToOneCategory(helper, COMPRESSOR_UID));
		registry.addRecipeCategories(new TwoToOneCategory(helper, ASSEMBLING_MACHINE_UID));
		registry.addRecipeCategories(new ScrapboxCategory(helper, SCRAPBOXINATOR_UID));
		registry.addRecipeCategories(new TwoToOneCategory(helper, SOLID_CANNING_MACHINE_UID));
		registry.addRecipeCategories(new TwoToOneCategory(helper, CHEMICAL_REACTOR_UID));
		registry.addRecipeCategories(new RollingMachineCategory(helper, ROLLING_MACHINE_UID));
		registry.addRecipeCategories(new IndustrialCentrifugeCategory(helper, INDUSTRIAL_CENTRIFUGE_UID));
		registry.addRecipeCategories(new IndustrialElectrolyzerCategory(helper, INDUSTRIAL_ELECTROLYZER_UID));
		registry.addRecipeCategories(new IndustrialElectrolyzerCategory(helper, DISTILLATION_TOWER_UID));
		registry.addRecipeCategories(new SawmillCategory(helper, SAWMILL_UID));
	}

	@Override
	public void register(IModRegistry registry) {
		registry.handleRecipes(
				FluidRecipeWrapper.class,
				FluidRecipeJEIWrapper::new,
				VanillaRecipeCategoryUid.CRAFTING
		);

		RegistryHandler handler = RegistryHandler.instance();

		Collection<ITRRecipeWrapper> alloyWrappers = handler.getAlloyRegistry().getRecipes().stream()
						.map(ITRRecipeWrapper::new).collect(Collectors.toList());
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
		if (ModLoadedUtil.IC2_LOADED && !TechRebornConfig.machineConfig.recycler.ic2Scrap) {
			registry.addRecipes(Arrays.asList(new RecyclerRecipeWrapper(SingleItemRecipeWrapper.SCRAP_INPUT, SingleItemRecipeWrapper.SCRAP, 45, 8)), RECYCLER_UID);
		}
		registry.addRecipes(
				handler.getWireMillRegistry().getRecipes().stream().map(OneToOneRecipeWrapper::new).collect(Collectors.toList()),
				WIRE_MILL_UID);
		registry.addRecipes(
				handler.getCompressorRegistry().getRecipes().stream().map(OneToOneRecipeWrapper::new).collect(Collectors.toList()),
				COMPRESSOR_UID);
		registry.addRecipes(
				handler.getAssemblingMachineRegistry().getRecipes().stream().map(ITRRecipeWrapper::new).collect(Collectors.toList()),
				ASSEMBLING_MACHINE_UID
		);
		registry.addRecipes(
				handler.getChemicalReactorRegistry().getRecipes().stream().map(ITRRecipeWrapper::new).collect(Collectors.toList()),
				CHEMICAL_REACTOR_UID
		);
		registry.addRecipes(
				handler.getSolidCanningMachineRegistry().getRecipes().stream().map(ITRRecipeWrapper::new).collect(Collectors.toList()),
				SOLID_CANNING_MACHINE_UID
		);
		registry.addRecipes(
				ScrapboxRecipeWrapper.buildRecipes(handler.getScrapboxRegistry().getRecipes()),
				SCRAPBOXINATOR_UID
		);
		registry.addRecipes(
				handler.getRollingMachineRegistry().getRecipes().stream().map(ITRRecipeWrapper::new).collect(Collectors.toList()),
				ROLLING_MACHINE_UID
		);
		registry.addRecipes(
				handler.getIndustrialCentrifugeRegistry().getRecipes().stream().map(ITRRecipeWrapper::new).collect(Collectors.toList()),
				INDUSTRIAL_CENTRIFUGE_UID
		);
		registry.addRecipes(
				handler.getIndustrialElectrolyzerRegistry().getRecipes().stream().map(ITRRecipeWrapper::new).collect(Collectors.toList()),
				INDUSTRIAL_ELECTROLYZER_UID
		);
		registry.addRecipes(
				handler.getDistillationTowerRegistry().getRecipes().stream().map(ITRRecipeWrapper::new).collect(Collectors.toList()),
				DISTILLATION_TOWER_UID
		);
		registry.addRecipes(
				handler.getSawmillRegistry().getRecipes().stream().map(ITRRecipeWrapper::new).collect(Collectors.toList()),
				SAWMILL_UID
		);
		addDefaultCatalysts(registry);
		addCatalysts(registry);
	}

	@SuppressWarnings("ConstantConditions")
	public static void addDefaultCatalysts(IModRegistry registry) {
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.iron_furnace), VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.electric_furnace), VanillaRecipeCategoryUid.SMELTING);
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.auto_crafting_table), VanillaRecipeCategoryUid.CRAFTING);
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
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.assembling_machine), ASSEMBLING_MACHINE_UID);
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.scrapboxinator), SCRAPBOXINATOR_UID);
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.solid_canning_machine), SOLID_CANNING_MACHINE_UID);
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.chemical_reactor), CHEMICAL_REACTOR_UID);
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.rolling_machine), ROLLING_MACHINE_UID);
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.industrial_centrifuge), INDUSTRIAL_CENTRIFUGE_UID);
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.industrial_electrolyzer), INDUSTRIAL_ELECTROLYZER_UID);
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.distillation_tower), DISTILLATION_TOWER_UID);
		registry.addRecipeCatalyst(new ItemStack(TRBlocks.industrial_sawmill), SAWMILL_UID);
	}
}
