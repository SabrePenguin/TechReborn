from resource.tr_recipe_data.alloy import get_alloys
from resource.tr_recipe_data.extractor import get_extractor_recipes
from resource.tr_recipe_data.furnace import get_furnace_recipes
from resource.tr_recipe_data.grinder import get_grinder_recipes


custom_recipe_data = {
    "techreborn": {
        "furnace": get_furnace_recipes(),
        "alloy": get_alloys(),
        "grinder": get_grinder_recipes(),
        "extractor": get_extractor_recipes(),
        "plates": {},
        "recycler": {},
    },
}
