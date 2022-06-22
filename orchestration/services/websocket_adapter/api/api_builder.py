from forge.core.api import BaseAPI
from forge.core.api import api_interface
from forge.core.api.events import Signal

from services import websocket_adapter


@api_interface
class WebsocketAdapterAPI(BaseAPI):
    service_name = websocket_adapter.SERVICE_NAME

    @staticmethod
    def send(connectionId: str, signal: Signal) -> None:
        """Forwards payload through websocket"""
