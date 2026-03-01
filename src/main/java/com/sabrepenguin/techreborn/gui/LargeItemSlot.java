package com.sabrepenguin.techreborn.gui;

import com.cleanroommc.modularui.drawable.UITexture;
import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import com.sabrepenguin.techreborn.Tags;

public class LargeItemSlot extends ItemSlot {
	private static final UITexture SLOT = UITexture.builder().location(Tags.MODID, "gui/big_slot").fullImage().build();

	@Override
	public void drawBackground(ModularGuiContext context, WidgetThemeEntry<?> widgetTheme) {
		int margin = (26 - SIZE) / 2;
		SLOT.draw(-margin, -margin, 26, 26);
	}
}
