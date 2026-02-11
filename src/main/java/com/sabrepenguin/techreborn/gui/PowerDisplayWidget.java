package com.sabrepenguin.techreborn.gui;

import com.cleanroommc.modularui.api.value.IIntValue;
import com.cleanroommc.modularui.api.value.ISyncOrValue;
import com.cleanroommc.modularui.api.value.sync.IIntSyncValue;
import com.cleanroommc.modularui.drawable.GuiDraw;
import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.value.IntValue;
import com.cleanroommc.modularui.widget.Widget;
import com.sabrepenguin.techreborn.Tags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

public class PowerDisplayWidget extends Widget<PowerDisplayWidget> {
	private int capacity = 0;
	private IIntValue<?> value;
	private static final ResourceLocation POWER = new ResourceLocation(Tags.MODID, "textures/gui/energy.png");

	public PowerDisplayWidget() {
		width(14);
		height(50);
	}

	@Override
	public void onInit() {
		if (value == null) {
			value = new IntValue(0);
		}
		super.onInit();
	}

	@Override
	public void draw(ModularGuiContext context, WidgetThemeEntry<?> widgetTheme) {
		super.draw(context, widgetTheme);
		int w = getArea().width;
		int h = getArea().height;
		GuiDraw.drawTexture(POWER, 0, 0, w, h, 0, 0, 0.5f, 1);
		float clipped = (float) this.value.getIntValue() / capacity;
		clipped = MathHelper.clamp(clipped, 0.0f, 1.0f);
		int actual = (int) (clipped*h);
		GuiDraw.drawTexture(POWER, 0, h - actual, w, h, 0.5f, 1-clipped, 1, 1);
	}

	public PowerDisplayWidget capacity(int capacity) {
		this.capacity = capacity;
		return this;
	}

	public PowerDisplayWidget value(IIntSyncValue<?> value) {
		setSyncOrValue(ISyncOrValue.orEmpty(value));
		return this;
	}

	@Override
	protected void setSyncOrValue(@NotNull ISyncOrValue syncOrValue) {
		super.setSyncOrValue(syncOrValue);
		this.value = syncOrValue.castNullable(IIntValue.class);
	}

	@Override
	public boolean isValidSyncOrValue(@NotNull ISyncOrValue syncOrValue) {
		return syncOrValue.isTypeOrEmpty(IIntValue.class);
	}
}
