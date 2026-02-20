package com.sabrepenguin.techreborn.gui;

import com.cleanroommc.modularui.ModularUIConfig;
import com.cleanroommc.modularui.api.value.IIntValue;
import com.cleanroommc.modularui.api.value.ISyncOrValue;
import com.cleanroommc.modularui.api.value.sync.IIntSyncValue;
import com.cleanroommc.modularui.api.widget.Interactable;
import com.cleanroommc.modularui.drawable.GuiDraw;
import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.value.IntValue;
import com.cleanroommc.modularui.widget.Widget;
import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.capability.IEnergyInformation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

import java.text.DecimalFormat;

public class PowerDisplayWidget extends Widget<PowerDisplayWidget> implements Interactable {
	private IIntValue<?> value;
	private IEnergyStorage storage;
	private static final ResourceLocation POWER = new ResourceLocation(Tags.MODID, "textures/gui/energy.png");
	private static final DecimalFormat DECIMAL = new DecimalFormat("#.##");

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
		this.tooltipDynamic(tooltip -> {
			int current = this.value.getIntValue();
			int max = storage.getMaxEnergyStored();
			int percentage = max > 0 ? (int) (((float) current / max) * 100) : 0;
			if (Interactable.hasShiftDown()) {
				tooltip.addLine( TextFormatting.GOLD.toString() + current + "/" + max + " FE");
				tooltip.addLine(TextFormatting.YELLOW.toString() + percentage + "%" + TextFormatting.WHITE + " charged");
				if (storage instanceof IEnergyInformation energyInformation) {
					if (energyInformation.getMaxInput() > 0) {
						tooltip.addLine("Input Rate: " + TextFormatting.GOLD + energyInformation.getMaxInput() + "FE");
					}
					if (energyInformation.getMaxOutput() > 0) {
						tooltip.addLine("Output Rate: " + TextFormatting.GOLD + energyInformation.getMaxOutput() + "FE");
					}
				}
			} else {
				tooltip.addLine( TextFormatting.GOLD + intToCompact(current) + "/" + intToCompact(max) + " FE");
				tooltip.addLine(TextFormatting.YELLOW.toString() + percentage + "%" + TextFormatting.WHITE + " charged");
				tooltip.addLine(TextFormatting.BLUE + "Shift" + TextFormatting.WHITE + " for more");
			}
		});
	}

	// TODO: Replaced with I18N version
	private String intToCompact(int in) {
		if (in < 1_000) return String.valueOf(in);
		else if (in < 1_000_000) return DECIMAL.format((double) in / 1_000) + "k";
		else if (in < 1_000_000_000) return DECIMAL.format((double) in / 1_000_000) + "m";
		return DECIMAL.format((double)in / 1_000_000_000) + "b";
	}

	@Override
	public void draw(ModularGuiContext context, WidgetThemeEntry<?> widgetTheme) {
		super.draw(context, widgetTheme);
		int w = getArea().width;
		int h = getArea().height;
		GuiDraw.drawTexture(POWER, 0, 0, w, h, 0, 0, 0.5f, 1);
		if (storage != null) {
			int max = storage.getMaxEnergyStored();
			int current = this.value.getIntValue();
			float clipped = max > 0 ? (float) current / max : 0;
			clipped = MathHelper.clamp(clipped, 0.0f, 1.0f);
			int maxFillHeight = h - 2;
			int y1 = h - 1;
			float v1 = (float) y1 / h;
			if (ModularUIConfig.smoothProgressBar) {
				float actual = clipped * maxFillHeight;
				if (actual > 0) {
					float y0 = y1 - actual;
					float v0 = y0 / h;
					GuiDraw.drawTexture(POWER, 0, y0, w, y1, 0.5f, v0, 1f, v1);
				}
			} else {
				int actual = (int) (clipped * maxFillHeight);
				if (actual > 0) {
					int y0 = y1 - actual;
					float v0 = (float) y0 / h;
					GuiDraw.drawTexture(POWER, 0, y0, w, y1, 0.5f, v0, 1f, v1);
				}
			}
		}
	}

	public PowerDisplayWidget value(IIntSyncValue<?> value) {
		setSyncOrValue(ISyncOrValue.orEmpty(value));
		return this;
	}

	public PowerDisplayWidget energyHandler(IEnergyStorage energyStorage) {
		this.storage = energyStorage;
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

	@Override
	public @NotNull Result onKeyPressed(char typedChar, int keyCode) {
		if (keyCode == Keyboard.KEY_LSHIFT || keyCode == Keyboard.KEY_RSHIFT) {
			markTooltipDirty();
		}
		return Interactable.super.onKeyPressed(typedChar, keyCode);
	}

	@Override
	public boolean onKeyRelease(char typedChar, int keyCode) {
		if (keyCode == Keyboard.KEY_LSHIFT || keyCode == Keyboard.KEY_RSHIFT) {
			markTooltipDirty();
		}
		return Interactable.super.onKeyRelease(typedChar, keyCode);
	}
}
