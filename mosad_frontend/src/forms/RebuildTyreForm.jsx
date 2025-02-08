import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import { TextField, Button, Grid, MenuItem, Box } from '@mui/material';

const statusOptions = [
  { value: 'IN_HOLD', label: 'In Hold' },
  { value: 'SENT_TO_REBUILD', label: 'Sent to Rebuild' },
  { value: 'DONE', label: 'Done' },
];

const RebuildTyreForm = ({ initialData = {}, onSubmit, onCancel }) => {
  const [formData, setFormData] = useState({
    customerId: '',
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
    setFormData({
      customerId: '',
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
  }, [initialData]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Validate required fields
    const {
      customerId,
      tyreNumber,
      tyreSize,
      tyreBrand,
      customerName,
      contactNumber,
      dateReceived,
      status,
    } = formData;
    if (!customerId || !tyreNumber || !tyreSize || !tyreBrand || !customerName || !contactNumber || !dateReceived || !status) {
      alert('Please fill in all required fields.');
      return;
    }
    onSubmit(formData);
  };

  return (
    <Box component="form" onSubmit={handleSubmit} sx={{ mt: 2, mb: 2 }}>
      <Grid container spacing={2}>
        <Grid item xs={12} sm={6}>
          <TextField
            label="Customer ID"
            name="customerId"
            type="number"
            value={formData.customerId}
            onChange={handleChange}
            required
            fullWidth
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            label="Tyre Number"
            name="tyreNumber"
            type="number"
            value={formData.tyreNumber}
            onChange={handleChange}
            required
            fullWidth
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            label="Tyre Size"
            name="tyreSize"
            value={formData.tyreSize}
            onChange={handleChange}
            required
            fullWidth
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            label="Tyre Brand"
            name="tyreBrand"
            value={formData.tyreBrand}
            onChange={handleChange}
            required
            fullWidth
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            label="Customer Name"
            name="customerName"
            value={formData.customerName}
            onChange={handleChange}
            required
            fullWidth
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            label="Contact Number"
            name="contactNumber"
            value={formData.contactNumber}
            onChange={handleChange}
            required
            fullWidth
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            label="Date Received"
            name="dateReceived"
            type="date"
            value={formData.dateReceived}
            onChange={handleChange}
            required
            fullWidth
            InputLabelProps={{ shrink: true }}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            label="Date Sent To Company"
            name="dateSentToCompany"
            type="date"
            value={formData.dateSentToCompany}
            onChange={handleChange}
            fullWidth
            InputLabelProps={{ shrink: true }}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            label="Sales Rep Number"
            name="salesRepNumber"
            value={formData.salesRepNumber}
            onChange={handleChange}
            fullWidth
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            label="Job Number"
            name="jobNumber"
            value={formData.jobNumber}
            onChange={handleChange}
            fullWidth
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            label="Date Received From Company"
            name="dateReceivedFromCompany"
            type="date"
            value={formData.dateReceivedFromCompany}
            onChange={handleChange}
            fullWidth
            InputLabelProps={{ shrink: true }}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            label="Date Delivered To Customer"
            name="dateDeliveredToCustomer"
            type="date"
            value={formData.dateDeliveredToCustomer}
            onChange={handleChange}
            fullWidth
            InputLabelProps={{ shrink: true }}
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            label="Bill Number"
            name="billNumber"
            value={formData.billNumber}
            onChange={handleChange}
            fullWidth
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            label="Price"
            name="price"
            type="number"
            value={formData.price}
            onChange={handleChange}
            fullWidth
          />
        </Grid>
        <Grid item xs={12} sm={6}>
          <TextField
            select
            label="Status"
            name="status"
            value={formData.status}
            onChange={handleChange}
            required
            fullWidth
          >
            {statusOptions.map((option) => (
              <MenuItem key={option.value} value={option.value}>
                {option.label}
              </MenuItem>
            ))}
          </TextField>
        </Grid>
      </Grid>
      <Box mt={2}>
        <Button type="submit" variant="contained" color="primary" sx={{ mr: 2 }}>
          Submit
        </Button>
        {onCancel && (
          <Button variant="outlined" color="secondary" onClick={onCancel}>
            Cancel
          </Button>
        )}
      </Box>
    </Box>
  );
};
RebuildTyreForm.propTypes = {
  initialData: PropTypes.object,
  onSubmit: PropTypes.func.isRequired,
  onCancel: PropTypes.func,
};

export default RebuildTyreForm;

