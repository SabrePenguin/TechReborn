package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.worldgen.WorldGenRubberTrees;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BlockRubberSapling extends BlockSapling {
	public BlockRubberSapling() {
		this.setRegistryName(Tags.MODID, "rubber_sapling");
		this.setTranslationKey(Tags.MODID + ".rubber_sapling");
		this.setCreativeTab(TechReborn.RESOURCE_TAB);
		this.setDefaultState(this.getDefaultState().withProperty(STAGE, 0));
		this.setSoundType(SoundType.PLANT);
	}

	@Override
	public void generateTree(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @NotNull Random rand) {
		if (!TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;
		WorldGenerator generator = new WorldGenRubberTrees(true);
		worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
		generator.generate(worldIn, rand, pos);
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		items.add(new ItemStack(this, 1, 0));
	}
}
