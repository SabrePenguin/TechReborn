package com.sabrepenguin.techreborn.blocks.meta;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.itemblock.IMetaInformation;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BlockMachineFrame extends Block implements IMetaInformation, INonStandardLocation {
	public static final PropertyEnum<Frame> TYPE = PropertyEnum.create(
			"type", Frame.class);

	public BlockMachineFrame() {
		super(Material.IRON);
		setCreativeTab(TechReborn.RESOURCE_TAB);
		setHardness(1f);
		setRegistryName(Tags.MODID, "machine_frame");
		setTranslationKey(Tags.MODID + ".machine_frame");
		setDefaultState(this.getDefaultState().withProperty(TYPE, Frame.BASIC));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE);
	}

	@Override
	public int damageDropped(@NotNull IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).metadata;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, Frame.META_MAP.get(meta));
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (Frame ore: Frame.values()) {
			items.add(new ItemStack(this, 1, ore.meta()));
		}
	}

	@Override
	public String getPrefix() {
		return "machines/structure/";
	}

	@Override
	public boolean hasNonStandardOreDict() {
		return true;
	}

	@Override
	public String[] getNonStandardOreDict(int meta) {
		Frame frame = Frame.META_MAP.get(meta);
		return switch (frame) {
			case BASIC -> new String[] {"machineBlockBasic", "machineBasic"};
			case ADVANCED -> new String[] {"machineBlockAdvanced"};
			case HIGHLY_ADVANCED -> new String[] {"machineBlockHighlyAdvanced", "machineBlockElite"};
		};
	}

	@Override
	public String getName(ItemStack stack) {
		return Frame.META_MAP.get(stack.getMetadata()).getName();
	}

	@Override
	public IProperty<?>[] getProperties() {
		return new IProperty[] {TYPE};
	}

	public enum Frame implements IStringSerializable {
		BASIC(0),
		ADVANCED(1),
		HIGHLY_ADVANCED(2);

		final static Int2ObjectMap<Frame> META_MAP = new Int2ObjectOpenHashMap<>();

		static {
			for (Frame ore: values()) META_MAP.put(ore.metadata, ore);
			META_MAP.defaultReturnValue(BASIC);
		}

		final int metadata;
		final int blast_resistance;

		Frame(int metadata) {
			this(metadata, 0);
		}

		Frame(int metadata, int blast_resistance) {
			this.metadata = metadata;
			this.blast_resistance = blast_resistance;
		}

		public int meta() {
			return this.metadata;
		}

		@Override
		public String getName() {
			return name().toLowerCase();
		}
	}

	@Override
	public List<Pair<String, Integer>> getMeta() {
		return Arrays.stream(Frame.values()).map(frame -> Pair.of(frame.getName(), frame.metadata)).collect(Collectors.toList());
	}

	public Frame[] getOre() {
		return Frame.values();
	}
}
