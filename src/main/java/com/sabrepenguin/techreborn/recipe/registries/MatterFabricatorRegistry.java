package com.sabrepenguin.techreborn.recipe.registries;

import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.util.ItemStackWrapper;
import it.unimi.dsi.fastutil.ints.Int2IntAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MatterFabricatorRegistry {
	private final Object2IntMap<ItemStackWrapper> values = new Object2IntArrayMap<>();
	private final Int2IntMap oreDictValues = new Int2IntAVLTreeMap();

	public void addItem(ItemStack stack, int value) {
		if (value < 1)
			throw new RuntimeException("Item values must be greater than 0");
		values.put(new ItemStackWrapper(stack), value);
	}

	public void addItem(String oredict, int value) {
		if (value < 1)
			throw new RuntimeException("Item values must be greater than 0");
		oreDictValues.put(OreDictionary.getOreID(oredict), value);
	}

	public int getItemValue(ItemStack stack) {
		ItemStackWrapper wrapper = new ItemStackWrapper(stack);
		if (values.containsKey(wrapper)) {
			return values.get(wrapper);
		} else {
			int[] oreIds = OreDictionary.getOreIDs(stack);
			for (int i: oreIds) {
				if (oreDictValues.containsKey(i))
					return oreDictValues.get(i);
			}
		}
		return 0;
	}

	@SuppressWarnings("ConstantConditions")
	public ItemStack getOutput() {
		return new ItemStack(TRItems.uumatter);
	}
}
