from resource.tr_recipe_data.extractor import get_extractor_recipes
from resource.tr_recipe_data.furnace import get_furnace_recipes


custom_recipe_data = {
    "techreborn": {
        "furnace": get_furnace_recipes(),
        "extractor": get_extractor_recipes(),
        "plates": {},
        "recycler": {},
    },
}
