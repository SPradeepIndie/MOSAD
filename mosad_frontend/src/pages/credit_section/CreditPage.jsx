import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import Button from '@mui/material/Button';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';

function Row({ row, onAddRepayment }) {
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
      alert('Please fill out all fields.');
    }
  };

  return (
    <>
      <TableRow sx={{ '& > *': { borderBottom: 'unset' } }}>
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
        <TableCell align="right">
          {row.balance - row.repayments.reduce((acc, repayment) => acc + repayment.amount, 0)}
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
              <Button
                variant="contained"
                size="small"
                onClick={handleDialogOpen}
                sx={{ marginTop: 2, backgroundColor: '#4CAF50', color: 'white' }}
              >
                Add New Repayment
              </Button>
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
      <Dialog open={openDialog} onClose={handleDialogClose}>
        <DialogTitle>Add New Repayment</DialogTitle>
        <DialogContent>
          <TextField
            margin="dense"
            label="Date"
            type="date"
            fullWidth
            variant="outlined"
            value={newRepayment.date}
            onChange={(e) => setNewRepayment({ ...newRepayment, date: e.target.value })}
          />
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
    </>
  );
}

const CreditPage = () => {
  const [rows, setRows] = useState([]);
  const [searchText, setSearchText] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      const token = localStorage.getItem('token');

      if (!token) {
        console.error('No token found in localStorage');
        setLoading(false);
        return;
      }

      try {
        const response = await axios.get('http://localhost:8080/api/v1/credit/all-credit-details', {
          headers: { Authorization: `Bearer ${token}` },
        });
        setRows(response.data);
        console.log(response.data);
        setLoading(false);
      } catch (error) {
        console.error('Error fetching data:', error);
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const handleAddRepayment = async (creditId, repayment) => {
    const token = localStorage.getItem('token');
  
    try {
      const response = await axios.post(
        'http://localhost:8080/api/v1/repayments',
        {
          creditId, // Pass creditId
          ...repayment, // Pass date and amount
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
  
      console.log('Repayment added successfully:', response.data);
  
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
    }
  };
  
  
  

  const filteredRows = rows.filter(
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
        <TableContainer sx={{ maxHeight: 400 }}>
          <Table stickyHeader aria-label="collapsible table">
            <TableHead>
              <TableRow>
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
                <Row key={row.creditId} row={row} onAddRepayment={handleAddRepayment} />
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      </Paper>
    </Container>
  );
};

export default CreditPage;
