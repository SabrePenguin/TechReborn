package com.sabrepenguin.techreborn.recipe.data;

import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.Part;
import com.sabrepenguin.techreborn.items.materials.Plate;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.builders.ShapedBuilder;
import com.sabrepenguin.techreborn.recipe.registries.ITRRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.stream.Stream;

public class RollingMachineRecipes {

	@SuppressWarnings("ConstantConditions")
	public static void init() {
		ITRRegistry registry = RegistryHandler.instance().getRollingMachineRegistry();

		int standardCost = 10000 / 250;

		registry.addRecipe(new ShapedBuilder(3)
				.pattern("NCN")
				.pattern("C C")
				.pattern("NCN")
				.define('N', "ingotNickel")
				.define('C', "ingotCopper")
				.setOutput(new ItemStack(TRItems.part, 3, Part.PartMeta.cupronickel_heating_coil.metadata()))
				.setRecipeTime(250)
				.setEnergyCost(standardCost)
				.buildShaped());
		registry.addRecipe(new ShapedBuilder(3)
				.pattern(" N ")
				.pattern("NCN")
				.pattern(" N ")
				.define('N', "ingotNickel")
				.define('C', "ingotChrome")
				.setOutput(new ItemStack(TRItems.part, 2, Part.PartMeta.nichrome_heating_coil.metadata()))
				.setRecipeTime(250)
				.setEnergyCost(standardCost)
				.buildShaped());
		registry.addRecipe(new ShapedBuilder(3)
				.pattern("RRR")
				.pattern("CAA")
				.pattern("CCA")
				.define('R', "ingotRefinedIron")
				.define('C', "ingotChrome")
				.define('A',
						Ingredient.fromStacks(
							Stream.of("ingotAluminum", "ingotAluminium")
									.flatMap(s -> OreDictionary.getOres(s, false).stream())
									.filter(stack -> !stack.isEmpty())
									.toArray(ItemStack[]::new)),
						1)
				.setOutput(new ItemStack(TRItems.part, 3, Part.PartMeta.kanthal_heating_coil.metadata()))
				.setRecipeTime(250)
				.setEnergyCost(standardCost)
				.buildShaped());
		registry.addRecipe(new ShapedBuilder(3)
				.pattern("AAA")
				.pattern("MMM")
				.pattern("AAA")
				.define('A', "ingotAluminum")
				.define('M', "dustMagnesium")
				.setOutput(new ItemStack(TRItems.plates, 3, Plate.PlateMeta.magnalium.metadata()))
				.setRecipeTime(250)
				.setEnergyCost(standardCost)
				.buildShaped());
		registry.addRecipe(new ShapedBuilder(3)
				.pattern(" T ")
				.pattern("T T")
				.pattern(" T ")
				.define('T', "ingotTin")
				.setOutput(new ItemStack(TRItems.cell, 24))
				.setRecipeTime(250)
				.setEnergyCost(standardCost)
				.buildShaped());
		registry.addRecipe(new ShapedBuilder(3, 2)
				.pattern("I I")
				.pattern("I I")
				.pattern(" I ")
				.define('I', "ingotIron")
				.setOutput(new ItemStack(Items.BUCKET, 2))
				.setRecipeTime(250)
				.setEnergyCost(standardCost)
				.buildShaped());
		registry.addRecipe(new ShapedBuilder(1, 3)
				.pattern("I")
				.pattern("S")
				.pattern("W")
				.define('I', "ingotIron")
				.define('S', "stickWood")
				.define('W', "plankWood")
				.setOutput(new ItemStack(Blocks.TRIPWIRE_HOOK, 4))
				.setRecipeTime(250)
				.setEnergyCost(standardCost)
				.buildShaped());
		registry.addRecipe(new ShapedBuilder(2, 1)
				.pattern("II")
				.define('I', "ingotIron")
				.setOutput(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, 2))
				.setRecipeTime(250)
				.setEnergyCost(standardCost)
				.buildShaped());
		registry.addRecipe(new ShapedBuilder(2, 1)
				.pattern("II")
				.define('I', "ingotGold")
				.setOutput(new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, 2))
				.setRecipeTime(250)
				.setEnergyCost(standardCost)
				.buildShaped());
		registry.addRecipe(new ShapedBuilder(3, 2)
				.pattern("III")
				.pattern("III")
				.define('I', "ingotIron")
				.setOutput(new ItemStack(Blocks.IRON_BARS, 24))
				.setRecipeTime(250)
				.setEnergyCost(standardCost)
				.buildShaped());
		registry.addRecipe(new ShapedBuilder(2, 3)
				.pattern("II")
				.pattern("II")
				.pattern("II")
				.define('I', "ingotIron")
				.setOutput(new ItemStack(Items.IRON_DOOR, 4))
				.setRecipeTime(250)
				.setEnergyCost(standardCost)
				.buildShaped());
		registry.addRecipe(new ShapedBuilder(3)
				.pattern("I I")
				.pattern("ISI")
				.pattern("I I")
				.define('I', "ingotIron")
				.define('S', "stickWood")
				.setOutput(new ItemStack(Blocks.RAIL, 24))
				.setRecipeTime(250)
				.setEnergyCost(standardCost)
				.buildShaped());
		registry.addRecipe(new ShapedBuilder(3)
				.pattern("I I")
				.pattern("ISI")
				.pattern("IRI")
				.define('I', "ingotGold")
				.define('S', "stickWood")
				.define('R', "dustRedstone")
				.setOutput(new ItemStack(Blocks.GOLDEN_RAIL, 8))
				.setRecipeTime(250)
				.setEnergyCost(standardCost)
				.buildShaped());
		registry.addRecipe(new ShapedBuilder(3)
				.pattern("I I")
				.pattern("IPI")
				.pattern("IRI")
				.define('I', "ingotIron")
				.define('P', new ItemStack(Blocks.STONE_PRESSURE_PLATE), 1)
				.define('R', "dustRedstone")
				.setOutput(new ItemStack(Blocks.DETECTOR_RAIL, 8))
				.setRecipeTime(250)
				.setEnergyCost(standardCost)
				.buildShaped());
		registry.addRecipe(new ShapedBuilder(3)
				.pattern("ISI")
				.pattern("IRI")
				.pattern("ISI")
				.define('I', "ingotIron")
				.define('S', "stickWood")
				.define('R', new ItemStack(Blocks.REDSTONE_TORCH), 1)
				.setOutput(new ItemStack(Blocks.ACTIVATOR_RAIL, 8))
				.setRecipeTime(250)
				.setEnergyCost(standardCost)
				.buildShaped());
		registry.addRecipe(new ShapedBuilder(3, 2)
				.pattern("I I")
				.pattern("III")
				.define('I', "ingotIron")
				.setOutput(new ItemStack(Items.MINECART, 2))
				.setRecipeTime(250)
				.setEnergyCost(standardCost)
				.buildShaped());

	}

}
