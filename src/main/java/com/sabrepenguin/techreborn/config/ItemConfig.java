package com.sabrepenguin.techreborn.config;

import net.minecraftforge.common.config.Config;

@Config.LangKey("config.item")
public class ItemConfig {
	@Config.LangKey("config.item.cloaking")
	public CloakingDevice cloakingDevice = new CloakingDevice();

	public static class CloakingDevice {
		@Config.LangKey("config.item.cloaking.max_energy")
		public int maxEnergy = 160_000_000;
		@Config.LangKey("config.item.cloaking.consumption")
		public int consumption = 40;
	}
}
