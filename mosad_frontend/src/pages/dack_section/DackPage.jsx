import React, { useEffect, useState } from 'react';
import {
  Container,
  Typography,
  TextField,
  Button,
  Box,
  Paper,
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
import GeneralSnackbarAlerts from '../../component/GeneralSnackbarAlerts.jsx';

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

  //Show alerts using snack bar
  const [showSnack, setShowSnack] = useState(false);
  const [alertType, setAlertType] = useState("warning");
  const [alertMsg, setAlertMsg] = useState("");

  const moreInfoForm = (infoTyre) => {
    return (
      <Paper elevation={2} sx={{ p: 3 }}>
        <Typography variant="h4" gutterBottom sx={{pb:2}}>
          Tyre Details
        </Typography>
        <Grid container spacing={3}>
          <Grid size={{xs:12,sm:6}}>
            <Typography variant="body1">
              Tyre Size: {infoTyre?.tyreSize}
            </Typography>
          </Grid>
          <Grid size={{xs:12,sm:6}}>
            <Typography variant="body1">
              Tyre Brand: {infoTyre?.tyreBrand}
            </Typography>
          </Grid>
          <Grid size={{xs:12,sm:6}}>
            <Typography variant="body1">
              Date Sent To Company: {infoTyre?.dateSentToCompany}
            </Typography>
          </Grid>
          <Grid size={{xs:12,sm:6}}>
            <Typography variant="body1">
              Sales Rep Number: {infoTyre?.salesRepNumber}
            </Typography>
          </Grid>
          <Grid size={{xs:12,sm:6}}>
            <Typography variant="body1">
              Job Number: {infoTyre?.jobNumber}
            </Typography>
          </Grid>
          <Grid size={{xs:12,sm:6}}>
            <Typography variant="body1">
              Date Received From Company: {infoTyre?.dateReceivedFromCompany}
            </Typography>
          </Grid>
          <Grid size={{xs:12}}>
            <Typography variant="body1">
              Date Delivered To Customer: {infoTyre?.dateDeliveredToCustomer}
            </Typography>
          </Grid>
        </Grid>
      </Paper>
    );
  };

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
      handleAlert("error", "Error fetching tyres");
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
      handleAlert("error", editingTyre ? 'Error updating tyre' : 'Error creating tyre');
    }
  };

  const handleDeleteTyre = async (id) => {
    if (window.confirm('Are you sure you want to delete this tyre?')) {
      try {
        await deleteTyre(id);
        setRefresh(!refresh);
      } catch (error) {
        console.error(error);
        handleAlert("error", "Error deleting tyre");
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

  const handleAlert = (type, msg) => {
    setAlertType(type);
    setAlertMsg(msg);
    setShowSnack(true);
  }

  return (
    <ThemeProvider theme={theme}>
      <Container sx={{ py: 4 }}>
      <GeneralSnackbarAlerts open={showSnack} type={alertType} msg={alertMsg} setOpen={setShowSnack}/>
        <Typography variant="h4" align="center" sx={{mb:2}}>
          Dack Tyre Management
        </Typography>

        <Paper sx={{ p: 2, mb: 3, boxShadow: 3, borderRadius: 2 }}>
          <Grid container spacing={2} alignItems="center">
            <Grid size={{xs:12,sm:8}}>
              <TextField
                type='tel'
                label="Filter by Contact Number"
                value={filter}
                onChange={(e) => {
                  const value = e.target.value;
                if (/^\d{0,10}$/.test(value)) {
                  handleFilterChange(e);
                }
                }}
                //onChange={handleFilterChange}
                variant="outlined"
                fullWidth
              />
            </Grid>
            <Grid size={{xs:12,sm:4}} sm={4} container spacing={1}>
              <Grid size={{xs:12}}>
                <Button variant="contained" color="primary" onClick={fetchTyres} fullWidth>
                  Search
                </Button>
              </Grid>
              <Grid size={{xs:12}}>
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
            setAlert={handleAlert}
          />
        </PopUp>


        <PopUp
          popUpTitle='More Info'
          openPopup={openInfoPopup}
          setOpenPopup={setOpenInfoPopup}
          setCancelButtonAction={handleCloseInfo}
          isDefaultButtonsDisplay={false}
        >
         {infoTyre && moreInfoForm(infoTyre)}
        </PopUp>
      </Container>
    </ThemeProvider>
  );
};

export default RebuildTyrePage;
