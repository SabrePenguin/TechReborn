package com.sabrepenguin.techreborn.recipe.data;

import com.sabrepenguin.techreborn.blocks.fluids.TRFluids;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.builders.BasicBuilder;
import com.sabrepenguin.techreborn.recipe.registries.ITRRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ChemicalReactorRecipes {
	public static final ITRRegistry REGISTRY = RegistryHandler.instance().getChemicalReactorRegistry();

	@SuppressWarnings("ConstantConditions")
	public static void init() {
		Fluid water = FluidRegistry.WATER;
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInputWithNBT(TRItems.cell.getCellWithFluid(TRFluids.COMPRESSED_AIR))
				.addInputWithNBT(getFluidWithCount(TRFluids.HYDROGEN, 4))
				.setOutput(getFluidWithCount(water, 5))
				.setRecipeTime(10)
				.setEnergyCost(120)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInputWithNBT(TRItems.cell.getCellWithFluid(TRFluids.SULFUR))
				.addInputWithNBT(getFluidWithCount(water, 2))
				.setOutput(getFluidWithCount(TRFluids.SULFURIC_ACID, 3))
				.setRecipeTime(1150)
				.setEnergyCost(120)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInputWithNBT(getFluidWithCount(TRFluids.SULFUR, 1))
				.addInputWithNBT(getFluidWithCount(TRFluids.SODIUM, 1))
				.setOutput(getFluidWithCount(TRFluids.SODIUM_SULFIDE, 2))
				.setRecipeTime(100)
				.setEnergyCost(120)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInputWithNBT(getFluidWithCount(water, 1))
				.addInputWithNBT(getFluidWithCount(TRFluids.NITROCARBON, 1))
				.setOutput(getFluidWithCount(TRFluids.GLYCERYL, 3))
				.setRecipeTime(583)
				.setEnergyCost(120)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInputWithNBT(getFluidWithCount(TRFluids.GLYCERYL, 1))
				.addInputWithNBT(getFluidWithCount(TRFluids.DIESEL, 4))
				.setOutput(getFluidWithCount(TRFluids.NITRO_DIESEL, 5))
				.setRecipeTime(1000)
				.setEnergyCost(120)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInputWithNBT(getFluidWithCount(TRFluids.GLYCERYL, 1))
				.addInputWithNBT(getFluidWithCount(TRFluids.CARBON, 4))
				.setOutput(getFluidWithCount(TRFluids.NITROCOAL_FUEL, 5))
				.setRecipeTime(1000)
				.setEnergyCost(120)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInputWithNBT(getFluidWithCount(TRFluids.NITROGEN, 1))
				.addInputWithNBT(getFluidWithCount(TRFluids.CARBON, 1))
				.setOutput(getFluidWithCount(TRFluids.NITROCARBON, 2))
				.setRecipeTime(1500)
				.setEnergyCost(120)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInputWithNBT(getFluidWithCount(TRFluids.HYDROGEN, 4))
				.addInputWithNBT(getFluidWithCount(TRFluids.CARBON, 1))
				.setOutput(getFluidWithCount(TRFluids.METHANE, 5))
				.setRecipeTime(3500)
				.setEnergyCost(120)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInputWithNBT(getFluidWithCount(TRFluids.SODIUM_SULFIDE, 1))
				.addInputWithNBT(getFluidWithCount(TRFluids.COMPRESSED_AIR, 1))
				.setOutput(getFluidWithCount(TRFluids.SODIUM_PERSULFATE, 2))
				.setRecipeTime(2000)
				.setEnergyCost(120)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInputWithNBT(getFluidWithCount(TRFluids.NITROGEN, 1))
				.addInputWithNBT(getFluidWithCount(TRFluids.COMPRESSED_AIR, 1))
				.setOutput(getFluidWithCount(TRFluids.NITROGEN_DIOXIDE, 2))
				.setRecipeTime(1250)
				.setEnergyCost(120)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInputWithNBT(getFluidWithCount(TRFluids.OIL, 1))
				.addInputWithNBT(getFluidWithCount(TRFluids.NITROGEN, 1))
				.setOutput(getFluidWithCount(TRFluids.NITROFUEL, 2))
				.setRecipeTime(1000)
				.setEnergyCost(120)
				.build());
	}

	@SuppressWarnings("ConstantConditions")
	private static ItemStack getFluidWithCount(Fluid fluid, int count) {
		ItemStack f = TRItems.cell.getCellWithFluid(fluid);
		f.setCount(count);
		return f;
	}
}
