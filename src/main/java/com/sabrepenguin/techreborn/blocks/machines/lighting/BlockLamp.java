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
public class BlockLamp extends BlockOmnidirectionalMachine {
	private final int brightness;

	private final AxisAlignedBB[] axisAlignedBB;

	public BlockLamp(String name, int brightness, AxisAlignedBB[] boundingBoxes) {
		super(Material.REDSTONE_LIGHT, name, "machines/lighting", 1.0f);
		this.axisAlignedBB = boundingBoxes;
		this.brightness = brightness;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return axisAlignedBB[state.getValue(FACING).getIndex()];
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
		return state.getValue(ACTIVE) ? brightness : 0;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
	}

	public static AxisAlignedBB[] generateBox(double width, double depth) {
		return new AxisAlignedBB[]{
				new AxisAlignedBB(width, 1.0 - depth, width, 1.0 - width, 1.0D, 1.0 - width),
				new AxisAlignedBB(width, 0.0D, width, 1.0 - width, depth, 1.0 - width),
				new AxisAlignedBB(width, width, 1.0 - depth, 1.0 - width, 1.0 - width, 1.0D),
				new AxisAlignedBB(width, width, 0.0D, 1.0 - width, 1.0 - width, depth),
				new AxisAlignedBB(1.0 - depth, width, width, 1.0D, 1.0 - width, 1.0 - width),
				new AxisAlignedBB(0.0D, width, width, depth, 1.0 - width, 1.0 - width),
		};
	}
}
