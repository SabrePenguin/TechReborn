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

public class ItemIngot extends ItemBase{

    protected static final String[] types = new String[] { "aluminum", "brass", "bronze", "chrome", "copper", "electrum",
            "invar", "iridium", "lead", "nickel", "platinum", "silver", "steel", "tin", "titanium", "tungsten",
            "hot_tungstensteel", "tungstensteel", "zinc", "refined_iron", "advanced_alloy", "mixed_metal",
            "iridium_alloy", "thorium", "uranium", "plutonium" };

    public static List<MetadataHelper> metaItems = new ArrayList<>();
    static {
        for(int i = 0; i < types.length; i++) {
            metaItems.add(new MetadataHelper(i, types[i]));
        }
    }

    public ItemIngot() {
        setHasSubtypes(true);
        setTranslationKey(Tags.MODID + ".ingot");
        setRegistryName(Tags.MODID, "ingot");
    }

    @Override
    public void registerOredict() {
        for (MetadataHelper metadata: metaItems) {
            ItemStack newItem = new ItemStack(this, 1, metadata.meta());
            OreDictionary.registerOre("ingot" + metadata.capitalize(), newItem);
        }
    }

    @Override
    public @NotNull String getTranslationKey(ItemStack stack) {
        int meta = stack.getMetadata();
        if (meta < 0 || meta >= types.length)
            meta = 0;
        return "item." + Tags.MODID + ".ingot." + types[meta];
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
    public String[] getTypes() {
        return types;
    }

    @Override
    public String getPrefix() {
        return "ingot/";
    }

    @Override
    public String getPostfix() {
        return "_ingot";
    }
}
