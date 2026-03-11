package com.sabrepenguin.techreborn.util;

import com.sabrepenguin.techreborn.items.TRItems;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class InventoryUtils {

	@SuppressWarnings("ConstantConditions")
	public static Predicate<ItemStack> IS_UPGRADE = itemStack -> itemStack.getItem() == TRItems.upgrades;

	public static Predicate<ItemStack> IS_POWER_ITEM = itemStack -> itemStack.hasCapability(CapabilityEnergy.ENERGY, null);

    public static List<ItemStack> dropItemHandlerInventory(IItemHandler itemHandler) {
        final List<ItemStack> items = new ArrayList<>();
        for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
            if (itemHandler.getStackInSlot(slot).isEmpty())
                continue;
            int amount = itemHandler.getStackInSlot(slot).getCount();
            if (!itemHandler.extractItem(slot, amount, true).isEmpty()) {
                final ItemStack stack = itemHandler.extractItem(slot, amount, false);
                items.add(stack);
            }
        }
        return items;
    }

    public static void spawnDroppedItems(IItemHandler itemHandler, World world, BlockPos pos) {
        for (ItemStack item: dropItemHandlerInventory(itemHandler)) {
            InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), item);
        }
    }
}
