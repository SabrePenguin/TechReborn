package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.Part;
import com.sabrepenguin.techreborn.util.WorldUtils;
import com.sabrepenguin.techreborn.util.handlers.ModSounds;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockRubberLog extends BlockLog {
	public static PropertyDirection SAP = PropertyDirection.create("sap", EnumFacing.Plane.HORIZONTAL);
	public static PropertyBool HAS_SAP = PropertyBool.create("has_sap");

	public BlockRubberLog() {
		super();
		this.setCreativeTab(TechReborn.RESOURCE_TAB);
		this.setHardness(2.0f);
		this.setRegistryName(Tags.MODID, "rubber_log");
		this.setTranslationKey(Tags.MODID + ".rubber_log");
		this.setDefaultState(blockState.getBaseState().withProperty(LOG_AXIS, EnumAxis.Y).withProperty(SAP, EnumFacing.NORTH).withProperty(HAS_SAP, false));
		this.setTickRandomly(true);
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
	public IBlockState getStateFromMeta(int meta) {
		/*
		 * God this is gonna be fun.
		 * 0 is on - Sap is enabled 1000
		 * 0 - North (00), 1 - South (01), 2 - East (10), 3 - West (11)
		 * 0 is off - Direction saved instead
		 * 1 - X, 2 - Z 3 - NONE, 4 - Y
		 */
		IBlockState state = this.getDefaultState();
		boolean hasSap = (meta & 1) != 0;
		int payload = meta >> 2 & 3;
		if (hasSap) {
			return state.withProperty(LOG_AXIS, EnumAxis.Y)
					.withProperty(HAS_SAP, true)
					.withProperty(SAP, EnumFacing.byHorizontalIndex(payload));
		}
		state = switch (payload) {
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
			meta |= 1;
			meta |= state.getValue(SAP).getHorizontalIndex() << 2;
		} else {
			int axis = switch (state.getValue(LOG_AXIS)) {
				case X -> 1;
				case Z -> 2;
				case NONE -> 3;
				default -> 0;
			};
			meta |= axis << 2;
		}
		return meta;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (worldIn.isRemote || state.getValue(LOG_AXIS) != EnumAxis.Y) return;
		super.updateTick(worldIn, pos, state, rand);
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

	@Override
	@SuppressWarnings("ConstantConditions")
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
									EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
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
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, LOG_AXIS, SAP, HAS_SAP);
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		super.getDrops(drops, world, pos, state, fortune);
		if (state.getValue(HAS_SAP) && new Random().nextInt(4) == 0) {
			drops.add(new ItemStack(TRItems.part, 1, Part.PartMeta.sap.metadata()));
		}
	}
}
