import React from 'react';
import { Box, Stack, Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import './RetailHomePage.css';

const RetailPage = ({ isOwner }) => {
  const navigate = useNavigate();

  return (
    <Box className="retail-homepage" sx={{ textAlign: 'center', padding: '20px' }}>
      {/* Image Section */}
      <Box className="image-section" sx={{ marginBottom: '20px' }}>
        <img
          src="/assets/retailCustomer.jpg" 
          alt="Retail Workspace"
          style={{ width: '100%', borderRadius: '10px', maxHeight: '300px', objectFit: 'cover' }}
        />
      </Box>

      {/* Button Section */}
      <Stack
        spacing={3}
        direction="row"
        sx={{
          justifyContent: 'center',
          flexWrap: 'wrap',
          gap: 2,
          marginBottom: 2,
        }}
      >
        <Button
          variant="contained"
          sx={{
            backgroundColor: '#4CAF50',
            color: '#fff',
            minWidth: '200px',
            height: '50px',
          }}
          onClick={() => navigate('/payment-history')}
        >
          Payment History
        </Button>
        <Button
          variant="contained"
          sx={{
            backgroundColor: '#4CAF50',
            color: '#fff',
            minWidth: '200px',
            height: '50px',
          }}
          onClick={() => navigate('/purchase-history')}
        >
          Purchase History
        </Button>
        <Button
          variant="contained"
          sx={{
            backgroundColor: '#4CAF50',
            color: '#fff',
            minWidth: '200px',
            height: '50px',
          }}
          onClick={() => navigate('/incomplete-transactions')}
        >
          Incomplete Transactions
        </Button>
      </Stack>

      {/* Conditional Rendering for Owner */}
      {isOwner && (
        <Stack
          direction="row"
          justifyContent="center"
          spacing={2}
          sx={{
            flexWrap: 'wrap',
          }}
        >
          <Button
            variant="contained"
            sx={{
              backgroundColor: '#4CAF50',
              color: '#fff',
              minWidth: '200px',
              height: '50px',
            }}
            onClick={() => navigate('/customer-details')}
          >
            Retail Customer Details
          </Button>
        </Stack>
      )}
    </Box>
  );
};

export default RetailPage;
