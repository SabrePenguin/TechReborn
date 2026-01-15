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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BlockStorage2 extends BlockBase implements INonStandardLocation, IVariants {

    private static final List<MetadataHelper> ORDERED_BLOCKS = new ArrayList<>();
    private static final Int2ObjectMap<MetadataHelper> META = new Int2ObjectOpenHashMap<>();

    static {
        ORDERED_BLOCKS.addAll(
                Arrays.asList(
                        new MetadataHelper(0, "tungstensteel"),
                        new MetadataHelper(1, "iridium_reinforced_tungstensteel"),
                        new MetadataHelper(2, "iridium_reinforced_stone"),
                        new MetadataHelper(3, "ruby"),
                        new MetadataHelper(4, "sapphire"),
                        new MetadataHelper(5, "peridot"),
                        new MetadataHelper(6, "yellow_garnet"),
                        new MetadataHelper(7, "red_garnet"),
                        new MetadataHelper(8, "copper"),
                        new MetadataHelper(9, "tin"),
                        new MetadataHelper(10, "refined_iron")
                )
        );
        for (MetadataHelper item: ORDERED_BLOCKS) {
            META.put(item.meta(), item);
        }
    }
    public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, META.size()-1);

    private static int TUNGSTENSTEEL;
    private static int IRIDIUM_REINFORCED_STONE;
    private static int IRIDIUM_REINFORCED_TUNGSTENSTEEL;
    static {
        for(int i = 0; i < META.size(); i++) {
            if (META.get(i).name().equals("tungstensteel")) {
                TUNGSTENSTEEL = i;
            } else if (META.get(i).name().equals("iridium_reinforced_tungstensteel")) {
                IRIDIUM_REINFORCED_TUNGSTENSTEEL = i;
            } else if (META.get(i).name().equals("iridium_reinforced_stone")) {
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
