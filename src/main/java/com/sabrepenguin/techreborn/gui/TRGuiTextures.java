package com.sabrepenguin.techreborn.gui;

import com.cleanroommc.modularui.drawable.UITexture;
import com.sabrepenguin.techreborn.Tags;

public class TRGuiTextures {
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
