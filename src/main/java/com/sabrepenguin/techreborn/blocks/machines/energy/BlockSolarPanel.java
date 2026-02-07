package com.sabrepenguin.techreborn.blocks.machines.energy;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.itemblock.IMetaInformation;
import com.sabrepenguin.techreborn.util.INonStandardLocation;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
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
public class BlockSolarPanel extends Block implements INonStandardLocation, IMetaInformation {
	public static final PropertyEnum<SolarPanelEnum> TYPE = PropertyEnum.create("type", SolarPanelEnum.class);
	public static final PropertyBool ACTIVE = PropertyBool.create("active");

	public BlockSolarPanel() {
		super(Material.IRON);
		this.setCreativeTab(TechReborn.RESOURCE_TAB);
		this.setHardness(2.0f);
		this.setRegistryName(Tags.MODID, "solar_panel");
		this.setTranslationKey(Tags.MODID + ".solar_panel");
		this.setDefaultState(this.getDefaultState().withProperty(TYPE, SolarPanelEnum.BASIC).withProperty(ACTIVE, false));
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(TYPE).metadata;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE, ACTIVE);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		boolean on = false;
		if (meta >= 8) {
			meta -= 8;
			on = true;
		}
		return this.getDefaultState().withProperty(TYPE, SolarPanelEnum.META_MAP.get(meta)).withProperty(ACTIVE, on);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = state.getValue(ACTIVE) ? 8 : 0;
		return state.getValue(TYPE).metadata + meta;
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (SolarPanelEnum solarPanel: SolarPanelEnum.values()) {
			items.add(new ItemStack(this, 1, solarPanel.metadata));
		}
	}

	@Override
	public String getPrefix() {
		return "machines/generators";
	}

	@Override
	public IProperty<?>[] getProperties() {
		return new IProperty[] {TYPE, ACTIVE};
	}

	@Override
	public String getName(ItemStack stack) {
		return SolarPanelEnum.META_MAP.get(stack.getMetadata()).getName();
	}

	@Override
	public List<Pair<String, Integer>> getMeta() {
		return Arrays.stream(SolarPanelEnum.values()).map(panel -> Pair.of(panel.getName(), panel.metadata)).collect(Collectors.toList());
	}

	public enum SolarPanelEnum implements IStringSerializable {
		BASIC(0),
		HYBRID(1),
		ADVANCED(2),
		ULTIMATE(3),
		QUANTUM(4);

		final static Int2ObjectMap<SolarPanelEnum> META_MAP = new Int2ObjectOpenHashMap<>();

		static {
			for (SolarPanelEnum e: values()) META_MAP.put(e.metadata, e);
			META_MAP.defaultReturnValue(BASIC);
		}

		final int metadata;

		SolarPanelEnum(int metadata) {
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
