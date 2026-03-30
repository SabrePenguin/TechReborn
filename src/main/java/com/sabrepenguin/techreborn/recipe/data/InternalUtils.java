package com.sabrepenguin.techreborn.recipe.data;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.recipe.BasicOutputRecipe;
import com.sabrepenguin.techreborn.recipe.registries.ITRRegistry;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InternalUtils {

	public static void registerTemplateOneToOne(ITRRegistry registry, String oreInputType, int inputCount,
												String oreOutputType, int outputCount, int recipeTime,
												int energyCost) {
		List<String> inputs = Arrays.stream(OreDictionary.getOreNames()).filter(name -> name.startsWith(oreInputType))
				.sorted().collect(Collectors.toList());
		registerTemplateOneToOne(inputs, registry, oreInputType, inputCount, oreOutputType, outputCount, recipeTime,
				energyCost);
	}

	// I absolutely hate this. TODO: Fucking remove
	public static void registerTemplateReplaceFirst(ITRRegistry registry, String original, String secondary,
													int inputCount, String oreOutputType, int outputCount,
													int recipeTime, int energyCost) {
		List<String> inputs = Arrays.stream(OreDictionary.getOreNames()).filter(name -> name.startsWith(original))
				.sorted().collect(Collectors.toList());
		inputs.forEach(entry -> {
			String inputType = entry.replaceFirst(original, secondary);
			if (OreDictionary.doesOreNameExist(inputType) && !OreDictionary.getOres(inputType).isEmpty()) {
				String equivalent = entry.replaceFirst(original, oreOutputType);
				if (OreDictionary.doesOreNameExist(equivalent) && !OreDictionary.getOres(equivalent).isEmpty()) {
					registerOreDict(registry, inputCount, outputCount, recipeTime, energyCost, inputType, equivalent);
				}
			}
		});
	}

	public static void registerTemplateOneToOne(List<String> inputs, ITRRegistry registry, String oreInputType,
												int inputCount,
												String oreOutputType, int outputCount, int recipeTime,
												int energyCost) {
		inputs.forEach(entry -> {
			String equivalent = entry.replaceFirst(oreInputType, oreOutputType);
			if (OreDictionary.doesOreNameExist(equivalent) && !OreDictionary.getOres(equivalent).isEmpty()) {
				registerOreDict(registry, inputCount, outputCount, recipeTime, energyCost, entry, equivalent);
			}
		});
	}

	private static void registerOreDict(ITRRegistry registry, int inputCount, int outputCount, int recipeTime,
										int energyCost, String oreName, String equivalent) {
		List<ItemStack> equivalents = OreDictionary.getOres(equivalent);
		if (!equivalents.isEmpty()) {
			equivalents.stream()
					.filter(stack -> Objects.requireNonNull(stack.getItem().getRegistryName()).getNamespace()
							.equalsIgnoreCase(Tags.MODID))
					.findFirst()
					.ifPresent(stack -> {
						ItemStack out = stack.copy();
						out.setCount(outputCount);
						registry.addRecipe(new BasicOutputRecipe(Arrays.asList(new CountedIngredient(inputCount,
								oreName)),
								out, recipeTime, energyCost));
					});
		}
	}
}
