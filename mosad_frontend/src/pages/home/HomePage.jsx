import React from 'react';
import Slideshow from '../../component/Slideshow';
import Tile from '../../component/Tile';
import { Box, Stack } from '@mui/material';
import DescriptionIcon from '@mui/icons-material/Description';
import InventoryIcon from '@mui/icons-material/Inventory';
import StorefrontIcon from '@mui/icons-material/Storefront';
import CreditCardIcon from '@mui/icons-material/CreditCard';
import AccountTreeIcon from '@mui/icons-material/AccountTree';
import PeopleIcon from '@mui/icons-material/People';
import AssessmentIcon from '@mui/icons-material/Assessment';
import { useNavigate } from 'react-router-dom';

function HomePage() {
  const navigate = useNavigate();

  const handleTileClick = (title) => {
    if (title === 'Stock') {
      navigate('/stock'); // Redirect to /stock
    } else if (title === 'Bill Generate') {
      navigate('/bill-generate'); // Example for other tiles
    }
    // Add more conditions for other tiles as needed
  };

  return (
    <>
      <Slideshow />
      <Box sx={{ marginTop: 4, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        {/* First Row: Three Tiles */}
        <Stack direction="row" sx={{ gap: '184px', marginBottom: 4 }}>
          <Tile
            title="Bill Generate"
            icon={<DescriptionIcon fontSize="large" />}
            link="/bill"
          />
          <Tile
            title="Stock"
            icon={<InventoryIcon fontSize="large" />}
            link="/stock"
          />
          <Tile
            title="Retail"
            icon={<StorefrontIcon fontSize="large" />}
            link="/retail"
          />
        </Stack>

        {/* Second Row: Three Tiles */}
        <Stack direction="row" sx={{ gap: '184px', marginBottom: 4 }}>
          <Tile
            title="Credit"
            icon={<CreditCardIcon fontSize="large" />}
            link="/credit"
          />
          <Tile
            title="Branches"
            icon={<AccountTreeIcon fontSize="large" />}
            link="/branch"
          />
          <Tile
            title="Employee"
            icon={<PeopleIcon fontSize="large" />}
            link="/employee"
          />
        </Stack>

        {/* Last Row: Centered Tile */}
        <Stack direction="row" justifyContent="center">
          <Tile
            title="Reports"
            icon={<AssessmentIcon fontSize="large" />}
            link="/future"
           />
        </Stack>
      </Box>
    </>
  );
}

export default HomePage;
