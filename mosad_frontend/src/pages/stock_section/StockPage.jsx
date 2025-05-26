import React, { useState, useEffect } from 'react';
import Tile from '../../component/Tile';
import { Box,TextField, Alert,Typography,Button,Grid2 as Grid } from '@mui/material';
import { Outlet } from 'react-router-dom';
import PropTypes from 'prop-types';
import {useFetchCategories,useAddCategory}  from '../../hooks/servicesHook/useStockService'
import PopUp from '../../component/PopUp';


// Icons for dynamic categories
import DescriptionIcon from '@mui/icons-material/Description';
import InventoryIcon from '@mui/icons-material/Inventory';
import StorefrontIcon from '@mui/icons-material/Storefront';
import CreditCardIcon from '@mui/icons-material/CreditCard';
import AddIcon from '@mui/icons-material/Add';
import useAuth from '../../hooks/useAuth';

const iconMap = {
  Tyre: <DescriptionIcon fontSize="large" />,
  Tube: <InventoryIcon fontSize="large" />,
  Tape: <StorefrontIcon fontSize="large" />,
  Battery: <CreditCardIcon fontSize="large" />,
};

function StockPage({ allowedRoles}) {
  const fetchCategories = useFetchCategories();
  const addCategory = useAddCategory();
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [newCategory, setNewCategory] = useState('');
  const [successMessage, setSuccessMessage] = useState(null);
  const {auth} =useAuth();

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

  const linkForRoutes = ()=>{
    if( allowedRoles.includes("ADMIN") || allowedRoles.includes("OWNER")){
      return '/stocks/category/brands'
    }
    if( allowedRoles.includes("STOCK_MANAGER")){
      return '/stock/category/brand'
    }
    if( allowedRoles.includes("BRANCH_MANAGER")){
      return '/branch/stock/category/brand'
    }
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
        <Grid container spacing={4} justifyContent="center">
          {categories.map((category) => (
            <Grid key={category.categoryName}>
              <Tile
                allowedRoles={["OWNER","ADMIN","STOCK_MANAGER","BRANCH_MANAGER"]}
                title={category.categoryName}
                icon={iconMap[category.categoryName] || <DescriptionIcon fontSize="large" />}
                link={linkForRoutes()}
                state={{ category: category.categoryName }}
              />
            </Grid>
          ))}
          {/* Add New Category Tile */}
          <Grid >
            <Tile
              allowedRoles={["OWNER","ADMIN","STOCK_MANAGER","BRANCH_MANAGER"]}
              title="Add New Category"
              icon={<AddIcon fontSize="large" />}
              onClick={() => setDialogOpen(true)}
            />
          </Grid>
        </Grid>
      </Box>

      {/* Dialog for Adding New Category */}
      <PopUp
          popUpTitle="Add New Category"
          openPopup={dialogOpen}
          setOpenPopup={setDialogOpen}
          setOkButtonAction={handleAddCategory}
          setCancelButtonAction={() => setDialogOpen(false)}
          isDefaultButtonsDisplay={false}
          width="md">
             <Box sx={{ p: 3, maxWidth: '400px', mx: 'auto' }}>
              <Grid container spacing={2} direction="column">
                <Grid size={{ xs:12}}>
                  <Typography variant="body1" gutterBottom>
                    Please provide the name of the Category you wish to add to the system:
                  </Typography>
                </Grid>
                <Grid size={{ xs:12}}>
                    <TextField
                        autoFocus
                        margin="dense"
                        label="Category Name"
                        fullWidth
                        variant="outlined"
                        value={newCategory}
                        onChange={(e) => setNewCategory(e.target.value)}
                      />
                </Grid>
                <Grid size={{ xs:12}} container justifyContent="flex-end" spacing={2}>
                  <Grid>
                    <Button onClick={() => setDialogOpen(false)} color="secondary">
                      Cancel
                    </Button>
                  </Grid>
                  <Grid >
                    <Button
                      onClick={handleAddCategory}
                      sx={{ color: 'white', backgroundColor: 'green', '&:hover': { backgroundColor: 'darkgreen' } }}
                    >
                      Add
                    </Button>
                  </Grid>
                </Grid>
              </Grid>
            </Box>
      </PopUp>
    </>
  );
}

StockPage.propTypes = {
  allowedRoles: PropTypes.object.isRequired,
};

export default StockPage;
