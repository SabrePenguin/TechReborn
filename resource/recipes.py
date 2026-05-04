from pathlib import Path

from resource.file import write_to_file
from resource.recipe_data import custom_recipe_data

MODID = "techreborn"
BASE = Path("src/main/resources/assets/techreborn")
RECIPES = BASE.joinpath("recipes")
CUSTOM_RECIPES = BASE.joinpath("trrecipes")


def standard_recipes():
    custom_recipes()


def custom_recipes():
    for mod_name, recipe_info in custom_recipe_data.items():
        base_name = CUSTOM_RECIPES.joinpath(mod_name)
        for recipe_type, recipes in recipe_info.items():
            recipe_folder = base_name.joinpath(recipe_type)
            for recipe_name, recipe in recipes.items():
                recipe_file = recipe_folder.joinpath(f"{recipe_name}.json")
                write_to_file(recipe_file, recipe)
