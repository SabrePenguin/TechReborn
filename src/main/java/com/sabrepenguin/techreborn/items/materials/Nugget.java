package com.sabrepenguin.techreborn.items.materials;

import com.sabrepenguin.techreborn.itemblock.IMetaMaterial;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Nugget implements IMetaMaterial {

	@Override
	public String getName(ItemStack stack) {
		return NuggetMeta.META_MAP.get(stack.getMetadata()).getName();
	}

	@Override
    public String getOreDict() {
        return "nugget";
    }

	public enum NuggetMeta implements IStringSerializable {
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
		iron(23),
		diamond(24);

		static final Int2ObjectMap<NuggetMeta> META_MAP = new Int2ObjectOpenHashMap<>();

		static {
			for (NuggetMeta nugget: values()) META_MAP.put(nugget.metadata, nugget);
			META_MAP.defaultReturnValue(aluminum);
		}

		final int metadata;

		NuggetMeta(int metadata) {
			this.metadata = metadata;
		}


		@Override
		public @NotNull String getName() {
			return name();
		}
	}

	@Override
	public List<Pair<String, Integer>> getMeta() {
		return Arrays.stream(NuggetMeta.values()).map(nugget -> Pair.of(nugget.getName(), nugget.metadata)).collect(Collectors.toList());
	}
}
