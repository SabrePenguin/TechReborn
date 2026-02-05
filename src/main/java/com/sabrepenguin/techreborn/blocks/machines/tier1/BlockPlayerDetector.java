package com.sabrepenguin.techreborn.blocks.machines.tier1;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.itemblock.IMetaInformation;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BlockPlayerDetector extends Block implements INonStandardLocation, IMetaInformation {
	public static final PropertyEnum<PlayerEnum> PLAYER = PropertyEnum.create("type", PlayerEnum.class);

	public BlockPlayerDetector() {
		super(Material.IRON);
		this.setCreativeTab(TechReborn.RESOURCE_TAB);
		this.setRegistryName(Tags.MODID, "player_detector");
		this.setTranslationKey(Tags.MODID + ".player_detector");
		this.setHardness(2.0f);
		this.setSoundType(SoundType.METAL);
		this.setDefaultState(this.getDefaultState().withProperty(PLAYER, PlayerEnum.ALL));
	}

	@Override
	public String getPrefix() {
		return "machines/tier1";
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, PLAYER);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(PLAYER, PlayerEnum.META_MAP.get(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(PLAYER).metadata;
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for(PlayerEnum player: PlayerEnum.values()) {
			items.add(new ItemStack(this, 1, player.metadata));
		}
	}

	@Override
	public String getName(ItemStack stack) {
		return PlayerEnum.META_MAP.get(stack.getMetadata()).getName();
	}

	public enum PlayerEnum implements IStringSerializable {
		ALL(0),
		OTHERS(1),
		YOU(2);

		final static Int2ObjectMap<PlayerEnum> META_MAP = new Int2ObjectOpenHashMap<>();

		static {
			for(PlayerEnum e: values()) META_MAP.put(e.metadata, e);
			META_MAP.defaultReturnValue(ALL);
		}

		final int metadata;

		PlayerEnum(int metadata) {
			this.metadata = metadata;
		}


		@Override
		public String getName() {
			return name().toLowerCase();
		}
	}

	@Override
	public List<Pair<String, Integer>> getMeta() {
		return Arrays.stream(PlayerEnum.values()).map(player -> Pair.of(player.getName(), player.metadata)).collect(Collectors.toList());
	}
}
