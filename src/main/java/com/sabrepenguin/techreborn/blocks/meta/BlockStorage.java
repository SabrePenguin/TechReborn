package com.sabrepenguin.techreborn.blocks.meta;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.itemblock.IMetaMaterial;
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

public class BlockStorage extends Block implements IMetaMaterial, INonStandardLocation {
	public static final PropertyEnum<Storage> TYPE = PropertyEnum.create(
			"type", Storage.class);

	public BlockStorage() {
		super(Material.IRON);
		setCreativeTab(TechReborn.RESOURCE_TAB);
		setHardness(2f);
		setRegistryName(Tags.MODID, "storage");
		setTranslationKey(Tags.MODID + ".storage");
		setDefaultState(this.getDefaultState().withProperty(TYPE, Storage.SILVER));
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
		return getDefaultState().withProperty(TYPE, Storage.META_MAP.get(meta));
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (Storage ore: Storage.values()) {
			items.add(new ItemStack(this, 1, ore.meta()));
		}
	}

	@Override
	public String getOreDict() {
		return "block";
	}

	@Override
	public String getName(ItemStack stack) {
		return Storage.META_MAP.get(stack.getMetadata()).getName();
	}

	@Override
	public IProperty<?>[] getProperties() {
		return new IProperty[] {TYPE};
	}

	public enum Storage implements IStringSerializable {
		SILVER(0),
		ALUMINUM(1),
		TITANIUM(2),
		CHROME(3),
		STEEL(4),
		BRASS(5),
		LEAD(6),
		ELECTRUM(7),
		ZINC(8),
		PLATINUM(9),
		TUNGSTEN(10),
		NICKEL(11),
		INVAR(12),
		IRIDIUM(13),
		BRONZE(14);

		final static Int2ObjectMap<Storage> META_MAP = new Int2ObjectOpenHashMap<>();

		static {
			for (Storage ore: values()) META_MAP.put(ore.metadata, ore);
			META_MAP.defaultReturnValue(SILVER);
		}

		final int metadata;
		final int blast_resistance;

		Storage(int metadata) {
			this(metadata, 0);
		}

		Storage(int metadata, int blast_resistance) {
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
		return Arrays.stream(Storage.values()).map(storage -> Pair.of(storage.getName(), storage.metadata)).collect(Collectors.toList());
	}

	public Storage[] getOre() {
		return Storage.values();
	}
}
