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
import useAuth from '../../hooks/useAuth'

function HomePage() {
  const {auth}=useAuth();

  const tiles = [
    {
      title: 'Bill Generate',
      icon: <DescriptionIcon fontSize="large" />,
      link: '/bill',
      authorizedRoles:["OWNER","ADMIN"]
    },
    {
      title: 'Stock',
      icon: <InventoryIcon fontSize="large" />,
      link: '/stock',
      authorizedRoles:["OWNER","ADMIN","STOCK_MANAGER"]
    },
    {
      title: 'Retail',
      icon: <StorefrontIcon fontSize="large" />,
      link: '/retail',
      authorizedRoles:["OWNER","ADMIN","RETAIL_CUSTOMER"]
    },
    {
      title: 'Credit',
      icon: <CreditCardIcon fontSize="large" />,
      link: '/credit',
      authorizedRoles:["OWNER","ADMIN"]
    },
    {
      title: 'Branches',
      icon: <AccountTreeIcon fontSize="large" />,
      link: '/branch',
      authorizedRoles:["OWNER","ADMIN","BRANCH_MANAGER"]
    },
    {
      title: 'Employee',
      icon: <PeopleIcon fontSize="large" />,
      link: '/employee',
      authorizedRoles:["OWNER","ADMIN","STOCK_MANAGER","BRANCH_MANAGER","MECHANIC"]
    },
    {
      title: 'Reports',
      icon:<AssessmentIcon fontSize="large"/>,
      link:"/future",
      authorizedRoles:["OWNER","ADMIN"]
    },
    {
      title:'Dack Tires',
      icon:<AssessmentIcon fontSize='large'/>,
      link:'/dack',
      authorizedRoles:["OWNER","ADMIN"]
    }
  ];

  return (
    <>
      <Slideshow />
      <Box
        sx={{
          marginTop: { xs: 2, sm: 3, md: 4, lg: 5, xl: 6 },
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          padding: { xs: 1, sm: 2, md: 3, lg: 4, xl: 5 }, // Reduced padding for responsiveness
        }}
      >
        
        <Stack
          direction="row"
          sx={{
            gap: { xs: '8px', sm: '20px', md: '50px', lg: '100px', xl: '120px' }, // Reduced gap for responsiveness
            marginBottom: { xs: 2, sm: 3, md: 4, lg: 5, xl: 6 },
            flexWrap: 'wrap',
            justifyContent: 'center',
          }}
        >
           {tiles.map((tile) => (
            <Tile key={tile.title}
              allowedRoles={tile.authorizedRoles}
              title={tile.title}
              icon={tile.icon}
              link={tile.link}
              />
            ))}
        </Stack>
      </Box>
    </>
  );
}

export default HomePage;
