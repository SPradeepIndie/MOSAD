import React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';

function Footer() {
  return (
    <AppBar
      position="static"
      sx={{
        backgroundColor: 'gray',
        maxWidth: '1600px',
        margin: 'auto',
        borderRadius: '8px',
        boxShadow: '0 -4px 8px rgba(0, 0, 0, 0.1)',
        top: 'auto',
        bottom: 0,
        marginTop: '20px',
      }}
    >
      <Toolbar sx={{ justifyContent: 'center' }}>
        {/* Footer Content */}
        <Typography
          component="div"
          sx={{
            textAlign: 'center',
            fontFamily: `'Roboto', sans-serif`,
            fontSize: '18px',
            fontWeight: 'bold',
            color: '#ffffff',
          }}
        >
          © {new Date().getFullYear()} Rashmi Tyre Center. All rights reserved.
        </Typography>
      </Toolbar>
    </AppBar>
  );
}

export default Footer;
