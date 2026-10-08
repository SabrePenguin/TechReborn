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

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SawmillCategory implements IRecipeCategory<ITRRecipeWrapper> {

	private final IDrawable background;
	private final String uid;

	protected final IDrawableStatic slot;
	protected final IDrawableStatic fluid_slot;
	protected final IDrawableAnimated right_arrow;

	public SawmillCategory(IGuiHelper helper, String uid) {
		background = helper.createBlankDrawable(123, 55);
		this.uid = uid;

		slot = helper.getSlotDrawable();
		fluid_slot = JEITextures.FLUID_TANK;
		right_arrow = JEITextures.RIGHT_ARROW.buildAnimated(100, IDrawableAnimated.StartDirection.LEFT, false);
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
		itemStackGroup.init(0, true, 24, 8);
		itemStackGroup.init(1, false, 64, 8);
		itemStackGroup.init(2, false, 84, 8);
		itemStackGroup.init(3, false, 104, 8);

		itemStackGroup.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
		itemStackGroup.set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
		itemStackGroup.set(2, ingredients.getOutputs(VanillaTypes.ITEM).get(1));
		itemStackGroup.set(3, ingredients.getOutputs(VanillaTypes.ITEM).get(2));

		IGuiFluidStackGroup fluidStackGroup = recipeLayout.getFluidStacks();
		fluidStackGroup.init(0, true, 4, 4, 12, 47, 10000, true, null);
		fluidStackGroup.set(0, ingredients.getInputs(VanillaTypes.FLUID).get(0));
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		JEITextures.RIGHT_ARROW_OUTLINE.draw(minecraft, 44, 9);
		right_arrow.draw(minecraft, 44, 9);

		fluid_slot.draw(minecraft, 0, 0);

		slot.draw(minecraft, 24, 8);
		slot.draw(minecraft, 64, 8);
		slot.draw(minecraft, 84, 8);
		slot.draw(minecraft, 104, 8);
	}
}
