import React from 'react'
import LockIcon from '@mui/icons-material/Lock';
import Alert from '@mui/material/Alert';
import {Container,Box,Typography,Button,Paper } from "@mui/material";
import { useNavigate } from "react-router-dom";


export const UnauthorizedPage = () => {
    const navigate=useNavigate();

    const goBack= ()=>navigate(-1);
  return (
      <Container maxWidth="sm" sx={{ 
               display: 'flex', flexDirection: 'column', 
               alignItems: 'center', justifyContent: 'center',
               height: '100vh' ,
           }}>
            <Paper square={false} elevation={3} sx={{ 
                width:'80%',
                display: 'flex', flexDirection: 'column', 
                alignItems: 'center', justifyContent: 'center',
                p:2,m:2}}>
            <Box sx={{ display: 'flex', alignItems: 'center', mb: 2 }}>
                <LockIcon sx={{ mr: 2 }} />
                <Typography variant="h5">Access Denied</Typography>
            </Box>
            <Alert severity="error">
                You do not have permission to access this page.
            </Alert>
            <Typography variant="body2" color="text.secondary" align="center">
                Please contact your administrator if you believe this is an error.
            </Typography>
            <Box sx={{ display: 'flex', alignItems: 'center', m: 2 }}>
                    <Button variant="outlined" onClick={goBack}>Go Back</Button>
                </Box>
            </Paper>

    </Container>
  )
}
