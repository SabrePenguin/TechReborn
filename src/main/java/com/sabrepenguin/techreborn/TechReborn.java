package com.sabrepenguin.techreborn;

import com.sabrepenguin.techreborn.proxy.CommonProxy;
import com.sabrepenguin.techreborn.recipe.TRRecipeLoader;
import com.sabrepenguin.techreborn.tabs.TRTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
            serverSide = "com.sabrepenguin.techreborn.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    public static final CreativeTabs RESOURCE_TAB = new TRTab();

    @EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items, etc. (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        // register to the event bus so that we can listen to events
        MinecraftForge.EVENT_BUS.register(this);
		proxy.preInit(event);
    }

    @SubscribeEvent
    // Register recipes here (Remove if not needed)
    public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		TRRecipeLoader loader = new TRRecipeLoader();
		loader.loadRecipes();
    }

    @EventHandler
    // load "Do your mod setup. Build whatever data structures you care about." (Remove if not needed)
    public void init(FMLInitializationEvent event) {
    }

    @EventHandler
    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {
    }

    @EventHandler
    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
    }
}
