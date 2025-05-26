from jose import jwt
from chatbot.config import JWT_SECRET

def get_user_role(token: str):
    try:
        payload = jwt.decode(token, JWT_SECRET, algorithms=["HS256"])
        return payload.get("role", "customer")
    except Exception:
        return "customer"