package com.sabrepenguin.techreborn.blocks.meta;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.itemblock.IEnumMeta;
import com.sabrepenguin.techreborn.util.models.IPropertyBlockstate;
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

public class BlockStorage2 extends Block implements IEnumMeta, IPropertyBlockstate {
	public static final PropertyEnum<Storage2> TYPE = PropertyEnum.create(
			"type", Storage2.class);

	public BlockStorage2() {
		super(Material.IRON);
		setCreativeTab(TechReborn.RESOURCE_TAB);
		setHardness(2f);
		setRegistryName(Tags.MODID, "storage2");
		setTranslationKey(Tags.MODID + ".storage2");
		setDefaultState(this.getDefaultState().withProperty(TYPE, Storage2.TUNGSTENSTEEL));
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
		return getDefaultState().withProperty(TYPE, Storage2.META_MAP.get(meta));
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (Storage2 ore: Storage2.values()) {
			items.add(new ItemStack(this, 1, ore.meta()));
		}
	}

	@Override
	protected boolean canSilkHarvest() {
		return true;
	}

	@Override
	public String getName(ItemStack stack) {
		return Storage2.META_MAP.get(stack.getMetadata()).getName();
	}

	@Override
	public IProperty<?>[] getProperties() {
		return new IProperty[] {TYPE};
	}

	public enum Storage2 implements IStringSerializable {
		TUNGSTENSTEEL(0),
		IRIDIUM_REINFORCED_TUNGSTENSTEEL(1),
		IRIDIUM_REINFORCED_STONE(2),
		RUBY(3),
		SAPPHIRE(4),
		PERIDOT(5),
		YELLOW_GARNET(6),
		RED_GARNET(7),
		COPPER(8),
		TIN(9),
		REFINED_IRON(10);

		final static Int2ObjectMap<Storage2> META_MAP = new Int2ObjectOpenHashMap<>();

		static {
			for (Storage2 ore: values()) META_MAP.put(ore.metadata, ore);
			META_MAP.defaultReturnValue(TUNGSTENSTEEL);
		}

		final int metadata;
		final int blast_resistance;

		Storage2(int metadata) {
			this(metadata, 0);
		}

		Storage2(int metadata, int blast_resistance) {
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
		return Arrays.stream(Storage2.values()).map(storage -> Pair.of(storage.getName(), storage.metadata)).collect(Collectors.toList());
	}

	public Storage2[] getOre() {
		return Storage2.values();
	}
}
