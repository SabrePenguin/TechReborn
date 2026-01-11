package com.sabrepenguin.techreborn.items.materials;

import com.sabrepenguin.techreborn.items.MetadataItem;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Part implements IMaterial {

    private static final List<MetadataItem> ORDERED_ITEMS = new ArrayList<>();
    private static final Int2ObjectMap<MetadataItem> META = new Int2ObjectOpenHashMap<>();
    // I'll switch later if Sai is fine without metadata
    static {
        ORDERED_ITEMS.addAll(
                Arrays.asList(
                        new MetadataItem(0, "energy_flow_circuit"),
                        new MetadataItem(1, "data_control_circuit"),
                        new MetadataItem(2, "data_storage_circuit"),
                        new MetadataItem(3, "data_orb"),
                        new MetadataItem(4, "diamond_grinding_head"),
                        new MetadataItem(5, "diamond_saw_blade"),
                        new MetadataItem(6, "tungsten_grinding_head"),
                        new MetadataItem(7, "helium_coolant_simple"),
                        new MetadataItem(8, "helium_coolant_triple"),
                        new MetadataItem(9, "helium_coolant_six"),
                        new MetadataItem(10, "nak_coolant_simple"),
                        new MetadataItem(11, "nak_coolant_triple"),
                        new MetadataItem(12, "nak_coolant_six"),
                        new MetadataItem(13, "cupronickel_heating_coil"),
                        new MetadataItem(14, "nichrome_heating_coil"),
                        new MetadataItem(15, "kanthal_heating_coil"),
//                        new MetadataItem(16, "none"),
                        new MetadataItem(17, "super_conductor"),
//                        new MetadataItem(18, "none"),
//                        new MetadataItem(19, "none"),
//                        new MetadataItem(20, "none"),
                        new MetadataItem(21, "plutonium_cell"),
                        new MetadataItem(22, "double_plutonium_cell"),
                        new MetadataItem(23, "quad_plutonium_cell"),
                        new MetadataItem(24, "computer_monitor"),
                        new MetadataItem(25, "machine_parts"),
                        new MetadataItem(26, "neutron_reflector"),
//                        new MetadataItem(27, "none"),
                        new MetadataItem(28, "thick_neutron_reflector"),
                        new MetadataItem(29, "electronic_circuit"),
                        new MetadataItem(30, "advanced_circuit"),
                        new MetadataItem(31, "sap"),
                        new MetadataItem(32, "rubber"),
                        new MetadataItem(33, "scrap"),
                        new MetadataItem(34, "carbon_mesh"),
                        new MetadataItem(35, "carbon_fiber"),
                        new MetadataItem(36, "coolant_simple"),
                        new MetadataItem(37, "coolant_triple"),
                        new MetadataItem(38, "coolant_six"),
                        new MetadataItem(39, "enhanced_super_conductor"),
                        new MetadataItem(40, "basic_circuit_board"),
                        new MetadataItem(41, "advanced_circuit_board"),
                        new MetadataItem(42, "advanced_circuit_parts"),
                        new MetadataItem(43, "processor_circuit_board"),
                        new MetadataItem(44, "plantball"),
                        new MetadataItem(45, "compressed_plantball"),
                        new MetadataItem(46, "bio_cell")
                )
        );
        for (MetadataItem item: ORDERED_ITEMS) {
            META.put(item.meta(), item);
        }
    }


    public static void addSmallDust(String name, int metadata) {
        if (!META.containsKey(metadata))
            META.put(metadata, new MetadataItem(metadata, name));
    }

    @Override
    public String getNameFromMeta(int meta) {
        if (META.containsKey(meta)) {
            return META.get(meta).name();
        }
        return "";
    }

    @Override
    public Collection<MetadataItem> getItems() {
        return META.values();
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
    public List<MetadataItem> getOrderedItems() {
        return ORDERED_ITEMS;
    }
 //        OreDictionary.registerOre("circuitBasic", getPartByName("electronic_circuit"));
 //        OreDictionary.registerOre("circuitAdvanced", getPartByName("advanced_circuit"));
 //        OreDictionary.registerOre("circuitStorage", getPartByName("data_storage_circuit"));
 //        OreDictionary.registerOre("circuitElite", getPartByName("data_control_circuit"));
 //        OreDictionary.registerOre("circuitMaster", getPartByName("energy_flow_circuit"));
 //
 //        OreDictionary.registerOre("reflectorBasic", getPartByName("neutron_reflector"));
 //        OreDictionary.registerOre("reflectorThick", getPartByName("thick_neutron_reflector"));
 //
 //        OreDictionary.registerOre("craftingDiamondGrinder", getPartByName("diamond_grinding_head"));
 //        OreDictionary.registerOre("craftingTungstenGrinder", getPartByName("tungsten_grinding_head"));
 //        OreDictionary.registerOre("craftingSuperconductor", getPartByName("super_conductor"));
 //
 //        OreDictionary.registerOre("materialResin", getPartByName("sap"));
 //        OreDictionary.registerOre("materialRubber", getPartByName("rubber"));
 //        OreDictionary.registerOre("itemRubber", getPartByName("rubber"));
 //
 //        OreDictionary.registerOre("itemScrap", getPartByName("scrap"));
 //        OreDictionary.registerOre("materialScrap", getPartByName("scrap"));
    @Override
    public String getOreDict() {
        return "";
//        return switch (out) {
//            case "energy_flow_circuit" -> new String[] {"circuitMaster"};
//            case "data_control_circuit" -> new String[] {"circuitElite"};
//            case "data_storage_circuit" -> new String[] {"circuitStorage"};
//            case "electronic_circuit" -> new String[] {"circuitBasic"};
//            case "advanced_circuit" -> new String[] {"circuitAdvanced"};
//
//            case "neutron_reflector" -> new String[] {"reflectorBasic"};
//            case "thick_neutron_reflector" -> new String[] {"reflectorThick"};
//
//            case "diamond_grinding_head" -> new String[] {"craftingDiamondGrinder"};
//            case "tungsten_grinding_head" -> new String[] {"craftingTungstenGrinder"};
//            case "super_conductor" -> new String[] {"craftingSuperconductor"};
//
//            case "sap" -> new String[] {"materialResin"};
//            case "rubber" -> new String[] {"materialRubber", "itemRubber"};
//            default -> new String[] {};
//        };
    }

    @Override
    public boolean hasNonStandardOreDict() {
        return true;
    }

    @Override
    public String[] getNonStandardOreDict(int meta) {
        String out = getNameFromMeta(meta);
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
}
