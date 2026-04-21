package com.sabrepenguin.techreborn.util;

import net.minecraftforge.fml.common.Loader;

public class ModLoadedUtil {
	public static boolean IC2_LOADED;

	public static void init() {
		IC2_LOADED = Loader.isModLoaded("ic2");
	}
}
