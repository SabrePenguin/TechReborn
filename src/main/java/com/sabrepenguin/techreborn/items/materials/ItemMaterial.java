package com.sabrepenguin.techreborn.items.materials;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.items.ItemBase;
import com.sabrepenguin.techreborn.items.MetadataItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Collectors;

public class ItemMaterial extends ItemBase {

    private final String registryName;
    private final String translationKey;
    private final IMaterial items;
    private final String prefix;
    private final String postfix;

    public ItemMaterial(String registryName, String translationKey, IMaterial items) {
        this(registryName, translationKey, items, "", "");
    }

    public ItemMaterial(String registryName, String translationKey, IMaterial items, String modelPrefix, String modelPostfix) {
        setTranslationKey(Tags.MODID  + "." + translationKey);
        setRegistryName(Tags.MODID, registryName);
        setHasSubtypes(true);
        setNoRepair();
        this.registryName = registryName;
        this.translationKey = translationKey;
        this.items = items;
        this.prefix = modelPrefix;
        this.postfix = modelPostfix;
    }

    @Override
    public void registerOredict() {
        for (MetadataItem metadata: items.getItems()) {
            String oredict = items.getOreDict();
            if (oredict.isEmpty() && items.hasNonStandardOreDict()) {
                String[] ores = items.getNonStandardOreDict(metadata.meta());
                for (String ore: ores) {
                    ItemStack newItem = new ItemStack(this, 1, metadata.meta());
                    OreDictionary.registerOre(ore, newItem);
                }
                continue;
            } else if (oredict.isEmpty()) {
                continue;
            }
            ItemStack newItem = new ItemStack(this, 1, metadata.meta());
            OreDictionary.registerOre(oredict + metadata.capitalize(), newItem);
        }
    }

    @Override
    public @NotNull String getTranslationKey(ItemStack stack) {
        int meta = stack.getMetadata();
        return "item." + Tags.MODID + "." + translationKey + "." + items.getNameFromMeta(meta);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (tab != TechReborn.RESOURCE_TAB)
            return;
        items.addAll(
                this.items.getOrderedItems().stream()
                        .map(item -> new ItemStack(this, 1, item.meta()))
                        .collect(Collectors.toList()));
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
    public Collection<MetadataItem> getItems() {
        return this.items.getItems();
    }

    public String getBlockstateJson() {
        return this.registryName;
    }
}
