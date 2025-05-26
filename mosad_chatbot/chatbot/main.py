from fastapi import FastAPI
from chatbot.api import chatbot

app = FastAPI()
app.include_router(chatbot.router)