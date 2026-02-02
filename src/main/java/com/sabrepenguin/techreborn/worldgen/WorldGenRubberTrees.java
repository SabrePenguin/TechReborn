package com.sabrepenguin.techreborn.worldgen;

import com.sabrepenguin.techreborn.blocks.BlockRubberLog;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class WorldGenRubberTrees extends WorldGenAbstractTree {

	@SuppressWarnings("ConstantConditions")
	private final IBlockState leavesBlockState = TRBlocks.rubber_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, false).withProperty(BlockLeaves.DECAYABLE, true);
	private static final int minTreeHeight = 5;

	public WorldGenRubberTrees(boolean shouldNotifyParent) {
		super(shouldNotifyParent);
	}

	@Override
	public boolean generate(@NotNull World worldIn, @NotNull Random rand, @NotNull BlockPos position) {
		int treeHeight = rand.nextInt(5) + minTreeHeight;
		if (position.getY() >= 1 && position.getY() + treeHeight + 1 <= worldIn.getHeight()) {
			if (!isSuitableLocation(worldIn, position, treeHeight)) {
				return false;
			} else {
				IBlockState state = worldIn.getBlockState(position.down());
				if (state.getBlock().canSustainPlant(state, worldIn, position.down(), EnumFacing.UP, (IPlantable) Blocks.SAPLING)
					&& position.getY() < worldIn.getHeight() - minTreeHeight - 1) {
					state.getBlock().onPlantGrow(state, worldIn, position.down(), position);
					generateLeaves(worldIn, position, treeHeight, rand);
					generateTrunk(worldIn, position, treeHeight, rand);
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("ConstantConditions")
	private void generateTrunk(World worldIn, BlockPos pos, int height, Random rand) {
		BlockPos topLogPosition = null;
		for (int h = 0; h < height - 1; h++) {
			BlockPos upN = pos.up(h);
			IBlockState state = worldIn.getBlockState(upN);
			Block block = state.getBlock();
			if (block.isAir(state, worldIn, upN) || block.isLeaves(state, worldIn, upN) || block.isReplaceable(worldIn, upN)) {
				IBlockState newState = TRBlocks.rubber_log.getDefaultState();
				// TODO: config
				if (rand.nextInt(10) == 0) {
					newState = newState.withProperty(BlockRubberLog.HAS_SAP, true)
									.withProperty(BlockRubberLog.SAP, EnumFacing.byHorizontalIndex(rand.nextInt(4)));
				}
				setBlockAndNotifyAdequately(worldIn, pos.up(h), newState);
				topLogPosition = upN;
			}
		}
		if (topLogPosition != null) {
			// TODO: config
			for (int i = 1; i <= 4; i++) {
				BlockPos spikePos = topLogPosition.up(i);
				setBlockAndNotifyAdequately(worldIn, spikePos, leavesBlockState);
			}
		}
	}

	private void generateLeaves(World worldIn, BlockPos pos, int height, Random rand) {
		for (int leavesY = pos.getY() - 3 + height; leavesY <= pos.getY() + height; leavesY++) {
			int layer = leavesY - (pos.getY() + height);
			int radius = 1 - layer / 2;
			for (int leavesX = pos.getX() - radius; leavesX <= pos.getX() + radius; leavesX++) {
				int relativeX = leavesX - pos.getX();
				for (int leavesZ = pos.getZ() - radius; leavesZ <= pos.getZ() + radius; leavesZ++) {
					int relativeZ = leavesZ - pos.getZ();
					if (Math.abs(relativeX) != radius || Math.abs(relativeZ) != radius || rand.nextInt(2) != 0 && layer != 0) {
						BlockPos newPos = new BlockPos(leavesX, leavesY, leavesZ);
						IBlockState state = worldIn.getBlockState(newPos);
						if (state.getBlock().isAir(state, worldIn, newPos) || state.getBlock().isLeaves(state, worldIn, newPos)
							|| state.getBlock().canBeReplacedByLeaves(state, worldIn, newPos)) {
							setBlockAndNotifyAdequately(worldIn, newPos, leavesBlockState);
						}
					}
				}
			}
		}
	}

	private boolean isSuitableLocation(World worldIn, BlockPos pos, int treeHeight) {
		boolean isSuitable = true;
		for (int yOffset = pos.getY(); yOffset <= pos.getY() + 1 + treeHeight; yOffset++) {
			int radius = 1;
			if (yOffset == pos.getY()) {
				radius = 0;
			}
			if (yOffset >= pos.getY() + 1 + treeHeight - 2) {
				radius = 2;
			}

			BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();

			if (yOffset >= 0 && yOffset < worldIn.getHeight() ) {
				for (int xOffset = pos.getX() - radius; xOffset <= pos.getX() + radius && isSuitable; xOffset++) {
					for (int zOffset = pos.getZ() - radius; zOffset <= pos.getZ() + radius && isSuitable; zOffset++) {
						isSuitable = isReplaceable(worldIn, blockPos.setPos(xOffset, yOffset, zOffset));
					}
				}
			}
		}
		return isSuitable;
	}
}
