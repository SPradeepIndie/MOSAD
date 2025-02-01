import React, { useState, useEffect } from 'react';
import {
  Box, Container, Collapse, IconButton, Button, Table, TableBody, TableCell,
  TableContainer, TableHead, TableRow, Typography, Paper, TextField,
  FormControlLabel, Dialog, DialogActions, DialogContent, DialogTitle, RadioGroup, Radio, FormControl
} from '@mui/material';
import { Delete, KeyboardArrowDown as KeyboardArrowDownIcon, KeyboardArrowUp as KeyboardArrowUpIcon } from '@mui/icons-material';
import { addRepayment,deleteRepayment, fetchAllCreditDetails } from '../../services/apiCreditService';
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider, DatePicker } from '@mui/x-date-pickers';
import dayjs from 'dayjs';
import GeneralMessage from '../../component/GeneralMessage';
import Loading from '../../component/Loading';
import ConfirmationDialog from '../../component/ConfirmationDialog';
import PropTypes from 'prop-types';

function Row({ row, onAddRepayment,onDeleteRepayment, setMessage, message,columns }) {
  const [open, setOpen] = useState(false);
  const [openDialog, setOpenDialog] = useState(false);
  const [newRepayment, setNewRepayment] = useState({ date: '', amount: '' });
  const [conformationDialog, setConformationDialog] = useState(false);


  const handleDialogOpen = () => {
    setOpenDialog(true);
    setNewRepayment({ ...newRepayment, date: dayjs().format('YYYY-MM-DD') });
  };

  const handleDialogClose = () => {
    setOpenDialog(false);
    setNewRepayment({ date: '', amount: '' });
  };

  const handleAddRepayment = () => {
    if (newRepayment.date && newRepayment.amount) {
      onAddRepayment(row.creditId, newRepayment); // Call the parent callback with new repayment details
      handleDialogClose();
    } else {
      setMessage({ type: 'error', text: 'Please fill in all fields!' });
      setTimeout(() => setMessage(null), 2000);
    }
  };

  const handleDeleteRepayment = (id) => {
    
    onDeleteRepayment(row.creditId,id);
    setConformationDialog(false);
  };

  

  const remainingBalance = row.balance - row.repayments.reduce((acc, repayment) => acc + repayment.amount, 0);

  return (
    <>
      
      <TableRow sx={{ '& > *': { borderBottom: 'unset' }, backgroundColor: remainingBalance == 0 ? '#C8E6C9' : '' }}>
        <TableCell>
          <IconButton
            aria-label="expand row"
            size="small"
            onClick={() => setOpen(!open)}
          >
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        {columns.creditId && <TableCell>{row.creditId}</TableCell>}
        <TableCell>{row.billId}</TableCell>
        <TableCell component="th" scope="row">
          {row.customerName}
        </TableCell>
        <TableCell>{row.contactNumber}</TableCell>
        <TableCell align="right">{row.balance}</TableCell>
        <TableCell>{dayjs(row.dueDate).format('YYYY-MM-DD')}</TableCell>
        <TableCell align="right" sx={{ color: remainingBalance == 0 ? 'green' : 'black', fontWeight: 'bold', fontSize: 20 }}>
          {remainingBalance == 0 ? 'Completed' : remainingBalance}
        </TableCell>
      </TableRow>

      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={7}>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <Box sx={{ margin: 1 }}>
              <Typography variant="h6" gutterBottom component="div">
                Repayment History
              </Typography>
              <Table size="small" aria-label="repayments">
                <TableHead>
                  <TableRow>
                    <TableCell>Repayment ID</TableCell>
                    <TableCell>Date</TableCell>
                    <TableCell align="right">Amount (Rs.)</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {row.repayments.map((repayment) => (
                    <TableRow key={repayment.repaymentId}>
                      <TableCell>{repayment.repaymentId}</TableCell>
                      <TableCell>{dayjs(repayment.date).format('YYYY-MM-DD')}</TableCell>
                      <TableCell align="right">{repayment.amount}</TableCell>
                      <TableCell align="right"><Delete onClick={()=>setConformationDialog(true)} sx={{scale:0.75,cursor:'pointer'}}/></TableCell>
                      {conformationDialog &&
      <ConfirmationDialog message='Are you sure you want to delete this repayment?' isOpen={open} onCancel={()=>setConformationDialog(false) } onConfirm={()=>handleDeleteRepayment(repayment.repaymentId)} />
    }
                    </TableRow>
                  ))}
                  <TableRow sx={{ borderTop: 2 }}>
                    <TableCell colSpan={2}>
                      <Typography variant="body1" fontWeight="bold">
                        Total Repaid
                      </Typography>
                    </TableCell>
                    <TableCell align="right" fontWeight="bold">
                      {row.repayments.reduce((acc, repayment) => acc + repayment.amount, 0)}
                    </TableCell>
                  </TableRow>
                </TableBody>
              </Table>
              {remainingBalance != 0 &&
                <Button
                  variant="contained"
                  size="small"
                  onClick={handleDialogOpen}
                  sx={{ marginTop: 2, backgroundColor: '#4CAF50', color: 'white' }}
                >
                  Add New Repayment
                </Button>
              }

            </Box>
          </Collapse>
        </TableCell>
      </TableRow>


      <Dialog
        open={openDialog}
        onClose={handleDialogClose}
        aria-modal="true"
        aria-labelledby="add-repayment-title"
        aria-describedby="add-repayment-description"

      >
        <DialogTitle id="add-repayment-title">Add New Repayment</DialogTitle>
        <DialogContent id="add-repayment-description">

          <LocalizationProvider dateAdapter={AdapterDayjs}>
            <DemoContainer components={['DatePicker']}>
              <DatePicker
                label="Basic date picker"
                value={dayjs()}
                onChange={(newValue) =>
                  setNewRepayment({ ...newRepayment, date: newValue ? newValue.format('YYYY-MM-DD') : value.format('YYYY-MM-DD') })
                }
                format='YYYY/MM/DD'
              />
            </DemoContainer>
          </LocalizationProvider>
          <TextField
            margin="dense"
            label="Amount"
            type="number"
            fullWidth
            variant="outlined"
            helperText={`Remaining Balance: ${remainingBalance}`}
            error={newRepayment.amount > remainingBalance}
            value={newRepayment.amount}
            onChange={(e) => setNewRepayment({ ...newRepayment, amount: e.target.value })}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleDialogClose}>Cancel</Button>
          {
            <Button
              onClick={handleAddRepayment}
              variant="contained"
              disabled={!newRepayment.amount || newRepayment.amount > remainingBalance}
              sx={{ backgroundColor: '#4CAF50', color: 'white' }}
            >
              Add
            </Button>
          }
        </DialogActions>

      </Dialog>
      {message && <GeneralMessage message={message} />}

    </>
  );


}


