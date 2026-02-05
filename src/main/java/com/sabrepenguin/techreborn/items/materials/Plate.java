package com.sabrepenguin.techreborn.items.materials;

import com.sabrepenguin.techreborn.itemblock.IMetaInformation;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Plate implements IMetaInformation {

	@Override
	public String getName(ItemStack stack) {
		return PlateMeta.META_MAP.get(stack.getMetadata()).getName();
	}

	@Override
    public String getOreDict() {
        return "plate";
    }

	public enum PlateMeta implements IStringSerializable {
		iron(0),
		gold(1),
		carbon(2),
		wood(3),
		redstone(4),
		diamond(5),
		emerald(6),
		coal(8),
		obsidian(9),
		lazurite(10),
		silicon(11),
		// Gems
		ruby(12),
		sapphire(13),
		peridot(14),
		red_garnet(15),
		yellow_garnet(16),
		// Ingots
		aluminum(17),
		brass(18),
		bronze(19),
		chrome(20),
		copper(21),
		electrum(22),
		invar(23),
		iridium(24),
		lead(25),
		nickel(26),
		platinum(27),
		silver(28),
		steel(29),
		tin(30),
		titanium(31),
		tungsten(32),
		tungstensteel(33),
		zinc(34),
		refined_iron(35),
		advanced_alloy(36),
		magnalium(37),
		iridium_alloy(38);

		static final Int2ObjectMap<PlateMeta> META_MAP = new Int2ObjectOpenHashMap<>();

		static {
			for (PlateMeta plate: values()) META_MAP.put(plate.metadata, plate);
			META_MAP.defaultReturnValue(iron);
		}

		final int metadata;

		PlateMeta(int metadata) {
			this.metadata = metadata;
		}

		public int metadata() {
			return this.metadata;
		}

		@Override
		public @NotNull String getName() {
			return name();
		}
	}

	@Override
	public List<Pair<String, Integer>> getMeta() {
		return Arrays.stream(PlateMeta.values()).map(plate -> Pair.of(plate.getName(), plate.metadata)).collect(Collectors.toList());
	}
}
