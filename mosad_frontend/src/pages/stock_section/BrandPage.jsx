import React from 'react';
import Tile from '../../component/Tile';
import { Box, Stack } from '@mui/material';
import DescriptionIcon from '@mui/icons-material/Description';
import InventoryIcon from '@mui/icons-material/Inventory';
import StorefrontIcon from '@mui/icons-material/Storefront';
import CreditCardIcon from '@mui/icons-material/CreditCard';
import AccountTreeIcon from '@mui/icons-material/AccountTree';
import PeopleIcon from '@mui/icons-material/People';
import AssessmentIcon from '@mui/icons-material/Assessment';

function BrandPage() {
  console.log('brandpage')
  return (
    <>
      <Box sx={{ marginTop: 4, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        {/* First Row: Three Tiles */}
        <Stack direction="row" sx={{ gap: '184px', marginBottom: 4 }}>
          <Tile
            title="CEAT"
            icon={<DescriptionIcon fontSize="large" />}
            link=""      
          />
          <Tile
            title="PRESA"
            icon={<InventoryIcon fontSize="large" />}
            link=""
          />
          <Tile
            title="LINGLONG"
            icon={<StorefrontIcon fontSize="large" />}
            link=""
          />
        </Stack>

        {/* Second Row: Three Tiles */}
        <Stack direction="row" sx={{ gap: '184px', marginBottom: 4 }}>
          <Tile
            title="RAPID"
            icon={<CreditCardIcon fontSize="large" />}
            link=""
          />
          <Tile
            title="Atlander"
            icon={<AccountTreeIcon fontSize="large" />}
            link=""
          />
          <Tile
            title="Brand 6"
            icon={<PeopleIcon fontSize="large" />}
            link=""   
          />
        </Stack>

        {/* Last Row: Centered Tile */}
        <Stack direction="row" justifyContent="center">
          <Tile
            title="Brand 7"
            icon={<AssessmentIcon fontSize="large" />}
            onClick={() => handleTileClick('')}
          />
        </Stack>
      </Box>

    </>
  );
}

export default BrandPage;

