import React, { useEffect, useState } from 'react';
import {
  Container,
  Typography,
  TextField,
  Button,
  Box,
  Dialog,
  DialogTitle,
  DialogContent,
  Card,
  CardContent,
  Grid,
  Slide,
  Paper,
} from '@mui/material';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import RebuildTyreTable from '../../component/RebuildTyreTable.jsx';
import RebuildTyreForm from '../../forms/RebuildTyreForm.jsx';
import PopUp from '../../component/PopUp.jsx';
import {
  fetchRebuildTyres as getAllTyres,
  fetchRebuildTyresByContact as getTyresByContactNumber,
  createRebuildTyre as createTyre,
  updateRebuildTyre as updateTyre,
  deleteRebuildTyre as deleteTyre,
} from '../../services/apiDackService';

const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2',
    },
    secondary: {
      main: '#dc004e',
    },
    background: {
      default: '#fafafa',
    },
  },
  typography: {
    fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif',
  },
});

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

import PropTypes from 'prop-types';

const DashboardSummary = ({ tyres }) => {
  const totalOrders = tyres.length;
  const statusCounts = tyres.reduce((acc, tyre) => {
    acc[tyre.status] = (acc[tyre.status] || 0) + 1;
    return acc;
  }, {});
  return (
    <Card sx={{ mb: 2, backgroundColor: '#e8f5e9' }}>
      <CardContent>
        <Grid container spacing={2}>
          <Grid item xs={3}>
            <Typography variant="h6">Total Orders</Typography>
            <Typography variant="h4">{totalOrders}</Typography>
          </Grid>
          <Grid item xs={3}>
            <Typography variant="h6">In Hold</Typography>
            <Typography variant="h4">{statusCounts['IN_HOLD'] || 0}</Typography>
          </Grid>
          <Grid item xs={3}>
            <Typography variant="h6">Sent to Rebuild</Typography>
            <Typography variant="h4">{statusCounts['SENT_TO_REBUILD'] || 0}</Typography>
          </Grid>
          <Grid item xs={3}>
            <Typography variant="h6">Done</Typography>
            <Typography variant="h4">{statusCounts['DONE'] || 0}</Typography>
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  );
};

DashboardSummary.propTypes = {
  tyres: PropTypes.array.isRequired,
};

const RebuildTyrePage = () => {
  const [tyres, setTyres] = useState([]);
  const [filter, setFilter] = useState('');
  const [editingTyre, setEditingTyre] = useState(null);
  const [refresh, setRefresh] = useState(false);
  const [openUpdatePopup, setOpenUpdatePopup] = useState(false);
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

  const handleFilterChange = (e) => {
    setFilter(e.target.value);
  };

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
    setOpenUpdatePopup(true);
  };

  const handleInfo = (tyre) => {
    setInfoTyre(tyre);
    setOpenInfoPopup(true);
  };

  const handleCancelUpdate = () => {
    setEditingTyre(null);
    setOpenUpdatePopup(false);
  };

  const handleCloseInfo = () => {
    setInfoTyre(null);
    setOpenInfoPopup(false);
  };

  const handleAddOrder = () => {
    setEditingTyre(null);
    setOpenUpdatePopup(true);
  };

  return (
    <ThemeProvider theme={theme}>
      <Container sx={{ py: 4 }}>
        <Typography variant="h4" align="center" gutterBottom>
          Rebuild Tyre Management
        </Typography>

        <DashboardSummary tyres={tyres} />

        <Paper sx={{ p: 2, mb: 2 }}>
          <Box display="flex" justifyContent="space-between" alignItems="center">
            <Box display="flex" alignItems="center">
              <TextField
                label="Filter by Contact Number"
                value={filter}
                onChange={handleFilterChange}
                variant="outlined"
                sx={{ mr: 2 }}
              />
              <Button variant="contained" onClick={fetchTyres} sx={{ mr: 1 }}>
                Search
              </Button>
              <Button variant="outlined" onClick={() => setFilter('')}>
                Clear Filter
              </Button>
            </Box>
            <Button variant="contained" color="primary" onClick={handleAddOrder}>
              Add Order
            </Button>
          </Box>
        </Paper>

        <RebuildTyreTable tyres={tyres} onUpdate={handleUpdate} onInfo={handleInfo} onDelete={handleDeleteTyre} />

        <PopUp
          popUpTitle={editingTyre ? 'Update Order' : 'Add New Order'}
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
                <Typography>
                  <strong>Tyre Size:</strong> {infoTyre.tyreSize}
                </Typography>
                <Typography>
                  <strong>Tyre Brand:</strong> {infoTyre.tyreBrand}
                </Typography>
                <Typography>
                  <strong>Date Sent To Company:</strong> {infoTyre.dateSentToCompany}
                </Typography>
                <Typography>
                  <strong>Sales Rep Number:</strong> {infoTyre.salesRepNumber}
                </Typography>
                <Typography>
                  <strong>Job Number:</strong> {infoTyre.jobNumber}
                </Typography>
                <Typography>
                  <strong>Date Received From Company:</strong> {infoTyre.dateReceivedFromCompany}
                </Typography>
                <Typography>
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
