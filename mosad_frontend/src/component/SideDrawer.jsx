import * as React from 'react';
import { styled,useTheme } from '@mui/material';
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
import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import IconButton from '@mui/material/IconButton';
import { Link } from 'react-router-dom'; 
import loadDashboard from '../utils/loadDashboard';

export default function SideDrawer() {
  const [open, setOpen] = React.useState(false);
  const theme = useTheme();

  const toggleDrawer = (newOpen) => () => {
    setOpen(newOpen);
  };

  const DrawerHeader = styled('div')(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
    justifyContent: 'flex-end',
  }));

  const drawerItems = [
    { title: 'Home', link: loadDashboard(),icon: <HomeIcon /> },
    { title: 'User Management', link: '/user', icon: <PeopleIcon /> },
  ];
  return (
    <span>
      <IconButton onClick={toggleDrawer(true)} edge="start" aria-label="menu" sx={{ color: 'black' }}>
        <MenuIcon />
      </IconButton>
      <Drawer open={open} onClose={toggleDrawer(false)}>
        <DrawerHeader>
          <IconButton onClick={toggleDrawer(false)}>
            {theme.direction === 'ltr' ? <ChevronLeftIcon /> : <ChevronRightIcon />}
          </IconButton>
        </DrawerHeader>
        <Divider/>
        <Box sx={{ width: 250 }} role="presentation" onClick={toggleDrawer(false)}>
          <List>
            {['Inbox'].map((text) => (
            <ListItem key={text} disablePadding>
              <ListItemButton component={Link} to='/notifications/credit'>
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