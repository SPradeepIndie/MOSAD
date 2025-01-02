import React, { useState, useEffect } from "react";
import { Box, TextField, Button, Typography, MenuItem, Select, FormControl, InputLabel } from "@mui/material";
import axios from "axios";

const SearchComponent = ({ onSearchResult }) => {
  const [brand, setBrand] = useState(""); // Holds the selected brand
  const [size, setSize] = useState(""); // Holds the entered tyre size
  const [brands, setBrands] = useState([]); // Holds the list of available brands
  const [error, setError] = useState(""); // Error message for search failures
  const [loading, setLoading] = useState(false); // Loading state for fetching brands

  // Fetch available brands when the component mounts
  useEffect(() => {
    const fetchBrands = async () => {
      try {
        setLoading(true);
        const response = await axios.get("/api/search/brands");
        if (response.status === 200 && Array.isArray(response.data)) {
          setBrands(response.data); // Set brands if the API response is an array
        } else {
          setBrands([]); // Default to an empty array if response is unexpected
          console.error("Unexpected response from the server:", response);
        }
      } catch (err) {
        console.error("Failed to fetch brands:", err);
        setError("Failed to load brands. Please try again.");
      } finally {
        setLoading(false);
      }
    };
    fetchBrands();
  }, []);

  const handleSearch = async () => {
    try {
      setError(""); // Clear any previous error
      const response = await axios.get("/api/search/brand", {
        params: { brand, size },
      });
      if (response.status === 200) {
        onSearchResult(response.data); // Pass results to parent component
      } else {
        onSearchResult([]); // Clear results if no content
      }
    } catch (err) {
      console.error("Search request failed:", err);
      setError("Failed to fetch search results. Please check your inputs.");
    }
  };

  return (
    <Box sx={{ display: "flex", flexDirection: "column", gap: 2, mb: 3 }}>
      <Typography variant="h5" sx={{ fontWeight: "bold", textAlign: "center" }}>
        Search Tyres
      </Typography>
      {loading ? (
        <Typography>Loading brands...</Typography>
      ) : (
        <FormControl fullWidth>
          <InputLabel id="brand-select-label">Brand</InputLabel>
          <Select
            labelId="brand-select-label"
            value={brand}
            onChange={(e) => setBrand(e.target.value)}
            variant="outlined"
          >
            {brands.map((b) => (
              <MenuItem key={b} value={b}>
                {b}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
      )}
      <TextField
        label="Size"
        value={size}
        onChange={(e) => setSize(e.target.value)}
        variant="outlined"
        fullWidth
      />
      <Button
        variant="contained"
        color="primary"
        onClick={handleSearch}
        disabled={loading || !brands.length}
      >
        Search
      </Button>
      {error && <Typography color="error">{error}</Typography>}
    </Box>
  );
};

export default SearchComponent;
