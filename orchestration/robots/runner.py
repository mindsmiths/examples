from __future__ import annotations

import asyncio
import json
import random
import subprocess
import sys
from datetime import datetime
from typing import Any, Literal, Union

import psutil
import websockets
from pydantic import BaseModel, Field

from urls import FORGE_SOCKET_URL
from utils import random_id


def on_signal(connection_id: str, signal: Signal) -> SocketMessage:
    if signal.type == "RestartRobot":
        if random.random() > 0.5:
            print(f"Restarting {connection_id}")
            kill_old_process()
            start_robot(connection_id)
            return SignalMessage(type='RobotRestarted', to=connection_id, success=True)
        else:
            print(f"Oh no, failed restarting {connection_id}")
            return SignalMessage(type='RobotRestarted', to=connection_id, success=False)
    else:
        print("I don't know what to do")


def kill_old_process():
    for proc in psutil.process_iter():
        if f"{connection_id}.py" in ' '.join(proc.cmdline()):
            proc.kill()


def start_robot(robot_name: str) -> None:
    subprocess.Popen(['python', f'robots/{robot_name}.py'])


# --------------------------------------------------------------------------------------

class Signal(BaseModel):
    id: str = Field(default_factory=lambda: random_id())
    timestamp: datetime = Field(default_factory=lambda: datetime.utcnow())  # noqa: C103
    from_: str = Field(alias='from', default='websocket')
    type: str

    class Config:
        extra = 'allow'
        use_enum_values = True


class SignalMessage(Signal):
    messageType: Literal['SIGNAL'] = 'SIGNAL'
    to: str


class EventMessage(Signal):
    messageType: Literal['EVENT'] = 'EVENT'


SocketMessage = Union[SignalMessage, EventMessage]


async def listen(connection_id: str):
    print("Establishing connection...")

    async with websockets.connect(FORGE_SOCKET_URL) as websocket:
        print(f"Connection established with socket id {str(websocket.id)}")
        await websocket.send(connection_id)

        while True:
            data = await websocket.recv()
            print(f"Received {data=}")
            signal = Signal(**json.loads(data))
            await send_response(signal, websocket)


async def send_response(signal: Signal, websocket: Any):
    response = on_signal(connection_id, signal)
    if response is not None:
        await websocket.send(response.json())


def start(connection_id: str):
    loop = asyncio.get_event_loop()
    loop.run_until_complete(listen(connection_id))


if __name__ == '__main__':
    if len(sys.argv) <= 1:
        print('Missing argument ID')
        exit(1)

    connection_id = sys.argv[1]
    start(connection_id)
