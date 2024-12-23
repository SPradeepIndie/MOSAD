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
import { useNavigate,useLocation } from 'react-router-dom';

function ItemTable() {
  
  const location = useLocation();
  const { title } = location.state || {};
  console.log(title);


     const navigate = useNavigate();
    
      const handleTileClick = (title) => {
        
          navigate('/stock/brands/itemtable',{state:{title}}); // Redirect to /stock/brands/itemtable with state
        
      };
  

  return (
    <>
      <HeaderBar />
      <br />
      

      <Box sx={{ marginTop: 4, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        {/* First Row: Three Tiles */}
        <Stack direction="row" sx={{ gap: '184px', marginBottom: 4 }}>
          <Tile
            title="CEAT"
            icon={<DescriptionIcon fontSize="large" />}
            onClick={() => handleTileClick('CEAT')}
          />
          <Tile
            title="PRESA"
            icon={<InventoryIcon fontSize="large" />}
            onClick={() => handleTileClick('tyre_presa')}
          />
          <Tile
            title="LINGLONG"
            icon={<StorefrontIcon fontSize="large" />}
            onClick={() => handleTileClick('tyre_linglong')}
          />
        </Stack>

        {/* Second Row: Three Tiles */}
        <Stack direction="row" sx={{ gap: '184px', marginBottom: 4 }}>
          <Tile
            title="RAPID"
            icon={<CreditCardIcon fontSize="large" />}
            onClick={() => handleTileClick('tyre_rapid')}
          />
          <Tile
            title="Atlander"
            icon={<AccountTreeIcon fontSize="large" />}
            onClick={() => handleTileClick('tyre_atlander')}
          />
          <Tile
            title="Brand 6"
            icon={<PeopleIcon fontSize="large" />}
            onClick={() => handleTileClick('')}
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

      <Footer />
    </>
  );
}

export default ItemTable;

