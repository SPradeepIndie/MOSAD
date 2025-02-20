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
import { fetchBrands,fetchBrandAndSizeData,fetchCategories,addCategory,addBrand,fetchBranches } from "../services/apiStockService";
import AddIcon from '@mui/icons-material/Add';
import IconButton from '@mui/material/IconButton';
import PopUp from "./PopUp";
import GeneralMessage from "./GeneralMessage";







const SearchComponent = ({ onAddToBill , quantity , setQuantity,setSelectedBranch,setSelectedCategory,setSelectedBrand,fetchandSetItems,handleSearchChange}) => {
  const [category,setCategory] = useState("Tyre"); // Holds the selected category
  const [brand, setBrand] = useState(""); // Holds the selected brand
  const [branch, setBranch] = useState( { branchId: 1, branchName: "Main" }); // Holds the selected branch
  const [branches, setBranches] = useState([]); // Holds the list of available branches
  
  const [size, setSize] = useState(""); // Holds the entered size
  const [name, setName] = useState(""); // Holds the entered name
  const [type, setType] = useState(""); // Holds the entered type
  const [categories, setCategories] = useState([]); // Holds the list of available categories
  const [brands, setBrands] = useState([]); // Holds the list of available brands
  const [results, setResults] = useState([]); // Holds search results
  const [error, setError] = useState(""); // Error message for search failures
  const [loadingBrands, setLoadingBrands] = useState(false); // Loading state for fetching brands

  const [newBrandName, setNewBrandName] = useState('');
  const [newCategoryName,setNewCategoryName] = useState('');
  const [message, setMessage] = useState(null);
    
  const [isAddingBrand, setIsAddingBrand] = useState(false);
  const [isAddingCategory, setIsAddingCategory] = useState(false);




  const loadBranches = async () => {
    try {
      const response = await fetchBranches();
      if (response.status === 200 && Array.isArray(response.data)) {
        // Set branches using for-of loop
        setBranches(response.data);
      } else {
        setBranches([]); // Default to an empty array if response is unexpected
        console.error("Unexpected response from the server:", response);
      }
    } catch (err) {
      console.error("Failed to fetch branches:", err);
      setError("Failed to load branches. Please try again.");
    } finally {
      setLoadingBrands(false);
    }
    
  };

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

  const handleAddCategory = async () => {
    
    setIsAddingCategory(true);
    setIsAddingBrand(false);
    

    if(newCategoryName !== ""){
    try {

      await addCategory(newCategoryName);
      
      setCategories([...categories, newCategoryName]);
      setNewCategoryName("");
      setIsAddingCategory(false);
      setMessage({type: 'success', text:"Category added successfully!"});
      setTimeout(() => setMessage(null), 2000);


      
    } catch (err) {
      console.error("Failed to add category:", err);
      
      setMessage({type: 'error', text:"Failed to add category !"});
      setTimeout(() => setMessage(null), 2000);
    }
  }
  };

  const handleAddBrand = async () => {
    
    setIsAddingBrand(true);
    setIsAddingCategory(false);
    
    if(newBrandName !== "") {
    try {
      const payload = {
              brandDTO: {
                brandName: newBrandName,
              },
              category: {
                categoryName:category,
              },
            };
      await addBrand(payload);
      
      console.log("Brand added successfully:");
      setBrands([...brands, newBrandName]);
      setIsAddingBrand(false);
      setNewBrandName("");
      setMessage({type: 'success', text:"Brand  added successfully!"});
      setTimeout(() => setMessage(null), 2000);
      
    } catch (err) {
      console.error("Failed to add brand:", err);
      setMessage({type: 'error', text:"Failed to add brand !"});
      setTimeout(() => setMessage(null), 2000);
    }
  }
  };


  // Fetch available brands when the component mounts
  useEffect(() => {

  loadBranches();

  loadcategories();

  loadbrands();

    
  }, []);
useEffect(() => {
  loadbrands();
}, [category]);

useEffect(() => {
  setResults([]);
  setSize("");
  setName("");
  setType("");

}, [category,brand]);


  const handleFilter=()=>{
  
   try {
      setSelectedBranch(branch.branchId);
      setSelectedCategory(category);
      setSelectedBrand(brand);
     
      fetchandSetItems();
      
   } catch (err) {
      console.error("Error during filter operation:", err);
   }
  }

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
        const unitPrice = parseFloat(row.itemDTO.companyPrice) || 0;
        onAddToBill({
            brand: row.itemDTO.brand,
            description: row.itemTyreDTO? `${row.itemTyreDTO.tyreSize} ${brand}` : `${row.itemDTO.itemName} ${brand}`,
            unitPrice: unitPrice,
            quantity: quantity,
            subtotal: unitPrice * quantity,
        });
    }
};


  

  return (
   <> 
   {message && <GeneralMessage message={message} />}
    <Grid2 xs={12}>
    <Box sx={{ display: "flex", flexDirection: "column", gap: 2, mb: 3, borderBottom: 1, borderColor: 'grey.500', borderRadius: 1, p: 2, boxShadow: 3 }}>
      <Typography variant="h5" sx={{ fontWeight: "bold", textAlign: "center" }}>
        Search
      </Typography>
  
      <Grid2 container gap={1} direction="row" rowSpacing={{ xs: 1, sm: 2, md: 3 }} columnSpacing={{ xs: 1, sm: 2, md: 3 }} justifyContent="space-between">
        
        {/* Branch Select with "+" Icon */}
        <Grid2 xs={12} sm={6} md={3} sx={{ display: "flex", alignItems: "center" }}>
          <FormControl fullWidth sx={{ minWidth: 120 }}>
            <InputLabel id="branch-select-label">Branch</InputLabel>
            <Select
              labelId="branch-select-label"
              value={branch.branchId}
              onChange={(e) => {
                const selectedBranch = branches.find(b => b.branchId === e.target.value);
                setBranch(selectedBranch);
                if (setSelectedBranch) {
                  setSelectedBranch(selectedBranch.branchId);
                }
              }}
              variant="outlined"
              fullWidth
            >
              {branches.map((b) => (
                <MenuItem key={b.branchId} value={b.branchId}>
                  {b.branchName}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
         
        </Grid2>
  
        {/* Category Select with "+" Icon */}
        <Grid2 xs={12} sm={6} md={3} sx={{ display: "flex", alignItems: "center" }}>
          <FormControl fullWidth sx={{ minWidth: 120 }}>
            <InputLabel id="category-select-label">Category</InputLabel>
            <Select
              labelId="category-select-label"
              value={category}
              onChange={(e) => {
                setCategory(e.target.value);
                if (setSelectedCategory) {
                  setSelectedCategory(e.target.value);
                }
              }}
              variant="outlined"
              fullWidth
            >
              {categories.map((c) => (
                <MenuItem key={c} value={c}>
                  {c}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          {/* Plus Icon for Category */}
          <IconButton
            color="primary"
            onClick={() => {handleAddCategory();}}
            sx={{ ml: 1 }}
          >
            <AddIcon />
          </IconButton>
        </Grid2>
  
        {/* Brand Select with "+" Icon */}
        <Grid2 xs={12} sm={6} md={3} sx={{ display: "flex", alignItems: "center" }}>
          <FormControl fullWidth sx={{ minWidth: 120 }}>
            <InputLabel id="brand-select-label">Brand</InputLabel>
            <Select
              labelId="brand-select-label"
              value={brand}
              onChange={(e) => {
                setBrand(e.target.value);
                if (fetchandSetItems && setSelectedBrand) {
                  setSelectedBrand(e.target.value);
                  fetchandSetItems();
                }
              }}
              variant="outlined"
              fullWidth
            >
              {brands.map((b) => (
                <MenuItem key={b} value={b}>
                  {b}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          {/* Plus Icon for Brand */}
          <IconButton
            color="primary"
            onClick={() => {handleAddBrand();}}
            sx={{ ml: 1 }}
          >
            <AddIcon />
          </IconButton>
        </Grid2>
  
      </Grid2>
  
      <Grid2 container gap={2} direction="row" rowSpacing={{ xs: 1, sm: 2, md: 3 }} columnSpacing={{ xs: 1, sm: 2, md: 3 }} justifyContent="space-between">
        <Grid2 xs={12} sm={6} md={4}>
          <TextField
            label="Name"
            variant="outlined"
            name="itemName"
            value={name}
            onChange={(e) => { setName(e.target.value); handleSearchChange(e); }}
            fullWidth
          />
        </Grid2>
  
        {category === "Tyre" && (
          <>
            <Grid2 xs={12} sm={6} md={4}>
              <TextField
                label="Tyre Size"
                variant="outlined"
                name="tyreSize"
                value={size}
                onChange={(e) => { setSize(e.target.value); handleSearchChange(e); }}
                fullWidth
              />
            </Grid2>
  
            <Grid2 xs={12} sm={6} md={4}>
              <TextField
                label="Vehicle Type"
                variant="outlined"
                name="vehicleType"
                value={type}
                onChange={(e) => { setType(e.target.value); handleSearchChange(e); }}
                fullWidth
              />
            </Grid2>
          </>
        )}
      </Grid2>
  
      {onAddToBill && (
        <Button
          variant="contained"
          color="primary"
          onClick={onAddToBill ? handleSearch : handleFilter}
          disabled={loadingBrands || !brand}
          fullWidth
        >
          Search
        </Button>
      )}
  
      {error && <Typography color="error">{error}</Typography>}
  
      {onAddToBill && results.length > 0 && (
        <TableContainer component={Paper} sx={{ mt: 2 }}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>Brand</TableCell>
                <TableCell>Name</TableCell>
                <TableCell>Price</TableCell>
                <TableCell>Store Count</TableCell>
                {category === "Tyre" && (
                  <>
                    <TableCell>Size</TableCell>
                    <TableCell>Pattern</TableCell>
                  </>
                )}
                <TableCell>Quantity</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {results.map((result) => (
                <TableRow key={result.id}>
                  <TableCell>{brand}</TableCell>
                  <TableCell>{result.itemDTO.itemName}</TableCell>
                  <TableCell>{result.itemDTO.companyPrice}</TableCell>
                  <TableCell>{result.itemBranchDTO.availableQuantity}</TableCell>
                  {category === "Tyre" && result.itemTyreDTO && (
                    <>
                      <TableCell>{result.itemTyreDTO.tyreSize}</TableCell>
                      <TableCell>{result.itemTyreDTO.pattern}</TableCell>
                    </>
                  )}
                  <TableCell>
                    <TextField
                      type="number"
                      size="small"
                      variant="outlined"
                      placeholder="Qty"
                      value={quantity}
                      onChange={(event) => setQuantity(event.target.value)}
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
  </Grid2>

      {isAddingBrand && <PopUp
        popUpTitle= { "Add New Brand"}
        openPopup={isAddingBrand}
        setOpenPopup={setIsAddingBrand}
        setOkButtonAction={handleAddBrand}
        setCancelButtonAction={() => setIsAddingBrand(false)}
        isDefaultButtonsDisplay={true}
      >
        <TextField
          autoFocus
          margin="dense"
          label="Brand Name"
          fullWidth
          variant="outlined"
          value={newBrandName}
          onChange={(e) => {
            setNewBrandName(e.target.value);
          }}
        />

      </PopUp>}

      {isAddingCategory && <PopUp
        popUpTitle= { "Add New Category"}
        openPopup={isAddingCategory}
        setOpenPopup={setIsAddingCategory}
        setOkButtonAction={handleAddCategory}
        setCancelButtonAction={() => setIsAddingCategory(false)}
        isDefaultButtonsDisplay={true}
      >
        <TextField
          autoFocus
          margin="dense"
          label="Category Name"
          fullWidth
          variant="outlined"
          value={newCategoryName}
          onChange={(e) => {
            setNewCategoryName(e.target.value);
          }}
        />

      </PopUp>}

  
  
  
   </>   
  
  );

};

SearchComponent.propTypes = {
  setSelectedBranch: PropTypes.func.isRequired, // A required function for setting the selected branch
  setSelectedCategory: PropTypes.func.isRequired, // A required function for setting the selected category
  setSelectedBrand: PropTypes.func.isRequired, // A required function for setting the selected brand
  fetchandSetItems: PropTypes.func.isRequired, // A required function for fetching and setting items
  onAddToBill: PropTypes.func.isRequired,    // A required function for adding to the bill
  handleSearchChange: PropTypes.func.isRequired,    // A required function for handling search changes
  
 
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
