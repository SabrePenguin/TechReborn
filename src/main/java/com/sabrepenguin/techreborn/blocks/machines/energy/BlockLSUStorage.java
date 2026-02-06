package com.sabrepenguin.techreborn.blocks.machines.energy;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockLSUStorage extends Block implements INonStandardLocation {
	public BlockLSUStorage() {
		super(Material.IRON);
		this.setHardness(1.0f);
		this.setCreativeTab(TechReborn.RESOURCE_TAB);
		this.setRegistryName(Tags.MODID, "lsu_storage");
		this.setTranslationKey(Tags.MODID + ".lsu_storage");
	}

	@Override
	public String getPrefix() {
		return "machines/energy";
	}

	@Override
	public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		return this == world.getBlockState(pos).getBlock();
	}
}
