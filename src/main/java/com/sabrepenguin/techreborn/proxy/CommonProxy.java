package com.sabrepenguin.techreborn.proxy;

import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.gui.GuiHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(TechReborn.instance, new GuiHandler());
	}

	public boolean hasFancyGraphics() {
		return Minecraft.getMinecraft().gameSettings.fancyGraphics;
	}
}
