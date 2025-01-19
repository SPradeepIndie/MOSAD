import React from "react";
import { TextField, Box, Typography,DialogActions,Button } from "@mui/material";
import PropTypes from "prop-types";

const ItemDetailsForm = ({ formData,handleChange,errors,onSubmit,closeDialog }) => {

  return (
    <form onSubmit={onSubmit}>
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
            gridTemplateColumns: { xs: "1fr", sm: "1fr 1fr", md: "1fr 1fr 1fr" },
            gap: "15px",
          }}
        >
          {Object.keys(formData).map((key) => (
            <TextField
              key={key}
              type={key === "officialSellingPrice" || key === "tyreCount" ? "number" : "text"} // Set input type based on the key
              label={key.replace(/([A-Z])/g, " $1").trim()} // Generate label from the key
              value={formData[key] || ""} // Set input value based on the key
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
      <DialogActions>
        <Button onClick={closeDialog} color="secondary">Cancel</Button>
        <Button type="submit" color="primary">Submit</Button>
      </DialogActions>

    </form>
  );
};

ItemDetailsForm.prototype={
  formData:PropTypes.object,
  handleChange:PropTypes.func,
  errors:PropTypes.object,
  onSubmit:PropTypes.func
}

export default ItemDetailsForm;
