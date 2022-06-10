from typing import Any

from forge.core.api.events import Event

from services import websocket_adapter


class NewSocketConnection(Event):
    connectionId: str

    @classmethod
    def get_service_name(cls) -> str:
        return websocket_adapter.SERVICE_NAME


class SocketConnectionClosed(Event):
    connectionId: str

    @classmethod
    def get_service_name(cls) -> str:
        return websocket_adapter.SERVICE_NAME


class SocketMessage(Event):
    connectionId: str
    message: Any

    @classmethod
    def get_service_name(cls) -> str:
        return websocket_adapter.SERVICE_NAME
