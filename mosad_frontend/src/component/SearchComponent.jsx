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
  Grid2
} from "@mui/material";
import { fetchBrands,fetchBrandAndSizeData,fetchCategories } from "../services/apiStockService";


const SearchComponent = ({ onAddToBill , quantity , setQuantity,setSelectedBranch,setSelectedCategory,setSelectedBrand,fetchandSetItems,handleSearchChange}) => {
  const [category,setCategory] = useState("Tyre"); // Holds the selected category
  const [brand, setBrand] = useState(""); // Holds the selected brand
  const [branch, setBranch] = useState( { branchId: 1, branchName: "Main" }); // Holds the selected branch
  
  const [size, setSize] = useState(""); // Holds the entered size
  const [name, setName] = useState(""); // Holds the entered name
  const [type, setType] = useState(""); // Holds the entered type
  const [categories, setCategories] = useState([]); // Holds the list of available categories
  const [brands, setBrands] = useState([]); // Holds the list of available brands
  const [results, setResults] = useState([]); // Holds search results
  const [error, setError] = useState(""); // Error message for search failures
  const [loadingBrands, setLoadingBrands] = useState(false); // Loading state for fetching brands

  const branches = [
    { branchId: 1, branchName: "Main" },
    { branchId: 2, branchName: "Branch A" },
    { branchId: 3, branchName: "Branch B" },
    { branchId: 4, branchName: "Branch C" },
  ]; // Sample branches

  async function getBrands(){
    return fetchBrands(category).then((result) => {
        return result;
      }).catch((error) => {
        return null; 
      });
  }

  async function getBrandAndSizeData(){
    return fetchBrandAndSizeData(category,brand,name,size,branch.branchId).then((result) => {
        return result;
      }).catch((error) => {
        return null;
      });
  }

  async function getCategories(){
    return fetchCategories().then((result) => {
        return result;
      }).catch((error) => {
        return null;
      });
  }


  const loadbrands = async () => {
    try {
      const response = await getBrands();
      if (response.status === 200 && Array.isArray(response.data)) {
        // Set brands using for-of loop
        setBrands(response.data.map((brand) => brand.brandName));
      } else {
        setBrands([]); // Default to an empty array if response is unexpected
        console.error("Unexpected response from the server:", response);
      }
    } catch (err) {
      console.error("Failed to fetch brands:", err);
      setError("Failed to load brands. Please try again.");
    } finally {
      setLoadingBrands(false);
    }
  }   
  const loadcategories = async () => {
    try {
      const response = await getCategories();
      if (response.status === 200 && Array.isArray(response.data)) {
        // Set brands using for-of loop
        setCategories(response.data.map((category) => category.categoryName));
      } else {
        setCategories([]); // Default to an empty array if response is unexpected
        console.error("Unexpected response from the server:", response);
      }
    } catch (err) {
      console.error("Failed to fetch categories:", err);
      setError("Failed to load categories. Please try again.");
    } finally {
      setLoadingBrands(false);
    }
  }

  // Fetch available brands when the component mounts
  useEffect(() => {
    
  loadcategories();

  loadbrands();
    
  }, []);
useEffect(() => {
  loadbrands();
}, [category]);


  const handleFilter=()=>{
  console.log("handleFilter clicked");
   try {
      setSelectedBranch(branch.branchId);
      setSelectedCategory(category);
      setSelectedBrand(brand);
     
      fetchandSetItems();
      console.log("handleFilter finished");
   } catch (err) {
      console.error("Error during filter operation:", err);
   }
  }

  const handleSearch = async () => {
    console.log("Search clicked");

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

      <Grid2 container gap={2} direction="row" rowSpacing={{ xs: 1, sm: 2, md: 3 }} columnSpacing={{ xs: 1, sm: 2, md: 3 }}>
        <Grid2 size={3} gap={2} spacing={3} direction="column">

          <FormControl fullWidth>
            <InputLabel id="branch-select-label">Branch</InputLabel>
            <Select
              labelId="branch-select-label"
              value={branch.branchId} // Use branchId here
              onChange={(e) => {
                const selectedBranch = branches.find(b => b.branchId === e.target.value);
                setBranch(selectedBranch); // Set the whole object
                if(setSelectedBranch){
                  setSelectedBranch(selectedBranch.branchId);
                }
                
              }}
              variant="outlined"
            >
              {branches.map((b) => (
                <MenuItem key={b.branchId} value={b.branchId}>
                  {b.branchName}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

        </Grid2>
        <Grid2 size={3} gap={2} spacing={3} direction="column">
          <FormControl fullWidth>
            <InputLabel id="category-select-label">Category</InputLabel>
            <Select
              labelId="category-select-label"
              value={category}
              onChange={(e) => { 
                  setCategory(e.target.value); 
                  if(setSelectedCategory){
                    setSelectedCategory(e.target.value); 
                  }
                }
              }
              variant="outlined"
            >
              {categories.map((c) => (
                <MenuItem key={c} value={c}>
                  {c}
                </MenuItem>
              ))}
            </Select>


          </FormControl>
        </Grid2>
        <Grid2 size={3} gap={2} spacing={3} direction="column">

          <FormControl fullWidth>
            <InputLabel id="brand-select-label">Brand</InputLabel>
            <Select
              labelId="brand-select-label"
              value={brand}
              onChange={(e) => { 
                setBrand(e.target.value); 
                setSelectedBrand(e.target.value); 
                if (fetchandSetItems) {
                  fetchandSetItems();
                }
              
              }}
              variant="outlined"
            >
              {brands.map((b) => (
                <MenuItem key={b} value={b}>
                  {b}
                </MenuItem>
              ))}
            </Select>


          </FormControl>
        </Grid2>
      </Grid2>

      <Grid2 container gap={2} direction="row" rowSpacing={{ xs: 1, sm: 2, md: 3 }} columnSpacing={{ xs: 1, sm: 2, md: 3 }}>

        <Grid2 >
          <TextField
            label="Name"
            variant="outlined"
            name="itemName"
            value={name}
            onChange={(e) => { setName(e.target.value); handleSearchChange(e); }}
            fullWidth
          />
        </Grid2>

        {category == "Tyre" &&
          <>
            <Grid2 >
              <TextField
                label="Tyre Size"
                variant="outlined"
                name="tyreSize"
                value={size}
                onChange={(e) => { setSize(e.target.value); handleSearchChange(e); }}
                fullWidth
              />
            </Grid2>

            <Grid2 >
              <TextField
                label="Vehicle Type"
                variant="outlined"
                name="vehicleType"
                value={type}
                onChange={(e) => { setType(e.target.value); handleSearchChange(e); }}
                fullWidth
              />
            </Grid2>
          </>}
      </Grid2>
      



      {onAddToBill && <Button
        variant="contained"
        color="primary"
        onClick={onAddToBill ? handleSearch : handleFilter}
        disabled={loadingBrands || !brand}
        
      >
        Search
      </Button>}

      {error && <Typography color="error">{error}</Typography>}

      {/* Results Table */}
      {onAddToBill && results.length > 0 && (
        <TableContainer component={Paper} sx={{ mt: 2 }}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Brand</TableCell>
                <TableCell>Price</TableCell>
                <TableCell>Store Count</TableCell>

                {category==="Tyre" &&
                  <>
                <TableCell>Size</TableCell>
                <TableCell>Pattern</TableCell>
                </>
                }
                
                <TableCell>Quantity</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {results.map((result) => (
                <TableRow key={result.id}>
                  <TableCell>{brand}</TableCell>
                  <TableCell>{result.itemDTO.companyPrice}</TableCell>
                  <TableCell>{result.itemBranchDTO.availableQuantity}</TableCell>
                  { category==="Tyre" && result.itemTyreDTO &&
                   <>
                    <TableCell>{result.itemTyreDTO.tyreSize}</TableCell>
                    <TableCell>{result.itemTyreDTO.pattern}</TableCell>
                   </>
                  }
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
  setSelectedBranch: PropTypes.func.isRequired, // A required function for setting the selected branch
  setSelectedCategory: PropTypes.func.isRequired, // A required function for setting the selected category
  setSelectedBrand: PropTypes.func.isRequired, // A required function for setting the selected brand
  fetchandSetItems: PropTypes.func.isRequired, // A required function for fetching and setting items
  handleFilterChange: PropTypes.func.isRequired, // A required function for handling filter changes
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
