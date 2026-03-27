package com.sabrepenguin.techreborn.jei.categories;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.jei.JEITextures;
import com.sabrepenguin.techreborn.jei.wrappers.OneToOneRecipeWrapper;
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
public class OneToOneCategory implements IRecipeCategory<OneToOneRecipeWrapper> {

	private final IDrawable background;
	private final String uid;

	protected final IDrawableStatic slot;
	protected final IDrawableStatic big_slot;
	protected final IDrawableAnimated right_arrow;

	public OneToOneCategory(IGuiHelper helper, String uid) {
		background = helper.createBlankDrawable(70, 47);
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
	public void setRecipe(IRecipeLayout recipeLayout, OneToOneRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStackGroup = recipeLayout.getItemStacks();
		itemStackGroup.init(0, true, 0, 4);
		itemStackGroup.init(1, false, 48, 4);

		itemStackGroup.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
		itemStackGroup.set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		RIGHT_ARROW_OUTLINE.draw(minecraft, 21, 5);
		right_arrow.draw(minecraft, 21, 5);

		slot.draw(minecraft, 0, 4);
		big_slot.draw(minecraft, 44, 0);
	}
}
