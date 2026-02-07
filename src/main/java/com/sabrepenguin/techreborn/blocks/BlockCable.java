package com.sabrepenguin.techreborn.blocks;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.itemblock.IMetaInformation;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockCable extends Block implements INonStandardLocation, IMetaInformation {
	public static final PropertyEnum<CableEnum> TYPE = PropertyEnum.create("type", CableEnum.class);

	public BlockCable() {
		super(Material.ROCK);
		this.setHardness(1.0f);
		this.setResistance(8f);
		this.setRegistryName(Tags.MODID, "cable");
		this.setTranslationKey(Tags.MODID + ".cable");
		this.setDefaultState(this.getDefaultState().withProperty(TYPE, CableEnum.COPPER));
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return super.getPickBlock(state, target, world, pos, player);
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, CableEnum.META_MAP.get(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).metadata;
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(TYPE).metadata;
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (CableEnum cable: CableEnum.values()) {
			items.add(new ItemStack(this, 1, cable.metadata));
		}
	}

	@Override
	public List<Pair<String, Integer>> getMeta() {
		return Arrays.stream(CableEnum.values()).map(cable -> Pair.of(cable.getName(), cable.metadata)).collect(Collectors.toList());
	}

	@Override
	public String getName(ItemStack stack) {
		return CableEnum.META_MAP.get(stack.getMetadata()).getName();
	}

	public enum CableEnum implements IStringSerializable {
		COPPER(0),
		TIN(1),
		GOLD(2),
		HV(3),
		GLASSFIBER(4),
		ICOPPER(5),
		IGOLD(6),
		IHV(7),
		SUPERCONDUCTOR(8);

		final int metadata;

		static final Int2ObjectMap<CableEnum> META_MAP = new Int2ObjectOpenHashMap<>();

		static {
			for(CableEnum e: values()) META_MAP.put(e.metadata, e);
			META_MAP.defaultReturnValue(COPPER);
		}

		CableEnum(int metadata) {
			this.metadata = metadata;
		}

		public int metadata() {
			return metadata;
		}

		@Override
		public String getName() {
			return name().toLowerCase();
		}
	}
}
