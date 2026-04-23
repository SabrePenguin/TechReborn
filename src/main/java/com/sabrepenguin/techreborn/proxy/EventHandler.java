package com.sabrepenguin.techreborn.proxy;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.items.TRItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Tags.MODID)
public class EventHandler {
	@SubscribeEvent
	@SuppressWarnings("ConstantConditions")
	public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
		if (event.getSlot().getSlotType() != EntityEquipmentSlot.Type.ARMOR) return;
		if (!(event.getEntityLiving() instanceof EntityPlayer player)) return;
		ItemStack from = event.getFrom();
		if (!from.isEmpty()) {
			if (from.getItem() == TRItems.cloakingdevice) {
				if (!player.isPotionActive(MobEffects.INVISIBILITY))
					player.setInvisible(false);
			}
		}
	}
}
