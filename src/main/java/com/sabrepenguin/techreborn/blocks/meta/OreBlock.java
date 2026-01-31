package com.sabrepenguin.techreborn.blocks.meta;

import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.TechReborn;
import com.sabrepenguin.techreborn.itemblock.IMetaMaterial;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.items.materials.Dust;
import com.sabrepenguin.techreborn.items.materials.Gem;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class OreBlock extends Block implements IMetaMaterial, INonStandardLocation {
	public static final PropertyEnum<Ore> TYPE = PropertyEnum.create(
			"type", Ore.class);

	public OreBlock() {
		super(Material.IRON);
		setCreativeTab(TechReborn.RESOURCE_TAB);
		setHardness(2f);
		setRegistryName(Tags.MODID, "ore");
		setTranslationKey(Tags.MODID + ".ore");
		setHarvestLevel("pickaxe", 2);
		setDefaultState(this.getDefaultState().withProperty(TYPE, Ore.GALENA));
	}

	@Override
	public String getOreDict() {
		return "ore";
	}

	@Override
	protected @NotNull BlockStateContainer createBlockState() {
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
	public @NotNull IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, Ore.META_MAP.get(meta));
	}

	@Override
	public void getSubBlocks(@NotNull CreativeTabs itemIn, @NotNull NonNullList<ItemStack> items) {
		for (Ore ore: Ore.values()) {
			items.add(new ItemStack(this, 1, ore.meta()));
		}
	}

	@Override
	protected boolean canSilkHarvest() {
		return true;
	}

	@Override
	public void getDrops(@NotNull NonNullList<ItemStack> drops, @NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull IBlockState state, int fortune) {
		Ore ore = state.getValue(TYPE);
		if (!useFortune(ore)) {
			drops.add(new ItemStack(Item.getItemFromBlock(this), 1, ore.metadata));
			return;
		}

		Random rand = world instanceof World
				? ((World) world).rand
				: RANDOM;

		Item item;
		if (ore == Ore.RUBY || ore == Ore.SAPPHIRE)
			item = TRItems.gem;
		else
			item = TRItems.dust;

		int metadata = switch (ore) {
			case RUBY -> Gem.GemMeta.ruby.metadata();
			case SAPPHIRE -> Gem.GemMeta.sapphire.metadata();
			case PYRITE -> Dust.MetaDust.pyrite.metadata();
			case SPHALERITE -> Dust.MetaDust.sphalerite.metadata();
			case CINNABAR -> Dust.MetaDust.cinnabar.metadata();
			case SODALITE -> Dust.MetaDust.sodalite.metadata();
			default -> throw new IllegalStateException("Unexpected value: " + ore);
		};
		int count = 1;
		if (fortune > 0) {
			int bonus = rand.nextInt(fortune + 2) - 1;
			if (bonus < 0) bonus = 0;
			count += bonus;
		}

		drops.add(new ItemStack(item, count, metadata));
	}

	@Override
	public int getExpDrop(IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos, int fortune) {
		Ore ore = state.getValue(TYPE);
		if (!useFortune(ore)) return 0;
		Random rand = world instanceof World
				? ((World) world).rand
				: RANDOM;
		return MathHelper.getInt(rand, 1, 4);
	}

	private boolean useFortune(Ore ore) {
		return ore == Ore.RUBY || ore == Ore.SAPPHIRE || ore == Ore.PYRITE || ore == Ore.CINNABAR || ore == Ore.SODALITE || ore == Ore.SPHALERITE;
	}

	@Override
	public String getName(ItemStack stack) {
		return Ore.META_MAP.get(stack.getMetadata()).getName();
	}

	@Override
	public IProperty<?>[] getProperties() {
		return new IProperty[] {TYPE};
	}

	public enum Ore implements IStringSerializable {
		GALENA(0),
		IRIDIUM(1),
		RUBY(2),
		SAPPHIRE(3),
		BAUXITE(4),
		PYRITE(5),
		CINNABAR(6),
		SPHALERITE(7),
		TUNGSTEN(8),
		SHELDONITE(9),
		PERIDOT(10),
		SODALITE(11),
		LEAD(12),
		SILVER(13);

		final static Int2ObjectMap<Ore> META_MAP = new Int2ObjectOpenHashMap<>();

		static {
			for (Ore ore: values()) META_MAP.put(ore.metadata, ore);
			META_MAP.defaultReturnValue(GALENA);
		}

		final int metadata;
		final int blast_resistance;

		Ore(int metadata) {
			this(metadata, 0);
		}

		Ore(int metadata, int blast_resistance) {
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
		return Arrays.stream(Ore.values()).map(ore -> Pair.of(ore.getName(), ore.metadata)).collect(Collectors.toList());
	}

	public Ore[] getOre() {
		return Ore.values();
	}
}
