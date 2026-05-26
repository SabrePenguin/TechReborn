package com.sabrepenguin.techreborn.config;

import net.minecraftforge.common.config.Config;

public class MachineConfig {
	@Config.LangKey("tile.techreborn.alloy_smelter.name")
	public Generic alloySmelter = new Generic(4000, 128);
	@Config.LangKey("tile.techreborn.grinder.name")
	public ElectricFurnace electricFurnace = new ElectricFurnace();
	@Config.LangKey("tile.techreborn.grinder.name")
	public Generic grinder = new Generic(40000, 128);
	@Config.LangKey("tile.techreborn.extractor.name")
	public Generic extractor = new Generic(40000, 128);
	@Config.LangKey("tile.techreborn.plate_bending_machine.name")
	public Generic plateBendingMachine = new Generic(40000, 128);
	@Config.LangKey("tile.techreborn.wire_mill.name")
	public Generic wireMill = new Generic(40000, 128);
	@Config.LangKey("tile.techreborn.compressor.name")
	public Generic compressor = new Generic(40000, 128);
	@Config.LangKey("tile.techreborn.recycler.name")
	public Recycler recycler = new Recycler();
	@Config.LangKey("tile.techreborn.assembling_machine.name")
	public Generic assemblingMachine = new Generic(40000, 128);
	@Config.LangKey("tile.techreborn.scrapboxinator.name")
	public Generic scrapBoxinator = new Generic(4000, 128);
	@Config.LangKey("tile.techreborn.chemical_reactor.name")
	public Generic chemicalReactor = new Generic(40000, 512);
	@Config.LangKey("tile.techreborn.solid_canning_machine.name")
	public Generic solidCanningMachine = new Generic(40000, 128);
	@Config.LangKey("tile.techreborn.charge_o_mat.name")
	public Generic chargeOMat = new Generic(400_000_000, 2048);
	@Config.LangKey("tile.techreborn.matter_fabricator.name")
	public MatterFabricator matterFabricator = new MatterFabricator();
	@Config.LangKey("tile.techreborn.matter_fabricator.name")
	public Generic rollingMachine = new Generic(40_000, 128);

	public static class Generic {
		@Config.LangKey("config.max_energy")
		public int maxEnergy;
		@Config.LangKey("config.transfer_limit")
		public int maxInput;

		public Generic(int energy, int maxInput) {
			this.maxEnergy = energy;
			this.maxInput = maxInput;
		}
	}

	public static class Recycler {
		@Config.LangKey("config.recycler.ic2scrap")
		public boolean ic2Scrap = false;
		@Config.LangKey("config.max_energy")
		public int maxEnergy = 4000;
		@Config.LangKey("config.transfer_limit")
		public int maxInput = 128;
		@Config.LangKey("config.recycler.scrap_chance")
		public int scrapChance = 6;
	}

	public static class ElectricFurnace {
		@Config.LangKey("config.max_cost")
		public int maxCost = 24;
		@Config.LangKey("config.max_energy")
		public int maxEnergy = 4000;
		@Config.LangKey("config.transfer_limit")
		public int maxInput = 128;
	}

	public static class MatterFabricator {
		@Config.LangKey("config.max_energy")
		public int maxEnergy = 40_000_000;
		@Config.LangKey("config.transfer_limit")
		public int maxInput = 32768;
		@Config.LangKey("config.max_energy")
		public int fabricationRate = 6000;
		@Config.LangKey("config.transfer_limit")
		@Config.RangeInt(min = 1)
		public int energyPerAmp = 20;
	}
}
