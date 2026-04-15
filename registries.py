def get_name_for_registry(registry: dict | list) -> str | None:
    if registry == INGOTS:
        return "ingot"
    elif registry == ORES:
        return "ore"
    elif registry == NUGGETS:
        return "nuggets"
    elif registry == DUSTS:
        return "dust"
    elif registry == SMALL_DUSTS:
        return "smalldust"
    elif registry == BLOCK_STORAGE:
        return "storage"
    elif registry == BLOCK_STORAGE2:
        return "storage2"
    elif registry == GEM:
        return "gem"
    elif registry == PARTS:
        return "part"
    elif registry == PLATES:
        return "plates"
    elif registry == UPGRADES:
        return "upgrades"
    elif registry == MACHINE_FRAME:
        return "machine_frame"
    elif registry == CABLES:
        return "cable"
    return None


INGOTS = {
    "aluminum": 0,
    "brass": 1,
    "bronze": 2,
    "chrome": 3,
    "copper": 4,
    "electrum": 5,
    "invar": 6,
    "iridium": 7,
    "lead": 8,
    "nickel": 9,
    "platinum": 10,
    "silver": 11,
    "steel": 12,
    "tin": 13,
    "titanium": 14,
    "tungsten": 15,
    "hot_tungstensteel": 16,
    "tungstensteel": 17,
    "zinc": 18,
    "refined_iron": 19,
    "advanced_alloy": 20,
    "mixed_metal": 21,
    "iridium_alloy": 22,
    "thorium": 23,
    "uranium": 24,
    "plutonium": 25,
    # "cupro_nickel": 26,
}
ORES = {
    "galena": 0,
    "iridium": 1,
    "ruby": 2,
    "sapphire": 3,
    "bauxite": 4,
    "pyrite": 5,
    "cinnabar": 6,
    "sphalerite": 7,
    "tungsten": 8,
    "sheldonite": 9,
    "peridot": 10,
    "sodalite": 11,
    "lead": 12,
    "silver": 13,
}
NUGGETS = {
    "aluminum": 0,
    "brass": 1,
    "bronze": 2,
    "chrome": 3,
    "copper": 4,
    "electrum": 5,
    "invar": 6,
    "iridium": 7,
    "lead": 8,
    "nickel": 9,
    "platinum": 10,
    "silver": 11,
    "steel": 12,
    "tin": 13,
    "titanium": 14,
    "tungsten": 15,
    "hot_tungstensteel": 16,
    "tungstensteel": 17,
    "zinc": 18,
    "refined_iron": 19,
    "iron": 23,
    "diamond": 24,
}
DUSTS = {
    "almandine": 0,
    "aluminum": 1,
    "andradite": 2,
    "ashes": 3,
    "basalt": 4,
    "bauxite": 5,
    "brass": 6,
    "bronze": 7,
    "calcite": 8,
    "charcoal": 9,
    "chrome": 10,
    "cinnabar": 11,
    "clay": 12,
    "coal": 13,
    "copper": 14,
    "dark_ashes": 15,
    "diamond": 16,
    "electrum": 17,
    "emerald": 18,
    "ender_eye": 19,
    "ender_pearl": 20,
    "endstone": 21,
    "flint": 22,
    "galena": 23,
    "gold": 24,
    "grossular": 25,
    "invar": 26,
    "iron": 27,
    "lazurite": 28,
    "lead": 29,
    "magnesium": 30,
    "manganese": 31,
    "marble": 32,
    "netherrack": 33,
    "nickel": 34,
    "obsidian": 35,
    "peridot": 36,
    "phosphorous": 37,
    "platinum": 38,
    "pyrite": 39,
    "pyrope": 40,
    "red_garnet": 41,
    "ruby": 43,
    "saltpeter": 44,
    "sapphire": 45,
    "saw_dust": 46,
    "silver": 47,
    "sodalite": 48,
    "spessartine": 49,
    "sphalerite": 50,
    "steel": 51,
    "sulfur": 52,
    "tin": 53,
    "titanium": 54,
    "tungsten": 55,
    "uvarovite": 56,
    "yellow_garnet": 58,
    "zinc": 59,
    "andesite": 61,
    "diorite": 62,
    "granite": 63,
    "iridium": 64,
    "thorium": 65,
    "uranium": 66,
    "plutonium": 67,
}
SMALL_DUSTS = {
    "almandine": 0,
    "aluminum": 1,
    "andradite": 2,
    "ashes": 3,
    "basalt": 4,
    "bauxite": 5,
    "brass": 6,
    "bronze": 7,
    "calcite": 8,
    "charcoal": 9,
    "chrome": 10,
    "cinnabar": 11,
    "clay": 12,
    "coal": 13,
    "copper": 14,
    "dark_ashes": 15,
    "diamond": 16,
    "electrum": 17,
    "emerald": 18,
    "ender_eye": 19,
    "ender_pearl": 20,
    "endstone": 21,
    "flint": 22,
    "galena": 23,
    "gold": 24,
    "grossular": 25,
    "invar": 26,
    "iron": 27,
    "lazurite": 28,
    "lead": 29,
    "magnesium": 30,
    "manganese": 31,
    "marble": 32,
    "netherrack": 33,
    "nickel": 34,
    "obsidian": 35,
    "peridot": 36,
    "phosphorous": 37,
    "platinum": 38,
    "pyrite": 39,
    "pyrope": 40,
    "red_garnet": 41,
    "ruby": 43,
    "saltpeter": 44,
    "sapphire": 45,
    "saw_dust": 46,
    "silver": 47,
    "sodalite": 48,
    "spessartine": 49,
    "sphalerite": 50,
    "steel": 51,
    "sulfur": 52,
    "tin": 53,
    "titanium": 54,
    "tungsten": 55,
    "uvarovite": 56,
    # META_PLACEHOLDER: 57,
    "yellow_garnet": 58,
    "zinc": 59,
    # META_PLACEHOLDER: 60
    "redstone": 61,
    "glowstone": 62,
    "andesite": 63,
    "diorite": 64,
    "granite": 65,
    "iridium": 66,
    "thorium": 67,
    "uranium": 68,
    "plutonium": 69,
}
BLOCK_STORAGE = {
    "silver": 0,
    "aluminum": 1,
    "titanium": 2,
    "chrome": 3,
    "steel": 4,
    "brass": 5,
    "lead": 6,
    "electrum": 7,
    "zinc": 8,
    "platinum": 9,
    "tungsten": 10,
    "nickel": 11,
    "invar": 12,
    "iridium": 13,
    "bronze": 14,
}
BLOCK_STORAGE2 = {
    "tungstensteel": 0,
    "iridium_reinforced_tungstensteel": 1,
    "iridium_reinforced_stone": 2,
    "ruby": 3,
    "sapphire": 4,
    "peridot": 5,
    "yellow_garnet": 6,
    "red_garnet": 7,
    "copper": 8,
    "tin": 9,
    "refined_iron": 10,
}
GEM = {"ruby": 0, "sapphire": 1, "peridot": 2, "red_garnet": 3, "yellow_garnet": 4}
PARTS = {
    "energy_flow_circuit": 0,
    "data_control_circuit": 1,
    "data_storage_circuit": 2,
    "data_orb": 3,
    "diamond_grinding_head": 4,
    "diamond_saw_blade": 5,
    "tungsten_grinding_head": 6,
    "helium_coolant_simple": 7,
    "helium_coolant_triple": 8,
    "helium_coolant_six": 9,
    "nak_coolant_simple": 10,
    "nak_coolant_triple": 11,
    "nak_coolant_six": 12,
    "cupronickel_heating_coil": 13,
    "nichrome_heating_coil": 14,
    "kanthal_heating_coil": 15,
    "super_conductor": 17,
    "plutonium_cell": 21,
    "double_plutonium_cell": 22,
    "quad_plutonium_cell": 23,
    "computer_monitor": 24,
    "machine_parts": 25,
    "neutron_reflector": 26,
    "thick_neutron_reflector": 28,
    "electronic_circuit": 29,
    "advanced_circuit": 30,
    "sap": 31,
    "rubber": 32,
    "scrap": 33,
    "carbon_mesh": 34,
    "carbon_fiber": 35,
    "coolant_simple": 36,
    "coolant_triple": 37,
    "coolant_six": 38,
    "enhanced_super_conductor": 39,
    "basic_circuit_board": 40,
    "advanced_circuit_board": 41,
    "advanced_circuit_parts": 42,
    "processor_circuit_board": 43,
    "plantball": 44,
    "compressed_plantball": 45,
    "bio_cell": 46,
}
PLATES = {
    "iron": 0,
    "gold": 1,
    "carbon": 2,
    "wood": 3,
    "redstone": 4,
    "diamond": 5,
    "emerald": 6,
    "coal": 8,
    "obsidian": 9,
    "lazurite": 10,
    "silicon": 11,
    # Gems
    "ruby": 12,
    "sapphire": 13,
    "peridot": 14,
    "red_garnet": 15,
    "yellow_garnet": 16,
    # Ingots
    "aluminum": 17,
    "brass": 18,
    "bronze": 19,
    "chrome": 20,
    "copper": 21,
    "electrum": 22,
    "invar": 23,
    "iridium": 24,
    "lead": 25,
    "nickel": 26,
    "platinum": 27,
    "silver": 28,
    "steel": 29,
    "tin": 30,
    "titanium": 31,
    "tungsten": 32,
    "tungstensteel": 33,
    "zinc": 34,
    "refined_iron": 35,
    "advanced_alloy": 36,
    "magnalium": 37,
    "iridium_alloy": 38,
}
UPGRADES = {
    "overclock": 0,
    "transformer": 1,
    "energy_storage": 2,
    "superconductor": 3,
}
MACHINE_FRAME = {
    "basic": 0,
    "advanced": 1,
    "highly_advanced": 2
}
CABLES = {
    "copper": 0,
    "tin": 1,
    "gold": 2,
    "hv": 3,
    "glass_fiber": 4,
    "insulated_copper": 5,
    "insulated_gold": 6,
    "insulated_hv": 7,
    "superconductor": 8
}

BLOCKS = ["rubber_leaves", "rubber_planks"]

FLAT_BLOCKS = ["rubber_sapling"]

MISC = ["uu_matter"]

TOOLS = [
    "bronze",
    "ruby",
    "peridot",
    "sapphire",
]

SINGLE_TOOLS = ["wrench", "treetap", "configurinator"]

ARMOR = ["cloaking_device", "lapotronic_orbpack", "lithium_batpack"]

##################################
#                                #
#   Smelting Recipe Registries   #
#                                #
##################################

SMELTABLE_DUSTS = [
    "brass",
    "bronze",
    "copper",
    "electrum",
    "gold",
    "invar",
    "iron",
    "lead",
    "nickel",
    "silver",
    "tin",
    "zinc",
]

SMELTABLE_ORES = ["lead", "silver"]

SMELTABLE_ORES2 = ["copper", "tin"]
