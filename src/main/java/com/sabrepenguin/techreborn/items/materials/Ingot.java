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

public class Ingot implements IMetaInformation {

	@Override
	public String getName(ItemStack stack) {
		return IngotMeta.META_MAP.get(stack.getMetadata()).getName();
	}

	@Override
    public String getOreDict() {
        return "ingot";
    }

	public enum IngotMeta implements IStringSerializable {
		aluminum(0),
		brass(1),
		bronze(2),
		chrome(3),
		copper(4),
		electrum(5),
		invar(6),
		iridium(7),
		lead(8),
		nickel(9),
		platinum(10),
		silver(11),
		steel(12),
		tin(13),
		titanium(14),
		tungsten(15),
		hot_tungstensteel(16),
		tungstensteel(17),
		zinc(18),
		refined_iron(19),
		advanced_alloy(20),
		mixed_metal(21),
		iridium_alloy(22),
		thorium(23),
		uranium(24),
		plutonium(25);

		static final Int2ObjectMap<IngotMeta> META_MAP = new Int2ObjectOpenHashMap<>();

		static {
			for (IngotMeta ingot: values()) META_MAP.put(ingot.metadata, ingot);
			META_MAP.defaultReturnValue(aluminum);
		}

		final int metadata;

		IngotMeta(int metadata) {
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
		return Arrays.stream(IngotMeta.values()).map(ingot -> Pair.of(ingot.getName(), ingot.metadata)).collect(Collectors.toList());
	}
}
