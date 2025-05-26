import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import { TextField, Button, MenuItem, Box, Grid2 as Grid, Paper,Typography } from '@mui/material';

const statusOptions = [
  { value: 'IN_HOLD', label: 'In Hold' },
  { value: 'SENT_TO_REBUILD', label: 'Sent to Rebuild' },
  { value: 'DONE', label: 'Done' },
];

const RebuildTyreForm = ({ initialData = {}, onSubmit, onCancel,setAlert }) => {
  const [formData, setFormData] = useState({
    tyreNumber: '',
    tyreSize: '',
    tyreBrand: '',
    customerName: '',
    contactNumber: '',
    dateReceived: '',
    dateSentToCompany: '',
    salesRepNumber: '',
    jobNumber: '',
    dateReceivedFromCompany: '',
    dateDeliveredToCustomer: '',
    billNumber: '',
    price: '',
    status: 'IN_HOLD',
    ...initialData,
  });

  useEffect(() => {
    setFormData((prev) => ({
      ...prev,
      ...initialData,
    }));
  }, [initialData]);

  const [error, setError] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));

     // Validate phone number (10 digits)
     if (name === 'contactNumber') {
      const phoneRegex = /^[0-9]{10}$/;
      if (!phoneRegex.test(value)) {
        setError('Please enter a valid 10-digit phone number.');
      } else {
        setError('');
      }
    }
 
  };

  const handleSubmit = (e) => {
    e.preventDefault();
   
    if (error){
      setAlert("warning",error);
      return
      }; // Prevent submission if there's an error
    const { tyreNumber, tyreSize, tyreBrand, customerName, contactNumber, dateReceived, status } = formData;
    if (!tyreNumber || !tyreSize || !tyreBrand || !customerName || !contactNumber || !dateReceived || !status) {
      setAlert("warning","Please fill in all required fields.");
      return;
    }
    onSubmit(formData);
  };

  return (
    <Paper elevation={3} sx={{ p: 4, maxWidth: 800, mx: 'auto' }}>
      <Typography variant="h5" gutterBottom>
        {initialData.id ? 'Edit Rebuild Tyre' : 'Add New Rebuild Tyre'}
      </Typography>
      <Box component="form" onSubmit={handleSubmit} sx={{ mt: 2 }}>
        <Grid container spacing={3}>
          {/* Left Column - Tyre Information */}
          <Grid item xs={12} md={6}>
            <Typography variant="subtitle1" gutterBottom>
              Tyre Information
            </Typography>
            <TextField label="Tyre Number" name="tyreNumber" type="number" value={formData.tyreNumber} onChange={handleChange} required fullWidth />
            <TextField label="Tyre Size" name="tyreSize" value={formData.tyreSize} onChange={handleChange} required fullWidth sx={{ mt: 2 }} />
            <TextField label="Tyre Brand" name="tyreBrand" value={formData.tyreBrand} onChange={handleChange} required fullWidth sx={{ mt: 2 }} />
          </Grid>

          {/* Right Column - Customer and Dates */}
          <Grid item xs={12} md={6}>
            <Typography variant="subtitle1" gutterBottom>
              Customer & Dates
            </Typography>
            <TextField label="Customer Name" name="customerName" value={formData.customerName} onChange={handleChange} required fullWidth />
            <TextField
              type='tel'
              label="Contact Number"
              name="contactNumber"
              value={formData.contactNumber}
              onChange={(e) => {
                const value = e.target.value;
                if (/^\d{0,10}$/.test(value)) {
                  handleChange(e);
                }
              }}
              required
              fullWidth
              sx={{ mt: 2 }}
              error={!!error} // Show error style if error exists
              helperText={error} // Show error message
            />            
      <TextField label="Date Received" name="dateReceived" type="date" value={formData.dateReceived} onChange={handleChange} required fullWidth InputLabelProps={{ shrink: true }} sx={{ mt: 2 }} />
            <TextField label="Status" name="status" select value={formData.status} onChange={handleChange} required fullWidth sx={{ mt: 2 }}>
              {statusOptions.map((option) => (
                <MenuItem key={option.value} value={option.value}>
                  {option.label}
                </MenuItem>
              ))}
            </TextField>
          </Grid>

          {/* Full Width - Optional Details */}
          <Grid item xs={12}>
            <Typography variant="subtitle1" gutterBottom sx={{ mt: 3 }}>
              Optional Details
            </Typography>
            <Grid container spacing={3}>
              <Grid item xs={12} sm={6} md={4}>
                <TextField label="Date Sent To Company" name="dateSentToCompany" type="date" value={formData.dateSentToCompany} onChange={handleChange} fullWidth InputLabelProps={{ shrink: true }} />
              </Grid>
              <Grid item xs={12} sm={6} md={4}>
                <TextField label="Sales Rep Number" name="salesRepNumber" value={formData.salesRepNumber} onChange={handleChange} fullWidth />
              </Grid>
              <Grid item xs={12} sm={6} md={4}>
                <TextField label="Job Number" name="jobNumber" value={formData.jobNumber} onChange={handleChange} fullWidth />
              </Grid>
              <Grid item xs={12} sm={6} md={4}>
                <TextField label="Date Received From Company" name="dateReceivedFromCompany" type="date" value={formData.dateReceivedFromCompany} onChange={handleChange} fullWidth InputLabelProps={{ shrink: true }} />
              </Grid>
              <Grid item xs={12} sm={6} md={4}>
                <TextField label="Date Delivered To Customer" name="dateDeliveredToCustomer" type="date" value={formData.dateDeliveredToCustomer} onChange={handleChange} fullWidth InputLabelProps={{ shrink: true }} />
              </Grid>
              <Grid item xs={12} sm={6} md={4}>
                <TextField label="Bill Number" name="billNumber" value={formData.billNumber} onChange={handleChange} fullWidth />
              </Grid>
              <Grid item xs={12} sm={6} md={4}>
                <TextField label="Price" name="price" type="number" value={formData.price} onChange={handleChange} fullWidth />
              </Grid>
            </Grid>
          </Grid>
        </Grid>
        <Box mt={3} sx={{ display: 'flex', justifyContent: 'flex-end' }}>
          {onCancel && (
            <Button variant="outlined" color="secondary" onClick={onCancel} sx={{ mr: 2 }}>
              Cancel
            </Button>
          )}
          <Button type="submit" variant="contained" color="primary">
            Submit
          </Button>
        </Box>
      </Box>
    </Paper>
  );
};

RebuildTyreForm.propTypes = {
  initialData: PropTypes.object,
  onSubmit: PropTypes.func.isRequired,
  onCancel: PropTypes.func,
};

export default RebuildTyreForm;
