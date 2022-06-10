import os
import random
import shutil
from time import sleep

from forge.api import send_event

import urls  # noqa
from utils import ensure_necessary_folders

EVE = 'Eve'


def process_task(task):
    # Example task processing
    ensure_necessary_folders()
    with open(f"robots/tasks/{task}", 'r') as f:
        # Fake loading data
        sleep(1)
        rows = len(f.readlines())
        print(f"Loaded {rows} rows.")
    shutil.move(f"robots/tasks/{task}", f'robots/done/{task}')
    return rows


# Waiting for tasks to load in DB
while True:
    tasks = os.listdir('robots/tasks/')
    for task in tasks:
        print(f"Doing task {task}")
        send_event(EVE, 'task_started', {'task': task})
        rows = process_task(task)
        send_event(EVE, 'task_finished', {'task': task, 'rows': rows})

    sleep(2)
