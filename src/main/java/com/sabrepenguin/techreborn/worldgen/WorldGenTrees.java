package com.sabrepenguin.techreborn.worldgen;

import com.sabrepenguin.techreborn.worldgen.generators.WorldGenRubberTrees;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGenTrees implements IWorldGenerator {
	private final WorldGenAbstractTree rubberTree = new WorldGenRubberTrees(true);

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.getDimension()) {
			case 0:
				generateTree(rubberTree, random, chunkX, chunkZ, world, Blocks.GRASS);
				break;
			default:
				break;
		}
	}

	private void generateTree(WorldGenAbstractTree tree, Random rand, int chunkX, int chunkZ, World world, Block topBlock) {
		int chance = 75; // TODO: Config
		int clusterSize = 5; // TODO: Config
		boolean isValidSpawn = false;
		int x = (chunkX * 16) + rand.nextInt(15);
		int z = (chunkZ * 16) + rand.nextInt(15);
		int y = calculateGenerationHeight(world, x, world.getHeight(x, z), 52, z, topBlock);
//		int y = world.getHeight(x, z); // TODO: Config
		if (52 > y || y > 72)
			return;
		BlockPos pos = new BlockPos(x, y, z);
		Biome biome = world.provider.getBiomeForCoords(pos);
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SWAMP)) {
			chance -= rand.nextInt(10) + 10;
			isValidSpawn = true;
		}
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST)) {
			chance -= rand.nextInt(5) + 3;
			isValidSpawn = true;
		}
		if (!isValidSpawn)
			return;
		if (chance <= 0)
			chance = 1;
		if (rand.nextInt(chance) == 0) {
			for (int i = 0; i < clusterSize; i++) {
				int xOffset = rand.nextInt(12) - 6;
				int zOffset = rand.nextInt(12) - 6;
				BlockPos offset = world.getHeight(pos.add(xOffset, 0, zOffset));
				tree.generate(world, rand, offset);
			}
		}
	}

	private static int calculateGenerationHeight(World world, int x, int initialY, int minY, int z, Block topBlock)
	{
		int y = initialY;
		boolean foundGround = false;

		for(; !foundGround && y >= minY; y--)
		{
			Block block = world.getBlockState(new BlockPos(x,y,z)).getBlock();
			foundGround = block == topBlock;
		}

		return y;
	}
}
