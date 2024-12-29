import { Box, Typography, TextField, Divider } from "@mui/material";
import { useState } from "react";

const PriceDetailsSection = () => {
  const [officialSellingPrice, setOfficialSellingPrice] = useState("");
  const [discount, setDiscount] = useState("");
  const [companyPrice, setCompanyPrice] = useState("");
  const [discountPrice, setDiscountPrice] = useState("");
  const [sellingPrice, setSellingPrice] = useState("");
  const [profit, setProfit] = useState("");

  // Update and calculate fields dynamically
  const handleFieldChange = (key, value) => {
    const numericValue = parseFloat(value) || 0;

    if (key === "officialSellingPrice") {
      setOfficialSellingPrice(numericValue);
      const cp = numericValue * 0.7; // Company Price = OSP * 70%
      setCompanyPrice(cp.toFixed(2));
      const dp = numericValue * (discount / 100 || 0); // Discount Price = OSP * Discount
      setDiscountPrice(dp.toFixed(2));
      const sp = numericValue - dp; // Selling Price = OSP - Discount Price
      setSellingPrice(sp.toFixed(2));
      setProfit((sp - cp).toFixed(2)); // Profit = Selling Price - Company Price
    }

    if (key === "discount") {
      setDiscount(numericValue);
      const dp = officialSellingPrice * (numericValue / 100 || 0); // Discount Price = OSP * Discount
      setDiscountPrice(dp.toFixed(2));
      const sp = officialSellingPrice - dp; // Selling Price = OSP - Discount Price
      setSellingPrice(sp.toFixed(2));
      setProfit((sp - companyPrice).toFixed(2)); // Profit = Selling Price - Company Price
    }
  };

  return (
    <Box
      id="pricedetails"
      sx={{
        marginBottom: "25px",
        padding: "20px",
        border: "1px solid #ccc",
        borderRadius: "8px",
      }}
    >
      {/* Title */}
      <Typography variant="h6" color="green" mb={2}>
        Prices Details
      </Typography>

      {/* Price Details */}
      <Box
        sx={{
          display: "grid",
          gridTemplateColumns: { xs: "1fr", sm: "1fr 1fr" },
          gap: "16px",
          alignItems: "center",
        }}
      >
        <TextField
          label="Company Price (CP)"
          variant="outlined"
          fullWidth
          value={companyPrice || ""}
          disabled
          placeholder="Company Price (CP)"
        />
        <TextField
          label="Official Selling Price (OSP)"
          variant="outlined"
          fullWidth
          value={officialSellingPrice || ""}
          onChange={(e) => handleFieldChange("officialSellingPrice", e.target.value)}
          placeholder="Enter OSP"
        />
      </Box>

      {/* Divider */}
      <Divider sx={{ marginY: "20px" }} />

      {/* Price Calculation Section */}
      <Typography variant="h6" sx={{ fontWeight: "bold", marginBottom: "10px" }}>
        Price Calculation
      </Typography>

      <Box
        sx={{
          display: "grid",
          gridTemplateColumns: { xs: "1fr", sm: "1fr 1fr", md: "1fr 1fr 1fr" },
          gap: "16px",
          alignItems: "center",
        }}
      >
        <TextField
          label="Discount (%)"
          variant="outlined"
          fullWidth
          value={discount || ""}
          onChange={(e) => handleFieldChange("discount", e.target.value)}
          placeholder="Enter discount percentage"
        />
        <TextField
          label="Discount Price"
          variant="outlined"
          fullWidth
          value={discountPrice || ""}
          disabled
          placeholder="(DP = OSP * Discount)"
        />
        <TextField
          label="Selling Price"
          variant="outlined"
          fullWidth
          value={sellingPrice || ""}
          disabled
          placeholder="(SP = OSP - DP)"
        />
        <TextField
          label="Profit"
          variant="outlined"
          fullWidth
          value={profit || ""}
          disabled
          placeholder="(Profit = SP - CP)"
        />
      </Box>
    </Box>
  );
};

export default PriceDetailsSection;
