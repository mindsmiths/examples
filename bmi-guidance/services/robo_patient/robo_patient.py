import logging
import random
from threading import Event
from threading import Thread

import requests
from forge.conf import settings
from forge.core.api import api
from forge.core.base import BaseService
from forge.utils.base import random_generator
from forge.utils.datetime_helpers import get_utc_datetime
from forge.utils.serialization import JsonSerializer
from telegram_adapter.api import TelegramReceivedMessage

from services.robo_patient.models import RoboPatient as RoboPatientModel
from services.robo_patient.settings import NUM_PATIENTS

logger = logging.getLogger(settings.DEFAULT_LOGGER)

BASE_URL = 'http://localhost:8000/'
exit = Event()


class RoboPatient(BaseService):

    def start(self) -> None:
        num_loaded = self.load_robo_patients()

        for i in range(num_loaded, NUM_PATIENTS):
            self.send_message(f"ROBO-{i}", f"Hi, I'm {i}")

        self.start_message_consumer()
        exit.set()

    def send_message(self, chat_id: str, text: str):
        msg = TelegramReceivedMessage(
            id=random_generator(),
            chatId=chat_id,
            receivedAt=get_utc_datetime(),
            timestamp=get_utc_datetime(),
            text=text,
            rawData={}
        )
        requests.post(BASE_URL + 'event/telegram_adapter/telegram_received_message',
                      data=JsonSerializer().serialize(msg.dict()))

    def load_robo_patients(self) -> int:
        robo_patients = list(RoboPatientModel.filter())
        num_loaded = min(NUM_PATIENTS, len(robo_patients))

        for robo_patient in robo_patients[:num_loaded]:
            Thread(target=self.simulate_patient, args=(robo_patient.chat_id, robo_patient.age, robo_patient.height,
                                                       robo_patient.low_weight, robo_patient.high_weight)).start()
        logger.debug(f"Loaded {num_loaded} existing robo patients")
        return num_loaded

    @api
    def init(self, chatId: str, age: int, height: int, lowWeight: float, highWeight: float) -> None:
        logger.debug(f"Inserting 'robo_patient' with id {chatId} in DB.")
        RoboPatientModel(chat_id=chatId, age=age, height=height, low_weight=lowWeight, high_weight=highWeight).create()
        Thread(target=self.simulate_patient, args=(chatId, age, height, lowWeight, highWeight)).start()

    def simulate_patient(self, chat_id: str, age: int, height: int, low_weight: float, high_weight: float):
        exit.wait(random.randint(2, 15))
        while not exit.is_set():
            is_obese = random.randint(0, 2)
            if is_obese:
                weight = random.randint(int(high_weight), int(high_weight * 1.5))
            elif is_obese == 1:
                weight = random.randint(int(high_weight * 0.9), int(high_weight * 1.1))
            else:
                weight = random.randint(int(low_weight), int(high_weight - 1))

            self.send_message(chat_id, str(weight))

            exit.wait(random.randint(10, 55))
