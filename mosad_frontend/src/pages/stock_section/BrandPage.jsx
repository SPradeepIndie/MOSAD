import React, { useState, useEffect } from 'react';
import Tile from '../../component/Tile';
import { Box, Grid2, Dialog, DialogTitle, DialogContent, DialogActions, TextField, Button, Alert } from '@mui/material';
import { useLocation } from 'react-router-dom';
import PropTypes from 'prop-types';
import { getBrands, addBrand } from '../../services/apiStockService';

// Icons for dynamic brands
import DescriptionIcon from '@mui/icons-material/Description';
import InventoryIcon from '@mui/icons-material/Inventory';
import StorefrontIcon from '@mui/icons-material/Storefront';
import CreditCardIcon from '@mui/icons-material/CreditCard';
import AddIcon from '@mui/icons-material/Add';

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
  const [dialogOpen, setDialogOpen] = useState(false);
  const [newBrandName, setNewBrandName] = useState('');
  const [successMessage, setSuccessMessage] = useState(null);

  useEffect(() => {
    const fetchBrands = async () => {
      try {
        const response = await getBrands(states.category);
        setBrands(response.data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchBrands();
  }, [states.category]);

  const handleAddBrand = async () => {
    if (!newBrandName.trim()) {
      setError('Brand name cannot be empty.');
      return;
    }

    try {
      const payload = {
        brandDTO: {
          brandName: newBrandName,
        },
        category: {
          categoryName: states.category,
        },
      };
      await addBrand(payload);
      setBrands((prev) => [...prev, { brandName: newBrandName }]);
      setNewBrandName('');
      setError(null);
      setDialogOpen(false); // Close the dialog after success
      setSuccessMessage(`Brand "${newBrandName}" added successfully!`);
      setTimeout(() => setSuccessMessage(null), 2000);
    } catch (err) {
      setError('Failed to add brand. Please try again.');
    }
  };

  if (loading) {
    return <h2 style={{ textAlign: 'center' }}>Loading...</h2>;
  }

  return (
    <>
      <h1 style={{ textAlign: 'center' ,color:'black'}}>Select a Brand</h1>

      {/* Success or Error Messages */}
      {successMessage && (
        <Alert severity="success" sx={{ marginTop: 2, zIndex: 1300 }}>
          {successMessage}
        </Alert>
      )}
      {error && (
        <Alert severity="error" sx={{ marginTop: 2, zIndex: 1300 }}>
          {error}
        </Alert>
      )}
      
      <Box sx={{ marginTop: 4 }}>
        <Grid2 container spacing={4} justifyContent="center">
          {brands.map((brand) => (
            <Grid2 xs={12} sm={6} md={4} key={brand.brandName}>
              <Tile
                title={brand.brandName}
                icon={iconMap[brand.brandName] || <DescriptionIcon fontSize="large" />}
                link={`${isFromBranch ? '/branch/stock/brand/item-view' : '/stock/item-view'}`}
                state={{ ...states, brand: brand.brandName }}
              />
            </Grid2>
          ))}
          {/* Add New Brand Tile */}
          <Grid2 xs={12} sm={6} md={4}>
            <Tile
              title="Add New Brand"
              icon={<AddIcon fontSize="large" />}
              onClick={() => setDialogOpen(true)}
            />
          </Grid2>
        </Grid2>
      </Box>

      {/* Dialog for Adding New Brand */}
      <Dialog open={dialogOpen} onClose={() => setDialogOpen(false)}>
        <DialogTitle>Add New Brand</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            label="Brand Name"
            fullWidth
            variant="outlined"
            value={newBrandName}
            onChange={(e) => setNewBrandName(e.target.value)}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setDialogOpen(false)} color="secondary">
            Cancel
          </Button>
          <Button onClick={handleAddBrand} color="primary">
            Add
          </Button>
        </DialogActions>
      </Dialog>

      
    </>
  );
}

BrandPage.propTypes = {
  isFromBranch: PropTypes.bool.isRequired,
};

export default BrandPage;
