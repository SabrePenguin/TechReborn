package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.util.MetadataHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BlockStorage2 extends BlockBase {

    public static final String[] types = new String[] { "tungstensteel", "iridium_reinforced_tungstensteel",
            "iridium_reinforced_stone", "ruby", "sapphire", "peridot", "yellow_garnet", "red_garnet", "copper", "tin",
            "refined_iron" };
    public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, types.length-1);

    private static int TUNGSTENSTEEL;
    private static int IRIDIUM_REINFORCED_STONE;
    private static int IRIDIUM_REINFORCED_TUNGSTENSTEEL;
    public static List<MetadataHelper> metaBlocks = new ArrayList<>();
    static {
        for(int i = 0; i < types.length; i++) {
            if (!types[i].equals(MetadataHelper.META_PLACEHOLDER)) {
                metaBlocks.add(new MetadataHelper(i, types[i]));
            }
            if (types[i].equals("tungstensteel")) {
                TUNGSTENSTEEL = i;
            } else if (types[i].equals("iridium_reinforced_tungstensteel")) {
                IRIDIUM_REINFORCED_TUNGSTENSTEEL = i;
            } else if (types[i].equals("iridium_reinforced_stone")) {
                IRIDIUM_REINFORCED_STONE = i;
            }
        }
    }

    public BlockStorage2() {
        super(Material.IRON);
        setHardness(2f);
        setRegistryName(Tags.MODID, "storage2");
        setTranslationKey(Tags.MODID + ".storage2");
        setDefaultState(this.getDefaultState().withProperty(TYPE, 0));
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        int blockType = world.getBlockState(pos).getValue(TYPE);
        if (blockType == TUNGSTENSTEEL || blockType == IRIDIUM_REINFORCED_STONE) {
            return 300f;
        } else if (blockType == IRIDIUM_REINFORCED_TUNGSTENSTEEL) {
            return 400f;
        }
        return 30f;
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
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
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
