from enum import Enum
from typing import Literal, Union

from forge.core.api.events import Signal


class SocketMessageType(str, Enum):
    SIGNAL = 'SIGNAL'
    EVENT = 'EVENT'


class SignalMessage(Signal):
    messageType: Literal['SIGNAL'] = SocketMessageType.SIGNAL
    to: str


class EventMessage(Signal):
    messageType: Literal['EVENT'] = SocketMessageType.EVENT


SocketMessage = Union[SignalMessage, EventMessage]
