package com.sabrepenguin.techreborn.blocks.meta;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.itemblock.IMetaInformation;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BlockMachineCasing extends Block implements IMetaInformation, INonStandardLocation {
	public static final PropertyEnum<Casing> TYPE = PropertyEnum.create(
			"type", Casing.class);

	public BlockMachineCasing() {
		super(Material.IRON);
		setCreativeTab(TechReborn.RESOURCE_TAB);
		setHardness(2f);
		setRegistryName(Tags.MODID, "machine_casing");
		setTranslationKey(Tags.MODID + ".machine_casing");
		setDefaultState(this.getDefaultState().withProperty(TYPE, Casing.STANDARD));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}

	@Override
	public int damageDropped(@NotNull IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).metadata;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, Casing.META_MAP.get(meta));
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (Casing ore: Casing.values()) {
			items.add(new ItemStack(this, 1, ore.meta()));
		}
	}

	@Override
	public String getPrefix() {
		return "machines/structure/";
	}

	@Override
	public String getName(ItemStack stack) {
		return Casing.META_MAP.get(stack.getMetadata()).getName();
	}

	@Override
	public IProperty<?>[] getProperties() {
		return new IProperty[] {TYPE};
	}

	public enum Casing implements IStringSerializable {
		STANDARD(0),
		REINFORCED(1),
		ADVANCED(2);

		final static Int2ObjectMap<Casing> META_MAP = new Int2ObjectOpenHashMap<>();

		static {
			for (Casing ore: values()) META_MAP.put(ore.metadata, ore);
			META_MAP.defaultReturnValue(STANDARD);
		}

		final int metadata;
		final int blast_resistance;

		Casing(int metadata) {
			this(metadata, 0);
		}

		Casing(int metadata, int blast_resistance) {
			this.metadata = metadata;
			this.blast_resistance = blast_resistance;
		}

		public int meta() {
			return this.metadata;
		}

		@Override
		public String getName() {
			return name().toLowerCase();
		}
	}

	@Override
	public List<Pair<String, Integer>> getMeta() {
		return Arrays.stream(Casing.values()).map(casing -> Pair.of(casing.getName(), casing.metadata)).collect(Collectors.toList());
	}

	public Casing[] getOre() {
		return Casing.values();
	}
}
