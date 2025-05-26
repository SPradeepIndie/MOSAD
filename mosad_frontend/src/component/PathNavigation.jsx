import { Button, Divider, Grid2, Paper, Typography } from '@mui/material';
import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import { Box } from '@mui/system';

const PathNavigation = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const handleGoBack = () => {
    navigate(-1); // Navigate back one step in the history
  };

  return (
    <>
     <Box sx={{ mx: 2, mt: 1, p: 1}}>
      <Grid2 container alignItems={'center'} direction={'row'}>
        <Grid2>
          <Button variant='contained' size='small' onClick={handleGoBack} startIcon={<ArrowBackIosIcon size="small" />}>
            Go Back
          </Button>
        </Grid2>

        <Grid2 sx={{ ml: 2 }}>
          <Typography variant='body2'>
             {location.pathname}
          </Typography>
        </Grid2>
      </Grid2>
    </Box>
    <Divider  variant="middle" sx={{ borderBottomWidth: 1,borderColor:'black' }}/>
    </>
   
  );
};

export default PathNavigation;