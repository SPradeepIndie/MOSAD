import React from "react";
import { Typography, Button, Box } from "@mui/material";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";

const RetailProductAvailableForm = ({ quantity, itemName, onClose }) => {
  return (
    <Box
      textAlign="center"
      bgcolor="#d4f7dc"
      p={3}
      borderRadius={2}
      display="flex"
      flexDirection="column"
      alignItems="center"
    >
      <CheckCircleIcon style={{ fontSize: 50, color: "#4caf50", marginBottom: "16px" }} />
      <Typography variant="h6" gutterBottom>
        {`${quantity} Items from ${itemName} is available.`}
      </Typography>
      <Button variant="contained" color="primary" onClick={onClose}>
        Close
      </Button>
    </Box>
  );
};

export default RetailProductAvailableForm;
