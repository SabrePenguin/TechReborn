from resource.recipe_builders import RecipeMultiplier, CustomRecipeBuilder, Ingredient
from registries import *

def _ore_to_dust_grinder_recipe(name: str, ore: str, count: int):
    return (
        CustomRecipeBuilder(recipe_type="techreborn:grinder")
        .with_time(300)
        .with_energy(8)
        .with_input(Ingredient.oredict(name, oretype=ore))
        .with_output(Ingredient.meta(DUSTS, name, count=count))
        .build()
    )

custom_recipe_data = {
    "techreborn": {
        "alloy": {
            **RecipeMultiplier(
                "invar",
                CustomRecipeBuilder()
                .with_type("techreborn:alloy")
                .with_output(Ingredient.meta(INGOTS, "invar", count=3))
                .with_time(140)
                .with_energy(64),
            )
            .add_inputs(
                [
                    Ingredient.oredict("dustIron", count=2),
                    Ingredient.oredict("ingotIron", count=2),
                ]
            )
            .add_inputs(
                [
                    Ingredient.oredict("dustNickel"),
                    Ingredient.oredict("ingotNickel"),
                ]
            )
            .run_build(),
            **RecipeMultiplier(
                "electrum",
                CustomRecipeBuilder()
                .with_type("techreborn:alloy")
                .with_output(Ingredient.meta(INGOTS, "electrum", count=2))
                .with_time(100)
                .with_energy(64),
            )
            .add_inputs(
                [Ingredient.oredict("dustGold"), Ingredient.oredict("ingotGold")]
            )
            .add_inputs(
                [
                    Ingredient.oredict("dustSilver"),
                    Ingredient.oredict("ingotSilver"),
                ]
            )
            .run_build(),
            **RecipeMultiplier(
                "bronze",
                CustomRecipeBuilder()
                .with_type("techreborn:alloy")
                .with_output(Ingredient.meta(INGOTS, "bronze", count=4))
                .with_time(100)
                .with_energy(64),
            )
            .add_inputs(
                [
                    Ingredient.oredict("dustCopper", count=3),
                    Ingredient.oredict("ingotCopper", count=3),
                ]
            )
            .add_inputs(
                [
                    Ingredient.oredict("dustTin"),
                    Ingredient.oredict("ingotTin"),
                ]
            )
            .run_build(),
            **RecipeMultiplier(
                "brass",
                CustomRecipeBuilder()
                .with_type("techreborn:alloy")
                .with_output(Ingredient.meta(INGOTS, "brass", count=4))
                .with_time(200)
                .with_energy(64),
            )
            .add_inputs(
                [
                    Ingredient.oredict("dustCopper", count=3),
                    Ingredient.oredict("ingotCopper", count=3),
                ]
            )
            .add_inputs(
                [
                    Ingredient.oredict("dustZinc"),
                    Ingredient.oredict("ingotZinc"),
                ]
            )
            .run_build(),
        },
        "grinder": {
            "01_ore_coal": CustomRecipeBuilder(recipe_type="techreborn:grinder")
            .with_time(300)
            .with_energy(8)
            .with_input(Ingredient.oredict("oreCoal"))
            .with_output(Ingredient.item("coal", metadata=0, count=3))
            .build(),
            "02_ore_diamond": CustomRecipeBuilder(recipe_type="techreborn:grinder")
            .with_time(300)
            .with_energy(8)
            .with_input(Ingredient.oredict("oreDiamond"))
            .with_output(Ingredient.item("diamond", count=2))
            .build(),
            "03_ore_emerald": CustomRecipeBuilder(recipe_type="techreborn:grinder")
            .with_time(300)
            .with_energy(8)
            .with_input(Ingredient.oredict("oreEmerald"))
            .with_output(Ingredient.item("emerald", count=2))
            .build(),
            "04_ore_lapis": CustomRecipeBuilder(recipe_type="techreborn:grinder")
            .with_time(300)
            .with_energy(8)
            .with_input(Ingredient.oredict("oreLapis"))
            .with_output(Ingredient.item("dye", metadata=4, count=10))
            .build(),
            "05_ore_redstone": CustomRecipeBuilder(recipe_type="techreborn:grinder")
            .with_time(300)
            .with_energy(8)
            .with_input(Ingredient.oredict("oreRedstone"))
            .with_output(Ingredient.item("redstone", count=6))
            .build(),

            "06_ore_iron": _ore_to_dust_grinder_recipe("iron", "ore", 2),
            "07_ore_gold": _ore_to_dust_grinder_recipe("gold", "ore", 2),
            "10_ore_galena": _ore_to_dust_grinder_recipe("galena", "ore", 2),
            "10_ore_ruby": _ore_to_dust_grinder_recipe("ruby", "ore", 2),
            "10_ore_sapphire": _ore_to_dust_grinder_recipe("sapphire", "ore", 2),
            "10_ore_bauxite": _ore_to_dust_grinder_recipe("bauxite", "ore", 4),
            "10_ore_pyrite": _ore_to_dust_grinder_recipe("pyrite", "ore", 5),
            "10_ore_cinnabar": _ore_to_dust_grinder_recipe("cinnabar", "ore", 3),
            "10_ore_sphalerite": _ore_to_dust_grinder_recipe("sphalerite", "ore", 4),
            "10_ore_tungsten": _ore_to_dust_grinder_recipe("tungsten", "ore", 2),
            "10_ore_sheldonite": CustomRecipeBuilder(recipe_type="techreborn:grinder")
            .with_time(300)
            .with_energy(8)
            .with_input(Ingredient.oredict("oreSheldonite"))
            .with_output(Ingredient.meta(DUSTS, "platinum", 2))
            .build(),
            "10_ore_platinum": _ore_to_dust_grinder_recipe("platinum", "ore", 2),
            "10_ore_peridot": _ore_to_dust_grinder_recipe("peridot", "ore", 2),
            "10_ore_sodalite": _ore_to_dust_grinder_recipe("sodalite", "ore", 12),
            "10_ore_copper": _ore_to_dust_grinder_recipe("copper", "ore", 2),
            "10_ore_tin": _ore_to_dust_grinder_recipe("tin", "ore", 2),
            "10_ore_silver": _ore_to_dust_grinder_recipe("silver", "ore", 2),
            "10_ore_lead": _ore_to_dust_grinder_recipe("lead", "ore", 2),
            "30_sand": CustomRecipeBuilder(recipe_type="techreborn:grinder")
            .with_time(300)
            .with_energy(8)
            .with_input(Ingredient.item("cobblestone"))
            .with_output(Ingredient.item("sand", metadata=0))
            .build(),
            "30_endstone": CustomRecipeBuilder(recipe_type="techreborn:grinder")
            .with_time(300)
            .with_energy(8)
            .with_input(Ingredient.item("end_stone"))
            .with_output(Ingredient.meta(DUSTS, "endstone"))
            .build(),
            "30_glowstone": CustomRecipeBuilder(recipe_type="techreborn:grinder")
            .with_time(300)
            .with_energy(8)
            .with_input(Ingredient.item("glowstone"))
            .with_output(Ingredient.item("glowstone_dust", count=4))
            .build(),
            "30_netherrack": CustomRecipeBuilder(recipe_type="techreborn:grinder")
            .with_time(300)
            .with_energy(8)
            .with_input(Ingredient.item("netherrack"))
            .with_output(Ingredient.meta(DUSTS, "netherrack"))
            .build(),
            "30_obsidian": CustomRecipeBuilder(recipe_type="techreborn:grinder")
            .with_time(300)
            .with_energy(8)
            .with_input(Ingredient.item("obsidian"))
            .with_output(Ingredient.meta(DUSTS, "obsidian", count=4))
            .build(),
            "40_blaze": CustomRecipeBuilder(recipe_type="techreborn:grinder")
            .with_time(300)
            .with_energy(8)
            .with_input(Ingredient.item("blaze_rod"))
            .with_output(Ingredient.item("blaze_powder", count=4))
            .build(),
            "40_bone": CustomRecipeBuilder(recipe_type="techreborn:grinder")
            .with_time(300)
            .with_energy(8)
            .with_input(Ingredient.item("bone"))
            .with_output(Ingredient.item("dye", metadata=15, count=6))
            .build(),
            "40_clay": CustomRecipeBuilder(recipe_type="techreborn:grinder")
            .with_time(300)
            .with_energy(8)
            .with_input(Ingredient.item("clay_ball"))
            .with_output(Ingredient.meta(DUSTS, "clay"))
            .build(),
            "40_ender_pearl": CustomRecipeBuilder(recipe_type="techreborn:grinder")
            .with_time(300)
            .with_energy(8)
            .with_input(Ingredient.item("ender_pearl"))
            .with_output(Ingredient.meta(DUSTS, "ender_pearl", count=2))
            .build(),
            "40_ender_eye": CustomRecipeBuilder(recipe_type="techreborn:grinder")
            .with_time(300)
            .with_energy(8)
            .with_input(Ingredient.item("ender_eye"))
            .with_output(Ingredient.meta(DUSTS, "ender_eye", count=2))
            .build(),
            "40_flint": CustomRecipeBuilder(recipe_type="techreborn:grinder")
            .with_time(300)
            .with_energy(8)
            .with_input(Ingredient.item("gravel"))
            .with_output(Ingredient.item("flint"))
            .build(),
            "40_magma": CustomRecipeBuilder(recipe_type="techreborn:grinder")
            .with_time(300)
            .with_energy(8)
            .with_input(Ingredient.item("magma"))
            .with_output(Ingredient.item("magma_cream", count=4))
            .build(),
            "40_plantball": CustomRecipeBuilder(recipe_type="techreborn:grinder")
            .with_time(300)
            .with_energy(8)
            .with_input(Ingredient.meta(PARTS, "plantball"))
            .with_output(Ingredient.item("dirt", metadata=0))
            .build(),
        },
        "extractor": {},
        "plates": {},
        "recycler": {},
    },
}


