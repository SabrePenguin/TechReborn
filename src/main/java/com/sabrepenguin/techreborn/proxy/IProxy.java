package com.sabrepenguin.techreborn.proxy;

import com.sabrepenguin.techreborn.TechReborn;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public interface IProxy {
	void preInit(FMLPreInitializationEvent event);

	void init(FMLInitializationEvent event);

	boolean hasFancyGraphics();
}
