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
import { useLocation} from 'react-router-dom';

function BrandPage({isFromBranch}) {
  const location=useLocation();
  const states=location.state;
  console.log(states);



  return (
      <Box sx={{ marginTop: 4, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        {/* First Row: Three Tiles */}
        <Stack direction="row" sx={{ gap: '184px', marginBottom: 4 }}>
          <Tile
            title="CEAT"
            icon={<DescriptionIcon fontSize="large" />}
            link={`${isFromBranch? "/branch/stock/brand/item-view" : "/stock/item-view"}`}
            state={{...states,brand:"CEAT"}}
          />
          <Tile
            title="PRESA"
            icon={<InventoryIcon fontSize="large" />}
            link={`${isFromBranch? "/branch/stock/brand/item-view" : "/stock/item-view"}`}
            state={{...states,brand:"PRESA"}}
          />
          <Tile
            title="LINGLONG"
            icon={<StorefrontIcon fontSize="large" />}
            link={`${isFromBranch? "/branch/stock/brand/item-view" : "/stock/item-view"}`}
            state={{...states,brand:"LINGLONG"}}
          />
        </Stack>

        {/* Second Row: Three Tiles */}
        <Stack direction="row" sx={{ gap: '184px', marginBottom: 4 }}>
          <Tile
            title="RAPID"
            icon={<CreditCardIcon fontSize="large" />}
            link={`${isFromBranch? "/branch/stock/brand/item-view" : "/stock/item-view"}`}
            state={{...states,brand:"RAPID"}}
          />
          <Tile
            title="Atlander"
            icon={<AccountTreeIcon fontSize="large" />}
            link={`${isFromBranch? "/branch/stock/brand/item-view" : "/stock/item-view"}`}
            state={{...states,brand:"Atlander"}}
          />
          <Tile
            title="Brand 6"
            icon={<PeopleIcon fontSize="large" />}
            link={`${isFromBranch? "/branch/stock/brand/item-view" : "/stock/item-view"}`}
            state={{...states,brand:"Brand 6"}}
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
  );
}

export default BrandPage;

