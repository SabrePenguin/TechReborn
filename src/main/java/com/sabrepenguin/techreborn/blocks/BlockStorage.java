package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.util.MetadataHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BlockStorage extends BlockBase{
    public static final String[] types = new String[] { "silver", "aluminum", "titanium", "chrome", "steel", "brass",
            "lead", "electrum", "zinc", "platinum", "tungsten", "nickel", "invar", "iridium", "bronze" };
    public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, types.length-1);

    public static List<MetadataHelper> metaBlocks = new ArrayList<>();
    static {
        for(int i = 0; i < types.length; i++) {
            if (!types[i].equals(MetadataHelper.META_PLACEHOLDER)) {
                metaBlocks.add(new MetadataHelper(i, types[i]));
            }
        }
    }

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
                metaBlocks.stream()
                        .map(blocks -> new ItemStack(this, 1, blocks.meta()))
                        .collect(Collectors.toList()));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if (meta >= types.length || meta < 0) {
            meta = 0;
        }
        return getDefaultState().withProperty(TYPE, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE);
    }

    @Override
    public String[] getTypes() {
        return types;
    }

    @Override
    public String getPrefix() {
        return "storage/";
    }

    @Override
    public String getPostfix() {
        return "_block";
    }

    @Override
    public void registerOredict() {
        for (MetadataHelper metadata: metaBlocks) {
            ItemStack newItem = new ItemStack(this, 1, metadata.meta());
            OreDictionary.registerOre("block" + metadata.capitalize(), newItem);
        }
    }
}
