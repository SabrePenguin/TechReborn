package com.sabrepenguin.techreborn.gui;

import com.cleanroommc.modularui.ModularUIConfig;
import com.cleanroommc.modularui.api.value.IDoubleValue;
import com.cleanroommc.modularui.api.value.ISyncOrValue;
import com.cleanroommc.modularui.drawable.UITexture;
import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetTheme;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.value.DoubleValue;
import com.cleanroommc.modularui.widget.Widget;
import com.sabrepenguin.techreborn.Tags;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

public class FurnaceFuelWidget extends Widget<FurnaceFuelWidget> {

	private static final UITexture FLAME = UITexture.builder().location(Tags.MODID, "gui/fire").imageSize(14, 28).build();
	private static final UITexture FLAME_BACK = FLAME.getSubArea(0f, 0f, 1f, 0.5f);
	private static final UITexture FLAME_FRONT = FLAME.getSubArea(0f, 0.5f, 1f, 1f);

	private IDoubleValue<?> doubleValue;

	public FurnaceFuelWidget() {
		width(14);
		height(14);
	}

	@Override
	public void onInit() {
		if (this.doubleValue == null) {
			this.doubleValue = new DoubleValue(0.5);
		}
	}

	@Override
	public void draw(ModularGuiContext context, WidgetThemeEntry<?> widgetTheme) {
		WidgetTheme theme = getActiveWidgetTheme(widgetTheme, isHovering());
		int w = getArea().width;
		int h = getArea().height;
		FLAME_BACK.draw(context, 0, 0, w, h, theme);
		float value = (float) this.doubleValue.getDoubleValue();
		if (Double.isNaN(value)) return;
		float burnAmount = MathHelper.clamp(value, 0.0f, 1.0f); // Should be zero to one, but we never know *what* will pass
		if (burnAmount > 0) {
			int maxFillHeight = h - 1;
			// TODO: Replace with non-experimental API
			float pixelCount = ModularUIConfig.smoothProgressBar ? burnAmount * maxFillHeight : (float) Math.ceil(burnAmount * maxFillHeight);
			float y0 = maxFillHeight - pixelCount;
			FLAME_FRONT.drawSubArea(0, y0, w, pixelCount + 1, 0, y0 / h, 1f, 1f, theme);
		}
	}

	@Override
	protected WidgetTheme getActiveWidgetTheme(WidgetThemeEntry<?> widgetTheme, boolean hover) {
		return super.getActiveWidgetTheme(widgetTheme, hover);
	}

	@Override
	public boolean isValidSyncOrValue(@NotNull ISyncOrValue syncOrValue) {
		return syncOrValue.isTypeOrEmpty(IDoubleValue.class);
	}

	@Override
	protected void setSyncOrValue(@NotNull ISyncOrValue syncOrValue) {
		super.setSyncOrValue(syncOrValue);
		this.doubleValue = syncOrValue.castNullable(IDoubleValue.class);
	}

	public FurnaceFuelWidget value(IDoubleValue<?> value) {
		setSyncOrValue(ISyncOrValue.orEmpty(value));
		return this;
	}
}
