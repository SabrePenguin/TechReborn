package com.sabrepenguin.techreborn.datagen.recipes;

import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.config.TechRebornConfig;
import com.sabrepenguin.techreborn.datagen.builders.ReplaceableShapedBuilder;
import com.sabrepenguin.techreborn.datagen.builders.ShapedBuilder;
import com.sabrepenguin.techreborn.datagen.builders.ShapelessBuilder;
import com.sabrepenguin.techreborn.datagen.builders.conditions.IC2Condition;
import com.sabrepenguin.techreborn.datagen.builders.ingredients.IBasicIngredient;
import com.sabrepenguin.techreborn.datagen.builders.ingredients.ListIngredient;
import com.sabrepenguin.techreborn.datagen.builders.ingredients.OreDictIngredient;
import com.sabrepenguin.techreborn.datagen.builders.ingredients.ReplaceableIngredient;
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
		}
	}

	public static void normal(File file) {
		File location = new File(file, "misc");
		new ShapelessBuilder<>()
				.name("rubber_planks")
				.requires(IC2Duplicates.RUBBER_WOOD.getIngredient())
				.withResult(new ItemStack(TRBlocks.rubber_planks, 4))
				.save(location);
		new ShapedBuilder<>()
				.name("iron_fence")
				.withCondition(new IC2Condition("dedupe"))
				.pattern("III")
				.pattern("III")
				.define('I', "ingotRefinedIron")
				.withResult(new ItemStack(TRBlocks.refined_iron_fence))
				.save(location);
	}

	public static void classic(File file) {
		File location = new File(file, "ic2compat/classic");
	}
}
