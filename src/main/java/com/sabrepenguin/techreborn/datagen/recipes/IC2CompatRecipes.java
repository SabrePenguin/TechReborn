package com.sabrepenguin.techreborn.datagen.recipes;

import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.datagen.builders.ShapedBuilder;
import com.sabrepenguin.techreborn.datagen.builders.ShapelessBuilder;
import com.sabrepenguin.techreborn.datagen.builders.conditions.IC2Condition;
import com.sabrepenguin.techreborn.items.TRItems;
import ic2.api.item.IC2Items;
import net.minecraft.item.ItemStack;

import java.io.File;

public class IC2CompatRecipes {
	public static void initRecipes(File file) {
		normal(file);
		if (TechRebornConfig.compat.ic2.classic) {
			try {
				classic(file);
			} catch (Exception e) {
				throw new RuntimeException("Unable to build classic ic2, please ensure IC2 is in Classic mode");
			}
		} else {
			try {
				experimental(file);
			} catch (Exception e) {
				throw new RuntimeException("Unable to build experimental ic2, please ensure IC2 is in Experimental mode");
			}
		}
	}

	@SuppressWarnings("ConstantConditions")
	public static void normal(File file) {
		File location = new File(file, "misc");
		new ShapelessBuilder<>()
				.name("rubber_planks")
				.requires(IC2Duplicates.RUBBER_WOOD.getIngredient())
				.withResult(new ItemStack(TRBlocks.rubber_planks, 4))
				.save(location);
		new ShapedBuilder<>()
				.name("iron_fence")
				.withCondition(IC2Condition.DeduplicateCondition())
				.pattern("III")
				.pattern("III")
				.define('I', "ingotRefinedIron")
				.withResult(new ItemStack(TRBlocks.refined_iron_fence))
				.save(location);
	}

	@SuppressWarnings("ConstantConditions")
	public static void classic(File file) {
		File location = new File(file, "ic2compat");
		new ShapelessBuilder<>()
				.name("electric_wrench")
				.withCondition(IC2Condition.ClassicCondition())
				.requires(new ItemStack(TRItems.wrench))
				.requires(IC2Duplicates.RE_BATTERY.getIngredient())
				.withResult(IC2Items.getItem("electric_wrench"))
				.save(location);
	}

	@SuppressWarnings("ConstantConditions")
	public static void experimental(File file) {
		File location = new File(file, "ic2compat");
		new ShapedBuilder<>()
				.name("nuclear_reactor")
				.withCondition(IC2Condition.ExperimentalCondition())
				.pattern("PCP")
				.pattern("RRR")
				.pattern("PGP")
				.define('P', IC2Items.getItem("plating", null))
				.define('C', "circuitAdvanced")
				.define('R', IC2Items.getItem("te", "reactor_chamber"))
				.define('G', new ItemStack(TRBlocks.solid_fuel_generator))
				.withResult(IC2Items.getItem("te", "nuclear_reactor"))
				.save(location);
		new ShapedBuilder<>()
				.name("reactor_chamber")
				.withCondition(IC2Condition.ExperimentalCondition())
				.pattern(" P ")
				.pattern("PBP")
				.pattern(" P ")
				.define('P', IC2Items.getItem("plating", null))
				.define('B', "machineBlockBasic")
				.withResult(IC2Items.getItem("te", "reactor_chamber"))
				.save(location);
		new ShapelessBuilder<>()
				.name("plating")
				.withCondition(IC2Condition.ExperimentalCondition())
				.requires("plateLead")
				.requires("plateAdvancedAlloy")
				.withResult(IC2Items.getItem("plating", null))
				.save(location);
	}
}
