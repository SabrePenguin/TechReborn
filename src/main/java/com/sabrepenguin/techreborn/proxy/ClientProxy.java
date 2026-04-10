package com.sabrepenguin.techreborn.proxy;

import com.sabrepenguin.techreborn.config.TechRebornConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProxy {
	@Override
	public void preInit(FMLPreInitializationEvent event) {

	}

	@Override
	public void init(FMLInitializationEvent event) {

	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		TechRebornConfig.EventHandler.cableDebugHandler();
	}

	@Override
	public boolean hasFancyGraphics() {
		return Minecraft.getMinecraft().gameSettings.fancyGraphics;
	}

}
