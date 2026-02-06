package com.sabrepenguin.techreborn.blocks.machines.lighting;

import com.sabrepenguin.techreborn.blocks.machines.BlockOmnidirectionalMachine;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockIncandescentLamp extends BlockOmnidirectionalMachine {
	private static final int BRIGHTNESS = 14;

	private static final AxisAlignedBB[] AXIS_ALIGNED_BB = {
		new AxisAlignedBB(0.25, 1.0 - 0.625, 0.25, 1.0 - 0.25, 1.0D, 1.0 - 0.25),
				new AxisAlignedBB(0.25, 0.0D, 0.25, 1.0 - 0.25, 0.625, 1.0 - 0.25),
				new AxisAlignedBB(0.25, 0.25, 1.0 - 0.625, 1.0 - 0.25, 1.0 - 0.25, 1.0D),
				new AxisAlignedBB(0.25, 0.25, 0.0D, 1.0 - 0.25, 1.0 - 0.25, 0.625),
				new AxisAlignedBB(1.0 - 0.625, 0.25, 0.25, 1.0D, 1.0 - 0.25, 1.0 - 0.25),
				new AxisAlignedBB(0.0D, 0.25, 0.25, 0.625, 1.0 - 0.25, 1.0 - 0.25)};

	public BlockIncandescentLamp() {
		super(Material.REDSTONE_LIGHT, "lamp_incandescent", "machines/lighting", 1.0f);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AXIS_ALIGNED_BB[state.getValue(FACING).getIndex()];
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(ACTIVE) ? BRIGHTNESS : 0;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
	}
}
