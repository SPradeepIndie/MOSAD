from fastapi import APIRouter, WebSocket, WebSocketDisconnect
from chatbot.services.chatbot_service import handle_chat

router = APIRouter()

@router.websocket("/chat")
async def chat_endpoint(websocket: WebSocket):
    await websocket.accept()
    role = "customer"  

    try:
        while True:
            data = await websocket.receive_text()

            
            if data.startswith("/admin login"):
                password = data.split(" ")[-1]
                if password == "admin123":
                    role = "admin"
                    await websocket.send_text("✅ Admin access granted. You can now perform management tasks.")
                else:
                    await websocket.send_text("❌ Incorrect admin password.")
                continue  

            
            response = handle_chat(data, role)
            await websocket.send_text(response)

    except WebSocketDisconnect:
        print("Client disconnected")
