package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockRubberLeaves extends BlockLeaves implements IBurnable {
	public BlockRubberLeaves() {
		super();
		this.setCreativeTab(TechReborn.RESOURCE_TAB);
		this.setRegistryName(Tags.MODID, "rubber_leaves");
		this.setTranslationKey(Tags.MODID + ".rubber_leaves");
		this.setDefaultState(blockState.getBaseState().withProperty(CHECK_DECAY, false).withProperty(DECAYABLE, false));
	}

	@Override
	public int getBurnTime() {
		return 100;
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
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, CHECK_DECAY, DECAYABLE);
	}

	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
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
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(CHECK_DECAY, false).withProperty(CHECK_DECAY, false);
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	public BlockPlanks.EnumType getWoodType(int meta) {
		return null;
	}

	@Override
	@SuppressWarnings("ConstantConditions")
	public Item getItemDropped(IBlockState state,Random rand, int fortune) {
		return Item.getItemFromBlock(TRBlocks.rubber_sapling);
	}

	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state) {
		return new ItemStack(Item.getItemFromBlock(this));
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return NonNullList.withSize(1, new ItemStack(this));
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean isOpaqueCube(IBlockState state) {
		if (!hasFancyLeaves()) {
			return super.isOpaqueCube(state);
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		if (!hasFancyLeaves()) {
			return super.getRenderLayer();
		}
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	@SuppressWarnings("deprecation")
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
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
