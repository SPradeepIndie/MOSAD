import React, { useState, useEffect } from 'react';
import Tile from '../../component/Tile';
import { Box, Stack } from '@mui/material';
import DescriptionIcon from '@mui/icons-material/Description';
import InventoryIcon from '@mui/icons-material/Inventory';
import StorefrontIcon from '@mui/icons-material/Storefront';
import CreditCardIcon from '@mui/icons-material/CreditCard';
import { useLocation } from 'react-router-dom';
import PropTypes from 'prop-types';
import { getBrands } from '../../services/apiStockService';

// Icon mapping for dynamic brands (can be expanded)
const iconMap = {
  CEAT: <DescriptionIcon fontSize="large" />,
  PRESA: <InventoryIcon fontSize="large" />,
  LINGLONG: <StorefrontIcon fontSize="large" />,
  RAPID: <CreditCardIcon fontSize="large" />,
};

function BrandPage({ isFromBranch }) {
  const location = useLocation();
  const states = location.state;

  const [brands, setBrands] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchBrands = async () => {
      try {
        const response = await getBrands(states.Category);
        
        setBrands(response.data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchBrands();
  }, []);

  if (loading) {
    return <h2 style={{ textAlign: 'center' }}>Loading brands...</h2>;
  }

  if (error) {
    return <h2 style={{ textAlign: 'center', color: 'red' }}>Error: {error}</h2>;
  }

  return (
    <Box sx={{ marginTop: 4, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
      {/* Display the brands in rows dynamically */}
      <Stack direction="row" flexWrap="wrap" justifyContent="center" gap={4}>
        {brands.map((brand, index) => (
          <Tile
            key={index}
            title={brand.brandName} // Assume API response has a `brandName` field
            icon={iconMap[brand.brandName] || <DescriptionIcon fontSize="large" />} // Default icon if not mapped
            link={`${isFromBranch ? "/branch/stock/brand/item-view" : "/stock/item-view"}`}
            state={{ ...states, brand: brand.brandName }}
          />
        ))}
      </Stack>
    </Box>
  );
}

BrandPage.propTypes = {
  isFromBranch: PropTypes.bool.isRequired,
};

export default BrandPage;
