package com.sabrepenguin.techreborn.datagen;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.datagen.recipes.IC2CompatRecipes;
import com.sabrepenguin.techreborn.datagen.recipes.StandardRecipes;
import net.minecraftforge.fml.common.Loader;

import java.io.File;

public class DatagenInit {
	private static final String RECIPE_DIR = "src/main/resources/assets/" + Tags.MODID + "/recipes";

	public static void init(String fileSource) {
		if (!Loader.isModLoaded("ic2")) {
			throw new RuntimeException("IC2 is not in the modlist, and must be loaded for datagen");
		}
		StandardRecipes.initRecipes(new File(fileSource, RECIPE_DIR));
		IC2CompatRecipes.initRecipes(new File(fileSource, RECIPE_DIR));
		throw new RuntimeException("Successfully built the files out, please restart without datagen enabled");
	}
}
