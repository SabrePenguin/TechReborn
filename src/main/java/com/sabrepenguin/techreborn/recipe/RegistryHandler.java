package com.sabrepenguin.techreborn.recipe;

public class RegistryHandler {
	private static final RegistryHandler INSTANCE = new RegistryHandler();
	private final BasicRegistry alloyRegistry = new BasicRegistry();

	public BasicRegistry getAlloyRegistry() {
		return alloyRegistry;
	}

	public static RegistryHandler instance() {
		return INSTANCE;
	}
}
