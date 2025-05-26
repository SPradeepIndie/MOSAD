import Grid2 from '@mui/material/Grid2'; // Import Grid2
import React, { useState } from "react";
import { Box, Typography } from "@mui/material";
import SearchComponent from "../../component/SearchComponent";
import ProductCardComponent from "../../component/ProductCardComponent";
import {useFetchItems} from "../../hooks/servicesHook/useStockService"; // Custom hook to fetch items

const FindProductAvailability = () => {
  const fetchItems = useFetchItems(); // Custom hook to fetch items
  // State for filters and search results
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [selectedBrand, setSelectedBrand] = useState(null);
  const [selectedBranch, setSelectedBranch] = useState(null); // Store selected branch
  const [size, setSize] = useState(""); // Store user-inputted size
  const [vehicleType, setVehicleType] = useState(""); // Store user-inputted vehicle type
  const [searchedResults, setSearchedResults] = useState([]);
  const [quantity, setQuantity] = useState(0);
  const [rows, setRows] = useState([]);

  

  // Function to simulate fetching items based on selected filters
  let mockData = [
    { id: 3, category: "Tyre", brand: "Atlander", size: "175/65", vehicleType: "Passenger Car" },
    { id: 5, category: "Tyre", brand: "Atlander", size: "184/90", vehicleType: "Three wheel" }
  ];
  

  const fetchAndSetItem = async () => {
    console.log("inside fetchAndSetItem function");
   

    if (selectedCategory && selectedBrand && selectedBranch ) {
      
      fetchItems({ params: { category: selectedCategory, brand: selectedBrand, branchId: selectedBranch, } })
        .then((response) => setRows(response.data))
        .catch((error) => console.error("Error fetching data:", error));
      console.log(rows);  

      //mockData = rows;
      console.log(mockData);
      // Filter mock data based on selected filters

      //map rows to mockData
      mockData = rows.map((item) => ({
        id: item.id,
        category: selectedCategory,
        brand: selectedBrand,
        size: item.itemTyreDTO.tyreSize,
        vehicleType: item.itemTyreDTO.vehicleType,
      }));


      const filteredResults = mockData.filter(
        (item) =>
          
          (!size || item.itemTyreDTO.tyreSize === size) &&
          (!vehicleType || item.itemTyreDTO.vehicleType === vehicleType)
      );
      
      

      // Set filtered results
      setSearchedResults(filteredResults);
      

    }
    else {
      console.error("Error fetching data: Category, Brand and Branch are required");
    }

  }

  // Trigger search when filters change
  const handleSearchChange = () => {
    fetchAndSetItem();
    fetchAndSetItem();
    fetchAndSetItem();
  };

  return (
    <Box sx={{ padding: 3 }}>
      {/* Search & Filter Component */}
      <SearchComponent
        setSelectedCategory={setSelectedCategory}
        setSelectedBrand={setSelectedBrand}
        setSelectedBranch={setSelectedBranch} // Set selected branch
        fetchandSetItems={fetchAndSetItem}
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
