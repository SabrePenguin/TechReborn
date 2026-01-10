package com.sabrepenguin.techreborn.items;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.items.materials.IMaterial;
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

    public ItemMaterial(String material, String translationKey, IMaterial items) {
        setTranslationKey(Tags.MODID  + "." + translationKey);
        setRegistryName(Tags.MODID, material);
        setHasSubtypes(true);
        this.material = material;
        this.translationKey = translationKey;
        this.items = items;
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
        return material + "/";
    }

    @Override
    public String getPostfix() {
        return "_" + this.material;
    }

    @Override
    public Collection<MetadataItem> getItems() {
        return this.items.getItems();
    }
}
