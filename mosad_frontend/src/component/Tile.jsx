import React from 'react';
import { Card, CardActionArea, CardContent, Typography, Box } from '@mui/material';
import { Link } from 'react-router-dom';
import useGlobalAccess from "../hooks/useGlobalAccess";

function Tile({ title, icon, link , catN ,brandN}) {
  const { setSelectedCategory, setSelectedBrand } = useGlobalAccess();

  const hadleCatBrand = () =>{
    setSelectedCategory(catN);
    setSelectedBrand(brandN);
  }
  return (
    <Card component={Link} to={link} onClick={ hadleCatBrand }
      sx={{
        height: 'auto',
        width: '15em',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
        boxShadow: 3,
        borderRadius: 2,
        cursor: 'pointer',
        backgroundColor: '#2E8B58', 
        ':hover': { 
          backgroundColor: '#FFC220', 
          boxShadow: 6 
        },
      }}
    >
      <CardActionArea sx={{ height: '100%', width: '100%', textAlign: 'center' }}>
        <CardContent>
          <Box
            sx={{
              fontSize: '40px',
              marginBottom: '10px',
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
            }}
          >
            {icon}
          </Box>
          <Typography
            variant="h6"
            sx={{
              //fontWeight: 'bold',
              color: 'white',
              fontSize: '18px',
            }}
          >
            {title}
          </Typography>
        </CardContent>
      </CardActionArea>
    </Card>
  );
}

export default Tile;
