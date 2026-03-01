package com.sabrepenguin.techreborn.jei.categories;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.jei.wrappers.AlloyWrapper;
import mcp.MethodsReturnNonnullByDefault;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.config.Constants;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class AlloyCategory implements IRecipeCategory<AlloyWrapper> {

	private final IDrawable background;
	private final String uid;

	protected final IDrawableStatic staticFlame;
	protected final IDrawableStatic slot;
	protected final IDrawableAnimated animatedFlame;
	protected final IDrawableAnimated arrow;

	public AlloyCategory(IGuiHelper helper, String uid) {
		background = helper.createBlankDrawable(92, 54);
		this.uid = uid;
		slot = helper.getSlotDrawable();
		staticFlame = helper.createDrawable(Constants.RECIPE_GUI_VANILLA, 82, 114, 14, 14);
		animatedFlame = helper.createAnimatedDrawable(staticFlame, 300, IDrawableAnimated.StartDirection.TOP, true);

		arrow = helper.drawableBuilder(Constants.RECIPE_GUI_VANILLA, 82, 128, 24, 17)
				.buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
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
		itemStackGroup.init(0, true, 0, 0);
		itemStackGroup.init(1, true, 21, 0);
		itemStackGroup.init(2, false, 74, 18);

		itemStackGroup.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
		itemStackGroup.set(1, ingredients.getInputs(VanillaTypes.ITEM).get(1));
		itemStackGroup.set(2, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		animatedFlame.draw(minecraft, 12, 20);
		arrow.draw(minecraft, 44, 19);

		slot.draw(minecraft, 0, 0);
		slot.draw(minecraft, 21, 0);
		slot.draw(minecraft, 10, 36);

		slot.draw(minecraft, 74, 18);
	}
}
