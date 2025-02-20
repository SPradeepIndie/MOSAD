import React, { useState } from 'react';
import { Card, CardContent, Typography, Box, Button, TextField } from '@mui/material';

function ProductCardComponent({ category, brand, size, ptr }) {
  if (!category) {
    throw new Error("Category is a required field.");
  }

  const [quantity, setQuantity] = useState(0);

  const handleIncrease = () => {
    setQuantity((prev) => prev + 1);
  };

  const handleDecrease = () => {
    setQuantity((prev) => (prev > 0 ? prev - 1 : 0));
  };

  return (
    <Card
      sx={{            
        width: '250px',
        borderRadius: '12px',
        boxShadow: 3,
        overflow: 'hidden',
        backgroundColor: '#EAF8E6',
      }}
    >
      <Box
        sx={{
          backgroundColor: '#2E8B58',
          color: 'white',
          padding: '16px',
          textAlign: 'center',
          fontWeight: 'bold',
        }}
      >
        <Typography variant="h6" sx={{ fontSize: '18px' }}>
          {category}
        </Typography>
      </Box>
      <CardContent>
        {brand && (
          <Typography variant="body1" sx={{ marginBottom: '8px' }}>
            <strong>Brand:</strong> {brand}
          </Typography>
        )}
        {size && (
          <Typography variant="body1" sx={{ marginBottom: '8px' }}>
            <strong>Size:</strong> {size}
          </Typography>
        )}
        {ptr && (
          <Typography variant="body1" sx={{ marginBottom: '16px' }}>
            <strong>PTR:</strong> {ptr}
          </Typography>
        )}
        <Box
          sx={{
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'space-between',
          }}
        >
          <Button
            onClick={handleDecrease}
            sx={{
              minWidth: '40px',
              height: '40px',
              borderRadius: '50%',
              backgroundColor: '#2E8B58',
              color: 'white',
              ':hover': { backgroundColor: '#FFC220' },
            }}
          >
            -
          </Button>
          <TextField
            value={quantity}
            onChange={(e) => setQuantity(Math.max(0, Number(e.target.value)))}
            inputProps={{
              style: { textAlign: 'center' },
              type: 'number',
              min: 0,
            }}
            sx={{
              width: '60px',
              textAlign: 'center',
              backgroundColor: 'white',
              borderRadius: '4px',
            }}
          />
          <Button
            onClick={handleIncrease}
            sx={{
              minWidth: '40px',
              height: '40px',
              borderRadius: '50%',
              backgroundColor: '#2E8B58',
              color: 'white',
              ':hover': { backgroundColor: '#FFC220' },
            }}
          >
            +
          </Button>
        </Box>
      </CardContent>
    </Card>
  );
}

export default ProductCardComponent;
