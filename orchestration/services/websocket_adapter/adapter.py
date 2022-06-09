import asyncio
import json
import logging
from typing import Any

from forge.conf import settings
from forge.core.api import api
from forge.core.base import BaseService
from forge.utils.store import Store

from .server import server

store = Store()
logger = logging.getLogger(settings.DEFAULT_LOGGER)


class WebsocketAdapter(BaseService):

    def start(self) -> None:
        logger.info("Starting message consumer for WebsocketAdapter...")
        self.start_async_message_consumer()
        asyncio.run(server())

    @api
    def send(self, connectionId: str, payload: Any) -> None:
        data = json.dumps(payload)
        asyncio.run(self.send_via_socket(connectionId, data))

    async def send_via_socket(self, connectionId: str, message: Any) -> None:
        messages = store.get(f"socket-{connectionId}", [])
        messages.append(message)
        store.set(f"socket-{connectionId}", messages)
        logger.debug(f"For sockets {connectionId} updated {messages=}")
