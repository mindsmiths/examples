import random
from time import sleep

from utils import ensure_necessary_folders

while True:
    sleep(2)
    task_id = str(random.randint(1000, 10000))
    lines = random.randint(10, 1000)

    print(f"Generating task {task_id}")
    ensure_necessary_folders()
    with open(f'robots/tasks/{task_id}', 'w') as f:
        f.writelines([f'Row {i}\n' for i in range(lines)])
