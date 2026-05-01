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
	@Config.RequiresMcRestart
	public BatteryItem batteryItem = new BatteryItem();
	@Config.LangKey("config.item.chainsaw")
	@Config.RequiresMcRestart
	public Chainsaws chainsaws = new Chainsaws();
	@Config.LangKey("config.item.drills")
	@Config.RequiresMcRestart
	public Drills drills = new Drills();
	@Config.LangKey("config.item.jackhammers")
	@Config.RequiresMcRestart
	public Jackhammers jackhammers = new Jackhammers();
	@Config.LangKey("item.techreborn.rockcutter.name")
	@Config.RequiresMcRestart
	public RockCutter rockCutter = new RockCutter();
	@Config.LangKey("item.techreborn.omnitool.name")
	@Config.RequiresMcRestart
	public OmniTool omniTool = new OmniTool();

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
		public int lapotronicMaxEnergy = 400_000_000;
		@Config.LangKey("config.lapotronic_crystal.max_energy")
		public int lapotronicCrystalMaxEnergy = 40_000_000;
		@Config.LangKey("config.energy_crystal.max_energy")
		public int energyCrystalMaxEnergy = 4_000_000;
	}

	public static class Chainsaws {
		@Config.LangKey("config.iron_chainsaw.max_energy")
		public int ironChainsawMaxEnergy = 40_000;
		@Config.LangKey("config.diamond_chainsaw.max_energy")
		public int diamondChainsawMaxEnergy = 400_000;
		@Config.LangKey("config.advanced_chainsaw.max_energy")
		public int advancedChainsawMaxEnergy = 4_000_000;
	}

	public static class Drills {
		@Config.LangKey("config.iron_drill.max_energy")
		public int ironDrillMaxEnergy = 40_000;
		@Config.LangKey("config.diamond_drill.max_energy")
		public int diamondDrillMaxEnergy = 400_000;
		@Config.LangKey("config.advanced_drill.max_energy")
		public int advancedDrillMaxEnergy = 4_000_000;
	}

	public static class Jackhammers {
		@Config.LangKey("config.steel_jackhammer.max_energy")
		public int steelJackhammerMaxEnergy = 40_000;
		@Config.LangKey("config.diamond_jackhammer.max_energy")
		public int diamondJackhammerMaxEnergy = 400_000;
		@Config.LangKey("config.advanced_jackhammer.max_energy")
		public int advancedJackhamerMaxEnergy = 4_000_000;
	}

	public static class RockCutter {
		@Config.LangKey("config.max_energy")
		public int rockCutterMaxEnergy = 400_000;
	}

	public static class OmniTool {
		@Config.LangKey("config.max_energy")
		public int omniToolMaxEnergy = 20_000;
	}
}
