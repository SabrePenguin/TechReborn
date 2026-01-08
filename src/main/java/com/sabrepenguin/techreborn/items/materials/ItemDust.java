package com.sabrepenguin.techreborn.items.materials;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.items.ItemBase;
import com.sabrepenguin.techreborn.util.MetadataHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemDust extends ItemBase {
    public static final String[] types = new String[] { "almandine", "aluminum", "andradite", "ashes", "basalt",
            "bauxite", "brass", "bronze", "calcite", "charcoal", "chrome", "cinnabar", "clay", "coal", "copper",
            "dark_ashes", "diamond", "electrum", "emerald", "ender_eye", "ender_pearl", "endstone", "flint", "galena",
            "gold", "grossular", "invar", "iron", "lazurite", "lead", "magnesium", "manganese", "marble", "netherrack",
            "nickel", "obsidian", "peridot", "phosphorous", "platinum", "pyrite", "pyrope", "red_garnet",
            MetadataHelper.META_PLACEHOLDER,
            "ruby", "saltpeter", "sapphire", "saw_dust", "silver", "sodalite", "spessartine", "sphalerite", "steel",
            "sulfur", "tin", "titanium", "tungsten", "uvarovite", MetadataHelper.META_PLACEHOLDER, "yellow_garnet", "zinc",
            MetadataHelper.META_PLACEHOLDER, "andesite", "diorite", "granite", "iridium", "thorium", "uranium", "plutonium" };

    public static List<MetadataHelper> metaItems = new ArrayList<>();
    static {
        for(int i = 0; i < types.length; i++) {
            if (!types[i].equals(MetadataHelper.META_PLACEHOLDER)) {
                metaItems.add(new MetadataHelper(i, types[i]));
            }
        }
    }

    public ItemDust() {
        setHasSubtypes(true);
        setTranslationKey(Tags.MODID + ".dust");
        setRegistryName(Tags.MODID, "dust");
    }

    @Override
    public @NotNull String getTranslationKey(ItemStack stack) {
        int meta = stack.getMetadata();
        if (meta < 0 || meta >= types.length)
            meta = 0;
        return "item." + Tags.MODID + ".dust." + types[meta];
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!tab.equals(TechReborn.RESOURCE_TAB)) {
            return;
        }
        items.addAll(
                metaItems.stream()
                        .map(item -> new ItemStack(this, 1, item.meta()))
                        .collect(Collectors.toList()));
    }

    @Override
    public void registerOredict() {
        for (MetadataHelper metadata: metaItems) {
            ItemStack newItem = new ItemStack(this, 1, metadata.meta());
            OreDictionary.registerOre("dust" + metadata.capitalize(), newItem);
        }
    }

    @Override
    public String[] getTypes() {
        return types;
    }

    @Override
    public String getPrefix() {
        return "dust/";
    }

    @Override
    public String getPostfix() {
        return "_dust";
    }
}
