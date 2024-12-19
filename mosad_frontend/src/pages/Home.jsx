import React from 'react';
import HeaderBar from '../component/Header';
import Slideshow from '../component/Slideshow';
import Tile from '../component/Tile';
import { Box, Stack } from '@mui/material';
import Footer from '../component/Footer';
import DescriptionIcon from '@mui/icons-material/Description';
import InventoryIcon from '@mui/icons-material/Inventory';
import StorefrontIcon from '@mui/icons-material/Storefront';
import CreditCardIcon from '@mui/icons-material/CreditCard';
import AccountTreeIcon from '@mui/icons-material/AccountTree';
import PeopleIcon from '@mui/icons-material/People';
import AssessmentIcon from '@mui/icons-material/Assessment';
import { useNavigate } from 'react-router-dom';

function Home() {
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
      <HeaderBar />
      <br />
      <Slideshow />

      <Box sx={{ marginTop: 4, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        {/* First Row: Three Tiles */}
        <Stack direction="row" sx={{ gap: '184px', marginBottom: 4 }}>
          <Tile
            title="Bill Generate"
            icon={<DescriptionIcon fontSize="large" />}
            onClick={() => handleTileClick('Bill Generate')}
          />
          <Tile
            title="Stock"
            icon={<InventoryIcon fontSize="large" />}
            onClick={() => handleTileClick('Stock')}
          />
          <Tile
            title="Retail"
            icon={<StorefrontIcon fontSize="large" />}
            onClick={() => handleTileClick('Retail')}
          />
        </Stack>

        {/* Second Row: Three Tiles */}
        <Stack direction="row" sx={{ gap: '184px', marginBottom: 4 }}>
          <Tile
            title="Credit"
            icon={<CreditCardIcon fontSize="large" />}
            onClick={() => handleTileClick('Credit')}
          />
          <Tile
            title="Branches"
            icon={<AccountTreeIcon fontSize="large" />}
            onClick={() => handleTileClick('Branches')}
          />
          <Tile
            title="Employee"
            icon={<PeopleIcon fontSize="large" />}
            onClick={() => handleTileClick('Employee')}
          />
        </Stack>

        {/* Last Row: Centered Tile */}
        <Stack direction="row" justifyContent="center">
          <Tile
            title="Reports"
            icon={<AssessmentIcon fontSize="large" />}
            onClick={() => handleTileClick('Reports')}
          />
        </Stack>
      </Box>

      <Footer />
    </>
  );
}

export default Home;
