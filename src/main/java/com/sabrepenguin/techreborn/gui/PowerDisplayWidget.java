package com.sabrepenguin.techreborn.gui;

import com.cleanroommc.modularui.ModularUIConfig;
import com.cleanroommc.modularui.api.value.IIntValue;
import com.cleanroommc.modularui.api.widget.Interactable;
import com.cleanroommc.modularui.drawable.GuiDraw;
import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.value.IntValue;
import com.cleanroommc.modularui.widget.Widget;
import com.sabrepenguin.techreborn.Tags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

import java.text.DecimalFormat;

public class PowerDisplayWidget extends Widget<PowerDisplayWidget> implements Interactable {
	private IIntValue<?> energy;
	private IIntValue<?> capacity;
	private IIntValue<?> maxReceive;
	private IIntValue<?> maxExtract;

	private int lastEnergy = -1;
	private int lastCapacity = -1;
	private int lastMaxReceive = -1;
	private int lastMaxExtract = -1;

	private static final ResourceLocation POWER = new ResourceLocation(Tags.MODID, "textures/gui/energy.png");
	private static final DecimalFormat DECIMAL = new DecimalFormat("#.##");

	public PowerDisplayWidget() {
		width(14);
		height(50);
	}

	@Override
	public void onInit() {
		if (energy == null) energy = new IntValue(0);
		if (capacity == null) capacity = new IntValue(0);
		if (maxReceive == null) maxReceive = new IntValue(0);
		if (maxExtract == null) maxExtract = new IntValue(0);
		super.onInit();
		this.tooltipDynamic(tooltip -> {
			int current = this.energy.getIntValue();
			int max = capacity.getIntValue();
			int percentage = max > 0 ? (int) (((float) current / max) * 100) : 0;
			if (Interactable.hasShiftDown()) {
				tooltip.addLine( TextFormatting.GOLD.toString() + current + "/" + max + " FE");
				tooltip.addLine(TextFormatting.YELLOW.toString() + percentage + "%" + TextFormatting.WHITE + " charged");
				int maxInput = maxReceive.getIntValue();
				int maxOuput = maxExtract.getIntValue();
				if (maxInput > 0) {
					tooltip.addLine("Input Rate: " + TextFormatting.GOLD + maxInput + "FE");
				}
				if (maxOuput > 0) {
					tooltip.addLine("Output Rate: " + TextFormatting.GOLD + maxOuput + "FE");
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

		int currentEnergy = energy.getIntValue();
		int currentCapacity = capacity.getIntValue();
		int currentMaxReceive = maxReceive.getIntValue();
		int currentMaxExtract = maxExtract.getIntValue();

		if (currentEnergy != lastEnergy || currentCapacity != lastCapacity
				|| currentMaxReceive != lastMaxReceive || currentMaxExtract != lastMaxExtract) {
			markTooltipDirty();
			lastEnergy = currentEnergy;
			lastCapacity = currentCapacity;
			lastMaxReceive = currentMaxReceive;
			lastMaxExtract = currentMaxExtract;
		}

		int w = getArea().width;
		int h = getArea().height;
		GuiDraw.drawTexture(POWER, 0, 0, w, h, 0, 0, 0.5f, 1);
		{
			float clipped = currentCapacity > 0 ? (float) currentEnergy / currentCapacity : 0;
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

	public PowerDisplayWidget capacity(IIntValue<?> capacity) {
		this.capacity = capacity;
		return this;
	}

	public PowerDisplayWidget maxReceive(IIntValue<?> maxReceive) {
		this.maxReceive = maxReceive;
		return this;
	}

	public PowerDisplayWidget energy(IIntValue<?> energy) {
		this.energy = energy;
		return this;
	}

	public PowerDisplayWidget maxExtract(IIntValue<?> maxExtract) {
		this.maxExtract = maxExtract;
		return this;
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
