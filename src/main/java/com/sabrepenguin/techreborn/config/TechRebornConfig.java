package com.sabrepenguin.techreborn.config;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.tileentity.cable.HandlerCable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Config(modid= Tags.MODID)
public class TechRebornConfig {

	@Config.LangKey("config.compat")
	public static Compat compat = new Compat();

	@Config.LangKey("config.misc")
	public static Misc misc = new Misc();

	public static class Compat {
		@Config.LangKey("config.ic2")
		public IC2 ic2;

		public Compat() {
			ic2 = new IC2();
		}
	}

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
		@Config.LangKey("config.cable.debugNetwork")
		public boolean debugNetwork;

		public Cable(boolean electrocutionDamage, boolean electrocutionSound, boolean electrocutionParticles) {
			this.electrocutionDamage = electrocutionDamage;
			this.electrocutionSound = electrocutionSound;
			this.electrocutionParticles = electrocutionParticles;
			debugNetwork = false;
		}
	}

	public static class IC2 {
		@Config.LangKey("config.ic2.deduplicate")
		public boolean deduplicate;
		@Config.LangKey("config.ic2.classic")
		public boolean classic;

		public IC2() {
			deduplicate = false;
			classic = false;
		}
	}

	@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Tags.MODID)
	public static class EventHandler {
		@SubscribeEvent
		public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(Tags.MODID)) {
				ConfigManager.sync(Tags.MODID, Config.Type.INSTANCE);
				cableDebugHandler();
			}
		}

		public static void cableDebugHandler() {
			if (misc.cable.debugNetwork) {
				MinecraftForge.EVENT_BUS.register(HandlerCable.class);
			} else {
				MinecraftForge.EVENT_BUS.unregister(HandlerCable.class);
			}
		}
	}
}
