package com.sabrepenguin.techreborn.blocks.meta;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.blocks.BlockBase;
import com.sabrepenguin.techreborn.blocks.IVariants;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import com.sabrepenguin.techreborn.util.MetadataHelper;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BlockStorage extends BlockBase implements INonStandardLocation, IVariants {
    private static final List<MetadataHelper> ORDERED_BLOCKS = new ArrayList<>();
    private static final Int2ObjectMap<MetadataHelper> META = new Int2ObjectOpenHashMap<>();

    static {
        ORDERED_BLOCKS.addAll(
                Arrays.asList(
                        new MetadataHelper(0, "silver"),
                        new MetadataHelper(1, "aluminum"),
                        new MetadataHelper(2, "titanium"),
                        new MetadataHelper(3, "chrome"),
                        new MetadataHelper(4, "steel"),
                        new MetadataHelper(5, "brass"),
                        new MetadataHelper(6, "lead"),
                        new MetadataHelper(7, "electrum"),
                        new MetadataHelper(8, "zinc"),
                        new MetadataHelper(9, "platinum"),
                        new MetadataHelper(10, "tungsten"),
                        new MetadataHelper(11, "nickel"),
                        new MetadataHelper(12, "invar"),
                        new MetadataHelper(13, "iridium"),
                        new MetadataHelper(14, "bronze")
                )
        );
        for (MetadataHelper item: ORDERED_BLOCKS) {
            META.put(item.meta(), item);
        }
    }
    public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, META.size()-1);

    public BlockStorage() {
        super(Material.IRON);
        setHardness(2f);
        setRegistryName(Tags.MODID, "storage");
        setTranslationKey(Tags.MODID + ".storage");
        setDefaultState(this.getDefaultState().withProperty(TYPE, 0));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        items.addAll(
                ORDERED_BLOCKS.stream()
                        .map(blocks -> new ItemStack(this, 1, blocks.meta()))
                        .collect(Collectors.toList()));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (meta >= META.size() || meta < 0) {
            meta = 0;
        }
        return getDefaultState().withProperty(TYPE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE);
    }

    @Override
    public void registerOredict() {
        for (MetadataHelper metadata: META.values()) {
            ItemStack newItem = new ItemStack(this, 1, metadata.meta());
            OreDictionary.registerOre("block" + metadata.capitalize(), newItem);
        }
    }

    @Override
    public Collection<MetadataHelper> getMetadata() {
        return META.values();
    }

    @Override
    public List<MetadataHelper> getListMetadata() {
        return ORDERED_BLOCKS;
    }
}
