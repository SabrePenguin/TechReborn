package com.sabrepenguin.techreborn.recipe.registries;

import com.sabrepenguin.techreborn.recipe.recipe.ITRRecipe;
import com.sabrepenguin.techreborn.recipe.recipe.VanillaRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class VanillaRegistry implements ITRRegistry {
	private TileEntity parent;

	public void bind(TileEntity entity) {
		parent = entity;
	}

	@Override
	public Collection<ITRRecipe> getRecipes() {
		return Collections.emptyList();
	}

	@Override
	public boolean addRecipe(ITRRecipe recipe) {
		return false;
	}

	@Override
	public ITRRecipe getRecipe(List<ItemStack> ingredientList) {
		return null;
	}

	@Override
	public ITRRecipe getRecipe(IItemHandler handler) {
		ItemHandlerWrapper wrapper = new ItemHandlerWrapper(handler);
		World world = (parent != null && parent.hasWorld()) ? parent.getWorld() : null;
		IRecipe recipe = CraftingManager.findMatchingRecipe(wrapper, world);
		if (recipe != null) {
			return new VanillaRecipeWrapper(recipe, wrapper);
		}
		return null;
	}
}
