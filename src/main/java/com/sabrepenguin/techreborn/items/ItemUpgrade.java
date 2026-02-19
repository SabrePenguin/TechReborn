package com.sabrepenguin.techreborn.items;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.itemblock.IMetaInformation;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemUpgrade extends Item implements IMetaInformation, INonStandardLocation {

	public ItemUpgrade() {
		this.setRegistryName(Tags.MODID, "upgrades");
		this.setTranslationKey(Tags.MODID + ".upgrade");
		this.setHasSubtypes(true);
		this.setMaxStackSize(16);
		this.setMaxDamage(0);
	}

	@Override
	public String getTranslationKey(ItemStack stack) {
		return this.getTranslationKey() + "." + UpgradeEnum.META.get(stack.getMetadata()).getName();
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab != TechReborn.RESOURCE_TAB) return;
		for(UpgradeEnum v: UpgradeEnum.values()) {
			items.add(new ItemStack(this, 1, v.metadata));
		}
	}

	@Override
	public String getPrefix() {
		return "upgrades";
	}

	@Override
	public String getPostfix() {
		return "_upgrade";
	}

	@Override
	public String getName(ItemStack stack) {
		return UpgradeEnum.META.get(stack.getMetadata()).getName();
	}

	@Override
	public List<Pair<String, Integer>> getMeta() {
		return Arrays.stream(UpgradeEnum.values()).map(v -> Pair.of(v.getName(), v.metadata)).collect(Collectors.toList());
	}

	public enum UpgradeEnum implements IStringSerializable {
		OVERCLOCK(0),
		TRANSFORMER(1),
		ENERGY_STORAGE(2),
		SUPERCONDUCTOR(3);

		final int metadata;

		final static Int2ObjectMap<UpgradeEnum> META = new Int2ObjectOpenHashMap<>();

		static {
			for (UpgradeEnum e: values()) META.put(e.metadata, e);
			META.defaultReturnValue(OVERCLOCK);
		}

		UpgradeEnum(int metadata) {
			this.metadata = metadata;
		}

		public int metadata() {
			return metadata;
		}

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}
	}
}
