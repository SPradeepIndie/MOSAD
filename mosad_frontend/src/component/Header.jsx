import AppBar from '@mui/material/AppBar';
import Button from '@mui/material/Button';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { React } from 'react';
import { useNavigate,Link } from 'react-router-dom';
import SideDrawer from './SideDrawer';

function HeaderBar({ setIsLoggedIn }) {
 
  const navigate = useNavigate();

  const handleLogout = () => {
    // Remove token from local storage
    localStorage.removeItem('token');
    setIsLoggedIn(false);
    // Redirect to login page
    navigate('/', { replace: true });

  };

  return (
    <AppBar
      position="static"
      sx={{
        backgroundColor: 'gray',
        maxWidth: '1600px',
        margin: 'auto',
        boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
      }}
    >
      <Toolbar sx={{ display: 'flex', justifyContent: 'center' }}>
        {/* Drawer */}
        <SideDrawer/>

        
        {/* Center: Professional Text */}
        <Link to="/" style={{ textDecoration: 'none' ,flexGrow: 1}}> 
        <Typography
          component="div"
          sx={{  
            textAlign: 'center',
            fontFamily: `'Roboto', sans-serif`,
            fontSize: '28px',
            fontWeight: 'bold',
            color: '#ffffff',
          }}
        >
          Rashmi Tyre Center
        </Typography>
        </Link>

        {/* Right Side: Logout Button */}
        <Button sx={{ color: 'black', fontWeight: 'bold' }} onClick={handleLogout}>Logout</Button>
      </Toolbar>
    </AppBar>
  );
}

export default HeaderBar;
