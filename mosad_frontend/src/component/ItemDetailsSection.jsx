import React from "react";
import { TextField, Box, Typography } from "@mui/material";
import { useState } from "react";

 
  

const ItemDetailsSection = ({ formData,handleChange,errors }) => {

  return (
    <Box
      id="itemdetails"
      sx={{
        marginBottom: "25px",
        padding: "20px",
        border: "1px solid #ccc",
        borderRadius: "8px",
      }}
    >
      <Typography variant="h6" color="green" mb={2}>
        Item Details
      </Typography>
      <Box
        sx={{
          display: "grid",
          gridTemplateColumns: "45% 45%",
          gap: "15px",
        }}
      >
        {Object.keys(formData).map((key) => (
          <TextField
            key={key}
            type={key === "officialSellingPrice" || key=== "tyreCount"  ? "number" : "text"} // Set input type based on the key
            label={key.replace(/([A-Z])/g, " $1").trim()} // Generate label from the key
            value={formData[key]}
            onChange={(e) =>
              handleChange(key, e.target.value)
            }
            required={key !== "itemID"} // Mark all fields except 'itemID' as required
            disabled={key === "itemID"} // Disable the 'itemID' field
            error={!!errors[key]} // Highlight the field in red if it has an error
            helperText={errors[key] || ""} // Show error message if available
            fullWidth
            variant="outlined"
            size="small"
            sx={{
              gridColumn: "span", // Ensures consistent spacing
              backgroundColor: "#fff",
            }}
          />
        ))}
      </Box>
    </Box>
  );
};

export default ItemDetailsSection;
