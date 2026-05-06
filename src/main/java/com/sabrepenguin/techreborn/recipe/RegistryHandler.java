package com.sabrepenguin.techreborn.recipe;

import com.sabrepenguin.techreborn.recipe.registries.BasicRegistry;
import com.sabrepenguin.techreborn.recipe.registries.ITRRegistry;
import com.sabrepenguin.techreborn.recipe.registries.SingletonRegistry;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RegistryHandler {
	private static final RegistryHandler INSTANCE = new RegistryHandler();
	private final BasicRegistry alloyRegistry = new BasicRegistry();
	private final BasicRegistry grinderRegistry = new BasicRegistry();
	private final BasicRegistry extractorRegistry = new BasicRegistry();
	private final BasicRegistry plateBenderRegistry = new BasicRegistry();
	private final BasicRegistry recyclerRegistry = new BasicRegistry();
	private final BasicRegistry wireMillRegistry = new BasicRegistry();
	private final BasicRegistry compressorRegistry = new BasicRegistry();
	private final List<ItemStack> scrapboxRecipes = new ArrayList<>();
	private final SingletonRegistry scrapboxRegistry = new SingletonRegistry();
	private final BasicRegistry assemblingMachineRegistry = new BasicRegistry();
	private final BasicRegistry solidCanningMachineRegistry = new BasicRegistry();
	private final BasicRegistry chemicalReactorRegistry = new BasicRegistry();

	public BasicRegistry getAlloyRegistry() {
		return alloyRegistry;
	}

	public BasicRegistry getGrinderRegistry() {
		return grinderRegistry;
	}

	public BasicRegistry getExtractorRegistry() {
		return extractorRegistry;
	}

	public BasicRegistry getPlateBenderRegistry() {
		return plateBenderRegistry;
	}

	public BasicRegistry getRecyclerRegistry() {
		return recyclerRegistry;
	}

	public BasicRegistry getWireMillRegistry() {
		return wireMillRegistry;
	}

	public BasicRegistry getCompressorRegistry() {
		return compressorRegistry;
	}

	public List<ItemStack> getScrapboxRecipes() {
		return scrapboxRecipes;
	}

	public ITRRegistry getScrapboxRegistry() {
		return scrapboxRegistry;
	}

	public BasicRegistry getAssemblingMachineRegistry() {
		return assemblingMachineRegistry;
	}

	public BasicRegistry getChemicalReactorRegistry() {
		return chemicalReactorRegistry;
	}

	public BasicRegistry getSolidCanningMachineRegistry() {
		return solidCanningMachineRegistry;
	}

	public static RegistryHandler instance() {
		return INSTANCE;
	}
}
