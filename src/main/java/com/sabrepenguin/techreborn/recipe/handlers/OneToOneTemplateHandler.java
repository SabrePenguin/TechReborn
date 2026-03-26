package com.sabrepenguin.techreborn.recipe.handlers;

import com.google.gson.JsonObject;
import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.recipe.BasicOutputRecipe;
import com.sabrepenguin.techreborn.recipe.registries.BasicRegistry;
import com.sabrepenguin.techreborn.recipe.ITRRecipeFactory;
import com.sabrepenguin.techreborn.recipe.RecipeLoadingException;
import com.sabrepenguin.techreborn.recipe.registries.ITRRegistry;
import com.sabrepenguin.techreborn.recipe.utils.CountedIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OneToOneTemplateHandler implements ITRRecipeFactory {
	@Override
	public void registerRecipe(final String recipeName, JsonObject json, JsonContext context, final ITRRegistry registry) throws RecipeLoadingException {
		String ore_input_type = JsonUtils.getString(json, "input_type");
		int input_count = JsonUtils.hasField(json, "input_count") ? JsonUtils.getInt(json, "input_count") : 1;
		String ore_output_type = JsonUtils.getString(json, "output_type");
		int output_count = JsonUtils.hasField(json, "output_count") ? JsonUtils.getInt(json, "output_count") : 1;
		int recipeTime;
		if (JsonUtils.hasField(json, "recipe_time")) {
			recipeTime = JsonUtils.getInt(json, "recipe_time");
		} else {
			throw new RecipeLoadingException("Template requires a recipe_time field");
		}
		int energyCost;
		if (JsonUtils.hasField(json, "fe_cost")) {
			energyCost = JsonUtils.getInt(json, "fe_cost");
		} else {
			throw new RecipeLoadingException("Template requires a fe_cost field");
		}
		List<String> inputs = Arrays.stream(OreDictionary.getOreNames()).filter(name -> name.startsWith(ore_input_type)).sorted().collect(Collectors.toList());
		inputs.forEach(entry -> {
			String equivalent = entry.replaceFirst(ore_input_type, ore_output_type);
			if (OreDictionary.doesOreNameExist(equivalent)) {
				List<ItemStack> equivalents = OreDictionary.getOres(equivalent);
				if (!equivalents.isEmpty()) {
					equivalents.stream()
							.filter(stack -> Objects.requireNonNull(stack.getItem().getRegistryName()).getNamespace().equalsIgnoreCase(Tags.MODID))
							.findFirst()
							.ifPresent(stack -> {
								ItemStack out = stack.copy();
								out.setCount(output_count);
								registry.addRecipe(recipeName + entry, new BasicOutputRecipe(Arrays.asList(new CountedIngredient(input_count, entry)), out, recipeTime, energyCost));
							});
				}
			}
		});
	}
}