const CreditPage = () => {
  const [rows, setRows] = useState([]);
  const [searchText, setSearchText] = useState('');
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState(null);
  const [state, setState] = useState({ all: false, completed: false, incompleted: true, });
  const [customerType, setCustomerType] = useState('RETAIL');
  const columns = {'creditId':false,'billId':true,'customerName':true,'contactNumber':true,'balance':true,'dueDate':true,'remainingBalance':true};

  const handleRadioChange = (event) => {
    const { value } = event.target;

    // Update state based on selected radio button
    setState({
      all: value === 'all',
      completed: value === 'completed',
      incompleted: value === 'incompleted',
    });
  };
  let selectedValue;
  if (state.all) {
    selectedValue = 'all';
  }
  else if (state.completed) {
    selectedValue = 'completed';
  }
  else {
    selectedValue = 'incompleted';
  }



  useEffect(() => {
    const fetchData = async () => {

      try {
        const response = await fetchAllCreditDetails(customerType);
        setRows(response.data);
        console.log(response.data);
        setLoading(false);
      } catch (error) {
        console.error('Error fetching data:', error);
        setLoading(true);
      }
    };

    fetchData();
  }, [customerType]);

  const handleAddRepayment = async (creditId, repayment) => {

    try {
      const response = await addRepayment(creditId, repayment);

      console.log('Repayment added successfully:', response.data);

      setMessage({ type: 'success', text: 'Repayment added successfully!' });
      setTimeout(() => setMessage(null), 2000);

      // Update the rows state
      setRows((prevRows) =>
        prevRows.map((row) =>
          row.creditId === creditId
            ? {
              ...row,
              repayments: [...row.repayments, response.data], // Append the new repayment
            }
            : row
        )
      );
    } catch (error) {
      console.error('Error adding repayment:', error.response?.data || error.message);
      setMessage({ type: 'error', text: 'Failed to add repayment!' });
      setTimeout(() => setMessage(null), 2000);
    }
  };

  const handleDeleteRepayment =async (creditId,id) => {
    
      try {
        const response = await deleteRepayment(id);
        console.log('Repayment deleted successfully:', response.data);

        setMessage({ type: 'success', text: 'Repayment deleted successfully!' });
        setTimeout(() => setMessage(null), 2000);

        // Update the rows state
        setRows((prevRows) =>
          prevRows.map((row) =>
            row.creditId === creditId
              ? {
                ...row,
                repayments: row.repayments.filter((repayment) => repayment.repaymentId !== id),
              }
              : row
          )
        );
      } catch (error) {
        console.error('Error deleting repayment:', error.response?.data || error.message);
        setMessage({ type: 'error', text: 'Failed to delete repayment!' });
        setTimeout(() => setMessage(null), 2000);
      }
    
  };



  let remainingBalance;
  const filteredRows = rows.filter((row) => {
    remainingBalance = row.balance - row.repayments.reduce((acc, repayment) => acc + repayment.amount, 0);

    if (state.all) return true; // Show all credits
    if (state.completed) return remainingBalance === 0; // Show completed credits
    if (state.incompleted) return remainingBalance > 0; // Show incomplete credits
    return false;
  }).filter(
    (row) =>
      row.customerName.toLowerCase().includes(searchText.toLowerCase()) ||
      row.contactNumber.toLowerCase().includes(searchText.toLowerCase()) ||
      row.creditId.toString().includes(searchText)
  );




  if (loading) {
    return (
      <Loading WhatsLoading={'Credit Details'} />
    );
  }

  return (
    <Container maxWidth="lg" sx={{ marginTop: 4 }}>

      <Paper elevation={3} sx={{ padding: 3 }}>
        <FormControl component="fieldset" sx={{ width: '100%' }}>
          {/* Top Row: Search Field and Radio Group */}
          <Box
            sx={{
              display: 'flex',
              flexDirection: { xs: 'column', sm: 'row' }, // Column for small screens
              alignItems: 'center',
              justifyContent: { sm: 'space-between' },
              marginBottom: 2,
              gap: 1, // Add spacing for stacked layout
            }}
          >
            <Box sx={{ display: 'flex', gap: 1 }}>
              <Button
                variant={customerType === 'RETAIL' ? 'contained' : 'outlined'}
                onClick={() => setCustomerType('RETAIL')}
                sx={{ flex: 1 }} // Take available space
              >
                Retail
              </Button>
              <Button
                variant={customerType === 'NORMAL' ? 'contained' : 'outlined'}
                onClick={() => setCustomerType('NORMAL')}
                sx={{ flex: 1 }} // Take available space
              >
                Normal
              </Button>
            </Box>

            {/* Search Field */}
            <TextField
              fullWidth
              size="small"
              label="Search By Name / Contact Number / Credit ID"
              variant="outlined"
              value={searchText}
              onChange={(e) => setSearchText(e.target.value)}
              sx={{
                flex: { xs: '1 1 100%', sm: '1' }, // Full width on small screens
                marginTop: { xs: 1, sm: 0 }, // Add margin on small screens
                minWidth: '25%',
              }}
            />

            {/* Radio Buttons */}
            <RadioGroup
              row
              name="creditOptions"
              value={selectedValue}
              onChange={handleRadioChange}
              sx={{
                justifyContent: 'flex-end', // Align buttons to the right
                flexWrap: 'wrap', // Allow wrapping on smaller screens
                gap: 1, // Add spacing between radio options
              }}
            >
              <FormControlLabel
                value="all"
                control={<Radio size="small" />}
                label={<Typography variant="body2">All Credits</Typography>}
              />
              <FormControlLabel
                value="completed"
                control={<Radio size="small" />}
                label={<Typography variant="body2">Completed Credits</Typography>}
              />
              <FormControlLabel
                value="incompleted"
                control={<Radio size="small" />}
                label={<Typography variant="body2">Incomplete Credits</Typography>}
              />
            </RadioGroup>
          </Box>

          {/* Stats Section */}
          <Box
            sx={{
              display: 'grid',
              gridTemplateColumns: { xs: '1fr', sm: 'repeat(auto-fit, minmax(200px, 1fr))' }, // Stack items on small screens
              gap: 2,
              marginTop: 2,
            }}
          >
            <Box sx={{ textAlign: 'center', backgroundColor: '#c4c4c4', padding: 1, borderRadius: 10 }}>
              <Typography variant="subtitle2">Total Incomplete Credits</Typography>
              <Typography variant="h6" sx={{ fontSize: '1rem', fontWeight: 600 }}>
                {rows.filter((row) => row.balance - row.repayments.reduce((acc, repayment) => acc + repayment.amount, 0) > 0).length}
              </Typography>
            </Box>
            <Box sx={{ textAlign: 'center', backgroundColor: '#f28fb0', padding: 1, borderRadius: 10 }}>
              <Typography variant="subtitle2">Total Remaining Balance</Typography>
              <Typography variant="h6" sx={{ fontSize: '1rem', fontWeight: 600 }}>
                {rows.reduce((acc, row) => acc + (row.balance - row.repayments.reduce((acc, repayment) => acc + repayment.amount, 0)), 0)}
              </Typography>
            </Box>
          </Box>
        </FormControl>


        <TableContainer sx={{ maxHeight: 440 }}>
          <Table stickyHeader aria-label="collapsible table" >
            <TableHead >
              <TableRow  >
                <TableCell />
                {columns.creditId && <TableCell >Credit ID</TableCell>}
                <TableCell >Bill ID</TableCell>
                <TableCell>Customer Name</TableCell>
                <TableCell>Contact Number</TableCell>
                <TableCell align="right">Credit Amount (Rs.)</TableCell>
                <TableCell>Due Date</TableCell>
                <TableCell align="right">Remaining Balance (Rs.)</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {filteredRows.map((row) => (
                <Row key={row.creditId} row={row} onAddRepayment={handleAddRepayment} onDeleteRepayment={handleDeleteRepayment} setMessage={setMessage} message={message} columns={columns} />
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Paper>
    </Container>
  );
};

// Add PropTypes for validation
Row.propTypes = {
  row: PropTypes.shape({
    creditId: PropTypes.number.isRequired,
    billId: PropTypes.number.isRequired,
    customerName: PropTypes.string.isRequired,
    contactNumber: PropTypes.string.isRequired,
    balance: PropTypes.number.isRequired,
    dueDate: PropTypes.string.isRequired,
    repayments: PropTypes.arrayOf(
      PropTypes.shape({
        repaymentId: PropTypes.number.isRequired,
        date: PropTypes.string.isRequired,
        amount: PropTypes.number.isRequired,
      })
    ).isRequired,
  }).isRequired,
  onAddRepayment: PropTypes.func.isRequired,
  onDeleteRepayment: PropTypes.func.isRequired,
  setMessage: PropTypes.func.isRequired,
  message: PropTypes.oneOfType([
    PropTypes.shape({
      type: PropTypes.string,
      text: PropTypes.string,
    }),
    PropTypes.oneOf([null]), // Allow null
  ]),
  columns: PropTypes.shape({
    creditId: PropTypes.number.isRequired
  }).isRequired
};

export default CreditPage;
