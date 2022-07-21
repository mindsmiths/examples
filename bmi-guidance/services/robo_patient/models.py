from forge.core.db import DBModel
from pydantic import Field
from forge.utils.base import random_generator


class RoboPatient(DBModel):
    id: str = Field(default_factory=lambda: random_generator())
    chat_id: str
    age: int
    height: int
    low_weight: float
    high_weight: float
