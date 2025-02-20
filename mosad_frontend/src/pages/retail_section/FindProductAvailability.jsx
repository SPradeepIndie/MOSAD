import Grid2 from '@mui/material/Grid2'; // Import Grid2
import React, { useState } from "react";
import { Box, Typography } from "@mui/material";
import SearchComponent from "../../component/SearchComponent";
import ProductCardComponent from "../../component/ProductCardComponent";

const FindProductAvailability = () => {
  // State for filters and search results
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [selectedBrand, setSelectedBrand] = useState(null);
  const [size, setSize] = useState(""); // Store user-inputted size
  const [vehicleType, setVehicleType] = useState(""); // Store user-inputted vehicle type
  const [searchedResults, setSearchedResults] = useState([]);
  const [quantity, setQuantity] = useState(0);

  // Function to simulate fetching items based on selected filters
  const fetchAndSetItems = async () => {
    try {
      // Mock API Call (Replace this with actual API call)
      const mockData = [
        { id: 1, category: "Tyre", brand: "DSI", size: "16", vehicleType: "SUV" },
        { id: 2, category: "Tyre", brand: "Presa", size: "18", vehicleType: "Truck" },
        { id: 3, category: "Tube", brand: "DSI", size: "", vehicleType: "" }
      ];

      // Filter data based on selected inputs
      const filteredResults = mockData.filter(
        (item) =>
          (!selectedCategory || item.category === selectedCategory) &&
          (!selectedBrand || item.brand === selectedBrand) &&
          (!size || item.size === size) &&
          (!vehicleType || item.vehicleType === vehicleType)
      );

      // Set filtered results
      setSearchedResults(filteredResults);
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  };

  // Trigger search when filters change
  const handleSearchChange = () => {
    fetchAndSetItems();
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
        handleSearchChange={handleSearchChange} // Trigger search on change
        onRetail={true}
        setSize={setSize} // Capture size input
        setVehicleType={setVehicleType} // Capture vehicle type input
      />

      {/* Display Search Results as Product Cards */}
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
                vehicleType={product.vehicleType}
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
