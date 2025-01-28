import {Link, Outlet } from "react-router-dom";
import React,{useState} from "react"; 
import {Box} from "@mui/material";
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';


const UserManagementLayout=()=>{
    const [value, setValue] = useState(0);

    const handleChange = (event, newValue) => {
      setValue(newValue);
    };

    const tabRoutes = [
        { path: '/user', label: 'User Details', id: 'user_details' },
        { path: '/user/view-all', label: 'View All Users', id: 'view_all_users' },
      ];
      
      const renderTabs = () => {
        return tabRoutes.map((route, index) => (
          <Tab 
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