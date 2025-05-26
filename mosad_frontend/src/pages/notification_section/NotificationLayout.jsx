import React, { useState } from "react";
import { Box, Tabs, Tab, Typography } from "@mui/material";
import { Link, Outlet } from "react-router-dom";

const NotificationLayout = () => {
  const [value, setValue] = useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  const tabRoutes = [
    { path: '/notifications/credit', label: 'Credit', id: 'credit_tab' },
    { path: '/notifications/stock', label: 'Stock', id: 'stock_tab' }
  ];

  const renderTabs = () => {
    return tabRoutes.map((route, index) => (
      <Tab
        key={"tab" + index}
        component={Link}
        to={route.path}
        label={route.label}
        id={route.id}
      />
    ));
  };

  return (
    <>
      <Typography variant="h5" sx={{ mb: 2 }}>Notifications</Typography>
      <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
        <Tabs value={value} onChange={handleChange}>
          {renderTabs()}
        </Tabs>
      </Box>
      <Outlet />
    </>
  );
};

export default NotificationLayout;
