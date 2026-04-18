package com.sabrepenguin.techreborn;

import com.sabrepenguin.techreborn.datagen.recipes.StandardRecipes;
import com.sabrepenguin.techreborn.networking.TechRebornPacketHandler;
import com.sabrepenguin.techreborn.proxy.IProxy;
import com.sabrepenguin.techreborn.recipe.TRRecipeLoader;
import com.sabrepenguin.techreborn.tabs.TRTab;
import com.sabrepenguin.techreborn.worldgen.WorldGenTrees;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MODID, version = Tags.VERSION, name = Tags.MODNAME, acceptedMinecraftVersions = "[1.12.2]")
public class TechReborn {

    @Mod.Instance
    public static TechReborn instance;

    public static final Logger LOGGER = LogManager.getLogger(Tags.MODID);

    @SidedProxy(
            modId = Tags.MODID,
            clientSide = "com.sabrepenguin.techreborn.proxy.ClientProxy",
            serverSide = "com.sabrepenguin.techreborn.proxy.ServerProxy"
    )
    public static IProxy proxy;

    public static final CreativeTabs RESOURCE_TAB = new TRTab();

	static {
		FluidRegistry.enableUniversalBucket();
	}

	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // register to the event bus so that we can listen to events
        MinecraftForge.EVENT_BUS.register(this);
		TechRebornPacketHandler.init();
		proxy.preInit(event);
    }

    @SubscribeEvent
    public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		TRRecipeLoader loader = new TRRecipeLoader();
		if (!loader.loadRecipes()) {
			LOGGER.warn("Could not load recipes properly!");
		}
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
		GameRegistry.registerWorldGenerator(new WorldGenTrees(), 0);
		proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
		boolean isDev = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
		if (isDev && System.getProperty(Tags.MODID + ".datagen") != null && System.getProperty(Tags.MODID + ".datagen").equals("true")) {
			StandardRecipes.initRecipes(System.getProperty(Tags.MODID + ".srcroot", ""));
		}
		proxy.postInit(event);
    }

    @EventHandler
    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
    }
}
