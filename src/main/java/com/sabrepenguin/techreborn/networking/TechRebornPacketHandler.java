package com.sabrepenguin.techreborn.networking;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.networking.sideconfig.PacketAutoConfig;
import com.sabrepenguin.techreborn.networking.sideconfig.PacketSideConfig;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class TechRebornPacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Tags.MODID);

	public static void init() {
		INSTANCE.registerMessage(PacketSideConfig.PacketSideConfigMessageHandler.class, PacketSideConfig.class, 0, Side.SERVER);
		INSTANCE.registerMessage(PacketAutoConfig.PacketAutoConfigMessageHandler.class, PacketAutoConfig.class, 1, Side.SERVER);
	}
}
