package com.sabrepenguin.techreborn.recipe;

public class RegistryHandler {
	private static final RegistryHandler INSTANCE = new RegistryHandler();
	private final AlloyRegistry alloyRegistry = new AlloyRegistry();

	public AlloyRegistry getAlloyRegistry() {
		return alloyRegistry;
	}

	public static RegistryHandler instance() {
		return INSTANCE;
	}
}
