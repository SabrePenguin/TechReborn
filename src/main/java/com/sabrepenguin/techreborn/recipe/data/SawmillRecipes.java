package com.sabrepenguin.techreborn.recipe.data;

import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.Dust;
import com.sabrepenguin.techreborn.recipe.RegistryHandler;
import com.sabrepenguin.techreborn.recipe.builders.MultiOutputBuilder;
import com.sabrepenguin.techreborn.recipe.registries.ITRFluidRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class SawmillRecipes {

	private static final int PLANK_ID = OreDictionary.getOreID("plankWood");

	@SuppressWarnings("ConstantConditions")
	public static void init() {
		ITRFluidRegistry registry = RegistryHandler.instance().getSawmillRegistry();

		Container craftingInventory = new Container() {
			@Override
			public boolean canInteractWith(EntityPlayer playerIn) {
				return false;
			}
		};
		InventoryCrafting craftingGrid = new InventoryCrafting(craftingInventory, 1, 1);
		OreDictionary.getOres("logWood").forEach(logStack -> {
			if (logStack.getMetadata() == OreDictionary.WILDCARD_VALUE && logStack.getHasSubtypes()) {
				for (int i = 0; i < 16; i++) {
					ItemStack newLogStack = logStack.copy();
					addRecipe(registry, craftingGrid, newLogStack, i);
				}
			} else {
				ItemStack newLogStack = logStack.copy();
				addRecipe(registry, craftingGrid, newLogStack, 0);
			}
		});
	}

	@SuppressWarnings("ConstantConditions")
	private static void addRecipe(ITRFluidRegistry registry, InventoryCrafting craftingGrid, ItemStack logStack, int damage) {
		logStack.setItemDamage(damage);
		craftingGrid.setInventorySlotContents(0, logStack);
		ItemStack result = getResult(craftingGrid);
		if (result.isEmpty())
			return;
		registry.addRecipe(new MultiOutputBuilder()
				.addInput(logStack)
				.addFluidInput(new FluidStack(FluidRegistry.getFluid("water"), 1000))
				.addOutput(result)
				.addOutput(new ItemStack(TRItems.dust, 3, Dust.MetaDust.saw_dust.metadata()))
				.addOutput(Items.PAPER, 1)
				.setRecipeTime(100)
				.setEnergyCost(512)
				.build());
	}

	private static ItemStack getResult(InventoryCrafting crafting) {
		for (IRecipe recipe : CraftingManager.REGISTRY) {
			if (recipe.canFit(1, 1) && recipe.matches(crafting, null)) {
				ItemStack result = recipe.getCraftingResult(crafting);
				for(int id: OreDictionary.getOreIDs(result)) {
					if (id == PLANK_ID)
						return result;
				}
				return ItemStack.EMPTY;
			}
		}
		return ItemStack.EMPTY;
	}
}
