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
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OreBlock2 extends Block implements IMetaInformation, INonStandardLocation {
	public static final PropertyEnum<Ore2> TYPE = PropertyEnum.create(
			"type", Ore2.class);

	public OreBlock2() {
		super(Material.IRON);
		setCreativeTab(TechReborn.RESOURCE_TAB);
		setHardness(2f);
		setRegistryName(Tags.MODID, "ore2");
		setTranslationKey(Tags.MODID + ".ore2");
		setHarvestLevel("pickaxe", 2);
		setDefaultState(this.getDefaultState().withProperty(TYPE, Ore2.COPPER));
	}

	@Override
	public String getOreDict() {
		return "ore";
	}

	@Override
	protected @NotNull BlockStateContainer createBlockState() {
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
	public @NotNull IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, Ore2.META_MAP.get(meta));
	}

	@Override
	public void getSubBlocks(@NotNull CreativeTabs itemIn, @NotNull NonNullList<ItemStack> items) {
		for (Ore2 ore: Ore2.values()) {
			items.add(new ItemStack(this, 1, ore.meta()));
		}
	}

	@Override
	protected boolean canSilkHarvest() {
		return true;
	}

	@Override
	public String getName(ItemStack stack) {
		return Ore2.META_MAP.get(stack.getMetadata()).getName();
	}

	@Override
	public boolean hasResourceLocation() {
		return true;
	}

	@Override
	public ResourceLocation getResourceLocation() {
		return new ResourceLocation(Tags.MODID, "ore");
	}

	@Override
	public IProperty<?>[] getProperties() {
		return new IProperty[] {TYPE};
	}

	public enum Ore2 implements IStringSerializable {
		COPPER(0),
		TIN(1);

		final static Int2ObjectMap<Ore2> META_MAP = new Int2ObjectOpenHashMap<>();

		static {
			for (Ore2 ore: values()) META_MAP.put(ore.metadata, ore);
			META_MAP.defaultReturnValue(COPPER);
		}

		final int metadata;

		Ore2(int metadata) {
			this.metadata = metadata;
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
		return Arrays.stream(Ore2.values()).map(ore -> Pair.of(ore.getName(), ore.metadata)).collect(Collectors.toList());
	}

	public Ore2[] getOre() {
		return Ore2.values();
	}
}
