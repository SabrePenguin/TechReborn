package com.sabrepenguin.techreborn.recipe.data;

import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.Plate;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.builders.BasicBuilder;
import com.sabrepenguin.techreborn.recipe.registries.BasicRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlateBenderRecipes {
	private static final BasicRegistry REGISTRY = RegistryHandler.instance().getPlateBenderRegistry();

	public static void init() {
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("ingotAdvancedAlloy")
				.setOutput(TRItems.plates, 1, Plate.PlateMeta.advanced_alloy.metadata())
				.setRecipeTime(100)
				.setEnergyCost(32)
				.build());
		REGISTRY.addRecipe(new BasicBuilder<>()
				.addInput("ingotRefinedIron")
				.setOutput(TRItems.plates, 1, Plate.PlateMeta.refined_iron.metadata())
				.setRecipeTime(100)
				.setEnergyCost(32)
				.build());
		{
			List<String> inputs = Arrays.stream(OreDictionary.getOreNames()).filter(name -> name.startsWith("ingot") // TODO: Replace with better filter
							&& !name.contains("iridium") && !name.equals("ingotAdvancedAlloy") && !name.equals("ingotIridiumAlloy") && !name.equals("ingotRefinedIron"))
					.sorted().collect(Collectors.toList());
			InternalUtils.registerTemplateOneToOne(inputs, REGISTRY, "ingot", 1, "plate", 1, 40, 100);
		}
	}
}
