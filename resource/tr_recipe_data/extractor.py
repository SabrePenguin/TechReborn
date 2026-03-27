from resource.recipe_builders import CustomRecipeBuilder, Ingredient
from registries import *


def get_extractor_recipes() -> dict[str, str]:
    return {
        "01_rubber_sapling": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.mod_item("rubber_sapling"))
        .with_output(Ingredient.meta(PARTS, "rubber"))
        .build(),
        "02_rubber_log": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.mod_item("rubber_log"))
        .with_output(Ingredient.meta(PARTS, "rubber"))
        .build(),
        "03_rubber_slime": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.item("slime_ball"))
        .with_output(Ingredient.meta(PARTS, "rubber", count=2))
        .build(),
        "04_rubber_sap": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.meta(PARTS, "sap"))
        .with_output(Ingredient.meta(PARTS, "rubber", count=3))
        .build(),
        "20_flowers_0": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.item("red_flower", metadata=0))
        .with_output(Ingredient.item("dye", metadata=1, count=2))
        .build(),
        "20_flowers_1": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.item("red_flower", metadata=1))
        .with_output(Ingredient.item("dye", metadata=12, count=2))
        .build(),
        "20_flowers_2": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.item("red_flower", metadata=2))
        .with_output(Ingredient.item("dye", metadata=13, count=2))
        .build(),
        "20_flowers_3": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.item("red_flower", metadata=3))
        .with_output(Ingredient.item("dye", metadata=7, count=2))
        .build(),
        "20_flowers_4": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.item("red_flower", metadata=4))
        .with_output(Ingredient.item("dye", metadata=1, count=2))
        .build(),
        "20_flowers_5": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.item("red_flower", metadata=5))
        .with_output(Ingredient.item("dye", metadata=14, count=2))
        .build(),
        "20_flowers_6": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.item("red_flower", metadata=6))
        .with_output(Ingredient.item("dye", metadata=7, count=2))
        .build(),
        "20_flowers_7": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.item("red_flower", metadata=7))
        .with_output(Ingredient.item("dye", metadata=9, count=2))
        .build(),
        "20_flowers_8": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.item("red_flower", metadata=8))
        .with_output(Ingredient.item("dye", metadata=7, count=2))
        .build(),
        "20_flowers_9": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.item("yellow_flower"))
        .with_output(Ingredient.item("dye", metadata=11, count=2))
        .build(),
        "30_double_flowers_0": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.item("double_plant", metadata=0))
        .with_output(Ingredient.item("dye", metadata=11, count=2))
        .build(),
        "30_double_flowers_1": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.item("double_plant", metadata=1))
        .with_output(Ingredient.item("dye", metadata=13, count=2))
        .build(),
        "30_double_flowers_2": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.item("double_plant", metadata=4))
        .with_output(Ingredient.item("dye", metadata=2, count=2))
        .build(),
        "30_double_flowers_3": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.item("double_plant", metadata=5))
        .with_output(Ingredient.item("dye", metadata=9, count=2))
        .build(),
        "30_double_plant_0": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.item("double_plant", metadata=2))
        .with_output(Ingredient.item("wheat_seeds", count=2))
        .build(),
        "30_double_plant_1": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.item("double_plant", metadata=3))
        .with_output(Ingredient.item("wheat_seeds", count=2))
        .build(),
        "31_tallgrass_0": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.item("tallgrass", metadata=1))
        .with_output(Ingredient.item("wheat_seeds"))
        .build(),
        "31_tallgrass_1": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.item("tallgrass", metadata=2))
        .with_output(Ingredient.item("wheat_seeds"))
        .build(),
        "deadbush": CustomRecipeBuilder(recipe_type="techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.item("deadbush"))
        .with_output(Ingredient.item("stick"))
        .build(),
        "wool": CustomRecipeBuilder("techreborn:extractor")
        .with_time(400)
        .with_energy(8)
        .with_input(Ingredient.oredict("wool"))
        .with_output(Ingredient.item("wool", metadata=0))
        .build(),
    }
