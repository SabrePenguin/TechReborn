package com.sabrepenguin.techreborn.items.materials;

import com.sabrepenguin.techreborn.Tags;
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

public class ItemGem extends ItemBase {

    public static final String[] types = new String[] { "ruby", "sapphire", "peridot", "red_garnet", "yellow_garnet" };

    public static List<MetadataHelper> metaItems = new ArrayList<>();
    static {
        for(int i = 0; i < types.length; i++) {
            metaItems.add(new MetadataHelper(i, types[i]));
        }
    }

    public ItemGem() {
        setTranslationKey(Tags.MODID  + ".gem");
        setRegistryName(Tags.MODID, "gem");
        setHasSubtypes(true);
    }

    @Override
    public void registerOredict() {
        for (MetadataHelper metadata: metaItems) {
            ItemStack newItem = new ItemStack(this, 1, metadata.meta());
            OreDictionary.registerOre("gem" + metadata.capitalize(), newItem);
        }
    }

    @Override
    public @NotNull String getTranslationKey(ItemStack stack) {
        int meta = stack.getMetadata();
        if (meta < 0 || meta >= types.length)
            meta = 0;
        return "item." + Tags.MODID + ".gem." + types[meta];
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        items.addAll(
                metaItems.stream()
                        .map(item -> new ItemStack(this, 1, item.meta()))
                        .collect(Collectors.toList()));
    }

    @Override
    public String[] getTypes() {
        return types;
    }

    @Override
    public String getPrefix() {
        return "gem/";
    }
}
