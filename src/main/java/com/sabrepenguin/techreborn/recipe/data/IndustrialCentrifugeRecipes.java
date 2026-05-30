package com.sabrepenguin.techreborn.recipe.data;

import com.sabrepenguin.techreborn.blocks.fluids.TRFluids;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.*;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.builders.MultiOutputBuilder;
import com.sabrepenguin.techreborn.recipe.registries.ITRRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

public class IndustrialCentrifugeRecipes {
	@SuppressWarnings("ConstantConditions")
	public static void init() {
		final ITRRegistry registry = RegistryHandler.instance().getIndustrialCentrifugeRegistry();

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(TRItems.cell.getCellWithFluid(FluidRegistry.getFluid("lava")), 16)
				.addOutput(new ItemStack(TRItems.ingot, 18, Ingot.IngotMeta.tin.metadata()))
				.addOutput(new ItemStack(TRItems.ingot, 4, Ingot.IngotMeta.copper.metadata()))
				.addOutput(new ItemStack(TRItems.ingot, 1, Ingot.IngotMeta.electrum.metadata()))
				.addOutput(new ItemStack(TRItems.smalldust, 1, DustSmall.DustSmallMeta.tungsten.metadata()))
				.setRecipeTime(15000)
				.setEnergyCost(20)
				.build());
		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.MAGMA_CREAM))
				.addOutput(Items.SLIME_BALL, 1)
				.addOutput(Items.BLAZE_POWDER, 1)
				.setRecipeTime(500)
				.setEnergyCost(20)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(TRItems.part, 4, Part.PartMeta.sap.metadata()))
				.addOutput(new ItemStack(TRItems.part, 14, Part.PartMeta.rubber.metadata()))
				.addOutput(new ItemStack(TRItems.part, 1, Part.PartMeta.plantball.metadata()))
				.addOutput(new ItemStack(TRItems.part, 1, Part.PartMeta.compressed_plantball.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(1300)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("logRubber", 16)
				.addInput(new ItemStack(TRItems.cell, 5))
				.addOutput(new ItemStack(TRItems.part, 8, Part.PartMeta.sap.metadata()))
				.addOutput(new ItemStack(TRItems.part, 6, Part.PartMeta.plantball.metadata()))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.CARBON), 4)
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dirt", 16)
				.addOutput(new ItemStack(Blocks.SAND, 8))
				.addOutput(new ItemStack(Items.CLAY_BALL, 1))
				.addOutput(new ItemStack(TRItems.part, 1, Part.PartMeta.plantball.metadata()))
				.addOutput(new ItemStack(TRItems.part, 1, Part.PartMeta.compressed_plantball.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(2500)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("grass", 16)
				.addOutput(new ItemStack(Blocks.SAND, 8))
				.addOutput(new ItemStack(Items.CLAY_BALL, 1))
				.addOutput(new ItemStack(TRItems.part, 2, Part.PartMeta.plantball.metadata()))
				.addOutput(new ItemStack(TRItems.part, 2, Part.PartMeta.compressed_plantball.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(2500)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Blocks.SOUL_SAND, 16))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(new ItemStack(Blocks.SAND, 10))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.OIL), 1)
				.addOutput(new ItemStack(TRItems.dust, 4, Dust.MetaDust.saltpeter.metadata()))
				.addOutput(new ItemStack(TRItems.dust, 1, Dust.MetaDust.coal.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(2500)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Blocks.MYCELIUM, 8))
				.addOutput(new ItemStack(Blocks.SAND, 4))
				.addOutput(new ItemStack(Blocks.BROWN_MUSHROOM, 2))
				.addOutput(new ItemStack(Blocks.RED_MUSHROOM, 2))
				.addOutput(new ItemStack(Items.CLAY_BALL, 1))
				.setEnergyCost(20)
				.setRecipeTime(1640)
				.build());

		// Dusts
		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustRedstone", 10)
				.addInput(new ItemStack(TRItems.cell, 4))
				.addOutput(new ItemStack(TRItems.dust, 5, Dust.MetaDust.pyrite.metadata()))
				.addOutput(new ItemStack(TRItems.dust, 1, Dust.MetaDust.ruby.metadata()))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.MERCURY), 3)
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.SILICON), 1)
				.setEnergyCost(20)
				.setRecipeTime(7000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustGlowstone", 16)
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(new ItemStack(Items.REDSTONE, 8))
				.addOutput(new ItemStack(TRItems.dust, 8, Dust.MetaDust.gold.metadata()))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.HELIUM), 1)
				.setEnergyCost(20)
				.setRecipeTime(25000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("gemLapis", 4)
				.addOutput(new ItemStack(TRItems.dust, 3, Dust.MetaDust.lazurite.metadata()))
				.addOutput(new ItemStack(TRItems.smalldust, 2, DustSmall.DustSmallMeta.sodalite.metadata()))
				.addOutput(new ItemStack(TRItems.smalldust, 1, DustSmall.DustSmallMeta.pyrite.metadata()))
				.addOutput(new ItemStack(TRItems.smalldust, 1, DustSmall.DustSmallMeta.calcite.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(1500)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustBasalt", 16)
				.addOutput(new ItemStack(TRItems.dust, 8, Dust.MetaDust.flint.metadata()))
				.addOutput(new ItemStack(TRItems.dust, 4, Dust.MetaDust.dark_ashes.metadata()))
				.addOutput(new ItemStack(TRItems.dust, 3, Dust.MetaDust.calcite.metadata()))
				.addOutput(new ItemStack(TRItems.dust, 1, Dust.MetaDust.peridot.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(2040)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustBrass", 1)
				.addOutput(new ItemStack(TRItems.smalldust, 3, DustSmall.DustSmallMeta.copper.metadata()))
				.addOutput(new ItemStack(TRItems.smalldust, 1, DustSmall.DustSmallMeta.zinc.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(1500)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustBronze", 1)
				.addOutput(new ItemStack(TRItems.smalldust, 6, DustSmall.DustSmallMeta.copper.metadata()))
				.addOutput(new ItemStack(TRItems.smalldust, 2, DustSmall.DustSmallMeta.tin.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(1500)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustCopper", 3)
				.addOutput(new ItemStack(TRItems.smalldust, 1, DustSmall.DustSmallMeta.gold.metadata()))
				.addOutput(new ItemStack(TRItems.smalldust, 1, DustSmall.DustSmallMeta.nickel.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(2400)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustDarkAshes", 2)
				.addOutput(new ItemStack(TRItems.dust, 1, Dust.MetaDust.ashes.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(240)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustElectrum", 1)
				.addOutput(new ItemStack(TRItems.smalldust, 2, DustSmall.DustSmallMeta.gold.metadata()))
				.addOutput(new ItemStack(TRItems.smalldust, 2, DustSmall.DustSmallMeta.silver.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(960)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustEnderEye", 2)
				.addOutput(new ItemStack(Items.BLAZE_POWDER, 1))
				.addOutput(new ItemStack(TRItems.dust, 1, Dust.MetaDust.ender_pearl.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(1840)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustEndstone", 16)
				.addInput(new ItemStack(TRItems.cell, 2))
				.addOutput(new ItemStack(Blocks.SAND, 12))
				.addOutput(new ItemStack(TRItems.smalldust, 1, DustSmall.DustSmallMeta.tungsten.metadata()))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.HELIUM), 1)
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.HELIUM3), 1)
				.setEnergyCost(20)
				.setRecipeTime(4800)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustGold", 3)
				.addOutput(new ItemStack(TRItems.smalldust, 1, DustSmall.DustSmallMeta.copper.metadata()))
				.addOutput(new ItemStack(TRItems.smalldust, 1, DustSmall.DustSmallMeta.nickel.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(2400)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustInvar", 3)
				.addOutput(new ItemStack(TRItems.dust, 2, Dust.MetaDust.iron.metadata()))
				.addOutput(new ItemStack(TRItems.dust, 1, Dust.MetaDust.nickel.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(1000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustIron", 2)
				.addOutput(new ItemStack(TRItems.smalldust, 1, DustSmall.DustSmallMeta.nickel.metadata()))
				.addOutput(new ItemStack(TRItems.smalldust, 1, DustSmall.DustSmallMeta.tin.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(1500)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustLead", 2)
				.addOutput(new ItemStack(TRItems.smalldust, 1, DustSmall.DustSmallMeta.silver.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(2400)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustMarble", 2)
				.addOutput(new ItemStack(TRItems.dust, 7, Dust.MetaDust.calcite.metadata()))
				.addOutput(new ItemStack(TRItems.dust, 1, Dust.MetaDust.magnesium.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(1040)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustNetherrack", 16)
				.addOutput(new ItemStack(TRItems.dust, 4, Dust.MetaDust.sulfur.metadata()))
				.addOutput(new ItemStack(Items.REDSTONE, 1))
				.addOutput(new ItemStack(TRItems.dust, 1, Dust.MetaDust.coal.metadata()))
				.addOutput(new ItemStack(Items.GOLD_NUGGET, 1))
				.setEnergyCost(20)
				.setRecipeTime(2400)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustNickel", 3)
				.addOutput(new ItemStack(TRItems.smalldust, 1, DustSmall.DustSmallMeta.copper.metadata()))
				.addOutput(new ItemStack(TRItems.smalldust, 1, DustSmall.DustSmallMeta.gold.metadata()))
				.addOutput(new ItemStack(TRItems.smalldust, 1, DustSmall.DustSmallMeta.iron.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(3440)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustPlatinum", 1)
				.addOutput(new ItemStack(TRItems.nuggets, 1, Nugget.NuggetMeta.iridium.metadata()))
				.addOutput(new ItemStack(TRItems.smalldust, 1, DustSmall.DustSmallMeta.nickel.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(3000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustRedGarnet", 16)
				.addOutput(new ItemStack(TRItems.dust, 8, Dust.MetaDust.spessartine.metadata()))
				.addOutput(new ItemStack(TRItems.dust, 5, Dust.MetaDust.almandine.metadata()))
				.addOutput(new ItemStack(TRItems.dust, 3, Dust.MetaDust.pyrope.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(3000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustSilver", 2)
				.addOutput(new ItemStack(TRItems.smalldust, 1, DustSmall.DustSmallMeta.lead.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(2400)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustTin", 2)
				.addOutput(new ItemStack(TRItems.smalldust, 1, DustSmall.DustSmallMeta.iron.metadata()))
				.addOutput(new ItemStack(TRItems.smalldust, 1, DustSmall.DustSmallMeta.zinc.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(2100)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustYellowGarnet", 16)
				.addOutput(new ItemStack(TRItems.dust, 8, Dust.MetaDust.grossular.metadata()))
				.addOutput(new ItemStack(TRItems.dust, 5, Dust.MetaDust.andradite.metadata()))
				.addOutput(new ItemStack(TRItems.dust, 3, Dust.MetaDust.uvarovite.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(3500)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput("dustZinc", 1)
				.addOutput(new ItemStack(TRItems.smalldust, 1, DustSmall.DustSmallMeta.tin.metadata()))
				.setEnergyCost(20)
				.setRecipeTime(1040)
				.build());

		// Cells
		registry.addRecipe(new MultiOutputBuilder()
				.addInput(TRItems.cell.getCellWithFluid(TRFluids.HYDROGEN), 4)
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.DEUTERIUM), 1)
				.addOutput(new ItemStack(TRItems.cell, 3))
				.setEnergyCost(20)
				.setRecipeTime(3000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(TRItems.cell.getCellWithFluid(TRFluids.DEUTERIUM), 4)
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.TRITIUM), 1)
				.addOutput(new ItemStack(TRItems.cell, 3))
				.setEnergyCost(20)
				.setRecipeTime(3000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(TRItems.cell.getCellWithFluid(TRFluids.HELIUM), 16)
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.HELIUM3), 1)
				.addOutput(new ItemStack(TRItems.cell, 15))
				.setEnergyCost(20)
				.setRecipeTime(10000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(TRItems.cell.getCellWithFluid(TRFluids.CALCIUM_CARBONATE), 1)
				.addOutput(new ItemStack(TRItems.dust, 1, Dust.MetaDust.calcite.metadata()))
				.addOutput(new ItemStack(TRItems.cell, 1))
				.setEnergyCost(20)
				.setRecipeTime(40)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(TRItems.cell.getCellWithFluid(TRFluids.SULFUR), 1)
				.addOutput(new ItemStack(TRItems.dust, 1, Dust.MetaDust.sulfur.metadata()))
				.addOutput(new ItemStack(TRItems.cell, 1))
				.setEnergyCost(20)
				.setRecipeTime(40)
				.build());

		// Methane
		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.GOLDEN_APPLE, 1, 1))
				.addInput(new ItemStack(TRItems.cell, 2))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 2)
				.addOutput(new ItemStack(Items.GOLD_INGOT, 64))
				.setEnergyCost(20)
				.setRecipeTime(10000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.GOLDEN_APPLE, 1))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.addOutput(new ItemStack(Items.GOLD_NUGGET, 6))
				.setEnergyCost(20)
				.setRecipeTime(10000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.GOLDEN_CARROT, 1))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.addOutput(new ItemStack(Items.GOLD_NUGGET, 6))
				.setEnergyCost(20)
				.setRecipeTime(10000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.SPECKLED_MELON, 1))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.addOutput(new ItemStack(Items.GOLD_NUGGET, 6))
				.setEnergyCost(20)
				.setRecipeTime(10000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.APPLE, 32))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.MUSHROOM_STEW, 16))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.BREAD, 64))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.PORKCHOP, 12))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.COOKED_PORKCHOP, 16))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.BEEF, 12))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.COOKED_BEEF, 16))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.CHICKEN, 12))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.COOKED_CHICKEN, 16))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.MUTTON, 12))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.COOKED_MUTTON, 16))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.RABBIT, 12))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.COOKED_RABBIT, 16))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.FISH, 12))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.COOKED_FISH, 16))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.MELON, 64))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Blocks.PUMPKIN, 16))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.ROTTEN_FLESH, 16))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.SPIDER_EYE, 32))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.CARROT, 16))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.POTATO, 16))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.POISONOUS_POTATO, 12))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.BAKED_POTATO, 24))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.BEETROOT, 12))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.BEETROOT_SOUP, 16))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.COOKIE, 64))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.CAKE, 8))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Blocks.BROWN_MUSHROOM_BLOCK, 12))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Blocks.RED_MUSHROOM_BLOCK, 12))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Blocks.BROWN_MUSHROOM, 12))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Blocks.RED_MUSHROOM, 12))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());

		registry.addRecipe(new MultiOutputBuilder()
				.addInput(new ItemStack(Items.NETHER_WART, 32))
				.addInput(new ItemStack(TRItems.cell, 1))
				.addOutput(TRItems.cell.getCellWithFluid(TRFluids.METHANE), 1)
				.setEnergyCost(20)
				.setRecipeTime(5000)
				.build());
	}
}
