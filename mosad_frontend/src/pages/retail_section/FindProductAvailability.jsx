import React, { useState, useEffect } from 'react';
import { Box, Button, Typography, FormControl, InputLabel, Select, MenuItem, Grid } from '@mui/material';
import { useFetchCategories, useFetchBrands, useFetchItems } from '../../hooks/servicesHook/useStockService';
import ProductCardComponent from './components/ProductCardComponent';


const FindProductAvailability = () => {

  const fetchCategories=useFetchCategories();
  const fetchItems = useFetchItems();
  const fetchBrands = useFetchBrands();
  
  const [categories, setCategories] = useState([]);
  const [brands, setBrands] = useState([]);
  const [tyreSizes, setTyreSizes] = useState([]);
  const [ptrOptions, setPtrOptions] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState('');
  const [selectedBrand, setSelectedBrand] = useState('');
  const [selectedTyreSize, setSelectedTyreSize] = useState('');
  const [selectedPTR, setSelectedPTR] = useState('');
  const [products, setProducts] = useState([]);
  const [filteredProducts, setFilteredProducts] = useState([]);

  useEffect(() => {
    fetchCategories()
      .then((response) => setCategories(response.data))
      .catch((error) => console.error('Error fetching categories:', error));
  }, []);

  useEffect(() => {
    if (selectedCategory) {
      fetchBrands(selectedCategory)
        .then((response) => setBrands(response.data))
        .catch((error) => console.error('Error fetching brands:', error));

      if (selectedCategory.toLowerCase() === 'tyre') {
        fetchItems({ category: selectedCategory })
          .then((response) => {
            setTyreSizes(response.data.tyreSizes || []);
            setPtrOptions(response.data.ptrOptions || []);
          })
          .catch((error) => console.error('Error fetching tyre and PTR data:', error));
      } else {
        setTyreSizes([]);
        setPtrOptions([]);
      }
    } else {
      setBrands([]);
      setTyreSizes([]);
      setPtrOptions([]);
    }
  }, [selectedCategory]);

  const handleFindNow = () => {
    fetchItems({ category: selectedCategory, brand: selectedBrand, size: selectedTyreSize, ptr: selectedPTR })
      .then((response) => {
        setProducts(response.data);
        setFilteredProducts(response.data);
      })
      .catch((error) => console.error('Error fetching products:', error));
  };

  const filterProducts = () => {
    let filtered = products;
    if (selectedTyreSize) {
      filtered = filtered.filter((product) => product.size === selectedTyreSize);
    }
    if (selectedPTR) {
      filtered = filtered.filter((product) => product.ptr === selectedPTR);
    }
    setFilteredProducts(filtered);
  };

  useEffect(() => {
    filterProducts();
  }, [selectedTyreSize, selectedPTR]);



  return (
    <Box sx={{ padding: 4 }}>
      <Typography variant="h4" sx={{ marginBottom: 4, textAlign: 'center' }}>
        Find Product Availability
      </Typography>

      <Grid container spacing={2} sx={{ marginBottom: 4 }}>
        {/* Category Dropdown */}
        <Grid item xs={12} sm={6} md={3}>
          <FormControl fullWidth>
            <InputLabel id="category-select-label">Category</InputLabel>
            <Select
              labelId="category-select-label"
              id="category-select"
              value={selectedCategory}
              onChange={(e) => setSelectedCategory(e.target.value)}
            >
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
              {categories.map((category) => (
                <MenuItem key={category.id} value={category.name}>
                  {category.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>

        {/* Brand Dropdown */}
        <Grid item xs={12} sm={6} md={3}>
          <FormControl fullWidth disabled={!selectedCategory}>
            <InputLabel id="brand-select-label">Brand</InputLabel>
            <Select
              labelId="brand-select-label"
              id="brand-select"
              value={selectedBrand}
              onChange={(e) => setSelectedBrand(e.target.value)}
            >
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
              {brands.map((brand) => (
                <MenuItem key={brand.id} value={brand.name}>
                  {brand.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Grid>

        {/* Conditional Fields for Tyre */}
        {selectedCategory.toLowerCase() === 'tyre' && (
          <>
            {/* Tyre Size Dropdown */}
            <Grid item xs={12} sm={6} md={3}>
              <FormControl fullWidth>
                <InputLabel id="tyre-size-select-label">Tyre Size</InputLabel>
                <Select
                  labelId="tyre-size-select-label"
                  id="tyre-size-select"
                  value={selectedTyreSize}
                  onChange={(e) => setSelectedTyreSize(e.target.value)}
                >
                  <MenuItem value="">
                    <em>None</em>
                  </MenuItem>
                  {tyreSizes.map((size, index) => (
                    <MenuItem key={index} value={size}>
                      {size}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Grid>

            {/* PTR Dropdown */}
            <Grid item xs={12} sm={6} md={3}>
              <FormControl fullWidth>
                <InputLabel id="ptr-select-label">PTR</InputLabel>
                <Select
                  labelId="ptr-select-label"
                  id="ptr-select"
                  value={selectedPTR}
                  onChange={(e) => setSelectedPTR(e.target.value)}
                >
                  <MenuItem value="">
                    <em>None</em>
                  </MenuItem>
                  {ptrOptions.map((ptr, index) => (
                    <MenuItem key={index} value={ptr}>
                      {ptr}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Grid>
          </>
        )}

        {/* Find Now Button */}
        <Grid item xs={12} sm={6} md={3} display="flex" alignItems="center">
          <Button variant="contained" onClick={handleFindNow} fullWidth disabled={!selectedCategory} >
            Find Now
          </Button>
        </Grid>
      </Grid>

      {/* Product Results */}
      <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 2, justifyContent: 'center' }}>
        {filteredProducts.map((product) => (
          <ProductCardComponent
            key={product.id}
            category={product.category}
            brand={product.brand}
            size={product.size}
            ptr={product.ptr}
          />
        ))}
      </Box>
    </Box>
  );
};

export default FindProductAvailability;
