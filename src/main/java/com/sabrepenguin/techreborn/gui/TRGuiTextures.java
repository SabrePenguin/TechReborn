package com.sabrepenguin.techreborn.gui;

import com.cleanroommc.modularui.drawable.Rectangle;
import com.cleanroommc.modularui.drawable.UITexture;
import com.cleanroommc.modularui.utils.Color;
import com.sabrepenguin.techreborn.Tags;

public class TRGuiTextures {

	public static final UITexture OUTPUT = UITexture.builder().location(Tags.MODID, "gui/output_slot").nonOpaque().fullImage().build();
	public static final UITexture INPUT = UITexture.builder().location(Tags.MODID, "gui/input_slot").nonOpaque().fullImage().build();
	public static final UITexture CHECK = UITexture.builder().location(Tags.MODID, "gui/check").nonOpaque().fullImage().build();
	public static final UITexture CONFIG = UITexture.builder().location(Tags.MODID, "gui/config_button").nonOpaque().fullImage().build();
	public static final UITexture WARNING = UITexture.builder().location(Tags.MODID, "gui/warning").nonOpaque().fullImage().build();
	public static final UITexture UPGRADE = UITexture.builder().location(Tags.MODID, "gui/upgrade_base").fullImage().build();
	public static final UITexture FLUID_TANK = UITexture.builder().location(Tags.MODID, "gui/fluid_tank").fullImage().build();
	public static final UITexture FLUID_TANK_OVERLAY = UITexture.builder().location(Tags.MODID, "gui/tank_overlay").fullImage().build();
	public static final UITexture FLUID_ITEM_SLOT = UITexture.builder().location(Tags.MODID, "gui/bucket_slot").fullImage().build();
	public static UITexture LEFT_PROGRESS_ARROW = UITexture.builder()
			.location(Tags.MODID, "gui/left_arrow")
			.imageSize(20, 40)
			.canApplyTheme()
			.build();
	public static UITexture DOWN_PROGRESS_ARROW = UITexture.builder()
			.location(Tags.MODID, "gui/down_arrow")
			.imageSize(20, 40)
			.canApplyTheme()
			.build();
	public static UITexture UP_PROGRESS_ARROW = UITexture.builder()
			.location(Tags.MODID, "gui/up_arrow")
			.imageSize(20, 40)
			.canApplyTheme()
			.build();
	public static UITexture UP_PROGRESS_ARROWS = UITexture.builder()
			.location(Tags.MODID, "gui/up_arrows")
			.imageSize(24, 20)
			.canApplyTheme()
			.build();
	public static UITexture LOCK = UITexture.builder()
			.location(Tags.MODID, "gui/lock")
			.imageSize(16, 24)
			.canApplyTheme()
			.build();

	public static Rectangle INPUT_BUTTON = new Rectangle().color(Color.rgb(135, 135, 255));
	public static Rectangle INPUT_BUTTON_OUTLINE = new Rectangle().color(Color.rgb(0, 0, 255)).hollow(2);
	public static Rectangle OUTPUT_BUTTON = new Rectangle().color(Color.rgb(255, 190, 123));
	public static Rectangle OUTPUT_BUTTON_OUTLINE = new Rectangle().color(Color.rgb(255, 172, 86)).hollow(2);
	public static Rectangle BIDIRECTIONAL_BUTTON = new Rectangle().color(Color.rgb(255, 57, 57));
	public static Rectangle BIDIRECTIONAL_BUTTON_OUTLINE = new Rectangle().color(Color.rgb(255, 0, 0)).hollow(2);
}
