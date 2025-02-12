import React from "react";
import Alert from '@mui/material/Alert';
import {Container,Box,Typography,Button, Paper } from "@mui/material";
import { ErrorOutline } from "@mui/icons-material";
import { useNavigate } from "react-router-dom";

const NotFoundPage=()=>{
    const navigate=useNavigate();

    const goHome= ()=>navigate('/home');

    return(
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
                    <ErrorOutline sx={{ mr: 2 }} />
                    <Typography variant="h5">Page Not Found</Typography>
                    </Box>
                    <Alert severity="error">
                    The page you are looking for does not exist.
                    </Alert>
                    <Typography variant="body2" color="text.secondary" align="center">
                    Please check the URL or return to the homepage.
                    </Typography>
                    <Box sx={{ display: 'flex', alignItems: 'center', m: 2 }}>
                        <Button variant="outlined" onClick={goHome}>Go to Home Page</Button>
                    </Box>
            </Paper>
            
            
        </Container>
    )
}

export default NotFoundPage;

