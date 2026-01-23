package com.sabrepenguin.techreborn.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.apache.commons.io.FilenameUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TRRecipeLoader {
	private final ModContainer mod;
	private final JsonContext context;
	private static final Gson GSON = new Gson();

	public TRRecipeLoader() {
		mod = Loader.instance().getIndexedModList().get(Tags.MODID);
		context = new JsonContext(Tags.MODID);
	}

	public boolean loadRecipes() {
		return CraftingHelper.findFiles(mod, "assets/" + Tags.MODID + "/trrecipes", this::preProcess, this::process, true, true);
	}

	private boolean preProcess(Path root) {
		return true;
	}

	private boolean process(Path root, Path file) {
		Path relativeFile = root.relativize(file);
		String name = relativeFile.toString();
		if (!FilenameUtils.getExtension(name).equals("json")) {
			return true;
		}
		name = FilenameUtils.getBaseName(name);
		ResourceLocation fileKey = new ResourceLocation(context.getModId(), name);
		try (BufferedReader reader = Files.newBufferedReader(file)) {
			JsonObject object = JsonUtils.fromJson(GSON, reader, JsonObject.class);
			TechReborn.LOGGER.info(object);
		} catch (IOException exception) {
			TechReborn.LOGGER.error("Could not read recipe {} from {}", fileKey, relativeFile);
			return false;
		}
		return true;
	}
}
