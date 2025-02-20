import React,{useState} from "react"; 
import {Box} from "@mui/material";
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import { Link, Outlet } from "react-router-dom";
const BillSectionLayout=()=>{

    const [value, setValue] = useState(0);
    
    const handleChange = ( event, newValue) => {
        setValue(newValue);
    };

    const tabRoutes = [
        { path: '/bill', label: 'bill page', id: 'bill_page' },
        { path: '/bill/AllBillsPage', label: 'all bill page', id: 'all_bill_page' }
        

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
            <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <Tabs value={value} onChange={handleChange} >
                    {renderTabs()}
                </Tabs>
            </Box>
            <Outlet/>                  
            </>
         
        )
}

export default BillSectionLayout;