import React, { useState } from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { Divider, Grid2 as Grid, Box, IconButton, Button,Tooltip } from '@mui/material';
import CallIcon from '@mui/icons-material/Call';

function Footer() {
  const [toolTipTitle,setToolTipTitle]=useState("");

  const handleCopyToClipboard = (number) => {
    navigator.clipboard.writeText(number);
    setToolTipTitle(`Number copied to clipboard!`);
    setTimeout(() => {
      setToolTipTitle(''); 
    }, 500);
  };
  return (
    <AppBar
      position="static"
      sx={{
        backgroundColor: '#085c47', 
        maxWidth: '1600px',
        margin: 'auto',
        boxShadow: '0 -4px 8px rgba(0, 0, 0, 0.2)', 
        top: 'auto',
        bottom: 0,
        padding: '20px 0', 
      }}
    >
      <Toolbar sx={{ flexDirection: 'column', justifyContent: 'center', m: 0 }}>
        <Grid
          container
          spacing={1}
          sx={{
            justifyContent: 'space-between',
            alignItems: 'center',
            width: '100%',
            textAlign: 'center',
          }}
          direction="column"
        >
          <Grid size={{ sm: 12 }}>
            <Typography
              variant="h5"
              sx={{
                fontFamily: `'Roboto', sans-serif`,
                color: '#ffffff',
                fontWeight: 600, 
                marginBottom: 1, 
              }}
            >
              Empowering journeys, enhancing every mile.
            </Typography>
          </Grid>
          <Grid size={{ sm: 12 }}>
            <Box display="flex" flexDirection="column" alignItems="center">
              <IconButton disabled sx={{m:0}}>
                <CallIcon sx={{ color: '#ffffff' }} />
              </IconButton>
              <Typography
                variant="subtitle1" 
                sx={{
                  fontFamily: `'Roboto', sans-serif`,
                  fontWeight: 'bold',
                  color: '#ffffff',
                  marginBottom: 0.5, 
                }}
              >
                Contact Rashmi Tyre Shop
              </Typography>
               <Box display="flex" flexDirection="row" alignItems="center">
                  {['0783918504', '0764690290', '0332274577'].map((number) => (
                    <Tooltip key={number} title={toolTipTitle}>
                      <Button
                        onClick={() => handleCopyToClipboard(number)}
                        sx={{ color: '#ffffff', textDecoration: 'none' }}
                      >
                        {number}
                      </Button>
                    </Tooltip>
                  ))}
                </Box>
              
            </Box>
          </Grid>

          <Divider sx={{ width: '100%', mt: 2}} color="white" />
          <Grid container justifyContent="space-between" sx={{ width: '100%' }}>
            <Grid size={4}>
              <Typography
                component="p"
                sx={{
                  fontFamily: `'Roboto', sans-serif`,
                  fontSize: '0.9em',
                  fontWeight: 500,
                  color: '#ffffff',
                }}
              >
                Developed By MOSAD
              </Typography>
            </Grid>
            <Grid size={4}>
              <Typography
                component="p"
                sx={{
                  fontFamily: `'Roboto', sans-serif`,
                  fontSize: '0.9em',
                  fontWeight: 500,
                  color: '#ffffff',
                }}
              >
                &copy; {new Date().getFullYear()} Rashmi Tyre Center. All rights reserved.
              </Typography>
            </Grid>
            <Grid size={4}>
              <Typography
                component="p"
                sx={{
                  fontFamily: `'Roboto', sans-serif`,
                  fontSize: '0.9em',
                  fontWeight: 500,
                  color: '#ffffff',
                }}
              >
                Rashmi Tyre Streamline Solution
              </Typography>
            </Grid>
          </Grid>
        </Grid>
      </Toolbar>
    </AppBar>
  );
}

export default Footer;