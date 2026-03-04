package com.sabrepenguin.techreborn.jei;

import com.sabrepenguin.techreborn.Tags;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableBuilder;
import mezz.jei.api.gui.IDrawableStatic;
import net.minecraft.util.ResourceLocation;

public class JEITextures {

	private static final ResourceLocation BIG_SLOT_LOCATION = new ResourceLocation(Tags.MODID, "textures/gui/big_slot.png");
	private static final ResourceLocation LEFT_ARROW_LOCATION = new ResourceLocation(Tags.MODID, "textures/gui/jei/left_arrow.png");
	private static final ResourceLocation LEFT_ARROW_OUTLINE_LOCATION = new ResourceLocation(Tags.MODID, "textures/gui/jei/left_arrow_dark.png");
	private static final ResourceLocation RIGHT_ARROW_LOCATION = new ResourceLocation(Tags.MODID, "textures/gui/jei/right_arrow.png");
	private static final ResourceLocation RIGHT_ARROW_OUTLINE_LOCATION = new ResourceLocation(Tags.MODID, "textures/gui/jei/right_arrow_dark.png");

	public static IDrawableStatic BIG_SLOT;

	public static IDrawableBuilder LEFT_ARROW;
	public static IDrawableStatic LEFT_ARROW_OUTLINE;
	public static IDrawableBuilder RIGHT_ARROW;
	public static IDrawableStatic RIGHT_ARROW_OUTLINE;

	public static void init(IGuiHelper helper) {
		BIG_SLOT = helper.drawableBuilder(BIG_SLOT_LOCATION, 0, 0, 26, 26).setTextureSize(26, 26).build();

		LEFT_ARROW = helper.drawableBuilder(LEFT_ARROW_LOCATION, 0, 0, 18, 16).setTextureSize(18, 16);
		LEFT_ARROW_OUTLINE = helper.drawableBuilder(LEFT_ARROW_OUTLINE_LOCATION, 0, 0, 18, 16).setTextureSize(18, 16).build();

		RIGHT_ARROW = helper.drawableBuilder(RIGHT_ARROW_LOCATION, 0, 0, 18, 16).setTextureSize(18, 16);
		RIGHT_ARROW_OUTLINE = helper.drawableBuilder(RIGHT_ARROW_OUTLINE_LOCATION, 0, 0, 18, 16).setTextureSize(18, 16).build();
	}
}
