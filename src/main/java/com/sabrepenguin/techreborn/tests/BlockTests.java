package com.sabrepenguin.techreborn.tests;

import com.gtnewhorizons.horizonqa.api.GameTestHelper;
import com.gtnewhorizons.horizonqa.api.annotation.GameTest;
import com.gtnewhorizons.horizonqa.api.annotation.GameTestHolder;
import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.BlockCable;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
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
		helper.succeedWhen(() -> trHelper.itemOutputEquals(target, targetResult, 1));
	}

	@GameTest(template = "extractor", timeoutTicks = 410)
	public static void extractorTest(GameTestHelper helper) {
		TechRebornHelper trHelper = new TechRebornHelper(helper);
		BlockPos target = new BlockPos(0, 0, 0);

		ItemStack targetStack = new ItemStack(TRItems.part, 1, Part.PartMeta.rubber.metadata());

		helper.assertBlockPresent(TRBlocks.extractor.getDefaultState(), target);
		helper.insertItem(target, new ItemStack(TRBlocks.rubber_sapling), 0);
		trHelper.insertPower(target, 3200);
		helper.succeedWhen(() -> trHelper.itemOutputEquals(target, targetStack, 1));
	}

	@GameTest(template = "plateBendingMachine", timeoutTicks = 110)
	public static void plateBendingTest(GameTestHelper helper) {
		TechRebornHelper trHelper = new TechRebornHelper(helper);
		BlockPos target = new BlockPos(0, 0, 0);

		ItemStack targetStack = new ItemStack(TRItems.plates, 1, Plate.PlateMeta.refined_iron.metadata());

		helper.assertBlockPresent(TRBlocks.plate_bending_machine.getDefaultState(), target);
		helper.insertItem(target, new ItemStack(TRItems.ingot, 1, Ingot.IngotMeta.refined_iron.metadata()), 0);
		trHelper.insertPower(target, 3200);
		helper.succeedWhen(() -> trHelper.itemOutputEquals(target, targetStack, 1));
	}

	@GameTest(template = "wireMill", timeoutTicks = 210)
	public static void wireMillTest(GameTestHelper helper) {
		TechRebornHelper trHelper = new TechRebornHelper(helper);
		BlockPos target = new BlockPos(0, 0, 0);

		ItemStack targetStack = new ItemStack(TRBlocks.cable, 6, BlockCable.CableEnum.GOLD.metadata());

		helper.assertBlockPresent(TRBlocks.wire_mill.getDefaultState(), target);
		helper.insertItem(target, new ItemStack(Items.GOLD_INGOT), 0);
		trHelper.insertPower(target, 800);
		helper.succeedWhen(() -> trHelper.itemOutputEquals(target, targetStack, 1));
	}

	@GameTest(template = "compressor", timeoutTicks = 410)
	public static void compressorTest(GameTestHelper helper) {
		TechRebornHelper trHelper = new TechRebornHelper(helper);
		BlockPos target = new BlockPos(0, 0, 0);

		ItemStack targetStack = new ItemStack(TRItems.plates, 1, Plate.PlateMeta.carbon.metadata());

		helper.assertBlockPresent(TRBlocks.compressor.getDefaultState(), target);
		helper.insertItem(target, new ItemStack(TRItems.part, 1, Part.PartMeta.carbon_mesh.metadata()), 0);
		trHelper.insertPower(target, 3200);
		helper.succeedWhen(() -> trHelper.itemOutputEquals(target, targetStack, 1));
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
}
