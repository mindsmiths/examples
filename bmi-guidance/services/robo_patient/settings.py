from environs import Env

env = Env()

NUM_PATIENTS = env.int('NUM_PATIENTS')
