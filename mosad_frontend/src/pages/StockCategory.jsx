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

function Stock() {
  const navigate = useNavigate();

  const handleTileClick = (title) => {
    if(title==='Tyre'){
      navigate('/stock/brands',{state:{title}});
    }
    
    
  };

  return (
    <>
      <HeaderBar />
      <br />
      
    <h1>Select a Category</h1>
      <Box sx={{ marginTop: 4, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        {/* First Row: Three Tiles */}
        <Stack direction="row" sx={{ gap: '184px', marginBottom: 4 }}>
          <Tile
            title="Tyre"
            icon={<DescriptionIcon fontSize="large" />}
            onClick={() => handleTileClick('Tyre')}
          />
          <Tile
            title="Tube"
            icon={<InventoryIcon fontSize="large" />}
            onClick={() => handleTileClick('Tube')}
          />
          <Tile
            title="Tape"
            icon={<StorefrontIcon fontSize="large" />}
            onClick={() => handleTileClick('Tape')}
          />
        </Stack>

        {/* Second Row: Three Tiles */}
        <Stack direction="row" sx={{ gap: '184px', marginBottom: 4 }}>
          
          <Tile
            title="Battery"
            icon={<CreditCardIcon fontSize="large" />}
            onClick={() => handleTileClick('Credit')}
          />

          <Tile
            title="A"
            icon={<AccountTreeIcon fontSize="large" />}
            onClick={() => handleTileClick('Branches')}
          />
          <Tile
            title="B"
            icon={<PeopleIcon fontSize="large" />}
            onClick={() => handleTileClick('Employee')}
          />
        </Stack>

       
      </Box>

    {/* Searching part  */}
    
      <Footer />
    </>
  );
}

export default Stock;
