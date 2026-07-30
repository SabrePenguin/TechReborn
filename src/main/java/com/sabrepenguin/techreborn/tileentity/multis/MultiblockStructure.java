package com.sabrepenguin.techreborn.tileentity.multis;

import com.github.bsideup.jabel.Desugar;
import com.sabrepenguin.techreborn.TechReborn;
import it.unimi.dsi.fastutil.chars.Char2CharArrayMap;
import it.unimi.dsi.fastutil.chars.Char2CharMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectArrayMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;


@Desugar
public record MultiblockStructure(char[][][] blocks, Char2ObjectMap<IBlockState> palette, BlockPos controllerOffset) {


	public static MultiblockStructure[] TEST_BLOCKS;
	static {
		Char2ObjectMap<IBlockState> TEST_MAP = new Char2ObjectArrayMap<>();
		TEST_MAP.put(' ', null);
		TEST_MAP.put('B', Blocks.DIRT.getDefaultState());
		TEST_MAP.put('A', Blocks.GRAVEL.getDefaultState());
		TEST_MAP.put('C', null);
//		TEST_MAP.put('D', state -> TRBlocks.distillation_tower.getDefaultState().withRotation(Rotation.CLOCKWISE_90).getValue(HorizontalMachineBlock.FACING) == state.getValue(HorizontalMachineBlock.FACING));
//		TEST_MAP.put('E', state -> TRBlocks.distillation_tower.getDefaultState().withRotation(Rotation.CLOCKWISE_180).getValue(HorizontalMachineBlock.FACING) == state.getValue(HorizontalMachineBlock.FACING));
//		TEST_MAP.put('F', state -> TRBlocks.distillation_tower.getDefaultState().withRotation(Rotation.COUNTERCLOCKWISE_90).getValue(HorizontalMachineBlock.FACING) == state.getValue(HorizontalMachineBlock.FACING));
		Char2CharArrayMap swapMap = new Char2CharArrayMap();
//		swapMap.put('C', 'D');
//		swapMap.put('D', 'E');
//		swapMap.put('E', 'F');
//		swapMap.put('F', 'C');
		TEST_BLOCKS = createStruct(new char[][][] {
				{
						{'B', 'B', 'B'},
						{'B', 'B', 'B'},
						{'B', 'B', 'B'},
						{' ', 'C', ' '}
				},
				{
						{'B', 'B', 'B'},
						{'B', 'B', 'B'},
						{'B', 'A', 'B'},
						{' ', ' ', ' '}
				},
		}, TEST_MAP, swapMap, new BlockPos(1, 0, 3));
	}

	public static void init() {
		TechReborn.LOGGER.debug("Loading multiblock structures");
	}

	/**
	 * Create a set of 4 rotated blocksets from one
	 * @param blocks The set of characters in the palette that forms the multi
	 * @param palette The map of characters to their blockstates
	 * @param controllerOffset The offset of the controller. Assume it faces north
	 * @return A set of 4 MultiblockStructure, with index indicating the direction
	 */
	public static MultiblockStructure[] createStruct(char[][][] blocks, Char2ObjectMap<IBlockState> palette, Char2CharMap swapMap, BlockPos controllerOffset) {
		MultiblockStructure[] structs = new MultiblockStructure[4];
		structs[0] = new MultiblockStructure(blocks, palette, controllerOffset);
		structs[1] = rotateStructure(structs[0], swapMap);
		structs[2] = rotateStructure(structs[1], swapMap);
		structs[3] = rotateStructure(structs[2], swapMap);
		return structs;
	}

	private static MultiblockStructure rotateStructure(MultiblockStructure original, Char2CharMap swapMap) {
		char[][][] rotatedArray = new char[original.blocks.length][][];

		for (int i = 0; i < original.blocks.length; i++) {
			rotatedArray[i] = rotateCW(original.blocks[i], swapMap);
		}
		BlockPos newPos = new BlockPos((original.blocks[0].length - 1 - original.controllerOffset.getZ()), original.controllerOffset.getY(), original.controllerOffset.getX());
		return new MultiblockStructure(rotatedArray, original.palette, newPos);
	}

	private static char[][] rotateCW(char[][] original, Char2CharMap swapMap) {
		final int M = original.length;
		final int N = original[0].length;
		char[][] ret = new char[N][M];
		for (int r = 0; r < M; r++) {
			for (int c = 0; c < N; c++) {
				if (!swapMap.containsKey(original[r][c]))
					ret[c][M-1-r] = original[r][c];
				else
					ret[c][M-1-r] = swapMap.get(original[r][c]);
			}
		}
		return ret;
	}

	public boolean statesEqual(char target, IBlockState currentState) {
		IBlockState blockState = palette.get(target);
		return blockState == null || currentState == null || blockState == currentState;
	}

	public IBlockState[][][] getSizedArray() {
		if (blocks.length == 0 || blocks[0].length == 0 || blocks[0][0].length == 0)
			throw new RuntimeException("Multiblock has a dimension of size 0");
		return new IBlockState[blocks.length][blocks[0].length][blocks[0][0].length];
	}
}
