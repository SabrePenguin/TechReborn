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
		alloyRecipes(file);
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
	}

	public static void classic(File file) {
		File location = new File(file, "ic2compat/classic");
		new ShapedBuilder<>()
				.name("iron_fence")
				.withCondition(new IC2Condition("classic_dedupe"))
				.pattern("III")
				.pattern("III")
				.define('I', "ingotRefinedIron")
				.withResult(new ItemStack(TRBlocks.refined_iron_fence))
				.save(location);
	}

	public static void alloyRecipes(File file) {
		File location = new File(file, "ic2compat/alloy");
		IBasicIngredient refinedIron = new OreDictIngredient("ingotRefinedIron", 1);
		IBasicIngredient nickel = new OreDictIngredient("ingotNickel", 1);
		IBasicIngredient invar = new OreDictIngredient("ingotInvar", 1);
		IBasicIngredient titanium = new OreDictIngredient("ingotTitanium", 1);
		IBasicIngredient tungsten = new OreDictIngredient("ingotTungsten", 1);
		IBasicIngredient tungstensteel = new OreDictIngredient("ingotTungstensteel", 1);
		IBasicIngredient bronze = new OreDictIngredient("ingotBronze", 1);
		IBasicIngredient brass = new OreDictIngredient("ingotBrass", 1);
		IBasicIngredient tin = new OreDictIngredient("ingotTin", 1);
		IBasicIngredient zinc = new OreDictIngredient("ingotZinc", 1);
		IBasicIngredient aluminum = new ListIngredient()
				.addIngredient(new OreDictIngredient("ingotAluminum", 1))
				.addIngredient(new OreDictIngredient("ingotAluminium", 1));
		ReplaceableIngredient metal = IC2Duplicates.MIXED_METAL.getIngredient();
		layered(location, "alloy_rbot", refinedIron, bronze, tin, metal.count(2));
		layered(location, "alloy_rboz", refinedIron, bronze, zinc, metal);
		layered(location, "alloy_rbat", refinedIron, brass, tin, metal);
		layered(location, "alloy_rbaz", refinedIron, brass, zinc, metal);
		layered(location, "alloy_nbot", nickel, bronze, tin, metal.count(3));
		layered(location, "alloy_nboz", nickel, bronze, zinc, metal);
		layered(location, "alloy_nbat", nickel, brass, tin, metal);
		layered(location, "alloy_nbaz", nickel, brass, zinc, metal);
		layered(location, "alloy_nboa", nickel, bronze, aluminum, metal.count(4));
		layered(location, "alloy_nbaa", nickel, brass, aluminum, metal);
		layered(location, "alloy_ibot", invar, bronze, tin, metal.count(4));
		layered(location, "alloy_iboz", invar, bronze, zinc, metal);
		layered(location, "alloy_ibat", invar, brass, tin, metal);
		layered(location, "alloy_ibaz", invar, brass, zinc, metal);
		layered(location, "alloy_iboa", invar, bronze, aluminum, metal.count(5));
		layered(location, "alloy_ibaa", invar, brass, aluminum, metal);
		layered(location, "alloy_tbot", titanium, bronze, tin, metal.count(5));
		layered(location, "alloy_tboz", titanium, bronze, zinc, metal);
		layered(location, "alloy_tbat", titanium, brass, tin, metal);
		layered(location, "alloy_tbaz", titanium, brass, zinc, metal);
		layered(location, "alloy_wbot", tungsten, bronze, tin, metal);
		layered(location, "alloy_wboz", tungsten, bronze, zinc, metal);
		layered(location, "alloy_wbat", tungsten, brass, tin, metal);
		layered(location, "alloy_wbaz", tungsten, brass, zinc, metal);
		layered(location, "alloy_iboa", titanium, bronze, aluminum, metal.count(6));
		layered(location, "alloy_ibaa", titanium, brass, aluminum, metal);
		layered(location, "alloy_wboa", tungsten, bronze, aluminum, metal);
		layered(location, "alloy_wbaa", tungsten, brass, aluminum, metal);
		layered(location, "alloy_tsbot", tungstensteel, bronze, tin, metal.count(8));
		layered(location, "alloy_tsboz", tungstensteel, bronze, zinc, metal);
		layered(location, "alloy_tsbat", tungstensteel, brass, tin, metal);
		layered(location, "alloy_tsbaz", tungstensteel, brass, zinc, metal);
		layered(location, "alloy_tsboa", tungstensteel, bronze, aluminum, metal.count(9));
		layered(location, "alloy_tsbaa", tungstensteel, brass, aluminum, metal);
	}

	private static void layered(File file, String name, IBasicIngredient top, IBasicIngredient middle, IBasicIngredient bottom, IBasicIngredient output) {
		new ReplaceableShapedBuilder<>()
				.name(name)
				.withCondition(new IC2Condition("dedupe"))
				.pattern("TTT")
				.pattern("MMM")
				.pattern("BBB")
				.define('T', top)
				.define('M', middle)
				.define('B', bottom)
				.withOutput(output)
				.save(file);
	}
}
