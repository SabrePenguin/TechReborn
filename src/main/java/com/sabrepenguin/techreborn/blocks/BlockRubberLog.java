package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.Part;
import com.sabrepenguin.techreborn.util.WorldUtils;
import com.sabrepenguin.techreborn.util.handlers.ModSounds;
import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
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
		if (worldIn.isRemote || state.getValue(LOG_AXIS) != EnumAxis.Y) return;
		super.updateTick(worldIn, pos, state, rand);
		if (!worldIn.isRemote && state.getValue(LOG_AXIS) == EnumAxis.Y) {
			if (!state.getValue(HAS_SAP)) {
				if (rand.nextInt(50) == 0) {
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
	@SuppressWarnings("ConstantConditions")
	public boolean onBlockActivated(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state,
									@NotNull EntityPlayer playerIn, @NotNull EnumHand hand, @NotNull EnumFacing facing,
									float hitX, float hitY, float hitZ) {
		super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		ItemStack itemInHand = playerIn.getHeldItem(hand);
		if (itemInHand.isEmpty())
			return false;
		if (itemInHand.getItem() == TRItems.treetap) {
			if (state.getValue(HAS_SAP) && state.getValue(SAP) == facing) {
				worldIn.setBlockState(pos, state.withProperty(HAS_SAP, false).withProperty(SAP, EnumFacing.NORTH));
				worldIn.playSound(null, pos, ModSounds.SAP_EXTRACT, SoundCategory.BLOCKS, 0.6f, 1f);
				if (!worldIn.isRemote) {
					playerIn.getHeldItem(hand).damageItem(1,playerIn);
					ItemStack drop = new ItemStack(TRItems.part, 1, Part.PartMeta.sap.metadata());
					if (!playerIn.inventory.addItemStackToInventory(drop)) {
						WorldUtils.dropItem(drop, worldIn, pos.offset(facing));
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	protected @NotNull BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, LOG_AXIS, SAP, HAS_SAP);
	}
}
