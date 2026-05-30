package com.sabrepenguin.techreborn.gui;

import com.cleanroommc.modularui.drawable.UITexture;
import com.sabrepenguin.techreborn.Tags;

public class TRGuiTextures {

	public static final UITexture OUTPUT = UITexture.builder().location(Tags.MODID, "gui/output_slot").nonOpaque().fullImage().build();
	public static final UITexture INPUT = UITexture.builder().location(Tags.MODID, "gui/input_slot").nonOpaque().fullImage().build();
	public static final UITexture CHECK = UITexture.builder().location(Tags.MODID, "gui/check").nonOpaque().fullImage().build();
	public static final UITexture CONFIG = UITexture.builder().location(Tags.MODID, "gui/config_button").nonOpaque().fullImage().build();
	public static final UITexture UPGRADE = UITexture.builder().location(Tags.MODID, "gui/upgrade_base").fullImage().build();
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
	public static UITexture LOCK = UITexture.builder()
			.location(Tags.MODID, "gui/lock")
			.imageSize(16, 24)
			.canApplyTheme()
			.build();
}
