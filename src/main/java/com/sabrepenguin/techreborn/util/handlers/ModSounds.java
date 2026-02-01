package com.sabrepenguin.techreborn.util.handlers;

import com.sabrepenguin.techreborn.Tags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Tags.MODID)
public class ModSounds {
	public static SoundEvent CABLE_SHOCK;
	public static SoundEvent BLOCK_DISMANTLE;
	public static SoundEvent SAP_EXTRACT;
	public static SoundEvent AUTO_CRAFTING;
	public static SoundEvent MACHINE_RUN;
	public static SoundEvent MACHINE_START;
	public static SoundEvent ALARM;
	public static SoundEvent ALARM_2;
	public static SoundEvent ALARM_3;

	public static void initSounds() {
		CABLE_SHOCK = registerSound("cable_shock");
		BLOCK_DISMANTLE = registerSound("block_dismantle");
		SAP_EXTRACT = registerSound("sap_extract");
		AUTO_CRAFTING = registerSound("auto_crafting");
		MACHINE_RUN = registerSound("machine_run");
		MACHINE_START = registerSound("machine_start");
		ALARM = registerSound("alarm");
		ALARM_2 = registerSound("alarm_2");
		ALARM_3 = registerSound("alarm_3");
	}

	private static SoundEvent registerSound(String name) {
		ResourceLocation location = new ResourceLocation(Tags.MODID, name);
		SoundEvent event = new SoundEvent(location);
		event.setRegistryName(name);
		return event;
	}

	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		initSounds();
		final SoundEvent[] soundEvents = {
				CABLE_SHOCK,
				BLOCK_DISMANTLE,
				SAP_EXTRACT,
				AUTO_CRAFTING,
				MACHINE_START,
				MACHINE_RUN,
				ALARM_3,
				ALARM_2,
				ALARM
		};
		event.getRegistry().registerAll(soundEvents);
	}
}
