package com.sabrepenguin.techreborn.items.materials;

import com.sabrepenguin.techreborn.itemblock.IEnumMeta;
import com.sabrepenguin.techreborn.items.MetadataItem;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class Dust implements IMaterial, IEnumMeta {

    // I'll switch later if Sai is fine without metadata
    private static final List<MetadataItem> ORDERED_ITEMS = new ArrayList<>();
    private static final Int2ObjectMap<MetadataItem> META = new Int2ObjectOpenHashMap<>();
    static {
        ORDERED_ITEMS.addAll(
                Arrays.asList(
                        new MetadataItem(0, "almandine"),
                        new MetadataItem(1, "aluminum"),
                        new MetadataItem(2, "andradite"),
                        new MetadataItem(3, "ashes"),
                        new MetadataItem(4, "basalt"),
                        new MetadataItem(5, "bauxite"),
                        new MetadataItem(6, "brass"),
                        new MetadataItem(7, "bronze"),
                        new MetadataItem(8, "calcite"),
                        new MetadataItem(9, "charcoal"),
                        new MetadataItem(10, "chrome"),
                        new MetadataItem(11, "cinnabar"),
                        new MetadataItem(12, "clay"),
                        new MetadataItem(13, "coal"),
                        new MetadataItem(14, "copper"),
                        new MetadataItem(15, "dark_ashes"),
                        new MetadataItem(16, "diamond"),
                        new MetadataItem(17, "electrum"),
                        new MetadataItem(18, "emerald"),
                        new MetadataItem(19, "ender_eye"),
                        new MetadataItem(20, "ender_pearl"),
                        new MetadataItem(21, "endstone"),
                        new MetadataItem(22, "flint"),
                        new MetadataItem(23, "galena"),
                        new MetadataItem(24, "gold"),
                        new MetadataItem(25, "grossular"),
                        new MetadataItem(26, "invar"),
                        new MetadataItem(27, "iron"),
                        new MetadataItem(28, "lazurite"),
                        new MetadataItem(29, "lead"),
                        new MetadataItem(30, "magnesium"),
                        new MetadataItem(31, "manganese"),
                        new MetadataItem(32, "marble"),
                        new MetadataItem(33, "netherrack"),
                        new MetadataItem(34, "nickel"),
                        new MetadataItem(35, "obsidian"),
                        new MetadataItem(36, "peridot"),
                        new MetadataItem(37, "phosphorous"),
                        new MetadataItem(38, "platinum"),
                        new MetadataItem(39, "pyrite"),
                        new MetadataItem(40, "pyrope"),
                        new MetadataItem(41, "red_garnet"),
//                      new MetadataItem(0, "none"),
                        new MetadataItem(43, "ruby"),
                        new MetadataItem(44, "saltpeter"),
                        new MetadataItem(45, "sapphire"),
                        new MetadataItem(46, "saw_dust"),
                        new MetadataItem(47, "silver"),
                        new MetadataItem(48, "sodalite"),
                        new MetadataItem(49, "spessartine"),
                        new MetadataItem(50, "sphalerite"),
                        new MetadataItem(51, "steel"),
                        new MetadataItem(52, "sulfur"),
                        new MetadataItem(53, "tin"),
                        new MetadataItem(54, "titanium"),
                        new MetadataItem(55, "tungsten"),
                        new MetadataItem(56, "uvarovite"),
//                      new MetadataItem(0, "none"),
                        new MetadataItem(58, "yellow_garnet"),
                        new MetadataItem(59, "zinc"),
//                      new MetadataItem(0, "none"),
                        new MetadataItem(61, "andesite"),
                        new MetadataItem(62, "diorite"),
                        new MetadataItem(63, "granite"),
                        new MetadataItem(64, "iridium"),
                        new MetadataItem(65, "thorium"),
                        new MetadataItem(66, "uranium"),
                        new MetadataItem(67, "plutonium")
                )
        );
        for (MetadataItem item: ORDERED_ITEMS) {
            META.put(item.meta(), item);
        }
    }

    @Override
    public Collection<MetadataItem> getItems() {
        return META.values();
    }

    @Override
    public List<MetadataItem> getOrderedItems() {
        return ORDERED_ITEMS;
    }

    @Override
    public String getNameFromMeta(int meta) {
		return MetaDust.META_MAP.get(meta).getName();
    }

    @Override
    public int getMetaFromName(String name) {
        for(MetadataItem item: META.values()) {
            if (item.name().equals(name))
                return item.meta();
        }
        return 0;
    }

    @Override
    public String getOreDict() {
        return "dust";
    }

	@Override
	public String getName(ItemStack stack) {
		return MetaDust.META_MAP.get(stack.getMetadata()).getName();
	}

	private enum MetaDust implements IStringSerializable {
		almandine(0),
		aluminum(1),
		andradite(2),
		ashes(3),
		basalt(4),
		bauxite(5),
		brass(6),
		bronze(7),
		calcite(8),
		charcoal(9),
		chrome(10),
		cinnabar(11),
		clay(12),
		coal(13),
		copper(14),
		dark_ashes(15),
		diamond(16),
		electrum(17),
		emerald(18),
		ender_eye(19),
		ender_pearl(20),
		endstone(21),
		flint(22),
		galena(23),
		gold(24),
		grossular(25),
		invar(26),
		iron(27),
		lazurite(28),
		lead(29),
		magnesium(30),
		manganese(31),
		marble(32),
		netherrack(33),
		nickel(34),
		obsidian(35),
		peridot(36),
		phosphorous(37),
		platinum(38),
		pyrite(39),
		pyrope(40),
		red_garnet(41),
		ruby(43),
		saltpeter(44),
		sapphire(45),
		saw_dust(46),
		silver(47),
		sodalite(48),
		spessartine(49),
		sphalerite(50),
		steel(51),
		sulfur(52),
		tin(53),
		titanium(54),
		tungsten(55),
		uvarovite(56),
		yellow_garnet(58),
		zinc(59),
		andesite(61),
		diorite(62),
		granite(63),
		iridium(64),
		thorium(65),
		uranium(66),
		plutonium(67);

		final static Int2ObjectMap<MetaDust> META_MAP = new Int2ObjectOpenHashMap<>();

		static {
			for (MetaDust dust: values()) META_MAP.put(dust.metadata, dust);
			META_MAP.defaultReturnValue(almandine);
		}

		final int metadata;

		MetaDust(int metadata) {
			this.metadata = metadata;
		}

		@Override
		public @NotNull String getName() {
			return name();
		}
	}

	@Override
	public List<Pair<String, Integer>> getMeta() {
		return Arrays.stream(MetaDust.values()).map(dust -> Pair.of(dust.getName(), dust.metadata)).collect(Collectors.toList());
	}
}
