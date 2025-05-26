import React, { useState, useEffect } from 'react';
import Tile from '../../component/Tile';
import { Box, TextField, Alert,Typography,Grid2 as Grid,Button } from '@mui/material';
import { useLocation } from 'react-router-dom';
import PropTypes from 'prop-types';
import { useAddBrand, useFetchBrands } from '../../hooks/servicesHook/useStockService';
import PopUp from '../../component/PopUp';

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

function BrandPage({ allowedRoles }) {
  const fetchBrands = useFetchBrands();
  const addBrand = useAddBrand();
  const location = useLocation();
  const states = location.state;

  const [brands, setBrands] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [dialogOpen, setDialogOpen] = useState(false);
  const [newBrandName, setNewBrandName] = useState('');
  const [successMessage, setSuccessMessage] = useState(null);

  useEffect(() => {
    const getBrands = async () => {
      try {
        const response = await fetchBrands(states.category);
        setBrands(response.data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    getBrands();
  }, [states.category]);

  const handleAddBrand = async () => {
    if (!newBrandName.trim()) {
      setError('Brand name cannot be empty.');
      setDialogOpen(false);
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


  const linkForRoutes = ()=>{
    if( allowedRoles.includes("ADMIN") || allowedRoles.includes("OWNER")){
      return '/stocks/category/brands/item-view'
    }
    if( allowedRoles.includes("STOCK_MANAGER")){
      return '/stock/category/brand/item-view'
    }
    if( allowedRoles.includes("BRANCH_MANAGER")){
      return '/branch/stock/category/brand/item-view'
    }
  } 
 
  return (
    <>
      <h1 style={{ textAlign: 'center', color: 'black' }}>Select a Brand</h1>

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
        <Grid container spacing={4} justifyContent="center">
          {brands.map((brand) => (
            <Grid key={brand.brandName}>
              <Tile
                allowedRoles={["OWNER","ADMIN","STOCK_MANAGER","BRANCH_MANAGER"]}
                title={brand.brandName}
                icon={iconMap[brand.brandName] || <DescriptionIcon fontSize="large" />}
                link={linkForRoutes()}
                state={{ ...states, brand: brand.brandName }}
              />
            </Grid>
          ))}
          {/* Add New Brand Tile */}
          <Grid>
            <Tile
              allowedRoles={["OWNER","ADMIN","STOCK_MANAGER","BRANCH_MANAGER"]}
              title="Add New Brand"
              icon={<AddIcon fontSize="large" />}
              onClick={() => setDialogOpen(true)}
            />
          </Grid>
        </Grid>
      </Box>

      {/* Dialog for Adding New Brand */}
      <PopUp
          popUpTitle="Add New Brand"
          openPopup={dialogOpen}
          setOpenPopup={setDialogOpen}
          setOkButtonAction={handleAddBrand}
          setCancelButtonAction={() => setDialogOpen(false)}
          isDefaultButtonsDisplay={false}
          width="md">
            <Box sx={{ p: 3, maxWidth: '400px', mx: 'auto' }}>
              <Grid container spacing={2} direction="column">
                <Grid size={{ xs:12}}>
                  <Typography variant="body1" gutterBottom>
                    Please provide the name of the brand you wish to add to the system:
                  </Typography>
                </Grid>
                <Grid size={{ xs:12}}>
                <TextField
                  autoFocus
                  margin="dense"
                  label="Brand Name"
                  fullWidth
                  variant="outlined"
                  value={newBrandName}
                  onChange={(e) => setNewBrandName(e.target.value)}
                />
                </Grid>
                <Grid size={{ xs:12}} container justifyContent="flex-end" spacing={2}>
                  <Grid >
                    <Button onClick={() => setDialogOpen(false)} color="secondary">
                      Cancel
                    </Button>
                  </Grid>
                  <Grid >
                    <Button
                      onClick={handleAddBrand}
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

BrandPage.propTypes = {
  allowedRoles: PropTypes.object.isRequired,
};

export default BrandPage;
