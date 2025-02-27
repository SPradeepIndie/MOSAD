import { useState } from "react";

const chatbotAPI = "http://127.0.0.1:8001/chatbot";

const useChatbot = () => {
  const [response, setResponse] = useState("");

  const sendMessage = async (message) => {
    try {
      const res = await fetch(chatbotAPI, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ text: message }),
      });

      const data = await res.json();
      setResponse(data.reply);
    } catch (error) {
      console.error("Chatbot API error:", error);
      setResponse("Error connecting to chatbot");
    }
  };

  return { response, sendMessage };
};

export default useChatbot;
