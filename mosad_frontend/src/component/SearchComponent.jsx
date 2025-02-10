import React, { useState, useEffect } from "react";
import PropTypes from 'prop-types';
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
import { fetchBrands,fetchBrandAndSizeData } from "../services/apiStockService";

const SearchComponent = ({ onAddToBill, quantity, setQuantity}) => {
  const [brand, setBrand] = useState(""); // Holds the selected brand
  const [size, setSize] = useState(""); // Holds the entered size
  const [brands, setBrands] = useState([]); // Holds the list of available brands
  const [results, setResults] = useState([]); // Holds search results
  const [error, setError] = useState(""); // Error message for search failures
  const [loadingBrands, setLoadingBrands] = useState(false); // Loading state for fetching brands

  const branchId = 1; // Hardcoded branch ID for now


  async function getBrands(){
    return fetchBrands("Tyre").then((result) => {
        return result;
      }).catch((error) => {
        return null; 
      });
  }

  async function getBrandAndSizeData(){
    return fetchBrandAndSizeData(brand,size,branchId).then((result) => {
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
        // Set brands using for-of loop
        setBrands(response.data.map((brand) => brand.brandName));
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
      const response = await getBrandAndSizeData();
      
      
      if (response.status === 200 && Array.isArray(response.data)) {
        setResults(response.data); // Set search results
        console.log("Search results:", results);
        
      } else {
        setResults([]); // Clear results if no content
        setError("No results found.");
      }
    } catch (err) {
      console.error("Search request failed:", err);
      setError("Failed to fetch search results. Please check your inputs.");
    }
  };

  const handleAddToBill = (row) => {
    if (quantity > 0) {
        const unitPrice = parseFloat(row.officialSellingPrice) || 0;
        onAddToBill({
            description: `${row.itemTyreDTO.tyreSize} ${brand}`,
            unitPrice: unitPrice,
            quantity: quantity,
            subtotal: unitPrice * quantity,
        });
    }
};

  

  return (
    <Box sx={{ display: "flex", flexDirection: "column", gap: 2, mb: 3 }}>
      <Typography variant="h5" sx={{ fontWeight: "bold", textAlign: "center" }}>
        Search 
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
                <TableCell>Store Count</TableCell>
                <TableCell>Quantity</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {results.map((result, index) => (
                <TableRow key={index}>
                  <TableCell>{brand}</TableCell>
                  <TableCell>{result.itemTyreDTO.tyreSize}</TableCell>
                  <TableCell>{result.itemTyreDTO.pattern}</TableCell>
                  <TableCell>{result.itemDTO.companyPrice}</TableCell>
                  <TableCell>{result.itemBranchDTO.availableQuantity}</TableCell>
                  <TableCell>
              <TextField
                type="number"
                size="small"
                variant="outlined"
                placeholder="Qty"
                value={quantity} 
                onChange={(event) => setQuantity(event.target.value)}  // Update state on change
              />
            </TableCell>
                  <TableCell>
                    <Button
                      variant="contained"
                      color="secondary"
                      onClick={() => handleAddToBill(result)}
                    >
                      Add
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
    </Box>
  );

};

SearchComponent.propTypes = {
  //onSearchResult: PropTypes.func.isRequired,  // A required function for handling search results
  onAddToBill: PropTypes.func.isRequired,    // A required function for adding to the bill
  
 
  quantity: (props, propName, componentName) => {
    const value = props[propName];

  // Allow null values
  if (value === null) {
    return null;
  }

  // Check if the number is positive
  if (value !== null && value < 0) {
    return new Error(
      `${propName} in ${componentName} must be a positive number. Received: ${value}`
    );
  }

  return null; 
  },   
  setQuantity: PropTypes.func.isRequired,    // A required function for setting the quantity
};


export default SearchComponent;
