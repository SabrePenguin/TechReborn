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
		inputs.forEach(entry -> {
			String equivalent = entry.replaceFirst(oreInputType, oreOutputType);
			if (OreDictionary.doesOreNameExist(equivalent)) {
				List<ItemStack> equivalents = OreDictionary.getOres(equivalent);
				if (!equivalents.isEmpty()) {
					equivalents.stream()
							.filter(stack -> Objects.requireNonNull(stack.getItem().getRegistryName()).getNamespace()
									.equalsIgnoreCase(Tags.MODID))
							.findFirst()
							.ifPresent(stack -> {
								ItemStack out = stack.copy();
								out.setCount(outputCount);
								registry.addRecipe(new BasicOutputRecipe(Arrays.asList(new CountedIngredient(inputCount, entry)),
												out, recipeTime, energyCost));
							});
				}
			}
		});
	}
}
