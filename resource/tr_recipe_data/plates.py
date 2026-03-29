from registries import *
from resource.recipe_builders import CustomRecipeBuilder, Ingredient


def get_plate_recipes() -> dict[str, str]:
    return {
        "01_advanced_alloy": CustomRecipeBuilder("techreborn:compressor")
        .with_input(Ingredient.meta(INGOTS, "advanced_alloy"))
        .with_output(Ingredient.meta(PLATES, "advanced_alloy"))
        .with_energy(80)
        .with_time(400)
        .build(),
        "02_carbon_mesh": CustomRecipeBuilder("techreborn:compressor")
        .with_input(Ingredient.meta(INGOTS, "advanced_alloy"))
        .with_output(Ingredient.meta(PLATES, "advanced_alloy"))
        .with_energy(80)
        .with_time(400)
        .build(),
    }