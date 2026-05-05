package com.sabrepenguin.techreborn.jei.wrappers;

import com.sabrepenguin.techreborn.config.TechRebornConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class RecyclerRecipeWrapper extends SingleItemRecipeWrapper {
	public RecyclerRecipeWrapper(ItemStack input, ItemStack output, int time, int energyCost) {
		super(input, output, time, energyCost);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
		String chance = "1/" + TechRebornConfig.machineConfig.recycler.scrapChance + " Chance";
		minecraft.fontRenderer.drawString(chance,
				(recipeWidth / 2) - (minecraft.fontRenderer.getStringWidth(chance) / 2),
				30 + ((minecraft.fontRenderer.FONT_HEIGHT + 1)*2), 0x444444);
	}
}
