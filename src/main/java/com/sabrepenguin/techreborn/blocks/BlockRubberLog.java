package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BlockRubberLog extends BlockLog {
	public static PropertyDirection SAP = PropertyDirection.create("sap", EnumFacing.Plane.HORIZONTAL);
	public static PropertyBool HAS_SAP = PropertyBool.create("has_sap");

	public BlockRubberLog() {
		super();
		this.setHardness(2.0f);
		this.setRegistryName(Tags.MODID, "rubber_log");
		this.setTranslationKey(Tags.MODID + ".rubber_log");
		this.setDefaultState(blockState.getBaseState().withProperty(LOG_AXIS, EnumAxis.Y).withProperty(SAP, EnumFacing.NORTH).withProperty(HAS_SAP, false));
		this.setTickRandomly(true);
	}



	@Override
	public @NotNull IBlockState getStateFromMeta(int meta) {
		/*
		 * God this is gonna be fun.
		 * 3 is on - Sap is enabled
		 * 0 - North, 1 - South, 2 - East, 3 - West (00, 01, 10, 11)
		 * 3 is off - Direction saved instead
		 * 1 - X, 2 - Z 3 - NONE, 4 - Y
		 */
		IBlockState state = this.getDefaultState();
		if ((meta & 8) != 0) {
			return state.withProperty(LOG_AXIS, EnumAxis.Y)
					.withProperty(HAS_SAP, true)
					.withProperty(SAP, EnumFacing.byHorizontalIndex(meta & 3));
		}
		state = switch (meta & 3) {
			case 1 -> state.withProperty(LOG_AXIS, EnumAxis.X);
			case 2 -> state.withProperty(LOG_AXIS, EnumAxis.Z);
			case 3 -> state.withProperty(LOG_AXIS, EnumAxis.NONE);
			default -> state.withProperty(LOG_AXIS, EnumAxis.Y);
		};

		return state.withProperty(HAS_SAP, false);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		if (state.getValue(HAS_SAP)) {
			meta |= 8;
			meta |= state.getValue(SAP).getHorizontalIndex();
		} else {
			meta |= switch (state.getValue(LOG_AXIS)) {
				case X -> 1;
				case Z -> 2;
				case NONE -> 3;
				default -> 0;
			};
		}
		return meta;
	}

	@Override
	public void updateTick(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		if (!worldIn.isRemote && state.getValue(LOG_AXIS) == EnumAxis.Y) {
			if (!state.getValue(HAS_SAP)) {
				if (rand.nextInt(2) == 0) {
					EnumFacing facing = EnumFacing.byHorizontalIndex(rand.nextInt(4));
					if (worldIn.getBlockState(pos.down()).getBlock() == this &&
						worldIn.getBlockState(pos.up()).getBlock() == this) {
						worldIn.setBlockState(pos, state.withProperty(HAS_SAP, true).withProperty(SAP, facing));
					}
				}
			}
		}
	}

	@Override
	protected @NotNull BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, LOG_AXIS, SAP, HAS_SAP);
	}
}
