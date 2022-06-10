import os
from time import sleep

from forge.api import send_event

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
    send_event(WALL_E, 'cleaned', {'tasks': len(done_tasks)})

    sleep(2)
