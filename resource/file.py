import os
from pathlib import Path

OVERWRITE = True

def write_to_file(file_name: str | Path, data: str | None):
    if data is None:
        print(f"Unable to write to file: {file_name}")
        return
    if not os.path.exists(os.path.dirname(file_name)):
        os.makedirs(os.path.dirname(file_name), exist_ok=True)
    if not os.path.exists(file_name) or OVERWRITE:
        with open(file_name, "w") as file:
            file.write(data)

def image_exists(path) -> bool:
    return os.path.exists(path)