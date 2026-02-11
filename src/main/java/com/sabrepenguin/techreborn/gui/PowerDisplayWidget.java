package com.sabrepenguin.techreborn.gui;

import com.cleanroommc.modularui.drawable.GuiDraw;
import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.widget.Widget;
import com.sabrepenguin.techreborn.Tags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class PowerDisplayWidget extends Widget<PowerDisplayWidget> {

	private static final ResourceLocation POWER = new ResourceLocation(Tags.MODID, "textures/gui/energy.png");

	public PowerDisplayWidget() {
		width(14);
		height(50);
	}

	@Override
	public void draw(ModularGuiContext context, WidgetThemeEntry<?> widgetTheme) {
		super.draw(context, widgetTheme);
		int w = getArea().width;
		int h = getArea().height;
		GuiDraw.drawTexture(POWER, 0, 0, w, h, 0, 0, 0.5f, 1);
		float clipped = 0.8f;
		clipped = MathHelper.clamp(clipped, 0.0f, 1.0f);
		int actual = (int) (clipped*h);
		GuiDraw.drawTexture(POWER, 0, h - actual, w, h, 0.5f, 1-clipped, 1, 1);
	}
}
