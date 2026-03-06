package com.sabrepenguin.techreborn.recipe;

public class RegistryHandler {
	private static final RegistryHandler INSTANCE = new RegistryHandler();
	private final BasicOutputRegistry alloyRegistry = new BasicOutputRegistry();

	public BasicOutputRegistry getAlloyRegistry() {
		return alloyRegistry;
	}

	public static RegistryHandler instance() {
		return INSTANCE;
	}
}
