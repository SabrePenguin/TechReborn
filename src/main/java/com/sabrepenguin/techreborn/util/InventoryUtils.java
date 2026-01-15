package com.sabrepenguin.techreborn.util;

import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtils {

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
