import React from "react";
import { TextField, Box, Typography,Button,DialogActions } from "@mui/material";
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
              type={key === "companyPrice" || key === "availableQuantity" || key=="retailPrice" || key=="discount"  ? "number" : "text"} // Set input type based on the key
              label={key.replace(/([A-Z])/g, " $1").trim()} // Generate label from the key
              value={formData[key] || ""} // Set input value based on the key
              onChange={(e) =>
                handleChange(key, e.target.value)
              }
              required={key !== "itemId"} // Mark all fields except 'itemId' as required
              disabled={key==="itemId"} // Disable the 'itemId' field
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

ItemDetailsForm.propTypes = {
  formData: PropTypes.object.isRequired,
  handleChange: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
  onSubmit: PropTypes.func.isRequired,
  closeDialog: PropTypes.func.isRequired
};

export default ItemDetailsForm;
