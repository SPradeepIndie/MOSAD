import AppBar from '@mui/material/AppBar';
import Button from '@mui/material/Button';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import { React, useState } from 'react';
import { Link ,useNavigate} from 'react-router-dom';
import SideDrawer from './SideDrawer';
import useAuth from "../hooks/useAuth"
import { useLogout } from '../hooks/servicesHook/useApiUserService';
import ConfirmationDialog from './ConfirmationDialog';
import Cookies from 'universal-cookie';

function HeaderBar() {
  const [openConfirmationDialog,setOpenConfirmationDialog] =useState(false);
  const logout = useLogout();
  const navigate = useNavigate();
  const{auth,setAuth}= useAuth();
  const cookies=new Cookies();

  const setConfirmButtonAction=(event)=>{
    handleLogout(event);
    setOpenConfirmationDialog(false)
  }
  const setCancelButtonAction=()=>{
    setOpenConfirmationDialog(false)
  }

  const handleLogout = (event) => {
    //send logout request to db
    logout().then((response)=>{
      console.log(response.data)
    }).finally(()=>{
      if(!auth.remember_me){
        cookies.remove("remember_me");
      }
      setAuth({})
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
        <Button sx={{ color: 'black', fontWeight: 'bold' }} onClick={()=>setOpenConfirmationDialog(true)}>Logout</Button>
          <ConfirmationDialog
              message={"Are you sure you want to logout"}
              onCancel={setCancelButtonAction}
              onConfirm={setConfirmButtonAction}
              isOpen={openConfirmationDialog}
            />
      </Toolbar>
    </AppBar>
    
  );
}

export default HeaderBar;
