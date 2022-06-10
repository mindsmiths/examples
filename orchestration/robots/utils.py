import os

def ensure_necessary_folders():
    ensure_robot_folder("tasks")
    ensure_robot_folder("done")

def ensure_robot_folder(name: str):
    path = f'robots/{name}'
    if not os.path.exists(path):
        os.makedirs(path)
