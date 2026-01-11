package com.sabrepenguin.techreborn.items.materials;

import com.sabrepenguin.techreborn.Tags;
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

    private final String material;
    private final String translationKey;
    private final IMaterial items;
    private final String prefix;
    private final String postfix;

    public ItemMaterial(String material, String translationKey, IMaterial items) {
        this(material, translationKey, items, "", "");
    }

    public ItemMaterial(String material, String translationKey, IMaterial items, String modelPrefix, String modelPostfix) {
        setTranslationKey(Tags.MODID  + "." + translationKey);
        setRegistryName(Tags.MODID, material);
        setHasSubtypes(true);
        setNoRepair();
        this.material = material;
        this.translationKey = translationKey;
        this.items = items;
        this.prefix = modelPrefix;
        this.postfix = modelPostfix;
    }

    @Override
    public void registerOredict() {
        for (MetadataItem metadata: items.getItems()) {
            String oredict = items.getOreDict(metadata.meta());
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
        return this.material;
    }
}
