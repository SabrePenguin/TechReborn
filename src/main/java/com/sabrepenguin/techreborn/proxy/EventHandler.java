package com.sabrepenguin.techreborn.proxy;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.items.TRItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
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

	@SubscribeEvent
	@SuppressWarnings("ConstantConditions")
	public static void getBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
		if (event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem() == TRItems.advancedchainsaw && event.getOriginalSpeed() > 1) {
			BlockPos pos = event.getPos();
			World world = event.getEntityPlayer().world;
			float speed = 20f;
			int blocks = 0;
			for (int i = 0; i < 10; i++) {
				BlockPos nextPos = pos.up(i);
				IBlockState nextState = world.getBlockState(nextPos);
				if (nextState.getBlock().isWood(world, nextPos)) {
					blocks++;
				}
			}
			event.setNewSpeed(speed / blocks);
		}
	}
}
