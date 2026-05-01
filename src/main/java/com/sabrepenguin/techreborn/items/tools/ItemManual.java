package com.sabrepenguin.techreborn.items.tools;

import com.sabrepenguin.techreborn.items.ItemHelper;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

//TODO: Add Patchouli
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemManual extends Item {
	public ItemManual() {
		ItemHelper.registerUnstackable(this, "manual");
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("Tech Reborn's original manual redirects you to the wiki.");
		tooltip.add("If you really need an in-game Tech Reborn manual, let the author know, and they'll put more work on it.");
	}
}
