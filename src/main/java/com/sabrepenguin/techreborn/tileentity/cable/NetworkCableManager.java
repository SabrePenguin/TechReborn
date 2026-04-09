package com.sabrepenguin.techreborn.tileentity.cable;

import com.sabrepenguin.techreborn.Tags;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = Tags.MODID)
public class NetworkCableManager {
	private static final Map<Integer, Set<NetworkCable>> activeNetworks = new ConcurrentHashMap<>();

	public static void registerNetwork(World world, NetworkCable cable) {
		activeNetworks.computeIfAbsent(world.provider.getDimension(), k -> ConcurrentHashMap.newKeySet()).add(cable);
	}

	public static void removeNetwork(World world, NetworkCable cable) {
		Set<NetworkCable> networks = activeNetworks.get(world.provider.getDimension());
		if (networks != null) {
			networks.remove(cable);
			if (networks.isEmpty()) {
				activeNetworks.remove(world.provider.getDimension());
			}
		}
	}

	@SubscribeEvent
	public static void onWorldTick(TickEvent.WorldTickEvent event) {
		if (!event.world.isRemote && event.phase == TickEvent.Phase.END) {
			int dimId = event.world.provider.getDimension();
			Set<NetworkCable> networks = activeNetworks.get(dimId);
			if (networks != null && !networks.isEmpty()) {
				for (NetworkCable n: networks) {
					n.tick();
				}
			}
		}
	}
}
