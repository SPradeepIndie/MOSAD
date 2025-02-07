import React from 'react';
import { Card, CardActionArea, CardContent, Typography, Box } from '@mui/material';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';

function Tile({ title, icon, link, state, onClick }) {
  const isClickable = Boolean(onClick) || Boolean(link);

  return (
    <Card
      component={isClickable && !onClick ? Link : 'div'} // Use 'div' if onClick is present
      to={link}
      state={state}
      onClick={onClick} // Trigger the onClick handler for non-link actions
      sx={{
        height: 'auto',
        width: '15em',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
        boxShadow: 3,
        borderRadius: 2,
        cursor: isClickable ? 'pointer' : 'default', // Ensure pointer cursor only when clickable
        backgroundColor: '#2E8B58',
        ':hover': isClickable
          ? { backgroundColor: '#FFC220', boxShadow: 6 }
          : undefined, // Disable hover effect if not clickable
      }}
    >
      <CardActionArea
        sx={{ height: '100%', width: '100%', textAlign: 'center' }}
        onClick={onClick} // Ensure onClick is handled properly
      >
        <CardContent>
          <Box
            sx={{
              fontSize: '40px',
              marginBottom: '10px',
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center',
            }}
          >
            {icon}
          </Box>
          <Typography
            variant="h6"
            sx={{
              color: 'white',
              fontSize: '18px',
            }}
          >
            {title}
          </Typography>
        </CardContent>
      </CardActionArea>
    </Card>
  );
}

Tile.propTypes = {
  title: PropTypes.string.isRequired,
  icon: PropTypes.node.isRequired,
  link: PropTypes.string,
  state: PropTypes.object,
  onClick: PropTypes.func, // Allow onClick for tiles that trigger actions instead of navigation
};

export default Tile;
