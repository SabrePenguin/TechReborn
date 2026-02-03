package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockReinforcedGlass extends BlockGlass {
	public BlockReinforcedGlass() {
		super(Material.GLASS, false);
		this.setCreativeTab(TechReborn.RESOURCE_TAB);
		this.setRegistryName(Tags.MODID, "reinforced_glass");
		this.setTranslationKey(Tags.MODID + ".reinforced_glass");
		this.setSoundType(SoundType.STONE);
		this.setHardness(4.0f);
		this.setResistance(60.0f);
	}

	@Override
	public int quantityDropped(Random random) {
		return 1;
	}
}
