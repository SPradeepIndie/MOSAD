import React from 'react';
import { Box, Container } from '@mui/material'
import Footer from '../../component/Footer';
import HeaderBar from '../../component/Header';
import { Outlet } from 'react-router-dom';

function HomeLayout() {

  return (
    <Container maxWidth="xl" disableGutters sx={{width:'100vw',height:'100vh'}}>
    <Box maxWidth="xl">
        <HeaderBar/>
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
