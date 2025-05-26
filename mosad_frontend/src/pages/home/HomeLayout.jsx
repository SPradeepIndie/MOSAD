import React from 'react';
import { Box, Container } from '@mui/material'
import Footer from '../../component/Footer';
import HeaderBar from '../../component/Header';
import { Outlet } from 'react-router-dom';
import PathNavigation from '../../component/PathNavigation';

function HomeLayout() {

  return (
    <Container maxWidth="xl" disableGutters sx={{
            display: 'flex',
            flexDirection: 'column',
            minHeight: '100vh',
            width: '100vw',
          }}>
    <Box maxWidth="xl">
        <HeaderBar/>
    </Box>
    <Box  sx={{
          maxWidth: 'xl',
          position: 'sticky',
          top: 3,
          zIndex: 100, // Ensure it stays above other content
          backgroundColor: 'background.paper', // Add a background color if needed
        }}>
        <PathNavigation/>
    </Box>
    <Box sx={{p:2, minHeight: '100vh'}}>
        <Outlet />
    </Box>
    <Box maxWidth="xl">
        <Footer />
    </Box>
    </Container>
  )

}



export default HomeLayout;
