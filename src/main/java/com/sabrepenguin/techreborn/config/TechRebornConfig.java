package com.sabrepenguin.techreborn.config;

import com.sabrepenguin.techreborn.Tags;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Config(modid= Tags.MODID)
public class TechRebornConfig {

	@Config.LangKey("config.misc")
	public static Misc misc = new Misc();

	public static class Misc {
		@Config.LangKey("config.cable")
		public Cable cable;

		public Misc() {
			cable = new Cable(true, true, true);
		}
	}

	public static class Cable {
		@Config.LangKey("config.cable.uninsulatedElectrocutionDamage")
		public boolean electrocutionDamage;
		@Config.LangKey("config.cable.uninsulatedElectrocutionSound")
		public boolean electrocutionSound;
		@Config.LangKey("config.cable.uninsulatedElectrocutionParticles")
		public boolean electrocutionParticles;

		public Cable(boolean electrocutionDamage, boolean electrocutionSound, boolean electrocutionParticles) {
			this.electrocutionDamage = electrocutionDamage;
			this.electrocutionSound = electrocutionSound;
			this.electrocutionParticles = electrocutionParticles;
		}
	}

	@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Tags.MODID)
	private static class EventHandler {
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(Tags.MODID)) {
				ConfigManager.sync(Tags.MODID, Config.Type.INSTANCE);
			}
		}
	}
}
