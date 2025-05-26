import React,{useState} from "react"; 
import {Box} from "@mui/material";
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import { Link, Outlet } from "react-router-dom";
import useAuth from "../../hooks/useAuth";

const BranchPageLayout=()=>{
    const [value, setValue] = useState(0);
    const {auth}=useAuth();

    const handleChange = (event, newValue) => {
      setValue(newValue);
    };
     
    const handleRouteArray=()=>{
      switch(auth.roles[0]){
          case "ADMIN":
          case  "OWNER":
            return [
              { path: '/branches', label: 'Branch Details', id: 'branch_details' },
              { path: '/branches/bill-history', label: 'Bill History', id: 'bill_history' },
              { path: '/branches/stocks/category', label: 'Stock', id: 'stock' },
              { path: '/branches/employee-view', label: 'Employee view', id: 'employee_view' },
            ];
          case "BRANCH_MANAGER": 
          return [
            { path: '/branch', label: 'Branch Details', id: 'branch_details' },
            { path: '/branch/bill-history', label: 'Bill History', id: 'bill_history' },
            { path: '/branch/stock/category', label: 'Stock', id: 'stock' },
            { path: '/branch/employee-view', label: 'Employee view', id: 'employee_view' },
          ];
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

export default BranchPageLayout;