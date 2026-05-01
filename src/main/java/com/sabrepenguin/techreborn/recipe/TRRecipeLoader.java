package com.sabrepenguin.techreborn.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.recipe.data.*;
import com.sabrepenguin.techreborn.recipe.handlers.AlloyHandler;
import com.sabrepenguin.techreborn.recipe.handlers.OneToOneHandler;
import com.sabrepenguin.techreborn.recipe.handlers.OneToOneTemplateHandler;
import com.sabrepenguin.techreborn.recipe.handlers.SmeltingHandler;
import com.sabrepenguin.techreborn.recipe.registries.ITRRegistry;
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
import java.util.*;

public class TRRecipeLoader {
	private final ModContainer mod;
	private final JsonContext context;
	private static final Gson GSON = new Gson();
	private static final Map<ResourceLocation, ITRRecipeFactory> recipeHandlers = new HashMap<>();
	private static final Map<ResourceLocation, ITRRegistry> registries = new HashMap<>();

	static {
		final RegistryHandler registry = RegistryHandler.instance();
		recipeHandlers.put(new ResourceLocation(Tags.MODID, "smelt"), new SmeltingHandler());
		recipeHandlers.put(new ResourceLocation(Tags.MODID, "alloy"), new AlloyHandler());
		registries.put(new ResourceLocation(Tags.MODID, "alloy"), registry.getAlloyRegistry());
		recipeHandlers.put(new ResourceLocation(Tags.MODID, "grinder"), new OneToOneHandler());
		recipeHandlers.put(new ResourceLocation(Tags.MODID, "grinder_template"), new OneToOneTemplateHandler());
		registries.put(new ResourceLocation(Tags.MODID, "grinder"), registry.getGrinderRegistry());
		recipeHandlers.put(new ResourceLocation(Tags.MODID, "extractor"), new OneToOneHandler());
		registries.put(new ResourceLocation(Tags.MODID, "extractor"), registry.getExtractorRegistry());
		recipeHandlers.put(new ResourceLocation(Tags.MODID, "recycler"), new OneToOneHandler());
		registries.put(new ResourceLocation(Tags.MODID, "recycler"), registry.getRecyclerRegistry());
		recipeHandlers.put(new ResourceLocation(Tags.MODID, "compressor"), new OneToOneHandler());
		registries.put(new ResourceLocation(Tags.MODID, "compressor"), registry.getCompressorRegistry());
	}

	public TRRecipeLoader() {
		mod = Loader.instance().getIndexedModList().get(Tags.MODID);
		context = new JsonContext(Tags.MODID);
	}

	public boolean loadRecipes() {
		AlloyRecipes.init();
		GrinderRecipes.init();
		ExtractorRecipes.init();
		CompressorRecipes.init();
		PlateBenderRecipes.init();
		WiremillRecipes.init();
		ScrapboxRecipes.init();
		boolean result = CraftingHelper.findFiles(mod, "assets/" + Tags.MODID + "/trrecipes/techreborn", this::preProcess, this::process, true, true);
		for (ITRRegistry registry: registries.values()) {
			registry.sortRecipes();
		}
		return result;
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
			parseJson(json, name);
		} catch (IOException exception) {
			TechReborn.LOGGER.error("Could not read recipe {} from {}", fileKey, relativeFile);
			return false;
		} catch (JsonSyntaxException exception) {
			TechReborn.LOGGER.error(exception.getMessage());
		} catch (RecipeLoadingException recipeException) {
			TechReborn.LOGGER.error("{}: {}", fileKey, recipeException.getMessage());
			return false;
		}
		return true;
	}

	private void parseJson(JsonObject json, String recipeName) {
		String type = JsonUtils.getString(json, "type");
		if (type.isEmpty())
			throw new JsonSyntaxException("Recipe type must be set");
		ITRRecipeFactory factory;
		ITRRegistry registry;
		if (type.endsWith("_template")) {
			factory = recipeHandlers.get(new ResourceLocation(type));
			registry = registries.get(new ResourceLocation(type.substring(0, type.lastIndexOf('_'))));
		} else {
			ResourceLocation loc = new ResourceLocation(type);
			factory = recipeHandlers.get(loc);
			registry = registries.get(loc);
		}
		if (factory != null) {
			factory.registerRecipe(recipeName, json, context, registry);
		}
	}
}
