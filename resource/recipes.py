from pathlib import Path

from registries import *
from resource.file import write_to_file
from resource.recipe_builders import (
    ShapedRecipeBuilder,
    ShapelessRecipeBuilder,
    Ingredient,
)
from resource.recipe_data import custom_recipe_data

MODID = "techreborn"
BASE = Path("src/main/resources/assets/techreborn")
RECIPES = BASE.joinpath("recipes")
CUSTOM_RECIPES = BASE.joinpath("trrecipes")


def _capitalize_oredict(ore_in: str) -> str:
    words: list[str] = ore_in.split("_")
    out = ""
    for word in words:
        out = out + word.capitalize()
    return out


def dust_craft_creation():
    dust_crafts = RECIPES.joinpath("dust")
    for input_name in SMALL_DUSTS.keys():
        fname = dust_crafts.joinpath(f"{input_name}_dust.json")
        recipe = (
            ShapedRecipeBuilder()
            .with_pattern(["XX", "XX"])
            .with_ingredient("X", Ingredient.oredict(input_name, "dustSmall"))
        )
        if input_name != "glowstone" and input_name != "redstone":
            recipe = recipe.with_output(Ingredient.meta(DUSTS, input_name))
        else:
            if input_name == "glowstone":
                input_name = "glowstone_dust"
            recipe = recipe.with_output(Ingredient.item(input_name))
        write_to_file(fname, recipe.build())


def smalldust_craft_creation():
    smalldust_crafts = RECIPES.joinpath("smalldust")
    for input_name in DUSTS.keys():
        fname = smalldust_crafts.joinpath(f"{input_name}_smalldust.json")
        recipe = (
            ShapelessRecipeBuilder()
            .with_input(Ingredient.oredict(input_name, "dust"))
            .with_output(Ingredient.meta(SMALL_DUSTS, input_name, count=4))
        )
        write_to_file(fname, recipe.build())
    write_to_file(
        smalldust_crafts.joinpath("glowstone_smalldust.json"),
        ShapelessRecipeBuilder()
        .with_input(Ingredient.oredict("dustGlowstone"))
        .with_output(Ingredient.meta(SMALL_DUSTS, "glowstone", count=4))
        .build(),
    )
    write_to_file(
        smalldust_crafts.joinpath("redstone_smalldust.json"),
        ShapelessRecipeBuilder()
        .with_input(Ingredient.oredict("dustRedstone"))
        .with_output(Ingredient.meta(SMALL_DUSTS, "redstone", count=4))
        .build(),
    )


def nugget_craft_creation():
    nugget_crafts = RECIPES.joinpath("nugget")
    fname = nugget_crafts.joinpath(f"iron_nugget.json")
    recipe = (
        ShapelessRecipeBuilder()
        .with_input(Ingredient.oredict("ingotIron"))
        .with_output(Ingredient.meta(NUGGETS, "iron", count=9))
    )
    write_to_file(fname, recipe.build())

    fname = nugget_crafts.joinpath(f"diamond_nugget.json")
    recipe = (
        ShapelessRecipeBuilder()
        .with_input(Ingredient.oredict("gemDiamond"))
        .with_output(Ingredient.meta(NUGGETS, "diamond", count=9))
    )
    write_to_file(fname, recipe.build())


def ingotn_craft_creation():
    ingot_crafts = RECIPES.joinpath("ingot")
    fname = ingot_crafts.joinpath(f"iron_ingotn.json")
    pattern = ["XXX", "XXX", "XXX"]
    recipe = (
        ShapedRecipeBuilder()
        .with_pattern(pattern)
        .with_ingredient("X", Ingredient.oredict("nuggetIron"))
        .with_output(Ingredient.item("iron_ingot"))
    )
    write_to_file(fname, recipe.build())


def generic_single_craft_creation(
    base_path: str,
    registry: dict,
    target_registry: dict,
    post_fix: str,
    ore_dict: str,
    count: int,
):
    base_crafts = RECIPES.joinpath(base_path)
    for name in registry.keys():
        if name not in target_registry:
            continue
        fname = base_crafts.joinpath(f"{name}_{post_fix}.json")
        recipe = (
            ShapelessRecipeBuilder()
            .with_input(Ingredient.oredict(name, ore_dict))
            .with_output(Ingredient.meta(target_registry, name, count=count))
        )
        write_to_file(fname, recipe.build())


