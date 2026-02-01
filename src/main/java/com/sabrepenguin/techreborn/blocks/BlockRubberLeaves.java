package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlockRubberLeaves extends BlockLeaves {
	public BlockRubberLeaves() {
		super();
		this.setRegistryName(Tags.MODID, "rubber_leaves");
		this.setTranslationKey(Tags.MODID + ".rubber_leaves");
		this.setDefaultState(blockState.getBaseState().withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, false));
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 30;
	}

	@Override
	public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return 60;
	}


	@Override
	protected @NotNull BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE);
	}

	@Override
	@SuppressWarnings("deprecation")
	public @NotNull IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) != 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		if (!state.getValue(DECAYABLE))
			meta |= 4;
		if (state.getValue(CHECK_DECAY))
			meta |= 8;
		return meta;
	}

	@Override
	public BlockPlanks.EnumType getWoodType(int meta) {
		return null;
	}

	@Override
	protected @NotNull ItemStack getSilkTouchDrop(@NotNull IBlockState state) {
		return new ItemStack(Item.getItemFromBlock(this));
	}

	@Override
	public @NotNull List<ItemStack> onSheared(@NotNull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return NonNullList.withSize(1, new ItemStack(this));
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isOpaqueCube(@NotNull IBlockState state) {
		if (!hasFancyLeaves()) {
			return super.isOpaqueCube(state);
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public @NotNull BlockRenderLayer getRenderLayer() {
		if (!hasFancyLeaves()) {
			return super.getRenderLayer();
		}
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	@SuppressWarnings("deprecation")
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(@NotNull IBlockState blockState, @NotNull IBlockAccess blockAccess, @NotNull BlockPos pos, @NotNull EnumFacing side) {
		if (!hasFancyLeaves()) {
			return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
		}
		return true;
	}

	private boolean hasFancyLeaves() {
		leavesFancy = TechReborn.proxy.hasFancyGraphics();
		return leavesFancy;
	}
}
