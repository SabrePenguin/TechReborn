import registries

def _capitalize_oredict(ore_in: str) -> str:
    words: list[str] = ore_in.split("_")
    out = ""
    for word in words:
        out = out + word.capitalize()
    return out

def _create_ore(ore: str, count: int = 0) -> dict:
    result: dict[str, str | int] = {"type": "forge:ore_dict", "ore": ore}
    if count > 0:
        result["count"] = count
    return result

def create_ore(oretype: str, ore: str, count: int = 0) -> dict:
    return _create_ore(oretype + _capitalize_oredict(ore), count)

def create_item(modid: str, item_name: str, data: int = -1, count: int = 0) -> dict:
    """
    Helper method to create items for recipes
    :param modid: Which mod to target
    :param item_name: The item name to target
    :param data: The metadata of the item
    :param count: The output count (for outputs only)
    :return: A complete item, to be passed as output to one of the recipe methods
    """
    result: dict[str, str | int] = {
        "item": modid + ":" + item_name,
    }
    if data >= 0:
        result["data"] = data
    if count > 0:
        result["count"] = count
    return result

def string_to_item(string_in: str, count: int = 0) -> dict:
    if count == 0:
        split_words: list[str] = string_in.split("#", 1)
        if len(split_words) == 2:
            s_count, string_in = split_words
            count = int(s_count)
    split_words = string_in.split(":", 1)
    modid = "minecraft"
    if len(split_words) == 2:
        modid = split_words.pop(0)
    split_words = split_words[0].split("@", 1)
    data = -1
    if len(split_words) == 2:
        data = int(split_words.pop())
    return create_item(modid, split_words[0], data, count)


def meta_string_to_item(string_in: str, registry: dict, count: int = 0) -> dict | None:
    if count == 0:
        split_words = string_in.split("#", 1)
        if len(split_words) == 2:
            s_count, string_in = split_words
            count = int(s_count)
    split_words = string_in.split(":", 1)
    modid = "minecraft"
    if len(split_words) == 2:
        modid = split_words.pop(0)
    registry_item_base_name = registries.get_name_for_registry(registry)
    data = registry.get(split_words[0], -1)
    if data == -1:
        return None
    return create_item(modid, registry_item_base_name, data, count)


def string_to_oredict(string_in: str, count: int = 0) -> dict:
    if count == 0:
        split_words: list[str] = string_in.split("#", 1)
        count = 0
        if len(split_words) == 2:
            count = int(split_words.pop(0))
        return _create_ore(split_words[0], count)
    return _create_ore(string_in, count)