def generic_3x3_craft_creation(
    base_path: str,
    registry: dict,
    target_registry: dict,
    post_fix: str,
    ore_dict: str,
    count: int = 1,
):
    base_crafts = RECIPES.joinpath(base_path)
    for name in registry.keys():
        if name not in target_registry:
            continue
        fname = base_crafts.joinpath(f"{name}_{post_fix}.json")
        recipe = (
            ShapedRecipeBuilder()
            .with_pattern(["XXX", "XXX", "XXX"])
            .with_ingredient("X", Ingredient.oredict(name, oretype=ore_dict))
            .with_output(Ingredient.meta(target_registry, name, count=count))
        )
        write_to_file(fname, recipe.build())


def gem_craft_creation():
    gem_crafts = RECIPES.joinpath("gem")
    fname = gem_crafts.joinpath("diamond_gemn.json")
    recipe = (
        ShapedRecipeBuilder()
        .with_pattern(["XXX", "XXX", "XXX"])
        .with_ingredient("X", Ingredient.oredict("nuggetDiamond"))
        .with_output(Ingredient.item("diamond"))
    )
    write_to_file(fname, recipe.build())


def standard_recipes():
    dust_craft_creation()
    smalldust_craft_creation()
    nugget_craft_creation()
    generic_single_craft_creation("nugget", INGOTS, NUGGETS, "nugget", "ingot", 9)

    ingotn_craft_creation()
    generic_3x3_craft_creation("ingot", NUGGETS, INGOTS, "ingotn", "nugget")
    generic_single_craft_creation("ingot", BLOCK_STORAGE, INGOTS, "ingotb", "block", 9)
    generic_single_craft_creation("ingot", BLOCK_STORAGE2, INGOTS, "ingotb", "block", 9)

    generic_single_craft_creation("gem", BLOCK_STORAGE2, GEM, "gemb", "block", 9)
    gem_craft_creation()

    generic_3x3_craft_creation("block", GEM, BLOCK_STORAGE2, "block", "gem")
    generic_3x3_craft_creation("block", INGOTS, BLOCK_STORAGE2, "block", "ingot")
    generic_3x3_craft_creation("block", INGOTS, BLOCK_STORAGE, "block", "ingot")

    parts_recipes()
    misc_recipes()
    machine_recipes()

    custom_recipes()

def misc_recipes():
    recipes = {
        "rubber_stairs_r": (
            ShapedRecipeBuilder()
            .with_pattern(["X  ","XX ","XXX"])
            .with_ingredient("X", Ingredient.item("rubber_planks", modid="techreborn"))
            .with_output(Ingredient.item("rubber_plank_stair", modid="techreborn", count=4))
        ),
        "rubber_stairs_l": (
            ShapedRecipeBuilder()
            .with_pattern(["  X"," XX","XXX"])
            .with_ingredient("X", Ingredient.item("rubber_planks", modid="techreborn"))
            .with_output(Ingredient.item("rubber_plank_stair", modid="techreborn", count=4))
        ),
        # "rubber_slab": ( # TODO: Fix slabs
        #     ShapedRecipeBuilder()
        #     .with_pattern(["XXX"])
        #     .with_ingredient("X", Ingredient.item("rubber_planks", modid="techreborn"))
        #     .with_output(Ingredient.item("rubber_plank_double_slab", modid="techreborn", count=6))
        # )
    }

    base_name = RECIPES.joinpath("misc")

    for recipe_name, recipe in recipes.items():
        fname = base_name.joinpath(f"{recipe_name}.json")
        write_to_file(fname, recipe.build())

