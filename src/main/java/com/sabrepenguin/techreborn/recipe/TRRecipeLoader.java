package com.sabrepenguin.techreborn.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
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
import java.util.HashMap;
import java.util.Map;

public class TRRecipeLoader {
	private final ModContainer mod;
	private final JsonContext context;
	private static final Gson GSON = new Gson();
	private static final Map<ResourceLocation, ITRRecipeFactory> recipeHandlers = new HashMap<>();

	static {
		recipeHandlers.put(new ResourceLocation(Tags.MODID, "smelt"), new SmeltingHandler());
		recipeHandlers.put(new ResourceLocation(Tags.MODID, "crusher"), new CrusherHandler());
		recipeHandlers.put(new ResourceLocation(Tags.MODID, "alloy"), new AlloyHandler());
	}

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
			JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);
			if (json == null) return false;
			TechReborn.LOGGER.info(json);
			parseJson(json);
		} catch (IOException exception) {
			TechReborn.LOGGER.error("Could not read recipe {} from {}", fileKey, relativeFile);
			return false;
		} catch (JsonSyntaxException exception) {
			TechReborn.LOGGER.error(exception.getMessage());
		}
		return true;
	}

	private void parseJson(JsonObject json) {
		String type = JsonUtils.getString(json, "type");
		if (type.isEmpty())
			throw new JsonSyntaxException("Recipe type must be set");
		ITRRecipeFactory factory = recipeHandlers.get(new ResourceLocation(type));
		if (factory != null) {
			factory.registerRecipe(json, context);
		}
	}
}
