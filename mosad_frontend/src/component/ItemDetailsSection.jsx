import React from "react";
import { TextField, Box, Typography } from "@mui/material";
import { useState } from "react";

 
  

const ItemDetailsSection = ({ formData, setFormData,handleChange,errors }) => {

    //const [errors, setErrors] = useState({});

    // const handleChange = (key, value) => {
    //     // Validate the field
    //     let fieldError = "";
      
    //     if (!value) {
    //       fieldError = `${key.replace(/([A-Z])/g, " $1").trim()} is required.`;
    //     } else if (key === "tyreCount" && !Number.isInteger(Number(value))) {
    //       fieldError = "PR must be a valid integer.";
    //     } else if (key === "officialSellingPrice" && !/^(-?\d+(\.\d+)?)$/.test(value)) {
    //       fieldError = "OSP must be a valid number.";
    //     }
      
    //     // Update the error state
    //     setErrors((prevErrors) => ({
    //       ...prevErrors,
    //       [key]: fieldError || undefined, // Clear the error if validation passes
    //     }));
      
    //     // Only update formData if there's no error
    //     if (!fieldError) {
    //       setFormData((prevData) => ({ ...prevData, [key]: value }));
    //     }
    //   };





  return (
    <Box
      id="itemdetails"
      sx={{
        marginBottom: "20px",
        padding: "15px",
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
          gridTemplateColumns: "auto auto",
          gap: "15px",
        }}
      >
        {Object.keys(formData).map((key) => (
          <TextField
            key={key}
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
