import os
from time import sleep

import requests

import urls  # noqa
from utils import ensure_necessary_folders

WALL_E = 'WallE'


def process_tasks():
    # Example tasks processing
    ensure_necessary_folders()
    done_tasks = os.listdir('robots/done/')
    print(f"Cleaning up {len(done_tasks)} completed tasks.")
    for task in done_tasks:
        os.remove(f"robots/done/{task}")
    return done_tasks


# Periodically cleaning completed tasks
while True:
    done_tasks = process_tasks()
    requests.post(f"{urls.FORGE_API_URL}/signal/robot/WallE/Cleaned", json={'tasks': len(done_tasks)})

    sleep(2)
