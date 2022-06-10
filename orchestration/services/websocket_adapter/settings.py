from environs import Env

env = Env()

WEBSOCKET_HOST = env.str('WEBSOCKET_HOST', '0.0.0.0')
WEBSOCKET_PORT = env.int('WEBSOCKET_PORT', 8765)
