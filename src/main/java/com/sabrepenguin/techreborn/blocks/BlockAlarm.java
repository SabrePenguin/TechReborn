package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockAlarm extends Block implements INonStandardLocation {
	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	private final AxisAlignedBB[] ALARM_BOUNDING_BOX = new AxisAlignedBB[]{
		new AxisAlignedBB(0.81, 1.0 - 0.19, 0.81, 1.0 - 0.81, 1.0D, 1.0 - 0.81),
				new AxisAlignedBB(0.81, 0.0D, 0.81, 1.0 - 0.81, 0.19, 1.0 - 0.81),
				new AxisAlignedBB(0.81, 0.81, 1.0 - 0.19, 1.0 - 0.81, 1.0 - 0.81, 1.0D),
				new AxisAlignedBB(0.81, 0.81, 0.0D, 1.0 - 0.81, 1.0 - 0.81, 0.19),
				new AxisAlignedBB(1.0 - 0.19, 0.81, 0.81, 1.0D, 1.0 - 0.81, 1.0 - 0.81),
				new AxisAlignedBB(0.0D, 0.81, 0.81, 0.19, 1.0 - 0.81, 1.0 - 0.81),
	};

	public BlockAlarm() {
		super(Material.ROCK);
		this.setCreativeTab(TechReborn.RESOURCE_TAB);
		this.setRegistryName(Tags.MODID, "alarm");
		this.setTranslationKey(Tags.MODID + ".alarm");
		this.setDefaultState(this.getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, false));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, ACTIVE);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int facingInt = state.getValue(FACING).getIndex();
		int activeInt = state.getValue(ACTIVE) ? 8 : 0;
		return facingInt + activeInt;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		Boolean active = (meta & 8) == 8;
		EnumFacing facing = EnumFacing.byIndex(meta & 7);
		return this.getDefaultState().withProperty(FACING, facing).withProperty(ACTIVE, active);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return ALARM_BOUNDING_BOX[state.getValue(FACING).getIndex()];
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public String getPrefix() {
		return "machines/lighting/";
	}

	@Override
	public IProperty<?>[] getProperties() {
		return new IProperty[] {FACING, ACTIVE};
	}
}
