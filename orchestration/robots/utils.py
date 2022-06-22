import os
import random
import string


def ensure_necessary_folders():
    ensure_robot_folder("tasks")
    ensure_robot_folder("done")


def ensure_robot_folder(name: str):
    path = f'robots/{name}'
    if not os.path.exists(path):
        os.makedirs(path)


def random_id() -> str:
    return ''.join(random.choice(string.ascii_letters + string.digits) for _ in range(16))
