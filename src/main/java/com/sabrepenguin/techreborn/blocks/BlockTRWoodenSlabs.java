package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class BlockTRWoodenSlabs extends BlockSlab {
	public static final PropertyEnum<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);

	protected BlockTRWoodenSlabs(Material material, String name) {
		super(material);
		this.setCreativeTab(TechReborn.RESOURCE_TAB);
		this.setRegistryName(Tags.MODID, name);
		this.setTranslationKey(Tags.MODID + "." + name);
		IBlockState state = this.blockState.getBaseState().withProperty(VARIANT, Variant.DEFAULT);
		if (!this.isDouble()) {
			state.withProperty(BlockSlab.HALF, EnumBlockHalf.BOTTOM);
		}
		this.setDefaultState(state);
		this.useNeighborBrightness = !this.isDouble();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return this.isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, HALF, VARIANT);
	}

	@Override
	public boolean isDouble() {
		return false;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 20;
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 5;
	}

	@Override
	public String getTranslationKey(int meta) {
		return this.getTranslationKey();
	}

	@Override
	public IProperty<?> getVariantProperty() {
		return VARIANT;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
		return Variant.DEFAULT;
	}

	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		if (this.isDouble()) {
			return this.getDefaultState();
		}
		if (meta > 1) meta = 1;
		return this.getDefaultState().withProperty(HALF, BlockSlab.EnumBlockHalf.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		if (this.isDouble()) {
			return 0;
		}
		return state.getValue(HALF).ordinal();
	}

	public enum Variant implements IStringSerializable {
		DEFAULT;

		@Override
		public String getName() {
			return "default";
		}
	}

	public static class Double extends BlockTRWoodenSlabs {
		public Double(Material material, String name) {
			super(material, name);
		}

		@Override
		public boolean isDouble() {
			return true;
		}
	}

	public static class Half extends BlockTRWoodenSlabs {
		public Half(Material material, String name) {
			super(material, name);
		}

		@Override
		public boolean isDouble() {
			return false;
		}
	}
}
