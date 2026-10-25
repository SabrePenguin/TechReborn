package com.sabrepenguin.techreborn.gui;

import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;

public class TRItemSlot extends ItemSlot {

    @Override
    public void draw(ModularGuiContext context, WidgetThemeEntry<?> widgetTheme) {
        if (!getSlot().getHasStack()) {
            TRGuiTextures.FLUID_ITEM_SLOT.draw(1, 2, 16, 16);
        }
        super.draw(context, widgetTheme);
    }
}