def parts_recipes():
    standard = {
        "energy_flow_circuit": (
            ShapedRecipeBuilder()
            .with_pattern(["CTC", " P ", "CTC"])  # TODO: Add crystals
            .with_ingredient("C", Ingredient.oredict("circuitAdvanced"))
            .with_ingredient("T", Ingredient.oredict("ingotTungsten"))
            .with_ingredient("P", Ingredient.oredict("plateIridiumAlloy"))
            .with_output(Ingredient.meta(PARTS, "energy_flow_circuit", count=4))
        ),
        "data_control_circuit": (
            ShapedRecipeBuilder()
            .with_pattern(["CTC", "TPT", "CTC"])
            .with_ingredient("C", Ingredient.oredict("circuitAdvanced"))
            .with_ingredient("T", Ingredient.oredict("ingotTungsten"))
            .with_ingredient("P", Ingredient.oredict("plateIridiumAlloy"))
            .with_output(Ingredient.meta(PARTS, "data_control_circuit"))
        ),
        "data_storage_circuit": (
            ShapedRecipeBuilder()
            .with_pattern(["RGR", "LCL", "PPP"])
            .with_ingredient("R", Ingredient.item("redstone"))
            .with_ingredient("G", Ingredient.item("glowstone_dust"))
            .with_ingredient("L", Ingredient.item("dye", metadata=4))
            .with_ingredient("C", Ingredient.oredict("circuitBasic"))
            .with_ingredient("P", Ingredient.oredict("plateEmerald"))
            .with_output(Ingredient.meta(PARTS, "data_storage_circuit"))
        ),
        "data_orb": (
            ShapedRecipeBuilder()
            .with_pattern(["XXX", "XOX", "XXX"])
            .with_ingredient("X", Ingredient.oredict("circuitStorage"))
            .with_ingredient("O", Ingredient.oredict("circuitElite"))
            .with_output(Ingredient.meta(PARTS, "data_orb"))
        ),
        "diamond_grinding_head": (
            ShapedRecipeBuilder()
            .with_pattern(["DSD", "SCS", "DSD"])
            .with_ingredient("D", Ingredient.oredict("dustDiamond"))
            .with_ingredient("S", Ingredient.oredict("ingotSteel"))
            .with_ingredient("C", Ingredient.item("diamond"))
            .with_output(Ingredient.meta(PARTS, "diamond_grinding_head", count=2))
        ),
        "diamond_saw_blade": (
            ShapedRecipeBuilder()
            .with_pattern(["DSD", "S S", "DSD"])
            .with_ingredient("D", Ingredient.oredict("dustDiamond"))
            .with_ingredient("S", Ingredient.oredict("ingotSteel"))
            .with_output(Ingredient.meta(PARTS, "diamond_saw_blade", count=4))
        ),
        "tungsten_grinding_head": (
            ShapedRecipeBuilder()
            .with_pattern(["TST", "SCS", "TST"])
            .with_ingredient("T", Ingredient.oredict("ingotTungsten"))
            .with_ingredient("S", Ingredient.oredict("ingotSteel"))
            .with_ingredient("C", Ingredient.oredict("blockSteel"))
            .with_output(Ingredient.meta(PARTS, "tungsten_grinding_head", count=2))
        ),
        "helium_coolant_simple": (
            ShapedRecipeBuilder()
            .with_pattern([" T ", "T T", " T "])  # TODO: Add helium cell
            .with_ingredient("T", Ingredient.oredict("ingotTin"))
            .with_output(Ingredient.meta(PARTS, "helium_coolant_simple"))
        ),
        "helium_coolant_triple": (
            ShapedRecipeBuilder()
            .with_pattern(["TTT", "HHH", "TTT"])
            .with_ingredient("T", Ingredient.oredict("ingotTin"))
            .with_ingredient("H", Ingredient.meta(PARTS, "helium_coolant_simple"))
            .with_output(Ingredient.meta(PARTS, "helium_coolant_triple"))
        ),
        "helium_coolant_six": (
            ShapedRecipeBuilder()
            .with_pattern(["THT", "TCT", "THT"])
            .with_ingredient("T", Ingredient.oredict("ingotTin"))
            .with_ingredient("H", Ingredient.meta(PARTS, "helium_coolant_triple"))
            .with_ingredient("C", Ingredient.oredict("ingotCopper"))
            .with_output(Ingredient.meta(PARTS, "helium_coolant_six"))
        ),
        "nak_coolant_simple_v": (  # TODO: Add fluid cells + rotate
            ShapedRecipeBuilder()
            .with_pattern(["T T", "   ", "T T"])
            .with_ingredient("T", Ingredient.oredict("ingotTin"))
            .with_output(Ingredient.meta(PARTS, "nak_coolant_simple"))
        ),
        "nak_coolant_triple": (
            ShapedRecipeBuilder()
            .with_pattern(["TTT", "NNN", "TTT"])
            .with_ingredient("T", Ingredient.oredict("ingotTin"))
            .with_ingredient("N", Ingredient.meta(PARTS, "nak_coolant_simple"))
            .with_output(Ingredient.meta(PARTS, "nak_coolant_triple"))
        ),
        "nak_coolant_six": (
            ShapedRecipeBuilder()
            .with_pattern(["TNT", "TCT", "TNT"])
            .with_ingredient("T", Ingredient.oredict("ingotTin"))
            .with_ingredient("N", Ingredient.meta(PARTS, "nak_coolant_triple"))
            .with_ingredient("C", Ingredient.oredict("ingotCopper"))
            .with_output(Ingredient.meta(PARTS, "nak_coolant_six"))
        ),
        "superconductor": (
            ShapedRecipeBuilder()
            .with_pattern(["HHH", "TIT", "EEE"])
            .with_ingredient("H", Ingredient.meta(PARTS, "helium_coolant_simple"))
            .with_ingredient("T", Ingredient.oredict("ingotTungsten"))
            .with_ingredient("I", Ingredient.oredict("plateIridiumAlloy"))
            .with_ingredient("E", Ingredient.meta(PARTS, "energy_flow_circuit"))
            .with_output(Ingredient.meta(PARTS, "super_conductor", count=4))
        ),
        "computer_monitor": (
            ShapedRecipeBuilder()
            .with_pattern(["ADA", "DGD", "ADA"])
            .with_ingredient("A", Ingredient.oredict("ingotAluminum"))
            .with_ingredient("D", Ingredient.oredict("dye"))
            .with_ingredient("G", Ingredient.oredict("paneGlass"))
            .with_output(Ingredient.meta(PARTS, "computer_monitor"))
        ),
        "neutron_reflector": (  # TODO: IC2
            ShapedRecipeBuilder()
            .with_pattern(["TCT", "CPC", "TCT"])
            .with_ingredient("T", Ingredient.oredict("dustTin"))
            .with_ingredient("C", Ingredient.oredict("dustCoal"))
            .with_ingredient("P", Ingredient.oredict("plateCopper"))
            .with_output(Ingredient.meta(PARTS, "neutron_reflector"))
        ),
        "thick_neutron_reflector": (  # TODO: IC2
            ShapedRecipeBuilder()
            .with_pattern([" N ", "N N", " N "])  # TODO: Add beryllium
            .with_ingredient("N", Ingredient.oredict("reflectorBasic"))
            .with_output(Ingredient.meta(PARTS, "thick_neutron_reflector"))
        ),
        "basic_circuit": (  # TODO: IC2
            ShapedRecipeBuilder()
            .with_pattern(["   ", "RIR", "   "])  # TODO: Add cables
            .with_ingredient("R", Ingredient.item("redstone"))
            .with_ingredient("I", Ingredient.oredict("ingotRefinedIron"))
            .with_output(Ingredient.meta(PARTS, "electronic_circuit"))
        ),
        "advanced_circuit": (  # TODO: IC2
            ShapedRecipeBuilder()
            .with_pattern(["RGR", "LCL", "RGR"])
            .with_ingredient("R", Ingredient.item("redstone"))
            .with_ingredient("G", Ingredient.item("glowstone_dust"))
            .with_ingredient("L", Ingredient.item("dye", metadata=4))
            .with_ingredient("C", Ingredient.oredict("circuitBasic"))
            .with_output(Ingredient.meta(PARTS, "advanced_circuit"))
        ),
        "carbon_fiber_dust": (  # TODO: IC2
            ShapedRecipeBuilder()
            .with_pattern([" C ", "C C", " C "])
            .with_ingredient("C", Ingredient.oredict("dustCoal"))
            .with_output(Ingredient.meta(PARTS, "carbon_fiber"))
        ),
        # "carbon_fiber_cell": ( # TODO: IC2
        #     ShapedRecipeBuilder()
        # ),
        "carbon_mesh": (
            ShapedRecipeBuilder()
            .with_pattern(["XX"])
            .with_ingredient("X", Ingredient.meta(PARTS, "carbon_fiber"))
            .with_output(Ingredient.meta(PARTS, "carbon_mesh"))
        ),
        "coolant_simple": (
            ShapedRecipeBuilder()
            .with_type("techreborn:shaped_fluid")
            .with_pattern([" T ", "TWT", " T "])
            .with_ingredient("T", Ingredient.oredict("ingotTin"))
            .with_ingredient("W", Ingredient.fluid("water"))
            .with_output(Ingredient.meta(PARTS, "coolant_simple", count=2))
        ),
        "coolant_simple_cell": (
            ShapedRecipeBuilder()
            .with_type("techreborn:shaped_fluid")
            .with_pattern([" T ", "TWT", " T "])
            .with_ingredient("T", Ingredient.oredict("ingotTin"))
            .with_ingredient("W", Ingredient.cell("water"))
            .with_output(Ingredient.meta(PARTS, "coolant_simple", count=2))
        ),
        "coolant_triple": (
            ShapedRecipeBuilder()
            .with_pattern(["TTT", "NNN", "TTT"])
            .with_ingredient("T", Ingredient.oredict("ingotTin"))
            .with_ingredient("N", Ingredient.meta(PARTS, "coolant_simple"))
            .with_output(Ingredient.meta(PARTS, "coolant_triple"))
        ),
        "coolant_six": (
            ShapedRecipeBuilder()
            .with_pattern(["TNT", "TCT", "TNT"])
            .with_ingredient("T", Ingredient.oredict("ingotTin"))
            .with_ingredient("N", Ingredient.meta(PARTS, "coolant_triple"))
            .with_ingredient("C", Ingredient.oredict("plateCopper"))
            .with_output(Ingredient.meta(PARTS, "coolant_six"))
        ),
        "cell": (
            ShapedRecipeBuilder()
            .with_pattern([" T ","TPT", " T "])
            .with_ingredient("T", Ingredient.oredict("ingotTin"))
            .with_ingredient("P", Ingredient.oredict("paneGlass"))
            .with_output(Ingredient.mod_item("cell", count=16))
        )
    }

    base_name = RECIPES.joinpath("generated")

    for recipe_name, recipe in standard.items():
        fname = base_name.joinpath(f"{recipe_name}.json")
        write_to_file(fname, recipe.build())

