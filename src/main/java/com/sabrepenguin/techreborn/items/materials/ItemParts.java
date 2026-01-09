package com.sabrepenguin.techreborn.items.materials;

import com.google.common.base.CaseFormat;
import com.sabrepenguin.techreborn.Tags;
import com.sabrepenguin.techreborn.items.ItemBase;
import com.sabrepenguin.techreborn.items.TRItems;
import com.sabrepenguin.techreborn.util.MetadataHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.NotNull;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemParts extends ItemBase {
    public static final String[] types = new String[] {
            "energy_flow_circuit", "data_control_circuit",
            "data_storage_circuit",
            "data_orb", "diamond_grinding_head", "diamond_saw_blade", "tungsten_grinding_head", "helium_coolant_simple",
            "helium_coolant_triple", "helium_coolant_six", "nak_coolant_simple", "nak_coolant_triple",
            "nak_coolant_six",
            "cupronickel_heating_coil", "nichrome_heating_coil", "kanthal_heating_coil", MetadataHelper.META_PLACEHOLDER,
            "super_conductor",
            MetadataHelper.META_PLACEHOLDER, MetadataHelper.META_PLACEHOLDER, MetadataHelper.META_PLACEHOLDER, "plutonium_cell",
            "double_plutonium_cell",
            "quad_plutonium_cell", "computer_monitor", "machine_parts", "neutron_reflector", MetadataHelper.META_PLACEHOLDER,
            "thick_neutron_reflector", "electronic_circuit", "advanced_circuit", "sap", "rubber", "scrap",
            "carbon_mesh", "carbon_fiber", "coolant_simple", "coolant_triple", "coolant_six",
            "enhanced_super_conductor", "basic_circuit_board",
            "advanced_circuit_board", "advanced_circuit_parts", "processor_circuit_board", "plantball",
            "compressed_plantball", "bio_cell" };

    public static List<MetadataHelper> metaItems = new ArrayList<>();
    static {
        for(int i = 0; i < types.length; i++) {
            if (!types[i].equals(MetadataHelper.META_PLACEHOLDER)) {
                metaItems.add(new MetadataHelper(i, types[i]));
            }
        }
    }

    public ItemParts() {
        setTranslationKey(Tags.MODID  + ".part");
        setRegistryName(Tags.MODID, "part");
        setHasSubtypes(true);
    }

    @Override
    public String[] getTypes() {
        return types;
    }

    @Override
    public String getPrefix() {
        return "part/";
    }

    @Override
    public void registerOredict() {
        OreDictionary.registerOre("circuitMaster", getPartByName("energy_flow_circuit"));
        OreDictionary.registerOre("circuitElite", getPartByName("data_control_circuit"));
        OreDictionary.registerOre("circuitStorage", getPartByName("data_storage_circuit"));
        OreDictionary.registerOre("circuitBasic", getPartByName("electronic_circuit"));
        OreDictionary.registerOre("circuitAdvanced", getPartByName("advanced_circuit"));
        OreDictionary.registerOre("circuitStorage", getPartByName("data_storage_circuit"));
        OreDictionary.registerOre("circuitElite", getPartByName("data_control_circuit"));
        OreDictionary.registerOre("circuitMaster", getPartByName("energy_flow_circuit"));

        OreDictionary.registerOre("reflectorBasic", getPartByName("neutron_reflector"));
        OreDictionary.registerOre("reflectorThick", getPartByName("thick_neutron_reflector"));

        OreDictionary.registerOre("craftingDiamondGrinder", getPartByName("diamond_grinding_head"));
        OreDictionary.registerOre("craftingTungstenGrinder", getPartByName("tungsten_grinding_head"));
        OreDictionary.registerOre("craftingSuperconductor", getPartByName("super_conductor"));

        OreDictionary.registerOre("materialResin", getPartByName("sap"));
        OreDictionary.registerOre("materialRubber", getPartByName("rubber"));
        OreDictionary.registerOre("itemRubber", getPartByName("rubber"));

        OreDictionary.registerOre("itemScrap", getPartByName("scrap"));
        OreDictionary.registerOre("materialScrap", getPartByName("scrap"));
    }

    @Override
    public @NotNull String getTranslationKey(ItemStack stack) {
        int meta = stack.getMetadata();
        if (meta < 0 || meta >= types.length)
            meta = 0;
        return "item." + Tags.MODID + ".part." + types[meta];
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        items.addAll(
                metaItems.stream()
                        .map(item -> new ItemStack(this, 1, item.meta()))
                        .collect(Collectors.toList()));
    }

    public ItemStack getPartByName(String name) {
        for (int i = 0; i < types.length; i++) {
            if (types[i].equalsIgnoreCase(name)) {
                return new ItemStack(this, 1, i);
            }
        }
        throw new InvalidParameterException("The part " + name + " could not be found.");
    }
}
