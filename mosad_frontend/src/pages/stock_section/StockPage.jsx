import React from 'react';
import Tile from '../../component/Tile';
import { Box, Stack } from '@mui/material';
import DescriptionIcon from '@mui/icons-material/Description';
import InventoryIcon from '@mui/icons-material/Inventory';
import StorefrontIcon from '@mui/icons-material/Storefront';
import CreditCardIcon from '@mui/icons-material/CreditCard';

function StockPage() {
  return (
    <>
    <h1>Select a Category</h1>
      <Box sx={{ marginTop: 4, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        {/* First Row: Three Tiles */}
        <Stack direction="row" sx={{ gap: '184px', marginBottom: 4 }}>
          <Tile
            title="Tyre"
            icon={<DescriptionIcon fontSize="large" />}
            link="/stock/brand"
          />
          <Tile
            title="Tube"
            icon={<InventoryIcon fontSize="large" />}
            link=""
          />
          <Tile
            title="Tape"
            icon={<StorefrontIcon fontSize="large" />}
            link=""
          />
        </Stack>

        {/* Second Row: Three Tiles */}
        <Stack direction="row" sx={{ gap: '184px', marginBottom: 4 }}>
          
          <Tile
            title="Battery"
            icon={<CreditCardIcon fontSize="large" />}
            link=""
          />
        </Stack>

       
      </Box>

    {/* Searching part  */}
    
    </>
  );
}

export default StockPage;
