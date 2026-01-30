package com.sabrepenguin.techreborn.blocks.machines;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.tileentity.ISetWorldNameable;
import com.sabrepenguin.techreborn.util.InventoryUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;

public abstract class HorizontalMachineBlock extends Block {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	public HorizontalMachineBlock(String registryName) {
		super(Material.IRON);
		this.setCreativeTab(TechReborn.RESOURCE_TAB);
		this.setRegistryName(Tags.MODID, registryName);
		this.setTranslationKey(Tags.MODID + "." + registryName);
		this.setHardness(2.0f);
		this.setSoundType(SoundType.METAL);
		this.setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVE, false));
	}

	@Override
	protected @NotNull BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, ACTIVE);
	}

	@Override
	public void onBlockAdded(World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
		if (!worldIn.isRemote)
		{
			IBlockState north = worldIn.getBlockState(pos.north());
			IBlockState south = worldIn.getBlockState(pos.south());
			IBlockState west = worldIn.getBlockState(pos.west());
			IBlockState east = worldIn.getBlockState(pos.east());
			EnumFacing face = state.getValue(FACING);

			if (face == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock())
			{
				face = EnumFacing.SOUTH;
			}
			else if (face == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock())
			{
				face = EnumFacing.NORTH;
			}
			else if (face == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock())
			{
				face = EnumFacing.EAST;
			}
			else if (face == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock())
			{
				face = EnumFacing.WEST;
			}

			worldIn.setBlockState(pos, state.withProperty(FACING, face), 2);
		}
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
			InventoryUtils.spawnDroppedItems(te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null), worldIn, pos);
		}
		super.breakBlock(worldIn, pos, state);
	}

	@SuppressWarnings("deprecation")
	@Override
	public @NotNull IBlockState getStateForPlacement(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public void onBlockPlacedBy(World worldIn, @NotNull BlockPos pos, IBlockState state, EntityLivingBase placer, @NotNull ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
		if (stack.hasDisplayName()) {
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof ISetWorldNameable named) {
				named.setCustomName(stack.getDisplayName());
			}
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public @NotNull IBlockState withRotation(@NotNull IBlockState state, @NotNull Rotation rot) {
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@SuppressWarnings("deprecation")
	@Override
	public @NotNull IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

	@SuppressWarnings("deprecation")
	@Override
	public @NotNull IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.byIndex(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y)
		{
			enumfacing = EnumFacing.NORTH;
		}

		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getIndex();
	}

	@SuppressWarnings("deprecation")
	@Override
	public @NotNull EnumBlockRenderType getRenderType(@NotNull IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}
