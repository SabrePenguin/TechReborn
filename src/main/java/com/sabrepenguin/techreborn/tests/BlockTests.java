package com.sabrepenguin.techreborn.tests;

import com.gtnewhorizons.horizonqa.api.GameTestHelper;
import com.gtnewhorizons.horizonqa.api.annotation.GameTest;
import com.gtnewhorizons.horizonqa.api.annotation.GameTestHolder;
import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.BlockCable;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.blocks.fluids.TRFluids;
import com.sabrepenguin.techreborn.blocks.meta.OreBlock;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.Dust;
import com.sabrepenguin.techreborn.items.materials.Ingot;
import com.sabrepenguin.techreborn.items.materials.Part;
import com.sabrepenguin.techreborn.items.materials.Plate;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

@GameTestHolder(Tags.MODID)
@SuppressWarnings({"ConstantConditions", "unused"})
public class BlockTests {
	// TODO: Run through all recipes

	@GameTest(template = "grinder", timeoutTicks = 400)
	public static void machineTestFail(GameTestHelper helper) {
		TechRebornHelper trHelper = new TechRebornHelper(helper);
		BlockPos target = new BlockPos(0, 0, 0);
		helper.assertBlockPresent(TRBlocks.grinder.getDefaultState(), target);
		helper.insertItem(target, new ItemStack(TRBlocks.ore, 1, OreBlock.Ore.LEAD.meta()), 0);
		trHelper.insertPower(target, 2300);
		helper.onEachTick(() -> helper.assertSlot(target, 1, ItemStack.EMPTY));
		helper.succeedAtTimeout();
	}

	@GameTest(template = "grinder", timeoutTicks = 310)
	public static void grinderTest(GameTestHelper helper) {
		TechRebornHelper trHelper = new TechRebornHelper(helper);
		BlockPos target = new BlockPos(0, 0, 0);

		ItemStack targetResult = new ItemStack(TRItems.dust, 2, Dust.MetaDust.lead.metadata());

		helper.assertBlockPresent(TRBlocks.grinder.getDefaultState(), target);
		helper.insertItem(target, new ItemStack(TRBlocks.ore, 1, OreBlock.Ore.LEAD.meta()), 0);
		trHelper.insertPower(target, 2400);
		helper.succeedWhen(() -> trHelper.itemEquals(target, targetResult, 1));
	}

	@GameTest(template = "extractor", timeoutTicks = 410)
	public static void extractorTest(GameTestHelper helper) {
		TechRebornHelper trHelper = new TechRebornHelper(helper);
		BlockPos target = new BlockPos(0, 0, 0);

		ItemStack targetStack = new ItemStack(TRItems.part, 1, Part.PartMeta.rubber.metadata());

		helper.assertBlockPresent(TRBlocks.extractor.getDefaultState(), target);
		helper.insertItem(target, new ItemStack(TRBlocks.rubber_sapling), 0);
		trHelper.insertPower(target, 3200);
		helper.succeedWhen(() -> trHelper.itemEquals(target, targetStack, 1));
	}

	@GameTest(template = "plateBendingMachine", timeoutTicks = 110)
	public static void plateBendingTest(GameTestHelper helper) {
		TechRebornHelper trHelper = new TechRebornHelper(helper);
		BlockPos target = new BlockPos(0, 0, 0);

		ItemStack targetStack = new ItemStack(TRItems.plates, 1, Plate.PlateMeta.refined_iron.metadata());

		helper.assertBlockPresent(TRBlocks.plate_bending_machine.getDefaultState(), target);
		helper.insertItem(target, new ItemStack(TRItems.ingot, 1, Ingot.IngotMeta.refined_iron.metadata()), 0);
		trHelper.insertPower(target, 3200);
		helper.succeedWhen(() -> trHelper.itemEquals(target, targetStack, 1));
	}

