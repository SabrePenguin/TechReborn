package com.sabrepenguin.techreborn.blocks.meta;

import com.sabrepenguin.techreborn.Tags;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.jetbrains.annotations.NotNull;

public class OreBlock extends Block {
	public static final PropertyInteger TYPE = PropertyInteger.create(
			"type", 0, Ore.SILVER.metadata);

	public OreBlock() {
		super(Material.IRON);
		setHardness(2f);
		setRegistryName(Tags.MODID, "ore");
		setTranslationKey(Tags.MODID + ".ore");
		setHarvestLevel("pickaxe", 2);
		setDefaultState(this.getDefaultState().withProperty(TYPE, 0));
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
		return state.getValue(TYPE);
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (Ore ore: Ore.values()) {
			items.add(new ItemStack(this, 1, ore.meta()));
		}
	}

	@Override
	protected boolean canSilkHarvest() {
		return true;
	}

	@Override
	protected @NotNull ItemStack getSilkTouchDrop(@NotNull IBlockState state) {
		return super.getSilkTouchDrop(state);
	}

	public enum Ore {
		GALENA(0),
		IRIDIUM(1),
		RUBY(2),
		SAPPHIRE(3),
		BAUXITE(4),
		PYRITE(5),
		CINNABAR(6),
		SPHALERITE(7),
		TUNGSTEN(8),
		SHELDONITE(9),
		PERIDOT(10),
		SODALITE(11),
		LEAD(12),
		SILVER(13);

		final int metadata;
		final int blast_resistance;

		Ore(int metadata) {
			this(metadata, 0);
		}

		Ore(int metadata, int blast_resistance) {
			this.metadata = metadata;
			this.blast_resistance = blast_resistance;
		}

		public int meta() {
			return this.metadata;
		}
	}

	public Ore[] getOre() {
		return Ore.values();
	}
}
