from typing import Any

from forge.core.api import BaseAPI
from forge.core.api import api_interface

from services import websocket_adapter

@api_interface
class WebsocketAdapterAPI(BaseAPI):
    service_name = websocket_adapter.SERVICE_NAME

    @staticmethod
    def send(connectionId: str, payload: Any) -> None:
        """Forwards payload through websocket"""
