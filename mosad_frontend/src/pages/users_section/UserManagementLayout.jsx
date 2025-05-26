import {Link, Outlet } from "react-router-dom";
import React,{useState} from "react"; 
import {Box} from "@mui/material";
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import useAuth from '../../hooks/useAuth'
import { useLocation } from "react-router-dom";

const UserManagementLayout=()=>{
  const {auth}=useAuth();
  const location=useLocation();
  const [value, setValue] = useState(()=>{
    return location.pathname=='/user'? 0:1
  });

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  const tabRoutes = [
      { path: '/user', label: 'User Details', id: 'user_details',
        authorizedLink:true},
      { path: '/user/view-all', label: 'View All Users', id: 'view_all_users',
        authorizedLink:auth?.roles?.find(role=>["ADMIN","OWNER"].includes(role))},
    ];
    
    const renderTabs = () => {
      return tabRoutes.map((route, index) => (
        <Tab 
          disabled={!route.authorizedLink}
          key={"tab"+index} 
          component={Link} 
          to={route.path} 
          label={route.label} 
          id={route.id} 
        />
      ));
    };
  
    return(
        <>
            <Box sx={{ borderBottom: 1, borderColor: 'divider'}}>
                <Tabs value={value} onChange={handleChange} >
                    {renderTabs()}
                </Tabs>
            </Box>
            <Outlet/>
        </>
     
    )

}

export default UserManagementLayout;