import React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import MenuIcon from '@mui/icons-material/Menu';
import { useNavigate } from 'react-router-dom';

function HeaderBar({setIsLoggedIn}) {
  const navigate = useNavigate();

  const handleLogout = () => {
    // Remove token from local storage
    localStorage.removeItem('token'); 
    setIsLoggedIn(false);
    // Redirect to login page
    navigate('/',{ replace: true }); 
  
  };
  return (
    <AppBar
      position="static"
      sx={{
        backgroundColor: 'gray',
        maxWidth: '1600px',
        margin: 'auto',
        borderRadius: '8px',
        boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
      }}
    >
      <Toolbar>
        {/* Left Side: Menu Icon */}
        <IconButton edge="start" aria-label="menu" sx={{ color: 'black' }}>
          <MenuIcon />
        </IconButton>

        {/* Center: Professional Text */}
        <Typography
          component="div"
          sx={{
            flexGrow: 1,
            textAlign: 'center',
            fontFamily: `'Roboto', sans-serif`,
            fontSize: '28px',
            fontWeight: 'bold',
            color: '#ffffff',
          }}
        >
          Rashmi Tyre Center
        </Typography>

        {/* Right Side: Logout Button */}
        <Button sx={{ color: 'black', fontWeight: 'bold' }} onClick={handleLogout}>Logout</Button>
      </Toolbar>
    </AppBar>
  );
}

export default HeaderBar;
