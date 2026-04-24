package com.sabrepenguin.techreborn.items.reactor;

import com.sabrepenguin.techreborn.items.TechRebornItem;
import ic2.api.info.Info;
import ic2.api.item.IHazmatLike;
import ic2.api.reactor.IBaseReactorComponent;
import ic2.api.reactor.IReactor;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemIC2DepletedThoriumFuelRod extends TechRebornItem implements IBaseReactorComponent {
	public ItemIC2DepletedThoriumFuelRod(String name) {
		super(name, "reactor");
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (entityIn instanceof EntityLivingBase entity && !IHazmatLike.hasCompleteHazmat(entity)) {
			if (entity instanceof EntityPlayer player && player.isCreative())
				return;
			entity.addPotionEffect(new PotionEffect(Info.POTION_RADIATION, 2000, 20));
		}
	}

	@Override
	public boolean canBePlacedIn(ItemStack itemStack, IReactor iReactor) {
		return false;
	}
}
