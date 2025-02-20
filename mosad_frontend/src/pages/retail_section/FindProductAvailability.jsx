import Grid2 from '@mui/material/Grid2'; // Import Grid2
import React, { useState } from "react";
import { Box, Typography } from "@mui/material";
import SearchComponent from "../../component/SearchComponent";
import ProductCardComponent from "../../component/ProductCardComponent";

const FindProductAvailability = () => {
  // State for filters and search results
  const [selectedBranch, setSelectedBranch] = useState(null); // Not used anymore
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [selectedBrand, setSelectedBrand] = useState(null);
  const [searchedResults, setSearchedResults] = useState([]);
  const [quantity, setQuantity] = useState(0);

  // Function to simulate fetching items based on selected filters
  const fetchAndSetItems = async () => {
    try {
      // Mock API Call (Replace this with actual API call)
      const mockData = [
        { id: 1, category: "Tyre", brand: "BrandA", size: "16 inch", ptr: 200 },
        { id: 2, category: "Tyre", brand: "BrandB", size: "18 inch", ptr: 250 },
        { id: 3, category: "Oil", brand: "BrandC", size: "5L", ptr: 100 },
      ];

      // Filter data based on selected criteria
      const filteredResults = mockData.filter(
        (item) =>
          (!selectedCategory || item.category === selectedCategory) &&
          (!selectedBrand || item.brand === selectedBrand)
      );

      setSearchedResults(filteredResults);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };
  return (
    <Box sx={{ padding: 3 }}>
      {/* Search & Filter Component */}
      <SearchComponent
        setSelectedCategory={setSelectedCategory}
        setSelectedBrand={setSelectedBrand}
        fetchandSetItems={fetchAndSetItems}
        quantity={quantity}
        setQuantity={setQuantity}
        handleSearchChange={() => {}} // If necessary, pass actual handler
      />

      {/* Display Search Results as Tiles */}
      <Typography variant="h6" sx={{ marginTop: 3, marginBottom: 2 }}>
        Search Results:
      </Typography>
      <Grid2 container spacing={2}>
        {searchedResults.length > 0 ? (
          searchedResults.map((product) => (
            <Grid2 xs={12} sm={6} md={4} key={product.id}>
              <ProductCardComponent
                category={product.category}
                brand={product.brand}
                size={product.size}
                ptr={product.ptr}
              />
            </Grid2>
          ))
        ) : (
          <Grid2 xs={12}>
            <Typography>No results found.</Typography>
          </Grid2>
        )}
      </Grid2>
    </Box>
  );
};

export default FindProductAvailability;
