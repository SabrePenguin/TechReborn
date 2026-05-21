package com.sabrepenguin.techreborn.gui;

import com.cleanroommc.modularui.drawable.GuiDraw;
import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.utils.Color;
import com.cleanroommc.modularui.widget.Widget;

public class RectangleWidget extends Widget<RectangleWidget> {

	protected int color;

	public RectangleWidget(int r, int g, int b) {
		color = Color.rgb(r, g, b);
	}

	@Override
	public void drawBackground(ModularGuiContext context, WidgetThemeEntry<?> widgetTheme) {
		super.drawBackground(context, widgetTheme);
		int w = getArea().w();
		int h = getArea().h();

		GuiDraw.drawRect(0, 0, w, h, color);
	}
}
