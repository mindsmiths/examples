import asyncio
import json
import logging

import websockets
from forge.conf import settings as forge_settings
from forge.utils.store import Store
from rule_engine.api import RuleEngineAPI
from websockets.exceptions import ConnectionClosed

from . import settings
from .api import SocketMessageType, SignalMessage, EventMessage
from .api.events import NewSocketConnection, SocketConnectionClosed

store = Store()
logger = logging.getLogger(forge_settings.DEFAULT_LOGGER)


async def process(websocket):
    logger.debug(f"New connection established, {websocket=}. Waiting for connection id...")
    connection_id = await websocket.recv()

    logger.debug(f"Got {connection_id=}")
    NewSocketConnection(connectionId=connection_id).emit()

    # store.set(f"socket-{connection_id}", [])
    asyncio.create_task(forward_messages_to_socket(websocket, connection_id))

    try:
        async for message in websocket:
            logger.debug(f"Websocket server received {message=}")
            message = json.loads(message)

            if message['messageType'] == SocketMessageType.SIGNAL:
                sig = SignalMessage(**message)
                RuleEngineAPI.send_signal(sig.to, sig)
            elif message['messageType'] == SocketMessageType.EVENT:
                EventMessage(**message).emit()
            else:
                raise ValueError(f"Unrecognized messageType='{message['messageType']}'")

    except ConnectionClosed:
        logger.debug(f"Connection {connection_id} killed.")
        SocketConnectionClosed(connectionId=connection_id).emit()
        # store.pop(f"socket-{connection_id}")


async def forward_messages_to_socket(websocket, connection_id: str):
    while True:
        try:
            messages = store.get(f"socket-{connection_id}", [])
            while messages:
                message = messages.pop(0)
                await websocket.send(message)
                store.set(f"socket-{connection_id}", messages)
        except ConnectionClosed:
            break
        await asyncio.sleep(0.5)


async def server():
    async with websockets.serve(process, host=settings.WEBSOCKET_HOST, port=settings.WEBSOCKET_PORT):
        await asyncio.Future()
