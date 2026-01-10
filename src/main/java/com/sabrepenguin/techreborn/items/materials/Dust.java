package com.sabrepenguin.techreborn.items.materials;

import com.sabrepenguin.techreborn.items.MetadataItem;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Dust implements IMaterial {

    // I'll switch later if Sai is fine without metadata
    private static final Int2ObjectMap<MetadataItem> META = new Int2ObjectOpenHashMap<>();
    static {
        List<MetadataItem> items = Arrays.asList(
                new MetadataItem(0, "almandine"),
                new MetadataItem(1, "aluminum"),
                new MetadataItem(2, "andradite"),
                new MetadataItem(3, "ashes"),
                new MetadataItem(4, "basalt"),
                new MetadataItem(5, "bauxite"),
                new MetadataItem(6, "brass"),
                new MetadataItem(7, "bronze"),
                new MetadataItem(8, "calcite"),
                new MetadataItem(9, "charcoal"),
                new MetadataItem(10, "chrome"),
                new MetadataItem(11, "cinnabar"),
                new MetadataItem(12, "clay"),
                new MetadataItem(13, "coal"),
                new MetadataItem(14, "copper"),
                new MetadataItem(15, "dark_ashes"),
                new MetadataItem(16, "diamond"),
                new MetadataItem(17, "electrum"),
                new MetadataItem(18, "emerald"),
                new MetadataItem(19, "ender_eye"),
                new MetadataItem(20, "ender_pearl"),
                new MetadataItem(21, "endstone"),
                new MetadataItem(22, "flint"),
                new MetadataItem(23, "galena"),
                new MetadataItem(24, "gold"),
                new MetadataItem(25, "grossular"),
                new MetadataItem(26, "invar"),
                new MetadataItem(27, "iron"),
                new MetadataItem(28, "lazurite"),
                new MetadataItem(29, "lead"),
                new MetadataItem(30, "magnesium"),
                new MetadataItem(31, "manganese"),
                new MetadataItem(32, "marble"),
                new MetadataItem(33, "netherrack"),
                new MetadataItem(34, "nickel"),
                new MetadataItem(35, "obsidian"),
                new MetadataItem(36, "peridot"),
                new MetadataItem(37, "phosphorous"),
                new MetadataItem(38, "platinum"),
                new MetadataItem(39, "pyrite"),
                new MetadataItem(40, "pyrope"),
                new MetadataItem(41, "red_garnet"),
//              new MetadataItem(0, "none"),
                new MetadataItem(43, "ruby"),
                new MetadataItem(44, "saltpeter"),
                new MetadataItem(45, "sapphire"),
                new MetadataItem(46, "saw_dust"),
                new MetadataItem(47, "silver"),
                new MetadataItem(48, "sodalite"),
                new MetadataItem(49, "spessartine"),
                new MetadataItem(50, "sphalerite"),
                new MetadataItem(51, "steel"),
                new MetadataItem(52, "sulfur"),
                new MetadataItem(53, "tin"),
                new MetadataItem(54, "titanium"),
                new MetadataItem(55, "tungsten"),
                new MetadataItem(56, "uvarovite"),
//              new MetadataItem(0, "none"),
                new MetadataItem(58, "yellow_garnet"),
                new MetadataItem(59, "zinc"),
//              new MetadataItem(0, "none"),
                new MetadataItem(61, "andesite"),
                new MetadataItem(62, "diorite"),
                new MetadataItem(63, "granite"),
                new MetadataItem(64, "iridium"),
                new MetadataItem(65, "thorium"),
                new MetadataItem(66, "uranium"),
                new MetadataItem(67, "plutonium")
        );
        for (MetadataItem item: items) {
            META.put(item.meta(), item);
        }
    }


    public static void addDust(String name, int metadata) {
        if (!META.containsKey(metadata))
            META.put(metadata, new MetadataItem(metadata, name));
    }

    @Override
    public Collection<MetadataItem> getItems() {
        return META.values();
    }

    @Override
    public String getNameFromMeta(int meta) {
        if (META.containsKey(meta)) {
            return META.get(meta).name();
        }
        return "";
    }

    @Override
    public int getMetaFromName(String name) {
        for(MetadataItem item: META.values()) {
            if (item.name().equals(name))
                return item.meta();
        }
        return 0;
    }

    @Override
    public String getOreDict(int meta) {
        return "dust";
    }
}
