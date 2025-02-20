import AppBar from '@mui/material/AppBar';
import Button from '@mui/material/Button';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { React } from 'react';
import { Link ,useNavigate} from 'react-router-dom';
import SideDrawer from './SideDrawer';
import useAuth from "../hooks/useAuth"
import { useLogout } from '../hooks/servicesHook/useApiUserService';


function HeaderBar() {
  const logout = useLogout();
  const navigate = useNavigate();
  const{auth,setAuth}= useAuth();

  const handleLogout = () => {
    //send logout request to db
    logout({"refreshToken":auth.refreshToken}).then((response)=>{
      alert(response.data);
    }).finally(()=>{
      setAuth({refresh_token:"",Authenticated:false,username:""}) 
      navigate('/login', { replace: true }); // Redirect to login page
    });
   
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
        <Link to="/home" style={{ textDecoration: 'none' ,flexGrow: 1}}> 
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