def machine_recipes():
    recipes = {
        "simple": {
            "iron_furnace": (
                ShapedRecipeBuilder()
                .with_pattern(["XXX","X X","XXX"])
                .with_ingredient("X", Ingredient.oredict("ingotIron"))
                .with_output(Ingredient.item("iron_furnace", modid="techreborn"))
            ),
            "iron_furnace_upgrade": (
                ShapedRecipeBuilder()
                .with_pattern([" I ","I I", "IFI"])
                .with_ingredient("I", Ingredient.oredict("ingotIron"))
                .with_ingredient("F", Ingredient.item("furnace"))
                .with_output(Ingredient.item("iron_furnace", modid="techreborn"))
            ),
            "iron_alloy_smelter": (
                ShapedRecipeBuilder()
                .with_pattern(["RRR","F F","RRR"])
                .with_ingredient("R", Ingredient.oredict("ingotRefinedIron"))
                .with_ingredient("F", Ingredient.item("iron_furnace", modid="techreborn"))
                .with_output(Ingredient.item("iron_alloy_furnace", modid="techreborn"))
            )
        },
        "processing": {
            "electric_furnace": (
                ShapedRecipeBuilder()
                .with_pattern([" C ", "RFR"])
                .with_ingredient("C", Ingredient.oredict("circuitBasic"))
                .with_ingredient("R", Ingredient.item("redstone"))
                .with_ingredient("F", Ingredient.item("iron_furnace", modid="techreborn"))
                .with_output(Ingredient.item("electric_furnace", modid="techreborn"))
            ),
            "alloy_smelter": (
                ShapedRecipeBuilder()
                .with_pattern([" C ", "FMF"])
                .with_ingredient("C", Ingredient.oredict("circuitBasic"))
                .with_ingredient("F", Ingredient.item("iron_furnace", modid="techreborn"))
                .with_ingredient("M", Ingredient.meta(MACHINE_FRAME, "basic"))
                .with_output(Ingredient.item("alloy_smelter", modid="techreborn"))
            ),
            "grinder": (
                ShapedRecipeBuilder()
                .with_pattern(["FFF", "CMC", " B "])
                .with_ingredient("B", Ingredient.oredict("circuitBasic"))
                .with_ingredient("M", Ingredient.meta(MACHINE_FRAME, "basic"))
                .with_ingredient("F", Ingredient.item("flint"))
                .with_ingredient("C", Ingredient.item("cobblestone"))
                .with_output(Ingredient.item("grinder", modid="techreborn"))
            )
        }
    }
    base_name = RECIPES.joinpath("machines")
    for subfolder, recipe_data in recipes.items():
        folder_name = base_name.joinpath(subfolder)
        for recipe_name, recipe in recipe_data.items():
            fname = folder_name.joinpath(f"{recipe_name}.json")
            write_to_file(fname, recipe.build())

def custom_recipes():
    for mod_name, recipe_info in custom_recipe_data.items():
        base_name = CUSTOM_RECIPES.joinpath(mod_name)
        for recipe_type, recipes in recipe_info.items():
            recipe_folder = base_name.joinpath(recipe_type)
            for recipe_name, recipe in recipes.items():
                recipe_file = recipe_folder.joinpath(f"{recipe_name}.json")
                write_to_file(recipe_file, recipe)
