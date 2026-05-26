package com.sabrepenguin.techreborn.jei.categories;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.jei.JEITextures;
import com.sabrepenguin.techreborn.jei.wrappers.ITRRecipeWrapper;
import mcp.MethodsReturnNonnullByDefault;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.sabrepenguin.techreborn.jei.JEITextures.RIGHT_ARROW;
import static com.sabrepenguin.techreborn.jei.JEITextures.RIGHT_ARROW_OUTLINE;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RollingMachineCategory implements IRecipeCategory<ITRRecipeWrapper> {
	private final IDrawable background;
	private final String uid;

	protected final IDrawableStatic slot;
	protected final IDrawableStatic big_slot;
	protected final IDrawableAnimated right_arrow;

	public RollingMachineCategory(IGuiHelper helper, String uid) {
		background = helper.createBlankDrawable(114, 84);
		this.uid = uid;

		slot = helper.getSlotDrawable();
		big_slot = JEITextures.BIG_SLOT;

		right_arrow = RIGHT_ARROW.buildAnimated(100, IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public String getUid() {
		return uid;
	}

	@Override
	public String getTitle() {
		return Translator.translateToLocal(uid);
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
	public void setRecipe(IRecipeLayout recipeLayout, ITRRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
		int index = 0;
		for (int row = 0; row < recipeWrapper.getHeight(); row++) {
			for (int col = 0; col < recipeWrapper.getWidth(); col++) {
				index = (row * recipeWrapper.getWidth()) + col;
				itemStackGroup.init(index, true, (18 * col), 4 + (18 * row));
				itemStackGroup.set(index, ingredients.getInputs(VanillaTypes.ITEM).get(index));
			}
		}
		index = index+1;
		itemStackGroup.init(index, false, 86, 22);
		itemStackGroup.set(index, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				slot.draw(minecraft, 18*row, 4 + (18*col));
			}
		}
		RIGHT_ARROW_OUTLINE.draw(minecraft, 60, 24);
		right_arrow.draw(minecraft, 60, 24);
		big_slot.draw(minecraft, 82, 18);
	}
}
