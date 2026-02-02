package com.sabrepenguin.techreborn.proxy;

import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.gui.GuiHandler;
import com.sabrepenguin.techreborn.worldgen.WorldGenTrees;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(TechReborn.instance, new GuiHandler());
	}

	public void init(FMLInitializationEvent event) {
		GameRegistry.registerWorldGenerator(new WorldGenTrees(), 0);
	}

	public boolean hasFancyGraphics() {
		return Minecraft.getMinecraft().gameSettings.fancyGraphics;
	}
}
