import React, { useState, useEffect } from "react";
import {
  Box,
  TextField,
  Button,
  Typography,
  MenuItem,
  Select,
  FormControl,
  InputLabel,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
} from "@mui/material";
import { fetchBrands } from "../services/apiStockService";

const SearchComponent = ({ onSearchResult }) => {
  const [brand, setBrand] = useState(""); // Holds the selected brand
  const [size, setSize] = useState(""); // Holds the entered size
  const [brands, setBrands] = useState([]); // Holds the list of available brands
  const [results, setResults] = useState([]); // Holds search results
  const [error, setError] = useState(""); // Error message for search failures
  const [loadingBrands, setLoadingBrands] = useState(false); // Loading state for fetching brands


  async function getBrands(){
    return fetchBrands().then((result) => {
        return result;
      }).catch((error) => {
        return null;
      });
  }

  // Fetch available brands when the component mounts
  useEffect(() => {
    const loadbrands = async () => {
    try {
      const response = await getBrands();
      if (response.status === 200 && Array.isArray(response.data)) {
          // Set brands if the API response is an array
          setBrands(response.data);
        
      } else {
        setBrands([]); // Default to an empty array if response is unexpected
        console.log(response.data)
        console.error("Unexpected response from the server:", response);
      }
    } catch (err) {
      console.error("Failed to fetch brands:", err);
      setError("Failed to load brands. Please try again.");
    } finally {
      setLoadingBrands(false);
    }
  }   
    loadbrands();
    
  }, []);

  const handleSearch = async () => {
    try {
      setError(""); // Clear any previous error
      const response = await axios.get("/api/search/tyres", {
        params: { brand, size },
      });
      if (response.status === 200 && Array.isArray(response.data)) {
        setResults(response.data); // Set search results
        onSearchResult(response.data); // Pass results to parent if needed
      } else {
        setResults([]); // Clear results if no content
        setError("No results found.");
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
      {loadingBrands ? (
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
        variant="outlined"
        value={size}
        onChange={(e) => setSize(e.target.value)}
        fullWidth
      />
      <Button
        variant="contained"
        color="primary"
        onClick={handleSearch}
        disabled={loadingBrands || !brand || !size}
      >
        Search
      </Button>
      {error && <Typography color="error">{error}</Typography>}

      {/* Results Table */}
      {results.length > 0 && (
        <TableContainer component={Paper} sx={{ mt: 2 }}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Brand</TableCell>
                <TableCell>Size</TableCell>
                <TableCell>Pattern</TableCell>
                <TableCell>Price</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {results.map((result, index) => (
                <TableRow key={index}>
                  <TableCell>{result.brand}</TableCell>
                  <TableCell>{result.size}</TableCell>
                  <TableCell>{result.pattern}</TableCell>
                  <TableCell>{result.price}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
    </Box>
  );
};

export default SearchComponent;
