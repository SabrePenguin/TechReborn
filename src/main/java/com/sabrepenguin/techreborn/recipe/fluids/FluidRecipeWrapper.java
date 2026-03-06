package com.sabrepenguin.techreborn.recipe.fluids;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class FluidRecipeWrapper extends IForgeRegistryEntry.Impl<IRecipe> implements IShapedRecipe {
	private final IShapedRecipe compose;

	public FluidRecipeWrapper(IShapedRecipe compose) {
		this.compose = compose;
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		return compose.matches(inv, worldIn);
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return compose.getCraftingResult(inv);
	}

	@Override
	public boolean canFit(int width, int height) {
		return compose.canFit(width, height);
	}

	@Override
	public ItemStack getRecipeOutput() {
		return compose.getRecipeOutput();
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return compose.getIngredients();
	}

	@Override
	public boolean isDynamic() {
		return compose.isDynamic();
	}

	@Override
	public String getGroup() {
		return compose.getGroup();
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		NonNullList<ItemStack> returnStacks = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
		NonNullList<Ingredient> ingredients = this.getIngredients();
		boolean[] ingredientsUsed = new boolean[ingredients.size()];

		for (int invIndex = 0; invIndex < inv.getSizeInventory(); invIndex++) {
			ItemStack targetStack = inv.getStackInSlot(invIndex);
			if (targetStack.isEmpty()) continue;

			boolean handledFluid = false;
			for (int ingredientIndex = 0; ingredientIndex < ingredients.size(); ingredientIndex++) {
				Ingredient ingredient = ingredients.get(ingredientIndex);
				if(!ingredientsUsed[ingredientIndex] && ingredient instanceof FluidIngredient fluidIngredient && ingredient.apply(targetStack)) {
					ItemStack copy = targetStack.copy();
					copy.setCount(1);
					IFluidHandlerItem handler = FluidUtil.getFluidHandler(copy);
					if (handler != null) {
						FluidStack resource = new FluidStack(fluidIngredient.getFluid(), fluidIngredient.getAmount());
						FluidStack drained = handler.drain(resource, false);
						if (drained != null && drained.amount >= fluidIngredient.getAmount()) {
							handler.drain(resource, true);
							returnStacks.set(invIndex, handler.getContainer());
							ingredientsUsed[ingredientIndex] = true;
							handledFluid = true;
							break;
						}
					}
				}
			}
			if (!handledFluid) {
				returnStacks.set(invIndex, ForgeHooks.getContainerItem(targetStack));
			}
		}
		return returnStacks;
	}

	@Override
	public int getRecipeWidth() {
		return compose.getRecipeWidth();
	}

	@Override
	public int getRecipeHeight() {
		return compose.getRecipeHeight();
	}
}
