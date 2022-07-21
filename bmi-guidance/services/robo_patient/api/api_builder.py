from forge.core.api import BaseAPI
from forge.core.api import api_interface

from services import robo_patient


@api_interface
class RoboPatientAPI(BaseAPI):
    service_name = robo_patient.SERVICE_NAME

    @staticmethod
    def init(id: str, age: int, height: int, lowWeight: float, highWeight: float) -> None:
        pass
