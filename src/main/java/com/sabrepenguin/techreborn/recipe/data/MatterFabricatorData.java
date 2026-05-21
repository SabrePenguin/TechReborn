package com.sabrepenguin.techreborn.recipe.data;

import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.registries.MatterFabricatorRegistry;

public class MatterFabricatorData {
	public static void init() {
		MatterFabricatorRegistry registry = RegistryHandler.instance().getMatterFabricatorRegistry();
		registry.addItem("itemScrap", 200);
		registry.addItem("materialScrap", 200);
		registry.addItem("itemScrapBox", 2000);
		registry.addItem("materialScrapBox", 2000);
	}
}
