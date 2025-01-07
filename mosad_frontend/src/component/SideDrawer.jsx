import * as React from 'react';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import List from '@mui/material/List';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import MenuIcon from '@mui/icons-material/Menu';
import HomeIcon from '@mui/icons-material/Home'; 
import PeopleIcon from '@mui/icons-material/People'; 
import IconButton from '@mui/material/IconButton';
import { Link } from 'react-router-dom'; 

export default function SideDrawer() {
  const [open, setOpen] = React.useState(false);

  const toggleDrawer = (newOpen) => () => {
    setOpen(newOpen);
  };

  const drawerItems = [
    { title: 'Home', link: '/home',icon: <HomeIcon /> },
    { title: 'User Management', link: '/user', icon: <PeopleIcon /> },
  ];
  return (
    <span>
      <IconButton onClick={toggleDrawer(true)} edge="start" aria-label="menu" sx={{ color: 'black' }}>
        <MenuIcon />
      </IconButton>
      <Drawer open={open} onClose={toggleDrawer(false)}>
        <Box sx={{ width: 250 }} role="presentation" onClick={toggleDrawer(false)}>
          <List>
            {['Inbox'].map((text) => (
            <ListItem key={text} disablePadding>
              <ListItemButton>
                <ListItemText primary={text} />
              </ListItemButton>
            </ListItem>
            ))}
          </List>
          <Divider />
          <List>
            {drawerItems.map((item) => (
              <ListItem key={item.title} disablePadding>
                <ListItemButton component={Link} to={item.link}> 
                  <ListItemIcon>{item.icon}</ListItemIcon>
                  <ListItemText primary={item.title} /> 
                </ListItemButton>
              </ListItem>
            ))}
          </List>
        </Box>
      </Drawer>
    </span>
  );
}