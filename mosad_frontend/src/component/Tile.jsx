import React from 'react';
import { Card, CardActionArea, CardContent, Typography, Box } from '@mui/material';

function Tile({ title, icon, onClick }) {
  return (
    <Card
      sx={{
        height: '200px',
        width: '300px',
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
      onClick={onClick}
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
