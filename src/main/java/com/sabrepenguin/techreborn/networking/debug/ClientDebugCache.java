package com.sabrepenguin.techreborn.networking.debug;

import net.minecraft.util.math.BlockPos;

import java.util.Set;

public class ClientDebugCache {
	private static Set<BlockPos> cachedPositions;

	public static void update(Set<BlockPos> pos) {
		cachedPositions = pos;
	}

	public static boolean isLoaded(BlockPos pos) {
		return cachedPositions != null && cachedPositions.contains(pos);
	}

	public static Set<BlockPos> getCachedPositions() {
		return cachedPositions;
	}
}
