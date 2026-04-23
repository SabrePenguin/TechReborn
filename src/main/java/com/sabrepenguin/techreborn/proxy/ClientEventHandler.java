package com.sabrepenguin.techreborn.proxy;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.armor.ItemCloak;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = Tags.MODID, value = Side.CLIENT)
public class ClientEventHandler {
	@SubscribeEvent
	@SuppressWarnings("ConstantConditions")
	public static void renderPlayer(RenderPlayerEvent.Pre event) {
		EntityPlayer player = event.getEntityPlayer();
		ItemStack chestSlot = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

		if (!chestSlot.isEmpty() && chestSlot.getItem() == TRItems.cloakingdevice) {
			if (ItemCloak.active(chestSlot))
				event.setCanceled(true);
		}
	}
}
