package com.sabrepenguin.techreborn.tileentity.multis;

import com.github.bsideup.jabel.Desugar;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.blocks.TRBlocks;
import com.sabrepenguin.techreborn.blocks.meta.BlockMachineCasing;
import it.unimi.dsi.fastutil.chars.Char2CharArrayMap;
import it.unimi.dsi.fastutil.chars.Char2CharMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectArrayMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;


@Desugar
public record MultiblockStructure(char[][][] blocks, Char2ObjectMap<IBlockState> palette, BlockPos controllerOffset) { //TODO: Replace rotated identical


	public static MultiblockStructure[] DISTILLATION_TOWER;
	public static MultiblockStructure[] SAWMILL;
	public static MultiblockStructure[] INDUSTRIAL_BLAST_FURNACE;
	public static MultiblockStructure[] IMPLOSION_COMPRESSOR;
	public static MultiblockStructure[] VACUUM_FREEZER;
	public static MultiblockStructure[] FLUID_REPLICATOR;
	static {
		{
			Char2ObjectMap<IBlockState> DISTILLATION_MAP = new Char2ObjectArrayMap<>();
			DISTILLATION_MAP.put(' ', null);
			DISTILLATION_MAP.put('S', TRBlocks.machine_casing.getDefaultState()
					.withProperty(BlockMachineCasing.TYPE, BlockMachineCasing.Casing.STANDARD));
			DISTILLATION_MAP.put('A', TRBlocks.machine_casing.getDefaultState()
					.withProperty(BlockMachineCasing.TYPE, BlockMachineCasing.Casing.ADVANCED));
			DISTILLATION_MAP.put('E', Blocks.AIR.getDefaultState());
			DISTILLATION_MAP.put('C', null);
			Char2CharArrayMap swapMap = new Char2CharArrayMap();
			DISTILLATION_TOWER = createStruct(new char[][][]{
					{
							{'S', 'S', 'S'},
							{'S', 'S', 'S'},
							{'S', 'S', 'S'},
							{' ', 'C', ' '}
					},
					{
							{'A', 'A', 'A'},
							{'A', 'E', 'A'},
							{'A', 'A', 'A'},
							{' ', ' ', ' '}
					},
					{
							{'S', 'S', 'S'},
							{'S', 'E', 'S'},
							{'S', 'S', 'S'},
							{' ', ' ', ' '}
					},
					{
							{'A', 'A', 'A'},
							{'A', 'A', 'A'},
							{'A', 'A', 'A'},
							{' ', ' ', ' '}
					},
			}, DISTILLATION_MAP, swapMap, new BlockPos(1, 0, 3));
		}
		{
			Char2ObjectMap<IBlockState> SAWMILL_MAP = new Char2ObjectArrayMap<>();
			SAWMILL_MAP.put(' ', null);
			SAWMILL_MAP.put('S', TRBlocks.machine_casing.getDefaultState()
					.withProperty(BlockMachineCasing.TYPE, BlockMachineCasing.Casing.STANDARD));
			SAWMILL_MAP.put('A', TRBlocks.machine_casing.getDefaultState()
					.withProperty(BlockMachineCasing.TYPE, BlockMachineCasing.Casing.REINFORCED));
			SAWMILL_MAP.put('E', Blocks.AIR.getDefaultState());
			SAWMILL_MAP.put('C', null);
			Char2CharArrayMap swapMap = new Char2CharArrayMap();
			SAWMILL = createStruct(new char[][][]{
					{
							{'S', 'S', 'S'},
							{'S', 'S', 'S'},
							{'S', 'S', 'S'},
							{' ', ' ', ' '}
					},
					{
							{'A', 'A', 'A'},
							{'A', 'E', 'A'},
							{'A', 'A', 'A'},
							{' ', 'C', ' '}
					},
					{
							{'S', 'S', 'S'},
							{'S', 'S', 'S'},
							{'S', 'S', 'S'},
							{' ', ' ', ' '}
					}
			}, SAWMILL_MAP, swapMap, new BlockPos(1, 1, 3));
		}
		{
			Char2ObjectMap<IBlockState> BLAST_MAP = new Char2ObjectArrayMap<>();
			BLAST_MAP.put(' ', null);
			BLAST_MAP.put('S', TRBlocks.machine_casing.getDefaultState()
					.withProperty(BlockMachineCasing.TYPE, BlockMachineCasing.Casing.STANDARD));
			BLAST_MAP.put('E', Blocks.AIR.getDefaultState());
			BLAST_MAP.put('C', null);
			Char2CharArrayMap swapMap = new Char2CharArrayMap();
			INDUSTRIAL_BLAST_FURNACE = createStruct(new char[][][]{
					{
							{'S', 'S', 'S'},
							{'S', 'S', 'S'},
							{'S', 'S', 'S'},
							{' ', 'C', ' '}
					},
					{
							{'S', 'S', 'S'},
							{'S', 'E', 'S'},
							{'S', 'S', 'S'},
							{' ', ' ', ' '}
					},
					{
							{'S', 'S', 'S'},
							{'S', 'E', 'S'},
							{'S', 'S', 'S'},
							{' ', ' ', ' '}
					},
					{
							{'S', 'S', 'S'},
							{'S', 'S', 'S'},
							{'S', 'S', 'S'},
							{' ', ' ', ' '}
					},
			}, BLAST_MAP, swapMap, new BlockPos(1, 0, 3));
		}
		{
			Char2ObjectMap<IBlockState> IMPLOSION_MAP = new Char2ObjectArrayMap<>();
			IMPLOSION_MAP.put(' ', null);
			IMPLOSION_MAP.put('S', TRBlocks.machine_casing.getDefaultState()
					.withProperty(BlockMachineCasing.TYPE, BlockMachineCasing.Casing.REINFORCED));
			IMPLOSION_MAP.put('E', Blocks.AIR.getDefaultState());
			IMPLOSION_MAP.put('C', null);
			Char2CharArrayMap swapMap = new Char2CharArrayMap();
			IMPLOSION_COMPRESSOR = createStruct(new char[][][]{
					{
							{'S', 'S', 'S'},
							{'S', 'S', 'S'},
							{'S', 'S', 'S'}
					},
					{
							{'S', 'S', 'S'},
							{'S', 'E', 'S'},
							{'S', 'S', 'S'}
					},
					{
							{'S', 'S', 'S'},
							{'S', 'S', 'S'},
							{'S', 'S', 'S'}
					},
					{
							{' ', ' ', ' '},
							{' ', 'C', ' '},
							{' ', ' ', ' '}
					},
			}, IMPLOSION_MAP, swapMap, new BlockPos(1, 3, 1));
		}
		{
			Char2ObjectMap<IBlockState> IMPLOSION_MAP = new Char2ObjectArrayMap<>();
			IMPLOSION_MAP.put(' ', null);
			IMPLOSION_MAP.put('S', TRBlocks.machine_casing.getDefaultState()
					.withProperty(BlockMachineCasing.TYPE, BlockMachineCasing.Casing.REINFORCED));
			IMPLOSION_MAP.put('E', Blocks.AIR.getDefaultState());
			IMPLOSION_MAP.put('C', null);
			Char2CharArrayMap swapMap = new Char2CharArrayMap();
			VACUUM_FREEZER = createStruct(new char[][][]{
					{
							{'S', 'S', 'S'},
							{'S', 'S', 'S'},
							{'S', 'S', 'S'}
					},
					{
							{' ', ' ', ' '},
							{' ', 'C', ' '},
							{' ', ' ', ' '}
					},
			}, IMPLOSION_MAP, swapMap, new BlockPos(1, 1, 1));
		}
		{
			Char2ObjectMap<IBlockState> REPLICATOR_MAP = new Char2ObjectArrayMap<>();
			REPLICATOR_MAP.put(' ', null);
			REPLICATOR_MAP.put('S', TRBlocks.machine_casing.getDefaultState()
					.withProperty(BlockMachineCasing.TYPE, BlockMachineCasing.Casing.REINFORCED));
			REPLICATOR_MAP.put('E', Blocks.AIR.getDefaultState());
			REPLICATOR_MAP.put('C', null);
			Char2CharArrayMap swapMap = new Char2CharArrayMap();
			FLUID_REPLICATOR = createStruct(new char[][][]{
					{
							{'S', 'S', 'S'},
							{'S', ' ', 'S'},
							{'S', 'S', 'S'},
							{' ', 'C', ' '}
					}
			}, REPLICATOR_MAP, swapMap, new BlockPos(1, 0, 3));
		}
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
