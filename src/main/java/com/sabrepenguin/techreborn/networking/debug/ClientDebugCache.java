package com.sabrepenguin.techreborn.networking.debug;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class ClientDebugCache {
	private static BlockPos lastCheckedPosition;
	private static final Boolean[] objectResults = new Boolean[6];

	public static void update(BlockPos pos, int index, boolean result) {
		if (!pos.equals(lastCheckedPosition)) {
			lastCheckedPosition = pos;
			for (int i = 0; i < 6; i++) {
				objectResults[i] = null;
			}
		}
		if (index < 6) {
			objectResults[index] = result;
		}
	}

	public static Boolean isLinked(BlockPos pos, EnumFacing facing) {
		if (objectResults[facing.getIndex()] == null)
			return null;
		if (!pos.equals(lastCheckedPosition))
			return null;
		return objectResults[facing.getIndex()];
	}
}
