import asyncio
import json
import random
import subprocess
import sys
from typing import Any

import psutil
import websockets

from urls import FORGE_SOCKET_URL


def on_message(connection_id: str, message: Any) -> Any:
    if message == "RESTART":
        if random.random() > 0.5:
            print(f"Restarting {connection_id}")
            kill_old_process()
            start_robot(connection_id)
            return True
        else:
            print(f"Oh no, failed restarting {connection_id}")
            return False
    else:
        print("I don't know what to do")

def kill_old_process():
    for proc in psutil.process_iter():
        if f"{connection_id}.py" in ' '.join(proc.cmdline()):
            proc.kill()

def start_robot(robot_name: str) -> None:
    subprocess.Popen(['python', f'robots/{robot_name}.py'])


# --------------------------------------------------------------------------------------

async def listen(connection_id: str):
    print("Establishing connection...")

    async with websockets.connect(f"ws://{FORGE_SOCKET_URL}") as websocket:
        print(f"Connection established with socket id {str(websocket.id)}")
        await websocket.send(connection_id)

        while True:
            data = await websocket.recv()
            print(f"Received {data=}")
            await send_response(data, websocket)

        print("Killing robot... Bye")
        exit()

async def send_response(data: Any, websocket: Any):
    message = json.loads(data)
    response = on_message(connection_id, message)
    if response is not None:
        await websocket.send(json.dumps(response))

def start(connection_id: str):
    loop = asyncio.get_event_loop()
    loop.run_until_complete(listen(connection_id))


if __name__ == '__main__':
    if len(sys.argv) <= 1:
        print('Missing argument ID')
        exit(1)

    connection_id = sys.argv[1]
    start(connection_id)
