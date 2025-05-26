import React,{useState} from "react"; 
import {Box} from "@mui/material";
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import { Link, Outlet } from "react-router-dom";
import "./RetailPageLayout.css";
import useAuth from "../../../hooks/useAuth";

const RetailPageLayout=()=>{
  const {auth}=useAuth();
  const [value, setValue] = useState(0);
  
  const handleChange = (event, newValue) => {
      setValue(newValue);
  };

  const handleRouteArray=()=>{
    switch(auth.roles[0]){
        case "ADMIN":
        case  "OWNER":
          return [
            { path: '/retails', label: 'Payment History', id: 'payment_history' },
            { path: '/retails/incomplete-transactions', label: 'Incomplete Transactions', id: 'incomplete_transactions'},
            { path: '/retails/product-availability', label: 'Find Product Availability', id: 'product_availability'}
      
          ];
        case "RETAIL_CUSTOMER": 
          return [
            { path: '/retail/', label: 'Payment History', id: 'payment_history' },
            { path: '/retail/incomplete-transactions', label: 'Incomplete Transactions', id: 'incomplete_transactions'},
            { path: '/retail/product-availability', label: 'Find Product Availability', id: 'product_availability'}
        ]
    }
  }
 

  
      const renderTabs = () => {
          return handleRouteArray().map((route, index) => (
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

export default RetailPageLayout;