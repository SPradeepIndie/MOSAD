import React from 'react';
import Tile from '../../component/Tile';
import { Box, Stack } from '@mui/material';
import DescriptionIcon from '@mui/icons-material/Description';
import InventoryIcon from '@mui/icons-material/Inventory';
import StorefrontIcon from '@mui/icons-material/Storefront';
import CreditCardIcon from '@mui/icons-material/CreditCard';
import { Outlet } from 'react-router-dom';

function StockPage({isFromBranch}) {
  if (typeof isFromBranch !== 'boolean') {
    throw new Error('isFromBranch prop must be a boolean');
  }
  return (
    <>
    <Outlet/>
    <h1>Select a Category</h1>
      <Box sx={{ marginTop: 4, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        {/* First Row: Three Tiles */}
        <Stack direction="row" sx={{ gap: '184px', marginBottom: 4 }}>
          <Tile
            title="Tyre"
            icon={<DescriptionIcon fontSize="large" />}
            link={`${isFromBranch? "/branch/stock/brand" : "/stock/brand"}`}
          />
          <Tile
            title="Tube"
            icon={<InventoryIcon fontSize="large" />}
            link={`${isFromBranch? "/branch/stock/brand" : "/stock/brand"}`}
          />
          <Tile
            title="Tape"
            icon={<StorefrontIcon fontSize="large" />}
            link={`${isFromBranch? "/branch/stock/brand" : "/stock/brand"}`}
          />
        </Stack>

        {/* Second Row: Three Tiles */}
        <Stack direction="row" sx={{ gap: '184px', marginBottom: 4 }}>
          
          <Tile
            title="Battery"
            icon={<CreditCardIcon fontSize="large" />}
            link={`${isFromBranch? "/branch/stock/brand" : "/stock/brand"}`}
          />
        </Stack>

       
      </Box>

    {/* Searching part  */}
    
    </>
  );
}

export default StockPage;
