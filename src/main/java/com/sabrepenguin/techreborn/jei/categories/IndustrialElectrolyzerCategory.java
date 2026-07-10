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
import net.minecraft.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.List;

import static com.sabrepenguin.techreborn.jei.JEITextures.RIGHT_ARROW_OUTLINE;
import static com.sabrepenguin.techreborn.jei.JEITextures.UP_ARROW_OUTLINE;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class IndustrialElectrolyzerCategory implements IRecipeCategory<ITRRecipeWrapper> {

	private final IDrawable background;
	private final String uid;

	protected final IDrawableStatic slot;
	protected final IDrawableStatic big_slot;
	protected final IDrawableAnimated up_arrow;

	public IndustrialElectrolyzerCategory(IGuiHelper helper, String uid) {
		background = helper.createBlankDrawable(87, 82);
		this.uid = uid;

		slot = helper.getSlotDrawable();
		big_slot = JEITextures.BIG_SLOT;

		up_arrow = JEITextures.UP_ARROW.buildAnimated(100, IDrawableAnimated.StartDirection.BOTTOM, false);
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
		itemStackGroup.init(0, true, 5, 38);
		itemStackGroup.init(1, true, 35, 38);
		itemStackGroup.init(2, false, 5, 5);
		itemStackGroup.init(3, false, 25, 5);
		itemStackGroup.init(4, false, 45, 5);
		itemStackGroup.init(5, false, 65, 5);

		List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
		List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);

		itemStackGroup.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
		if (inputs.size() > 1)
			itemStackGroup.set(1, ingredients.getInputs(VanillaTypes.ITEM).get(1));

		itemStackGroup.set(2, ingredients.getOutputs(VanillaTypes.ITEM).get(0));
		if (outputs.size() > 1)
			itemStackGroup.set(3, ingredients.getOutputs(VanillaTypes.ITEM).get(1));
		if (outputs.size() > 2)
			itemStackGroup.set(4, ingredients.getOutputs(VanillaTypes.ITEM).get(2));
		if (outputs.size() > 3)
			itemStackGroup.set(5, ingredients.getOutputs(VanillaTypes.ITEM).get(3));
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		slot.draw(minecraft, 5, 38);
		slot.draw(minecraft, 35, 38);
		slot.draw(minecraft, 5, 5);
		slot.draw(minecraft, 25, 5);
		slot.draw(minecraft, 45, 5);
		slot.draw(minecraft, 65, 5);
		UP_ARROW_OUTLINE.draw(minecraft, 32, 26);
		up_arrow.draw(minecraft, 32, 26);
	}
}
