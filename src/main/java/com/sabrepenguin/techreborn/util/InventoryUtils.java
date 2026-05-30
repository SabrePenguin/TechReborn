package com.sabrepenguin.techreborn.util;

import com.sabrepenguin.techreborn.items.TRItems;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.oredict.OreDictionary;

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

	public static ItemStack findItem(EntityPlayer player, String oreDict) {
		int torchId = OreDictionary.getOreID(oreDict);
		for(int i = 0; i < player.inventory.mainInventory.size(); i++) {
			ItemStack checkStack = player.inventory.getStackInSlot(i);
			if (!checkStack.isEmpty()) {
				int[] ids = OreDictionary.getOreIDs(checkStack);
				for(int id: ids) {
					if (id == torchId)
						return checkStack;
				}
			}
		}
		return ItemStack.EMPTY;
	}

	// TODO: Make this count aware and previous-iteration aware
	public static void setBalanceCache(IItemHandler handler, Object2ObjectMap<ItemStackWrapper, IntList> map) {
		map.clear();
		for (int slot = 0; slot < handler.getSlots(); slot++) {
			ItemStack stack = handler.getStackInSlot(slot);
			if (!stack.isEmpty()) {
				map.computeIfAbsent(new ItemStackWrapper(stack.copy()), k -> new IntArrayList()).add(slot);
			}
		}
	}

	public static void balanceHandler(IItemHandler handler, Object2ObjectMap<ItemStackWrapper, IntList> cache) {
		for (var entry: cache.entrySet()) {
			IntList ints = entry.getValue();
			int size = ints.size();
			if (size < 2) // Don't balance one slot
				continue;
			int sum = 0;
			for (int i = 0; i < size; i++) {
				sum += handler.getStackInSlot(ints.getInt(i)).getCount();
			}
			int slotTotal = sum / size;
			int remainder = sum % size;
			int tempRemainder = remainder;
			int sumPool = 0;
			for (int i = 0; i < size; i++) {
				int slot = ints.getInt(i);
				int currentSize = handler.getStackInSlot(slot).getCount();
				int targetSize = slotTotal + (tempRemainder > 0 ? 1 : 0);
				if (tempRemainder > 0)
					tempRemainder--;
				if (currentSize > targetSize) {
					int extracted = currentSize - targetSize;
					sumPool += handler.extractItem(slot, extracted, false).getCount();
				}
			}

			if (sumPool <= 0)
				return;

			ItemStack copy = handler.getStackInSlot(ints.getInt(0));

			for (int i = 0; i < size; i++) {
				int slot = ints.getInt(i);
				int currentSize = handler.getStackInSlot(slot).getCount();
				int targetSize = slotTotal + (remainder > 0 ? 1 : 0);
				if (remainder > 0) {
					remainder--;
				}

				if (currentSize < targetSize) {
					int toInsert = targetSize - currentSize;
					if (toInsert > sumPool) {
						toInsert = sumPool;
					}
					ItemStack insert = handler.extractItem(slot, currentSize, false);
					insert.grow(toInsert);
					handler.insertItem(slot, insert, false);
					sumPool -= toInsert;
					if (sumPool <= 0)
						break;
				}
			}
		}
	}
}
