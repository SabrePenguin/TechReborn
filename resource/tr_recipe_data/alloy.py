from resource.recipe_builders import RecipeMultiplier, CustomRecipeBuilder, Ingredient
from registries import *


def get_alloys() -> dict[str, str]:
    return {
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
        .add_inputs([Ingredient.oredict("dustGold"), Ingredient.oredict("ingotGold")])
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
    }
