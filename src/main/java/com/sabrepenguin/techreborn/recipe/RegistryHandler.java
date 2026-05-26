package com.sabrepenguin.techreborn.recipe;

import com.sabrepenguin.techreborn.recipe.registries.*;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RegistryHandler {
	private static final RegistryHandler INSTANCE = new RegistryHandler();
	private final ITRRegistry alloyRegistry = new ShapelessRegistry();
	private final ITRRegistry grinderRegistry = new ShapelessRegistry();
	private final ITRRegistry extractorRegistry = new ShapelessRegistry();
	private final ITRRegistry plateBenderRegistry = new ShapelessRegistry();
	private final ITRRegistry recyclerRegistry = new ShapelessRegistry();
	private final ITRRegistry wireMillRegistry = new ShapelessRegistry();
	private final ITRRegistry compressorRegistry = new ShapelessRegistry();
	private final List<ItemStack> scrapboxRecipes = new ArrayList<>();
	private final SingletonRegistry scrapboxRegistry = new SingletonRegistry();
	private final ITRRegistry assemblingMachineRegistry = new ShapelessRegistry();
	private final ITRRegistry solidCanningMachineRegistry = new ShapelessRegistry();
	private final ITRRegistry chemicalReactorRegistry = new ShapelessRegistry();
	private final MatterFabricatorRegistry matterFabricatorRegistry = new MatterFabricatorRegistry();
	private final ITRRegistry rollingMachineRegistry = new ShapedRegistry();

	public ITRRegistry getAlloyRegistry() {
		return alloyRegistry;
	}

	public ITRRegistry getGrinderRegistry() {
		return grinderRegistry;
	}

	public ITRRegistry getExtractorRegistry() {
		return extractorRegistry;
	}

	public ITRRegistry getPlateBenderRegistry() {
		return plateBenderRegistry;
	}

	public ITRRegistry getRecyclerRegistry() {
		return recyclerRegistry;
	}

	public ITRRegistry getWireMillRegistry() {
		return wireMillRegistry;
	}

	public ITRRegistry getCompressorRegistry() {
		return compressorRegistry;
	}

	public List<ItemStack> getScrapboxRecipes() {
		return scrapboxRecipes;
	}

	public ITRRegistry getScrapboxRegistry() {
		return scrapboxRegistry;
	}

	public ITRRegistry getAssemblingMachineRegistry() {
		return assemblingMachineRegistry;
	}

	public ITRRegistry getChemicalReactorRegistry() {
		return chemicalReactorRegistry;
	}

	public ITRRegistry getSolidCanningMachineRegistry() {
		return solidCanningMachineRegistry;
	}

	public MatterFabricatorRegistry getMatterFabricatorRegistry() {
		return matterFabricatorRegistry;
	}

	public ITRRegistry getRollingMachineRegistry() {
		return rollingMachineRegistry;
	}

	public static RegistryHandler instance() {
		return INSTANCE;
	}
}
