package com.sabrepenguin.techreborn.items.materials;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Part implements IMetaMaterial {

	@Override
	public String getName(ItemStack stack) {
		return PartMeta.META_MAP.get(stack.getMetadata()).getName();
	}

	@Override
    public String getOreDict() {
        return "";
    }

    @Override
    public boolean hasNonStandardOreDict() {
        return true;
    }

    @Override
    public String[] getNonStandardOreDict(int meta) {
        String out = PartMeta.META_MAP.get(meta).getName();
        return switch (out) {
            case "energy_flow_circuit" -> new String[] {"circuitMaster"};
            case "data_control_circuit" -> new String[] {"circuitElite"};
            case "data_storage_circuit" -> new String[] {"circuitStorage"};
            case "electronic_circuit" -> new String[] {"circuitBasic"};
            case "advanced_circuit" -> new String[] {"circuitAdvanced"};

            case "neutron_reflector" -> new String[] {"reflectorBasic"};
            case "thick_neutron_reflector" -> new String[] {"reflectorThick"};

            case "diamond_grinding_head" -> new String[] {"craftingDiamondGrinder"};
            case "tungsten_grinding_head" -> new String[] {"craftingTungstenGrinder"};
            case "super_conductor" -> new String[] {"craftingSuperconductor"};

            case "sap" -> new String[] {"materialResin"};
            case "rubber" -> new String[] {"materialRubber", "itemRubber"};
            default -> new String[] {};
        };
    }

	public enum PartMeta implements IStringSerializable {
		energy_flow_circuit(0),
		data_control_circuit(1),
		data_storage_circuit(2),
		data_orb(3),
		diamond_grinding_head(4),
		diamond_saw_blade(5),
		tungsten_grinding_head(6),
		helium_coolant_simple(7),
		helium_coolant_triple(8),
		helium_coolant_six(9),
		nak_coolant_simple(10),
		nak_coolant_triple(11),
		nak_coolant_six(12),
		cupronickel_heating_coil(13),
		nichrome_heating_coil(14),
		kanthal_heating_coil(15),
		super_conductor(17),
		plutonium_cell(21),
		double_plutonium_cell(22),
		quad_plutonium_cell(23),
		computer_monitor(24),
		machine_parts(25),
		neutron_reflector(26),
		thick_neutron_reflector(28),
		electronic_circuit(29),
		advanced_circuit(30),
		sap(31),
		rubber(32),
		scrap(33),
		carbon_mesh(34),
		carbon_fiber(35),
		coolant_simple(36),
		coolant_triple(37),
		coolant_six(38),
		enhanced_super_conductor(39),
		basic_circuit_board(40),
		advanced_circuit_board(41),
		advanced_circuit_parts(42),
		processor_circuit_board(43),
		plantball(44),
		compressed_plantball(45),
		bio_cell(46);

		static final Int2ObjectMap<PartMeta> META_MAP = new Int2ObjectOpenHashMap<>();

		static {
			for (PartMeta part: values()) META_MAP.put(part.metadata, part);
			META_MAP.defaultReturnValue(energy_flow_circuit);
		}

		final int metadata;

		PartMeta(int metadata) {
			this.metadata = metadata;
		}

		@Override
		public @NotNull String getName() {
			return name();
		}
	}

	@Override
	public List<Pair<String, Integer>> getMeta() {
		return Arrays.stream(PartMeta.values()).map(part -> Pair.of(part.getName(), part.metadata)).collect(Collectors.toList());
	}
}
