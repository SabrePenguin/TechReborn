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
	private static final Map<ResourceLocation, BasicRegistry> registries = new HashMap<>();

	static {
		final RegistryHandler registry = RegistryHandler.instance();
		recipeHandlers.put(new ResourceLocation(Tags.MODID, "smelt"), new SmeltingHandler());
		recipeHandlers.put(new ResourceLocation(Tags.MODID, "alloy"), new AlloyHandler());
		registries.put(new ResourceLocation(Tags.MODID, "alloy"), registry.getAlloyRegistry());
		recipeHandlers.put(new ResourceLocation(Tags.MODID, "grinder"), new OneToOneHandler());
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
			parseJson(json);
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

	private void parseJson(JsonObject json) {
		String type = JsonUtils.getString(json, "type");
		if (type.isEmpty())
			throw new JsonSyntaxException("Recipe type must be set");
		ResourceLocation loc = new ResourceLocation(type);
		ITRRecipeFactory factory = recipeHandlers.get(loc);
		BasicRegistry registry = registries.get(loc);
		if (factory != null) {
			factory.registerRecipe(json, context, registry);
		}
	}
}
