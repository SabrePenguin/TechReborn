package com.sabrepenguin.techreborn.items.materials;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.items.ItemBase;
import com.sabrepenguin.techreborn.items.ItemHelper;
import com.sabrepenguin.techreborn.util.MetadataHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemNugget extends ItemBase {
    public static final String[] types = new String[] { "aluminum", "brass", "bronze", "chrome", "copper", "electrum",
            "invar", "iridium", "lead", "nickel", "platinum", "silver", "steel", "tin", "titanium", "tungsten",
            "hot_tungstensteel", "tungstensteel", "zinc", "refined_iron", ItemHelper.META_PLACEHOLDER,
            ItemHelper.META_PLACEHOLDER,
            ItemHelper.META_PLACEHOLDER, "iron", "diamond" };

    public static List<MetadataHelper> metaItems = new ArrayList<>();
    static {
        for(int i = 0; i < types.length; i++) {
            if (!types[i].equals(ItemHelper.META_PLACEHOLDER)) {
                metaItems.add(new MetadataHelper(i, types[i]));
            }
        }
    }

    public ItemNugget() {
        setHasSubtypes(true);
        setTranslationKey(Tags.MODID + ".nuggets");
        setRegistryName(Tags.MODID, "nuggets");
    }

    @Override
    public @NotNull String getTranslationKey(ItemStack stack) {
        int meta = stack.getMetadata();
        if (meta < 0 || meta >= types.length)
            meta = 0;
        return "item." + Tags.MODID + ".nuggets." + types[meta];
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
            OreDictionary.registerOre("nugget" + metadata.capitalize(), newItem);
        }
    }

    @Override
    public String[] getTypes() {
        return types;
    }

    @Override
    public String getPrefix() {
        return "nugget/";
    }

    @Override
    public String getPostfix() {
        return "_nugget";
    }
}
