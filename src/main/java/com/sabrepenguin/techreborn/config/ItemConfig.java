package com.sabrepenguin.techreborn.config;

import net.minecraftforge.common.config.Config;

@Config.LangKey("config.item")
public class ItemConfig {
	@Config.LangKey("item.techreborn.cloakingdevice.name")
	public CloakingDevice cloakingDevice = new CloakingDevice();
	@Config.LangKey("item.techreborn.lapotronpack.name")
	public LapotronPack lapotronPack = new LapotronPack();
	@Config.LangKey("item.techreborn.lithiumbatpack.name")
	public LithiumBatpack lithiumBatpack = new LithiumBatpack();
	@Config.LangKey("item.techreborn.rebattery.name")
	public BatteryItem batteryItem = new BatteryItem();
	@Config.LangKey("config.item.chainsaw")
	public Chainsaws chainsaws = new Chainsaws();

	public static class CloakingDevice {
		@Config.LangKey("config.max_energy")
		public int maxEnergy = 40_000_000;
		@Config.LangKey("config.consumption")
		public int consumption = 10;
	}

	public static class LapotronPack {
		@Config.LangKey("config.max_energy")
		public int maxEnergy = 400_000_000;
		@Config.LangKey("config.transfer_limit")
		public int transferLimit = 100_000;
	}

	public static class LithiumBatpack {
		@Config.LangKey("config.max_energy")
		public int maxEnergy = 16_000_000;
		@Config.LangKey("config.transfer_limit")
		public int transferLimit = 10_000;
	}

	public static class BatteryItem {
		@Config.LangKey("config.lapotronic.max_energy")
		@Config.RequiresMcRestart
		public int lapotronicMaxEnergy = 400_000_000;
		@Config.LangKey("config.lapotronic_crystal.max_energy")
		@Config.RequiresMcRestart
		public int lapotronicCrystalMaxEnergy = 40_000_000;
		@Config.LangKey("config.energy_crystal.max_energy")
		@Config.RequiresMcRestart
		public int energyCrystalMaxEnergy = 4_000_000;
	}

	public static class Chainsaws {
		@Config.LangKey("config.iron_chainsaw.max_energy")
		@Config.RequiresMcRestart
		public int ironChainsawMaxEnergy = 40_000;
		@Config.LangKey("config.diamond_chainsaw.max_energy")
		@Config.RequiresMcRestart
		public int diamondChainsawMaxEnergy = 400_000;
		@Config.LangKey("config.advanced_chainsaw.max_energy")
		@Config.RequiresMcRestart
		public int advancedChainsawMaxEnergy = 4_000_000;
	}
}
