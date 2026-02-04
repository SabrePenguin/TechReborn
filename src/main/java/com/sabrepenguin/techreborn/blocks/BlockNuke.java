package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockNuke extends Block implements INonStandardLocation {

	public static final PropertyBool OVERLAY = PropertyBool.create("overlay");

	public BlockNuke() {
		super(Material.TNT);
		this.setCreativeTab(TechReborn.RESOURCE_TAB);
		this.setRegistryName(Tags.MODID, "nuke");
		this.setTranslationKey(Tags.MODID + ".nuke");
		this.setDefaultState(this.getDefaultState().withProperty(OVERLAY, false));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(OVERLAY, (meta & 1) == 1);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(OVERLAY) ? 1 : 0;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, OVERLAY);
	}

	@Override
	public IProperty<?>[] getProperties() {
		return new IProperty[] {OVERLAY};
	}
}
