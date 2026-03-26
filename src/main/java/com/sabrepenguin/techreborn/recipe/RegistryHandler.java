package com.sabrepenguin.techreborn.recipe;

import com.sabrepenguin.techreborn.recipe.registries.BasicRegistry;

public class RegistryHandler {
	private static final RegistryHandler INSTANCE = new RegistryHandler();
	private final BasicRegistry alloyRegistry = new BasicRegistry();
	private final BasicRegistry grinderRegistry = new BasicRegistry();
	private final BasicRegistry extractorRegistry = new BasicRegistry();
	private final BasicRegistry plateBenderRegistry = new BasicRegistry();
	private final BasicRegistry recyclerRegistry = new BasicRegistry();
	private final BasicRegistry wireMillRegistry = new BasicRegistry();
	private final BasicRegistry compressorRegistry = new BasicRegistry();

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

	public static RegistryHandler instance() {
		return INSTANCE;
	}
}
