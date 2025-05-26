import React, { useEffect, useState } from "react";
import {
  Box,
  Card,
  CardContent,
  Typography,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  CircularProgress
} from "@mui/material";
import Inventory2Icon from "@mui/icons-material/Inventory2"; // icon for stock
import { useFetchNotificationsByType } from '../../hooks/servicesHook/useNotificationService'; // Adjust the path as necessary

const StockNotifications = () => {
  const [notifications, setNotifications] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchNotificationsByType = useFetchNotificationsByType();

  useEffect(() => {
    const fetchNotifications = async () => {
      try {
        const response = await fetchNotificationsByType("Low Stock");
        setNotifications(response.data);
      } catch (error) {
        console.error("Failed to fetch low stock notifications:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchNotifications();
  }, []);

  if (loading) {
    return (
      <Box sx={{ p: 2, textAlign: "center" }}>
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Box sx={{ p: 2 }}>
      <List>
        {notifications.map((notif, index) => (
          <ListItem key={index} disablePadding sx={{ mb: 2 }}>
            <Card sx={{ width: '100%' }}>
              <CardContent sx={{ display: "flex", alignItems: "center" }}>
                <ListItemIcon>
                  <Inventory2Icon color="warning" />
                </ListItemIcon>
                <ListItemText
                  primary={
                    <Typography variant="subtitle1" fontWeight="bold">
                      {notif.msg}
                    </Typography>
                  }
                />
              </CardContent>
            </Card>
          </ListItem>
        ))}
      </List>
    </Box>
  );
};

export default StockNotifications;