	@GameTest(template = "wireMill", timeoutTicks = 210)
	public static void wireMillTest(GameTestHelper helper) {
		TechRebornHelper trHelper = new TechRebornHelper(helper);
		BlockPos target = new BlockPos(0, 0, 0);

		ItemStack targetStack = new ItemStack(TRBlocks.cable, 6, BlockCable.CableEnum.GOLD.metadata());

		helper.assertBlockPresent(TRBlocks.wire_mill.getDefaultState(), target);
		helper.insertItem(target, new ItemStack(Items.GOLD_INGOT), 0);
		trHelper.insertPower(target, 800);
		helper.succeedWhen(() -> trHelper.itemEquals(target, targetStack, 1));
	}

	@GameTest(template = "compressor", timeoutTicks = 410)
	public static void compressorTest(GameTestHelper helper) {
		TechRebornHelper trHelper = new TechRebornHelper(helper);
		BlockPos target = new BlockPos(0, 0, 0);

		ItemStack targetStack = new ItemStack(TRItems.plates, 1, Plate.PlateMeta.carbon.metadata());

		helper.assertBlockPresent(TRBlocks.compressor.getDefaultState(), target);
		helper.insertItem(target, new ItemStack(TRItems.part, 1, Part.PartMeta.carbon_mesh.metadata()), 0);
		trHelper.insertPower(target, 3200);
		helper.succeedWhen(() -> trHelper.itemEquals(target, targetStack, 1));
	}

	@GameTest(template = "scrapboxinator")
	public static void scrapboxinatorTest(GameTestHelper helper) {
		TechRebornHelper trHelper = new TechRebornHelper(helper);
		BlockPos target = new BlockPos(0, 0, 0);

		helper.assertBlockPresent(TRBlocks.scrapboxinator.getDefaultState(), target);
		helper.insertItem(target, new ItemStack(TRItems.scrapbox), 0);
		trHelper.insertPower(target, 3200);
		helper.succeedWhen(() -> trHelper.itemExists(target, 1));
	}

	@GameTest(template = "assemblingMachine", timeoutTicks = 810)
	public static void assemblingMachineTest(GameTestHelper helper) {
		TechRebornHelper trHelper = new TechRebornHelper(helper);
		BlockPos target = new BlockPos(0, 0, 0);
		ItemStack targetResult = new ItemStack(TRItems.part, 2, Part.PartMeta.advanced_circuit_parts.metadata());

		helper.assertBlockPresent(TRBlocks.assembling_machine.getDefaultState(), target);
		helper.insertItem(target, new ItemStack(Items.DYE, 1, 4), 0);
		helper.insertItem(target, new ItemStack(TRItems.dust, 1, Dust.MetaDust.electrum.metadata()), 1);
		trHelper.insertPower(target, 6400);
		helper.succeedWhen(() -> trHelper.itemEquals(target, targetResult, 2));
	}

	@GameTest(template = "canningMachine", timeoutTicks = 110)
	public static void canningMachineTest(GameTestHelper helper) {
		TechRebornHelper trHelper = new TechRebornHelper(helper);
		BlockPos target = new BlockPos(0, 0, 0);
		ItemStack targetResult = new ItemStack(TRItems.part, 1, Part.PartMeta.bio_cell.metadata());

		helper.assertBlockPresent(TRBlocks.solid_canning_machine.getDefaultState(), target);
		helper.insertItem(target, new ItemStack(TRItems.part, 1, Part.PartMeta.compressed_plantball.metadata()), 0);
		helper.insertItem(target, new ItemStack(TRItems.cell), 1);
		trHelper.insertPower(target, 400);
		helper.succeedWhen(() -> trHelper.itemEquals(target, targetResult, 2));
	}

	@GameTest(template = "chemicalReactor")
	public static void chemicalReactorTest(GameTestHelper helper) {
		TechRebornHelper trHelper = new TechRebornHelper(helper);
		BlockPos target = new BlockPos(0, 0, 0);
		ItemStack targetResult = TechRebornHelper.getFluidWithCount(TRFluids.SODIUM_SULFIDE, 2);

		helper.assertBlockPresent(TRBlocks.chemical_reactor.getDefaultState(), target);
		helper.insertItem(target, TechRebornHelper.getFluidWithCount(TRFluids.SULFUR, 1), 0);
		helper.insertItem(target, TechRebornHelper.getFluidWithCount(TRFluids.SODIUM, 1), 1);
		trHelper.insertPower(target, 12000);
		helper.succeedWhen(() -> trHelper.itemEquals(target, targetResult, 2));
	}

