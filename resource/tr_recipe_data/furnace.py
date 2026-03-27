from registries import *
from resource.recipe_builders import Ingredient, FurnaceRecipeBuilder


def get_furnace_recipes() -> dict[str, str]:
    return {
        "brass_dust": FurnaceRecipeBuilder()
        .with_input(Ingredient.meta(DUSTS, "brass"))
        .with_output(Ingredient.meta(INGOTS, "brass"))
        .build(),
        "bronze_dust": FurnaceRecipeBuilder()
        .with_input(Ingredient.meta(DUSTS, "bronze"))
        .with_output(Ingredient.meta(INGOTS, "bronze"))
        .build(),
        "copper_dust": FurnaceRecipeBuilder()
        .with_input(Ingredient.meta(DUSTS, "copper"))
        .with_output(Ingredient.meta(INGOTS, "copper"))
        .build(),
        "electrum_dust": FurnaceRecipeBuilder()
        .with_input(Ingredient.meta(DUSTS, "electrum"))
        .with_output(Ingredient.meta(INGOTS, "electrum"))
        .build(),
        "invar_dust": FurnaceRecipeBuilder()
        .with_input(Ingredient.meta(DUSTS, "invar"))
        .with_output(Ingredient.meta(INGOTS, "invar"))
        .build(),
        "lead_dust": FurnaceRecipeBuilder()
        .with_input(Ingredient.meta(DUSTS, "lead"))
        .with_output(Ingredient.meta(INGOTS, "lead"))
        .build(),
        "lead_ore": FurnaceRecipeBuilder()
        .with_input(Ingredient.meta(ORES, "lead"))
        .with_output(Ingredient.meta(INGOTS, "lead"))
        .build(),
        "nickel_dust": FurnaceRecipeBuilder()
        .with_input(Ingredient.meta(DUSTS, "nickel"))
        .with_output(Ingredient.meta(INGOTS, "nickel"))
        .build(),
        "silver_dust": FurnaceRecipeBuilder()
        .with_input(Ingredient.meta(DUSTS, "silver"))
        .with_output(Ingredient.meta(INGOTS, "silver"))
        .build(),
        "silver_ore": FurnaceRecipeBuilder()
        .with_input(Ingredient.meta(ORES, "silver"))
        .with_output(Ingredient.meta(INGOTS, "silver"))
        .build(),
        "tin_dust": FurnaceRecipeBuilder()
        .with_input(Ingredient.meta(DUSTS, "tin"))
        .with_output(Ingredient.meta(INGOTS, "tin"))
        .build(),
        "zinc_dust": FurnaceRecipeBuilder()
        .with_input(Ingredient.meta(DUSTS, "zinc"))
        .with_output(Ingredient.meta(INGOTS, "zinc"))
        .build(),
    }
