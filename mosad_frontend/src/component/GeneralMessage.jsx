import Alert from "@mui/material/Alert";

const GeneralMessage = ({ message}) => {
  return (
    <Alert
      variant="filled"
      severity={message.type}
      style={{
        position: "fixed", // Makes the message fixed on the screen
        top: "10px", // Distance from the top
        right: "10px", // Distance from the right
        zIndex: 1000, // Ensures it stays above other elements
      }}
    >
      {message.text}
    </Alert>
  );
};

export default GeneralMessage;