	@GameTest(template = "autoCraftingTable", timeoutTicks = 120)
	public static void autoCraftingTableTest(GameTestHelper helper) {
		TechRebornHelper trHelper = new TechRebornHelper(helper);
		BlockPos target = new BlockPos(0, 0, 0);
		ItemStack targetResult = new ItemStack(Items.CAKE);

		helper.assertBlockPresent(TRBlocks.auto_crafting_table.getDefaultState(), target);
		helper.insertItem(target, new ItemStack(Items.MILK_BUCKET), 0);
		helper.insertItem(target, new ItemStack(Items.MILK_BUCKET), 1);
		helper.insertItem(target, new ItemStack(Items.MILK_BUCKET), 2);
		helper.insertItem(target, new ItemStack(Items.SUGAR), 3);
		helper.insertItem(target, new ItemStack(Items.EGG), 4);
		helper.insertItem(target, new ItemStack(Items.SUGAR), 5);
		helper.insertItem(target, new ItemStack(Items.WHEAT), 6);
		helper.insertItem(target, new ItemStack(Items.WHEAT), 7);
		helper.insertItem(target, new ItemStack(Items.WHEAT), 8);
		trHelper.insertPower(target, 4800);
		helper.succeedWhen(() -> trHelper.itemEquals(target, targetResult, 9) &&
				trHelper.itemEquals(target, new ItemStack(Items.BUCKET), 0) &&
				trHelper.itemEquals(target, new ItemStack(Items.BUCKET), 1) &&
				trHelper.itemEquals(target, new ItemStack(Items.BUCKET), 2));
	}

	@GameTest(template = "rollingMachine", timeoutTicks = 250)
	public static void rolllingMachineTest(GameTestHelper helper) {
		TechRebornHelper trHelper = new TechRebornHelper(helper);
		BlockPos target = new BlockPos(0, 0, 0);
		ItemStack nickel = new ItemStack(TRItems.ingot, 1, Ingot.IngotMeta.nickel.metadata());
		ItemStack copper = new ItemStack(TRItems.ingot, 1, Ingot.IngotMeta.copper.metadata());
		ItemStack targetResult = new ItemStack(TRItems.part, 3, Part.PartMeta.cupronickel_heating_coil.metadata());

		helper.assertBlockPresent(TRBlocks.rolling_machine.getDefaultState(), target);
		helper.insertItem(target, nickel, 0);
		helper.insertItem(target, copper, 1);
		helper.insertItem(target, nickel, 2);
		helper.insertItem(target, copper, 3);
		helper.insertItem(target, copper, 5);
		helper.insertItem(target, nickel, 6);
		helper.insertItem(target, copper, 7);
		helper.insertItem(target, nickel, 8);
		trHelper.insertPower(target, 10000);
		helper.succeedWhen(() -> trHelper.itemEquals(target, targetResult, 9));
	}

	@GameTest(template = "rollingMachine", timeoutTicks = 50)
	public static void balanceTest(GameTestHelper helper) {
		TechRebornHelper trHelper = new TechRebornHelper(helper);
		ItemStack targetResult = new ItemStack(Items.GOLD_INGOT, 32);
		BlockPos target = new BlockPos(0, 0, 0);

		trHelper.lockMachine(target);
		helper.assertBlockPresent(TRBlocks.rolling_machine.getDefaultState(), target);
		helper.insertItem(target, new ItemStack(Items.GOLD_INGOT, 63), 0);
		helper.insertItem(target, new ItemStack(Items.GOLD_INGOT, 1), 1);
		trHelper.insertPower(target, 4800);
		helper.succeedWhen(() -> trHelper.itemEquals(target, targetResult, 0) &&
				trHelper.itemEquals(target, targetResult, 1));
	}
}
