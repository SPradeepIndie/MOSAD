// Chatbot.jsx
import { useEffect, useRef, useState } from "react";
import {
  Avatar,
  Box,
  Button,
  IconButton,
  InputAdornment,
  Paper,
  Slide,
  TextField,
  Typography,
  CircularProgress
} from "@mui/material";
import ChatIcon from '@mui/icons-material/Chat';
import CloseIcon from '@mui/icons-material/Close';
import SendIcon from '@mui/icons-material/Send';

const Chatbot = () => {
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState("");
  const [open, setOpen] = useState(false);
  const [loading, setLoading] = useState(false);
  const chatBoxRef = useRef(null);
  const socketRef = useRef(null);

  useEffect(() => {
    socketRef.current = new WebSocket("ws://localhost:8000/chat");

    socketRef.current.onmessage = (event) => {
      setMessages((prev) => [...prev, { sender: "bot", text: event.data }]);
      setLoading(false);
    };

    return () => socketRef.current.close();
  }, []);

  useEffect(() => {
    if (chatBoxRef.current) {
      chatBoxRef.current.scrollTop = chatBoxRef.current.scrollHeight;
    }
  }, [messages, loading]);

  const sendMessage = () => {
    if (!input.trim() || socketRef.current.readyState !== WebSocket.OPEN) return;
    socketRef.current.send(input);
    setMessages((prev) => [...prev, { sender: "user", text: input }]);
    setInput("");
    setLoading(true);
  };

  return (
    <>
      {!open && (
        <IconButton
          onClick={() => setOpen(true)}
          sx={{
            position: "fixed",
            bottom: 20,
            right: 20,
            backgroundColor: "primary.main",
            color: "white",
            '&:hover': { backgroundColor: "primary.dark" },
            zIndex: 1000
          }}
        >
          <ChatIcon />
        </IconButton>
      )}

      <Slide direction="up" in={open} mountOnEnter unmountOnExit>
        <Paper
          elevation={6}
          sx={{
            position: "fixed",
            bottom: 80,
            right: 20,
            width: 400,
            maxHeight: 600,
            display: "flex",
            flexDirection: "column",
            zIndex: 1500,
            borderRadius: 3,
            overflow: "hidden"
          }}
        >
          {/* Header */}
          <Box display="flex" alignItems="center" justifyContent="space-between" px={2} py={1} bgcolor="primary.main" color="white">
            <Box display="flex" alignItems="center" gap={1}>
              <Avatar sx={{ width: 28, height: 28 }}>🤖</Avatar>
              <Typography variant="subtitle1">MOSAD Assistant</Typography>
            </Box>
            <IconButton onClick={() => setOpen(false)} sx={{ color: "white" }}><CloseIcon /></IconButton>
          </Box>

          {/* Chat messages */}
          <Box ref={chatBoxRef} sx={{ flexGrow: 1, px: 2, py: 1, overflowY: "auto", backgroundColor: '#f9f9f9' }}>
            {messages.map((msg, idx) => (
              <Box
                key={idx}
                sx={{
                  display: "flex",
                  justifyContent: msg.sender === "user" ? "flex-end" : "flex-start",
                  mb: 1
                }}
              >
                <Box
                  sx={{
                    maxWidth: "80%",
                    px: 2,
                    py: 1,
                    borderRadius: 2,
                    bgcolor: msg.sender === "user" ? "primary.main" : "grey.300",
                    color: msg.sender === "user" ? "white" : "black"
                  }}
                >
                  <Typography variant="body2">{msg.text}</Typography>
                </Box>
              </Box>
            ))}
            {loading && (
              <Box sx={{ display: "flex", alignItems: "center", gap: 1, mb: 1 }}>
                <Typography variant="body2">Bot is typing</Typography>
                <CircularProgress size={14} />
              </Box>
            )}
          </Box>

          {/* Input */}
          <Box sx={{ px: 2, py: 1, borderTop: "1px solid #ddd" }}>
            <TextField
              fullWidth
              size="small"
              placeholder="Ask something..."
              value={input}
              onChange={(e) => setInput(e.target.value)}
              onKeyDown={(e) => e.key === "Enter" && sendMessage()}
              InputProps={{
                endAdornment: (
                  <InputAdornment position="end">
                    <IconButton onClick={sendMessage} color="primary">
                      <SendIcon />
                    </IconButton>
                  </InputAdornment>
                )
              }}
            />
          </Box>
        </Paper>
      </Slide>
    </>
  );
};

export default Chatbot;
