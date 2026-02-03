package com.sabrepenguin.techreborn.items.materials;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.itemblock.IMetaMaterial;
import com.sabrepenguin.techreborn.util.ExtraStringUtils;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Collectors;

public class MaterialItem extends Item implements INonStandardLocation {
	private final String prefix;
	private final String postfix;
	private final IMetaMaterial meta;

	public MaterialItem(String registryName, String translationKey, IMetaMaterial item) {
		this(registryName, translationKey, item, "",  "");
	}

	public MaterialItem(String registryName, String translationKey, IMetaMaterial item, String prefix, String postfix) {
		this.setRegistryName(Tags.MODID, registryName);
		this.setTranslationKey(Tags.MODID + "." + translationKey);
		this.setHasSubtypes(true);
		this.setNoRepair();
		this.setMaxDamage(0);
		this.prefix = prefix;
		this.postfix = postfix;
		this.meta = item;
	}

	public void registerOredict() {
		for (Pair<String, Integer> metadata: this.meta.getMeta()) {
			String oredict = this.meta.getOreDict();
			if (oredict.isEmpty() && this.meta.hasNonStandardOreDict()) {
				String[] ores = this.meta.getNonStandardOreDict(metadata.getRight());
				for (String ore: ores) {
					ItemStack newItem = new ItemStack(this, 1, metadata.getRight());
					OreDictionary.registerOre(ore, newItem);
				}
			} else {
				ItemStack newItem = new ItemStack(this, 1, metadata.getRight());
				OreDictionary.registerOre(oredict + ExtraStringUtils.capitalizeByUnderscore(metadata.getLeft()), newItem);
			}
		}
	}

	@Override
	public String getPrefix() {
		return prefix;
	}

	@Override
	public String getPostfix() {
		return this.postfix;
	}

	@Override
	public @NotNull String getTranslationKey(@NotNull ItemStack stack) {
		return this.getTranslationKey() + "." + meta.getName(stack);
	}

	@Override
	public void getSubItems(@NotNull CreativeTabs tab, @NotNull NonNullList<ItemStack> items) {
		if (tab != TechReborn.RESOURCE_TAB)
			return;
		items.addAll(meta.getMeta()
						.stream()
						.map(m -> new ItemStack(this, 1, m.getRight()))
						.collect(Collectors.toList()));
	}

	public Collection<Pair<String, Integer>> getMeta() {
		return this.meta.getMeta();
	}
}
