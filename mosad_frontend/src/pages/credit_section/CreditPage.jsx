import React, { useState, useEffect } from 'react';
import {
  Box, Container, Collapse, IconButton, Button, Table, TableBody, TableCell,
  TableContainer, TableHead, TableRow, Typography, Paper, TextField, Checkbox,
  FormGroup, FormControlLabel, Dialog, DialogActions, DialogContent, DialogTitle,RadioGroup, Radio, FormControl
} from '@mui/material';
import { KeyboardArrowDown as KeyboardArrowDownIcon, KeyboardArrowUp as KeyboardArrowUpIcon } from '@mui/icons-material';
import { addRepayment, fetchAllCreditDetails } from '../../services/apiCreditService';
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider, DatePicker } from '@mui/x-date-pickers';
import GeneralMessage from '../../component/GeneralMessage';

function Row({ row, onAddRepayment, setMessage,message,state,setState}) {
  const [open, setOpen] = useState(false);
  const [openDialog, setOpenDialog] = useState(false);
  const [newRepayment, setNewRepayment] = useState({ date: '', amount: '' });
  

  const handleDialogOpen = () => {
    setOpenDialog(true);
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

  const remainingBalance = row.balance - row.repayments.reduce((acc, repayment) => acc + repayment.amount, 0);

  return (
    <>
      
      
      {state.all && remainingBalance >= 0 &&
      <TableRow sx={{ '& > *': { borderBottom: 'unset' }, backgroundColor: remainingBalance == 0 ? '#C8E6C9' : 'white' }}>
        <TableCell>
          <IconButton
            aria-label="expand row"
            size="small"
            onClick={() => setOpen(!open)}
          >
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        <TableCell>{row.creditId}</TableCell>
        <TableCell component="th" scope="row">
          {row.customerName}
        </TableCell>
        <TableCell>{row.contactNumber}</TableCell>
        <TableCell align="right">{row.balance}</TableCell>
        <TableCell>{new Date(row.dueDate).toLocaleDateString()}</TableCell>
        <TableCell align="right" sx={{ color: remainingBalance == 0 ? 'green' : 'black' , fontWeight: 'bold', fontSize: 20 }}>
          {remainingBalance}
        </TableCell>
      </TableRow>}
      {state.all &&
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
                    <TableCell align="right">Amount ($)</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {row.repayments.map((repayment) => (
                    <TableRow key={repayment.repaymentId}>
                      <TableCell>{repayment.repaymentId}</TableCell>
                      <TableCell>{new Date(repayment.date).toLocaleDateString()}</TableCell>
                      <TableCell align="right">{repayment.amount}</TableCell>
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
    }

      {state.completed && remainingBalance == 0 &&
        <TableRow sx={{ '& > *': { borderBottom: 'unset' }, backgroundColor: remainingBalance == 0 ? '#C8E6C9' : 'white' }}>
          <TableCell>
            <IconButton
              aria-label="expand row"
              size="small"
              onClick={() => setOpen(!open)}
            >
              {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
            </IconButton>
          </TableCell>
          <TableCell>{row.creditId}</TableCell>
          <TableCell component="th" scope="row">
            {row.customerName}
          </TableCell>
          <TableCell>{row.contactNumber}</TableCell>
          <TableCell align="right">{row.balance}</TableCell>
          <TableCell>{new Date(row.dueDate).toLocaleDateString()}</TableCell>
          <TableCell align="right" sx={{ color: remainingBalance == 0 ? 'green' : 'black', fontWeight: 'bold', fontSize: 20 }}>
            {remainingBalance}
          </TableCell>
        </TableRow>}
      {state.completed && remainingBalance == 0 &&
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
                      <TableCell align="right">Amount ($)</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {row.repayments.map((repayment) => (
                      <TableRow key={repayment.repaymentId}>
                        <TableCell>{repayment.repaymentId}</TableCell>
                        <TableCell>{new Date(repayment.date).toLocaleDateString()}</TableCell>
                        <TableCell align="right">{repayment.amount}</TableCell>
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
      }

      {state.incompleted && remainingBalance > 0 &&
        <TableRow sx={{ '& > *': { borderBottom: 'unset' }, backgroundColor: remainingBalance == 0 ? '#C8E6C9' : 'white' }}>
          <TableCell>
            <IconButton
              aria-label="expand row"
              size="small"
              onClick={() => setOpen(!open)}
            >
              {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
            </IconButton>
          </TableCell>
          <TableCell>{row.creditId}</TableCell>
          <TableCell component="th" scope="row">
            {row.customerName}
          </TableCell>
          <TableCell>{row.contactNumber}</TableCell>
          <TableCell align="right">{row.balance}</TableCell>
          <TableCell>{new Date(row.dueDate).toLocaleDateString()}</TableCell>
          <TableCell align="right" sx={{ color: remainingBalance == 0 ? 'green' : 'black', fontWeight: 'bold', fontSize: 20 }}>
            {remainingBalance}
          </TableCell>
        </TableRow>}
      {state.incompleted && remainingBalance > 0 &&
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
                      <TableCell align="right">Amount ($)</TableCell>
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {row.repayments.map((repayment) => (
                      <TableRow key={repayment.repaymentId}>
                        <TableCell>{repayment.repaymentId}</TableCell>
                        <TableCell>{new Date(repayment.date).toLocaleDateString()}</TableCell>
                        <TableCell align="right">{repayment.amount}</TableCell>
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
      }

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
                value={null}
                onChange={(newValue) =>
                  setNewRepayment({ ...newRepayment, date: newValue ? newValue.toISOString() : null })
                }
              />
            </DemoContainer>
          </LocalizationProvider>
          <TextField
            margin="dense"
            label="Amount"
            type="number"
            fullWidth
            variant="outlined"
            value={newRepayment.amount}
            onChange={(e) => setNewRepayment({ ...newRepayment, amount: e.target.value })}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleDialogClose}>Cancel</Button>
          <Button
            onClick={handleAddRepayment}
            variant="contained"
            sx={{ backgroundColor: '#4CAF50', color: 'white' }}
          >
            Add
          </Button>
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
  const [state, setState] = useState({all: false ,completed: false,incompleted: true,});

  const handleRadioChange = (event) => {
    const { value } = event.target;

    // Update state based on selected radio button
    setState({
      all: value === 'all',
      completed: value === 'completed',
      incompleted: value === 'incompleted',
    });
  };
  

  useEffect(() => {
    const fetchData = async () => {
    
      try {
        const response = await fetchAllCreditDetails();
        setRows(response.data);
        //console.log(response.data);
        setLoading(false);
      } catch (error) {
        console.error('Error fetching data:', error);
        setLoading(false);
      }
    };

    fetchData();
  }, []);

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
  



  const  filteredRows = rows.filter(

      (row) =>
        
        row.customerName.toLowerCase().includes(searchText.toLowerCase()) ||
        row.contactNumber.toLowerCase().includes(searchText.toLowerCase()) ||
        row.creditId.toString().includes(searchText) 
        
    );
 


  if (loading) {
    return (
      <Container maxWidth="lg" sx={{ marginTop: 4 }}>
        <Typography variant="h4" align="center">
          Loading...
        </Typography>
      </Container>
    );
  }

  return (
    <Container maxWidth="lg" sx={{ marginTop: 4 }}>
     
      <Paper elevation={3} sx={{ padding: 3 }}>

        <TextField
          fullWidth
          label="Search By Name / Contact Number / Credit ID"
          variant="outlined"
          value={searchText}
          onChange={(e) => setSearchText(e.target.value)}
          sx={{ marginBottom: 2 }}
        />
        <FormControl>
          <RadioGroup
            row
            name="creditOptions"
            value={state.all ? 'all' : state.completed ? 'completed' : 'incompleted'}
            onChange={handleRadioChange}
          >
            <FormControlLabel
              value="all"
              control={<Radio />}
              label="All Credits"
            />
            <FormControlLabel
              value="completed"
              control={<Radio />}
              label="Completed Credits"
            />
            <FormControlLabel
              value="incompleted"
              control={<Radio />}
              label="Incomplete Credits"
            />
          </RadioGroup>
        </FormControl>
        <TableContainer sx={{ maxHeight: 400 }}>
          <Table stickyHeader aria-label="collapsible table">
            <TableHead>
              <TableRow  >
                <TableCell />
                <TableCell>Credit ID</TableCell>
                <TableCell>Customer Name</TableCell>
                <TableCell>Contact Number</TableCell>
                <TableCell align="right">Balance ($)</TableCell>
                <TableCell>Due Date</TableCell>
                <TableCell align="right">Remaining Balance ($)</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {filteredRows.map((row) => (
                <Row key={row.creditId} row={row} onAddRepayment={handleAddRepayment} setMessage={setMessage} message={message} state={state} setState={setState} />
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Paper>
    </Container>
  );
};

export default CreditPage;
