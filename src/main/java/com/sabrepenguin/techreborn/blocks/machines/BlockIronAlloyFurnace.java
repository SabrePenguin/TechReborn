package com.sabrepenguin.techreborn.blocks.machines;

import com.cleanroommc.modularui.factory.GuiFactories;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.gui.GUI;
import com.sabrepenguin.techreborn.tileentity.tier0.TileEntityIronAlloyFurnace;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockIronAlloyFurnace extends HorizontalMachineBlock implements INonStandardLocation, ITileEntityProvider {
	public BlockIronAlloyFurnace() {
		super("iron_alloy_furnace");
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			GuiFactories.tileEntity().open(playerIn, pos);
			//playerIn.openGui(TechReborn.instance, GUI.IRON_ALLOY_FURNACE.getId(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

	@Override
	public boolean hasTileEntity(@NotNull IBlockState state) {
		return true;
	}

	@Override
	public @Nullable TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityIronAlloyFurnace();
	}

	@Override
	public @Nullable TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityIronAlloyFurnace();
	}

	@Override
	public IProperty<?>[] getProperties() {
		return new IProperty[] {FACING, ACTIVE};
	}

	@Override
	public String getPrefix() {
		return "machines/tier0/";
	}

	public static void setState(boolean active, World worldIn, BlockPos pos)
	{
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tileEntity = worldIn.getTileEntity(pos);

		if (active) {
			worldIn.setBlockState(pos, TRBlocks.iron_alloy_furnace.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(ACTIVE, true), 3);
		} else {
			worldIn.setBlockState(pos, TRBlocks.iron_alloy_furnace.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(ACTIVE, false), 3);
		}
		if (tileEntity != null) {
			tileEntity.validate();
			worldIn.setTileEntity(pos, tileEntity);
		}
	}
}
