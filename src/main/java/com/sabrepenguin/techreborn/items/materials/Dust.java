package com.sabrepenguin.techreborn.items.materials;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class Dust implements IMetaMaterial {

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
