import Alert from "@mui/material/Alert";
import PropTypes from "prop-types";

const GeneralMessage = ({ message}) => {
  return (
    <Alert
      variant="filled"
      severity={message.type}
      style={{
        position: "fixed", // Makes the message fixed on the screen
        top: "10px", // Distance from the top
        right: "10px", // Distance from the right
        zIndex: 1400, // Ensures it stays above other elements
      }}
    >
      {message.text}
    </Alert>
  );
};

GeneralMessage.propTypes={
  message: PropTypes.shape({
    type:PropTypes.string.isRequired,
    text:PropTypes.string.isRequired
  })
}

export default GeneralMessage;
