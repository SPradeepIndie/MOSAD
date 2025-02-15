// src/pages/RebuildTyrePage.js
import React, { useEffect, useState } from 'react';
import { Container, Typography, TextField, Button, Box } from '@mui/material';
import RebuildTyreTable from '../../component/RebuildTyreTable.jsx';
import RebuildTyreForm from '../../forms/RebuildTyreForm.jsx';
import PopUp from '../../component/PopUp.jsx';
import {
  useFetchRebuildTyres,
  useCreateRebuildTyre,
  useFetchRebuildTyresByContact,
  useDeleteRebuildTyre,
  useUpdateRebuildTyre
} from '../../hooks/servicesHook/useDackService.js'


const RebuildTyrePage = () => {
  const getAllTyres = useFetchRebuildTyres();
  const getTyresByContactNumber=useFetchRebuildTyresByContact();
  const createTyre=useCreateRebuildTyre();
  const updateTyre=useUpdateRebuildTyre();
  const deleteTyre=useDeleteRebuildTyre();
  const [tyres, setTyres] = useState([]);
  const [filter, setFilter] = useState('');
  const [editingTyre, setEditingTyre] = useState(null);
  const [refresh, setRefresh] = useState(false);
  const [openUpdatePopup, setOpenUpdatePopup] = useState(false);
  const [openInfoPopup, setOpenInfoPopup] = useState(false);
  const [infoTyre, setInfoTyre] = useState(null);

  // Fetch tyre data from the API
  const fetchTyres = async () => {
    try {
      let response;
      if (filter) {
        response = await getTyresByContactNumber(filter);
      } else {
        response = await getAllTyres();
      }
      console.log('Fetched tyres:', response.data);
      setTyres(response.data);
    } catch (error) {
      console.error(error);
      alert('Error fetching tyre data');
    }
  };

  useEffect(() => {
    fetchTyres();
  }, [filter, refresh]);

  // Handlers for search/filter inputs
  const handleFilterChange = (e) => {
    setFilter(e.target.value);
  };

  // Form submission for both add and update
  const handleFormSubmit = async (formData) => {
    try {
      if (editingTyre) {
        await updateTyre(editingTyre.itemId, formData);
        setEditingTyre(null);
      } else {
        await createTyre(formData);
      }
      setRefresh(!refresh);
      setOpenUpdatePopup(false);
    } catch (error) {
      console.error(error);
      alert(editingTyre ? 'Error updating tyre' : 'Error creating tyre');
    }
  };

  // Handler for deletion
  const handleDeleteTyre = async (id) => {
    if (window.confirm('Are you sure you want to delete this tyre?')) {
      try {
        await deleteTyre(id);
        setRefresh(!refresh);
      } catch (error) {
        console.error(error);
        alert('Error deleting tyre');
      }
    }
  };

  // When clicking "Update" on a row, open the update popup with the form pre-filled
  const handleUpdate = (tyre) => {
    setEditingTyre(tyre);
    setOpenUpdatePopup(true);
  };

  // When clicking "More Info", open the info popup with the removed fields
  const handleInfo = (tyre) => {
    setInfoTyre(tyre);
    setOpenInfoPopup(true);
  };

  // Cancel the update form
  const handleCancelUpdate = () => {
    setEditingTyre(null);
    setOpenUpdatePopup(false);
  };

  // Close the info popup
  const handleCloseInfo = () => {
    setInfoTyre(null);
    setOpenInfoPopup(false);
  };

  // When clicking "Add Order", open the update popup with an empty form
  const handleAddOrder = () => {
    setEditingTyre(null);
    setOpenUpdatePopup(true);
  };

  return (
    <Container>
      <Typography variant="h4" align="center" gutterBottom>
        Rebuild Tyre Management
      </Typography>
      <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
        {/* Left side: search/filter controls */}
        <Box display="flex" alignItems="center">
          <TextField
            label="Filter by Contact Number"
            value={filter}
            onChange={handleFilterChange}
            variant="outlined"
            sx={{ mr: 2 }}
          />
          <Button variant="contained" onClick={fetchTyres}>
            Search
          </Button>
          <Button variant="outlined" onClick={() => setFilter('')} sx={{ ml: 2 }}>
            Clear Filter
          </Button>
        </Box>
        {/* Right side: Add Order button */}
        <Button variant="contained" color="primary" onClick={handleAddOrder}>
          Add Order
        </Button>
      </Box>

      {/* Table displaying current items from the database */}
      <RebuildTyreTable tyres={tyres} onUpdate={handleUpdate} onInfo={handleInfo} onDelete={handleDeleteTyre} />

      {/* Pop-up form for Add/Update */}
      <PopUp
        popUpTitle={editingTyre ? "Update Order" : "Add New Order"}
        openPopup={openUpdatePopup}
        setOpenPopup={setOpenUpdatePopup}
        setCancelButtonAction={handleCancelUpdate}
        paperSx={{
          position: 'absolute',
          right: 20,
          top: 50,
          m: 0,
        }}
        isDefaultButtonsDisplay={false}
      >
        <RebuildTyreForm
          initialData={editingTyre || {}}
          onSubmit={handleFormSubmit}
          onCancel={handleCancelUpdate}
        />
      </PopUp>

      {/* Pop-up for More Info to display removed fields */}
      <Dialog open={openInfoPopup} onClose={handleCloseInfo} maxWidth="sm" fullWidth>
        <DialogTitle>More Info</DialogTitle>
        <DialogContent dividers>
          {infoTyre && (
            <Box>
              <Typography><strong>Tyre Size:</strong> {infoTyre.tyreSize}</Typography>
              <Typography><strong>Tyre Brand:</strong> {infoTyre.tyreBrand}</Typography>
              <Typography><strong>Date Sent To Company:</strong> {infoTyre.dateSentToCompany}</Typography>
              <Typography><strong>Sales Rep Number:</strong> {infoTyre.salesRepNumber}</Typography>
              <Typography><strong>Job Number:</strong> {infoTyre.jobNumber}</Typography>
              <Typography><strong>Date Received From Company:</strong> {infoTyre.dateReceivedFromCompany}</Typography>
              <Typography><strong>Date Delivered To Customer:</strong> {infoTyre.dateDeliveredToCustomer}</Typography>
            </Box>
          )}
        </DialogContent>
        <Box display="flex" justifyContent="flex-end" p={2}>
          <Button variant="outlined" onClick={handleCloseInfo}>Close</Button>
        </Box>
      </Dialog>
    </Container>
  );
};

export default RebuildTyrePage;
