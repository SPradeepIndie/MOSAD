import React from "react";
import { TextField, Box, Typography,Button,DialogActions } from "@mui/material";
import PropTypes from "prop-types";

const ItemDetailsForm = ({ formData,handleChange,errors,onSubmit,closeDialog,stockIn,setStockIn,operationType }) => {



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
          {Object.keys(formData).map((key) => 
          ((operationType=="Add" && key==="availableQuantity") || key==="itemId" || key==="retailPrice" || key==="discount")?null:(
            <TextField
              key={key}
              type={key === "companyPrice" || key === "availableQuantity" || key=="retailPrice" || key=="discount"  ? "number" : "text"} // Set input type based on the key
              label={key==="companyPrice" ? "Official selling price": key.replace(/([A-Z])/g, " $1").trim()} // Generate label from the key
              value={formData[key] || ""} // Set input value based on the key
              onChange={(e) =>
                handleChange(key, e.target.value)
              }
              required={key !== "itemId"} // Mark all fields except 'itemId' as required
              //disabled={key!=="companyPrice"} // Enable the companyPrice field in Edit mode
              disabled={operationType=="Edit" || key==="availableQuantity"} // Disable the All fields in Edit mode

              error={!!errors[key]} // Highlight the field in red if it has an error
              helperText={errors[key] || ""} // Show error message if available
              fullWidth
              variant="outlined"
              size="small"
              sx={{
                gridColumn: "span", // Ensures consistent spacing
                backgroundColor: "#fff",
              }}
              slotProps={{
                input: {
                  inputProps: key === "companyPrice"  ? {min:0} : undefined, // Set min value for number inputs
                },
              }}
            />
          ))}
          {/* New Stock Input Field */}
          <TextField
            type="number"
            label="Stock In"
            value={stockIn.stockIn}
            onChange={(e) => {
              const value = e.target.value;
              if (value === '' || Number(value) >= 0) {
                setStockIn((prev) => ({ ...prev, stockIn: value }));
              }
            }}
            htmlInput={{ min: 0 }}
            fullWidth
            variant="outlined"
            size="small"
            sx={{ gridColumn: "span" }}
          />



          <TextField
            type="date"
            label="Date"
            value={stockIn.date}
            onChange={(e) => setStockIn((prev) => ({ ...prev, date: e.target.value }))}
            fullWidth
            variant="outlined"
            size="small"
            sx={{ gridColumn: "span" }}
          />
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
  closeDialog: PropTypes.func.isRequired,
  operationType: PropTypes.string.isRequired,
  stockIn: PropTypes.shape({
    stockIn: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
    date: PropTypes.string.isRequired,
  }).isRequired,
  setStockIn: PropTypes.func.isRequired,
};

export default ItemDetailsForm;
