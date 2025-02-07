import React, { useState, useEffect } from 'react';
import Tile from '../../component/Tile';
import { Box, Grid2, Dialog, DialogTitle, DialogContent, DialogActions, TextField, Button, Alert } from '@mui/material';
import { Outlet } from 'react-router-dom';
import PropTypes from 'prop-types';
import { fetchCategories, addCategory } from '../../services/apiStockService';

// Icons for dynamic categories
import DescriptionIcon from '@mui/icons-material/Description';
import InventoryIcon from '@mui/icons-material/Inventory';
import StorefrontIcon from '@mui/icons-material/Storefront';
import CreditCardIcon from '@mui/icons-material/CreditCard';
import AddIcon from '@mui/icons-material/Add';

const iconMap = {
  Tyre: <DescriptionIcon fontSize="large" />,
  Tube: <InventoryIcon fontSize="large" />,
  Tape: <StorefrontIcon fontSize="large" />,
  Battery: <CreditCardIcon fontSize="large" />,
};

function StockPage({ isFromBranch }) {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [newCategory, setNewCategory] = useState('');
  const [successMessage, setSuccessMessage] = useState(null);

  useEffect(() => {
    const getCategories = async () => {
      try {
        const response = await fetchCategories();
        setCategories(response.data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    getCategories();
  }, []);

  const handleAddCategory = async () => {
    if (!newCategory.trim()) {
      setError('Category name cannot be empty.');
      return;
    }

    try {
      await addCategory(newCategory);
      
      setCategories((prev) => [...prev, { categoryName: newCategory }]);
      setNewCategory('');
      setError(null);
      setDialogOpen(false); // Close the dialog after success
      setSuccessMessage(`Category "${newCategory}" added successfully!`);
      setTimeout(()=>setSuccessMessage(null),2000);
    } catch (err) {
      setError('Failed to add category. Please try again.');
    }
  };

  if (loading) {
    return <h2 style={{ textAlign: 'center' }}>Loading...</h2>;
  }
  return (
    <>
      <Outlet />
      <h1 style={{ textAlign: 'center',color:'black' }}>Select a Category</h1>

    {/* Success or Error Messages */}
    {successMessage && (
        <Alert severity="success" sx={{ marginTop: 2,zIndex:1300 }}>
          {successMessage}
        </Alert>
      )}
      {error && (
        <Alert severity="error" sx={{ marginTop: 2,zIndex:1300 }}>
          {error}
        </Alert>
      )}

      <Box sx={{ marginTop: 4 }}>
        <Grid2 container spacing={4} justifyContent="center">
          {categories.map((category) => (
            <Grid2
              xs={12}
              sm={6}
              md={4}
              key={category.categoryName}
            >
              <Tile
                title={category.categoryName}
                icon={iconMap[category.categoryName] || <DescriptionIcon fontSize="large" />}
                link={`${isFromBranch ? '/branch/stock/brand' : '/stock/brand'}`}
                state={{ category: category.categoryName }}
              />
            </Grid2>
          ))}
          {/* Add New Category Tile */}
          <Grid2
            xs={12}
            sm={6}
            md={4}
          >
            <Tile
              title="Add New Category"
              icon={<AddIcon fontSize="large" />}
              onClick={() => setDialogOpen(true)}
            />
          </Grid2>
        </Grid2>
      </Box>

      {/* Dialog for Adding New Category */}
      <Dialog open={dialogOpen} onClose={() => setDialogOpen(false)}>
        <DialogTitle>Add New Category</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            label="Category Name"
            fullWidth
            variant="outlined"
            value={newCategory}
            onChange={(e) => setNewCategory(e.target.value)}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setDialogOpen(false)} color="secondary">
            Cancel
          </Button>
          <Button onClick={handleAddCategory} color="primary">
            Add
          </Button>
        </DialogActions>
      </Dialog>

      
    </>
  );
}

StockPage.propTypes = {
  isFromBranch: PropTypes.bool.isRequired,
};

export default StockPage;
