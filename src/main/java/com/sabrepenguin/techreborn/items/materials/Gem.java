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

public class Gem implements IMetaInformation {

	@Override
	public String getName(ItemStack stack) {
		return GemMeta.META_MAP.get(stack.getMetadata()).getName();
	}

	@Override
    public String getOreDict() {
        return "gem";
    }

	public enum GemMeta implements IStringSerializable {
		ruby(0),
		sapphire(1),
		peridot(2),
		red_garnet(3),
		yellow_garnet(4);

		final static Int2ObjectMap<GemMeta> META_MAP = new Int2ObjectOpenHashMap<>();

		static {
			for (GemMeta gem: values()) META_MAP.put(gem.metadata, gem);
			META_MAP.defaultReturnValue(ruby);
		}

		final int metadata;

		GemMeta(int metadata) {
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
		return Arrays.stream(GemMeta.values()).map(gem -> Pair.of(gem.getName(), gem.metadata)).collect(Collectors.toList());
	}
}
