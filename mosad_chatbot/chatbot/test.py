import asyncio
import websockets

async def test_chat():
    uri = "ws://localhost:8000/chat"  # Adjust if using a different port or host
    async with websockets.connect(uri) as websocket:
        test_question = ""  # Replace with an actual question from your CSV
        await websocket.send(test_question)
        print(f"Sent: {test_question}")
        response = await websocket.recv()
        print(f"Bot: {response}")

if __name__ == "__main__":
    asyncio.run(test_chat())