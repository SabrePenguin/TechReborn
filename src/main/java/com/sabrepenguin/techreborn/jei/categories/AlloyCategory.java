package com.sabrepenguin.techreborn.jei.categories;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.jei.JEITextures;
import com.sabrepenguin.techreborn.jei.wrappers.AlloyWrapper;
import mcp.MethodsReturnNonnullByDefault;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.sabrepenguin.techreborn.jei.JEITextures.*;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class AlloyCategory implements IRecipeCategory<AlloyWrapper> {

	private final IDrawable background;
	private final String uid;

	protected final IDrawableStatic slot;
	protected final IDrawableStatic big_slot;
	protected final IDrawableAnimated right_arrow;
	protected final IDrawableAnimated left_arrow;

	public AlloyCategory(IGuiHelper helper, String uid) {
		background = helper.createBlankDrawable(114, 47);
		this.uid = uid;

		slot = helper.getSlotDrawable();
		big_slot = JEITextures.BIG_SLOT;

		left_arrow = LEFT_ARROW.buildAnimated(100, IDrawableAnimated.StartDirection.RIGHT, false);
		right_arrow = RIGHT_ARROW.buildAnimated(100, IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public String getUid() {
		return uid;
	}

	@Override
	public String getTitle() {
		return Translator.translateToLocal("techreborn.jei.category.alloy.furnace");
	}

	@Override
	public String getModName() {
		return Tags.MODNAME;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, AlloyWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
		itemStackGroup.init(0, true, 0, 4);
		itemStackGroup.init(1, true, 96, 4);
		itemStackGroup.init(2, false, 48, 4);

		itemStackGroup.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
		itemStackGroup.set(1, ingredients.getInputs(VanillaTypes.ITEM).get(1));
		itemStackGroup.set(2, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		RIGHT_ARROW_OUTLINE.draw(minecraft, 21, 5);
		right_arrow.draw(minecraft, 21, 5);

		slot.draw(minecraft, 0, 4);
		big_slot.draw(minecraft, 44, 0);
		slot.draw(minecraft, 96, 4);

		LEFT_ARROW_OUTLINE.draw(minecraft, 73, 5);
		left_arrow.draw(minecraft, 73, 5);
	}
}
