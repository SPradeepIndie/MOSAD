import React, { useState } from 'react';
import { Container, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Typography, Button, Drawer, Box } from '@mui/material';

const BillList = () => {
  const [openDrawer, setOpenDrawer] = useState(false);
  const [selectedBill, setSelectedBill] = useState(null);

  const bills = [
    { id: 1, billNumber: 'B001', customer: 'John Doe', date: '2025-02-01', amount: 100, phone: '123-456-7890' },
    { id: 2, billNumber: 'B002', customer: 'Jane Smith', date: '2025-02-02', amount: 150, phone: '987-654-3210' },
    // Add more bill data as necessary
  ];

  const handleOpenDrawer = (bill) => {
    setSelectedBill(bill);
    setOpenDrawer(true);
  };

  const handleCloseDrawer = () => {
    setOpenDrawer(false);
    setSelectedBill(null);
  };

  return (
    <Container>
      <Typography variant="h4" gutterBottom>
        All Issued Bills
      </Typography>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell><strong>Bill Number</strong></TableCell>
              <TableCell><strong>Customer</strong></TableCell>
              <TableCell><strong>Date</strong></TableCell>
              <TableCell><strong>Amount (USD)</strong></TableCell>
              <TableCell><strong>Telephone</strong></TableCell> {/* Added Telephone column */}
              <TableCell><strong>Action</strong></TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
  {bills.map((bill) => (
    <TableRow key={bill.id}>
      <TableCell>{bill.billNumber}</TableCell>
      <TableCell>{bill.customer}</TableCell>
      <TableCell>{bill.date}</TableCell>
      <TableCell>{bill.amount}</TableCell>
      <TableCell>{bill.phone}</TableCell>
      <TableCell>
        <Button variant="outlined" color="primary" onClick={() => handleOpenDrawer(bill)}>
          View
        </Button>
      </TableCell>
    </TableRow>
  ))}
</TableBody>


        </Table>
      </TableContainer>

      {/* Side Drawer to show bill details */}
      <Drawer
        anchor="right"
        open={openDrawer}
        onClose={handleCloseDrawer}
        sx={{
          width: 300,
          flexShrink: 0,
          '& .MuiDrawer-paper': {
            width: 300,
            padding: 2,
          },
        }}
      >
        <Box>
          {selectedBill ? (
            <>
              <Typography variant="h6">Bill Details</Typography>
              <Typography><strong>Bill Number:</strong> {selectedBill.billNumber}</Typography>
              <Typography><strong>Customer:</strong> {selectedBill.customer}</Typography>
              <Typography><strong>Date:</strong> {selectedBill.date}</Typography>
              <Typography><strong>Amount:</strong> {selectedBill.amount}</Typography>
              <Typography><strong>Telephone:</strong> {selectedBill.phone}</Typography>
            </>
          ) : (
            <Typography>No bill selected</Typography>
          )}
        </Box>
      </Drawer>
    </Container>
  );
};

export default BillList;
