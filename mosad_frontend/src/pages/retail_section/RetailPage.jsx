import React from 'react';
import { Box, Stack, Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import './RetailHomePage.css';
import  retailCustomerBaner from '../../assets/retailCustomer.jpg';
import { Grid2 } from '@mui/material';

import PaymentIcon from '@mui/icons-material/Payment';
import HistoryIcon from '@mui/icons-material/History';
import WarningIcon from '@mui/icons-material/Warning';
import PeopleIcon from '@mui/icons-material/People';
import Tile from "../../component/Tile"; 



const RetailPage = ({ isOwner }) => {
  const navigate = useNavigate();
  isOwner = true;

  const tiles = [
    {
      title: 'Payment History',
      icon: <PaymentIcon style={{ fontSize: '40px', color: 'white' }} />,
      link: '/payment-history',
      catN: 'payment',
      brandN: 'all',
    },
    {
      title: 'Purchase History',
      icon: <HistoryIcon style={{ fontSize: '40px', color: 'white' }} />,
      link: '/purchase-history',
      catN: 'purchase',
      brandN: 'all',
    },
    {
      title: 'Incomplete Transactions',
      icon: <WarningIcon style={{ fontSize: '40px', color: 'white' }} />,
      link: '/incomplete-transactions',
      catN: 'incomplete',
      brandN: 'all',
    },
  ];

  if (isOwner) {
    tiles.push({
      title: 'Retail Customer Details',
      icon: <PeopleIcon style={{ fontSize: '40px', color: 'white' }} />,
      link: '/customer-details',
      catN: 'customer',
      brandN: 'all',
    });
  }

  return (
    <Grid2
      container
      spacing={3}
      columns={2}
      justifyContent="center"
      sx={{ marginBottom: 2 }}
    >
      {tiles.map((tile, index) => (
        <Grid2 item xs={1} key={index}>
          <Tile 
            title={tile.title} 
            icon={tile.icon} 
            link={tile.link} 
            catN={tile.catN} 
            brandN={tile.brandN} 
          />
        </Grid2>
      ))}
    </Grid2>
  );
};

export default RetailPage;
