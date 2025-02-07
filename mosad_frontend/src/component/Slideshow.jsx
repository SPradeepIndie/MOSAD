import React from 'react';
import Carousel from 'react-material-ui-carousel';
import { Paper,  Typography } from '@mui/material';

import pic1 from './resources/pic1.jpg';
import pic2 from './resources/pic2.jpg';
import pic3 from './resources/pic3.jpg';

const items = [
  {
    title: "Welcome to Rashmi Tyre Center",
    description: "Your one-stop solution for all your tire needs!",
    image: pic1,
  },
  {
    title: "Best Quality Tires",
    description: "Offering a wide range of premium quality tires.",
    image: pic2,
  },
  {
    title: "Customer Satisfaction",
    description: "We prioritize your satisfaction with exceptional service.",
    image: pic3,
  },
];

function Slideshow() {
  return (
    <Carousel>
      {items.map((item, index) => (
        <Slide key={index} item={item} />
      ))}
    </Carousel>
  );
}

function Slide({ item }) {
  return (
    <Paper
      elevation={3}
      sx={{
        textAlign: 'center',
        backgroundImage: `url(${item.image})`,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        height: '300px',
        color: 'white',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
      }}
    >
      <Typography variant="h4" sx={{ fontWeight: 'bold', mb: 2 }}>
        {item.title}
      </Typography>
      <Typography variant="body1" sx={{ mb: 2 }}>
        {item.description}
      </Typography>
      
    </Paper>
  );
}

export default Slideshow;
