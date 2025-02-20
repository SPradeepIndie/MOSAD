import React, { useEffect, useState } from 'react';
import {
  Container,
  Typography,
  TextField,
  Button,
  Box,
  Paper,
  Dialog,
  DialogTitle,
  DialogContent,
  Slide,
  Grid2 as Grid,
} from '@mui/material';
import { createTheme, ThemeProvider } from '@mui/material/styles';
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


const theme = createTheme({
  palette: {
    primary: { main: '#1976d2' },
    secondary: { main: '#dc004e' },
    background: { default: '#f5f5f5' },
  },
  typography: {
    fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif',
  },
});

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

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
  const [openFormPopup, setOpenFormPopup] = useState(false);
  const [openInfoPopup, setOpenInfoPopup] = useState(false);
  const [infoTyre, setInfoTyre] = useState(null);

  const fetchTyres = async () => {
    try {
      let response;
      if (filter) {
        response = await getTyresByContactNumber(filter);
      } else {
        response = await getAllTyres();
      }
      setTyres(response.data);
    } catch (error) {
      console.error(error);
      alert('Error fetching tyre data');
    }
  };

  useEffect(() => {
    fetchTyres();
  }, [filter, refresh]);

  const handleFilterChange = (e) => setFilter(e.target.value);

  const handleFormSubmit = async (formData) => {
    try {
      if (editingTyre) {
        await updateTyre(editingTyre.itemId, formData);
        setEditingTyre(null);
      } else {
        await createTyre(formData);
      }
      setRefresh(!refresh);
      setOpenFormPopup(false);
    } catch (error) {
      console.error(error);
      alert(editingTyre ? 'Error updating tyre' : 'Error creating tyre');
    }
  };

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

  const handleUpdate = (tyre) => {
    setEditingTyre(tyre);
    setOpenFormPopup(true);
  };

  const handleInfo = (tyre) => {
    setInfoTyre(tyre);
    setOpenInfoPopup(true);
  };

  const handleCancelUpdate = () => {
    setEditingTyre(null);
    setOpenFormPopup(false);
  };

  const handleCloseInfo = () => {
    setInfoTyre(null);
    setOpenInfoPopup(false);
  };

  const handleAddOrder = () => {
    setEditingTyre(null);
    setOpenFormPopup(true);
  };

  return (
    <ThemeProvider theme={theme}>
      <Container sx={{ py: 4 }}>
        <Typography variant="h4" align="center" gutterBottom>
          Rebuild Tyre Management
        </Typography>

        <Paper sx={{ p: 2, mb: 3, boxShadow: 3, borderRadius: 2 }}>
          <Grid container spacing={2} alignItems="center">
            <Grid item xs={12} sm={8}>
              <TextField
                label="Filter by Contact Number"
                value={filter}
                onChange={handleFilterChange}
                variant="outlined"
                fullWidth
              />
            </Grid>
            <Grid item xs={12} sm={4} container spacing={1}>
              <Grid item xs={6}>
                <Button variant="contained" color="primary" onClick={fetchTyres} fullWidth>
                  Search
                </Button>
              </Grid>
              <Grid item xs={6}>
                <Button variant="outlined" onClick={() => setFilter('')} fullWidth>
                  Clear Filter
                </Button>
              </Grid>
            </Grid>
          </Grid>
        </Paper>

        <Box display="flex" justifyContent="flex-end" mb={2}>
          <Button variant="contained" color="primary" onClick={handleAddOrder}>
            Add Order
          </Button>
        </Box>

        <RebuildTyreTable
          tyres={tyres}
          onUpdate={handleUpdate}
          onInfo={handleInfo}
          onDelete={handleDeleteTyre}
        />

        <PopUp
          popUpTitle={editingTyre ? 'Update Order' : 'Add New Order'}
          openPopup={openFormPopup}
          setOpenPopup={setOpenFormPopup}
          setCancelButtonAction={handleCancelUpdate}
          isDefaultButtonsDisplay={false}
        >
          <RebuildTyreForm
            initialData={editingTyre || {}}
            onSubmit={handleFormSubmit}
            onCancel={handleCancelUpdate}
          />
        </PopUp>

        <Dialog
          open={openInfoPopup}
          onClose={handleCloseInfo}
          maxWidth="sm"
          fullWidth
          TransitionComponent={Transition}
        >
          <DialogTitle>More Info</DialogTitle>
          <DialogContent dividers>
            {infoTyre && (
              <Box>
                <Typography variant="body1">
                  <strong>Tyre Size:</strong> {infoTyre.tyreSize}
                </Typography>
                <Typography variant="body1">
                  <strong>Tyre Brand:</strong> {infoTyre.tyreBrand}
                </Typography>
                <Typography variant="body1">
                  <strong>Date Sent To Company:</strong> {infoTyre.dateSentToCompany}
                </Typography>
                <Typography variant="body1">
                  <strong>Sales Rep Number:</strong> {infoTyre.salesRepNumber}
                </Typography>
                <Typography variant="body1">
                  <strong>Job Number:</strong> {infoTyre.jobNumber}
                </Typography>
                <Typography variant="body1">
                  <strong>Date Received From Company:</strong> {infoTyre.dateReceivedFromCompany}
                </Typography>
                <Typography variant="body1">
                  <strong>Date Delivered To Customer:</strong> {infoTyre.dateDeliveredToCustomer}
                </Typography>
              </Box>
            )}
          </DialogContent>
          <Box display="flex" justifyContent="flex-end" p={2}>
            <Button variant="outlined" onClick={handleCloseInfo}>
              Close
            </Button>
          </Box>
        </Dialog>
      </Container>
    </ThemeProvider>
  );
};

export default RebuildTyrePage;
