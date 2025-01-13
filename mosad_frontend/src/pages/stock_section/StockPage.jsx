import React from 'react';
import Tile from '../../component/Tile';
import { Box, Grid2 } from '@mui/material';
import DescriptionIcon from '@mui/icons-material/Description';
import InventoryIcon from '@mui/icons-material/Inventory';
import StorefrontIcon from '@mui/icons-material/Storefront';
import CreditCardIcon from '@mui/icons-material/CreditCard';
import { Outlet } from 'react-router-dom';
import PropTypes from 'prop-types';

function StockPage({ isFromBranch }) {
  const tileData = [
    {
      title: "Tyre",
      icon: <DescriptionIcon fontSize="large" />,
      link: `${isFromBranch ? "/branch/stock/brand" : "/stock/brand"}`,
      state:{category:"Tyre"}
    },
    {
      title: "Tube",
      icon: <InventoryIcon fontSize="large" />,
      link: `${isFromBranch ? "/branch/stock/brand" : "/stock/brand"}`,
      state:{category:"Tube"}
    },
    {
      title: "Tape",
      icon: <StorefrontIcon fontSize="large" />,
      link: `${isFromBranch ? "/branch/stock/brand" : "/stock/brand"}`,
      state:{category:"Tape"}
    },
    {
      title: "Battery",
      icon: <CreditCardIcon fontSize="large" />,
      link: `${isFromBranch ? "/branch/stock/brand" : "/stock/brand"}`,
      state:{category:"Battery"}
    }
  ];

  if (typeof isFromBranch !== 'boolean') {
    throw new Error('isFromBranch prop must be a boolean');
  }

  return (
    <>
      <Outlet />
      <h1 style={{ textAlign: 'center' }}>Select a Category</h1>
      <Box sx={{ marginTop: 4 }}>
        {/* Responsive Grid */}
        <Grid2 container spacing={4} justifyContent="center">
          {tileData.map((tile, index) => (
            <Grid2
              xs={12} // Full width on extra-small screens
              sm={6} // Two tiles per row on small screens
              md={4} // Three tiles per row on medium screens
              key={index}
            >
              
              <Tile title={tile.title} icon={tile.icon} link={tile.link} state={tile.state} />
            </Grid2>
          ))}
        </Grid2>
      </Box>

    {/* Searching part  */}
    
    </>
  );
}

StockPage.prototype={
  isFromBranch:PropTypes.bool.isRequired
}

export default StockPage;
