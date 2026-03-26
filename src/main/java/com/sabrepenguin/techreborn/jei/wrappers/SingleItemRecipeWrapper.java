package com.sabrepenguin.techreborn.jei.wrappers;

import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.Part;
import com.sabrepenguin.techreborn.util.ExtraStringUtils;
import mcp.MethodsReturnNonnullByDefault;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SingleItemRecipeWrapper implements IRecipeWrapper {
	public static final ItemStack SCRAP_INPUT = new ItemStack(Item.getItemFromBlock(Blocks.DIRT), 1, 0);
	public static final ItemStack SCRAP = new ItemStack(TRItems.part, 1, Part.PartMeta.scrap.metadata());

	static {
		SCRAP_INPUT.setStackDisplayName("Any item"); // TODO: Set to translatable string
	}

	private final ItemStack input;
	private final ItemStack output;
	private final int time;
	private final int energyCost;

	public SingleItemRecipeWrapper(ItemStack input, ItemStack output, int time, int energyCost) {
		this.input = input;
		this.output = output;
		this.time = time;
		this.energyCost = energyCost;
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		String energy = ExtraStringUtils.numberToCompactNumber(energyCost) + " FE";
		minecraft.fontRenderer.drawString(energy,
				(recipeWidth / 2) - (minecraft.fontRenderer.getStringWidth(energy) / 2),
				30, 0x444444);
		String timeString = (time/20) + " sec";
		minecraft.fontRenderer.drawString(timeString,
				(recipeWidth / 2) - (minecraft.fontRenderer.getStringWidth(timeString) / 2),
				30 + minecraft.fontRenderer.FONT_HEIGHT + 1, 0x444444);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<List<ItemStack>> inputs = new ArrayList<>();
		inputs.add(Arrays.asList(input));
		ingredients.setInputLists(VanillaTypes.ITEM, inputs);
		ingredients.setOutput(VanillaTypes.ITEM, output);
	}
}
