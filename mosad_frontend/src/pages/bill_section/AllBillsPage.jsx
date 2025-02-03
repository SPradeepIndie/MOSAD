import React, { useState } from "react";
import {
  Container,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
  Button,
  Drawer,
  Box,
  TextField,
} from "@mui/material";
import { LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import dayjs from "dayjs";

const BillList = () => {
  const [openDrawer, setOpenDrawer] = useState(false);
  const [selectedBill, setSelectedBill] = useState(null);
  const [filterPhone, setFilterPhone] = useState("");
  const [filterDate, setFilterDate] = useState(null);

  const bills = [
    { id: 1, billNumber: "B001", customer: "John Doe", date: "2025-02-01", amount: 100, phone: "123-456-7890" },
    { id: 2, billNumber: "B002", customer: "Jane Smith", date: "2025-02-02", amount: 150, phone: "987-654-3210" },
  ];

  const handleOpenDrawer = (bill) => {
    setSelectedBill(bill);
    setOpenDrawer(true);
  };

  const handleCloseDrawer = () => {
    setOpenDrawer(false);
    setSelectedBill(null);
  };

  // Filtering logic
  const filteredBills = bills.filter((bill) => {
    const matchesPhone = filterPhone ? bill.phone.includes(filterPhone) : true;
    const matchesDate = filterDate ? bill.date === dayjs(filterDate).format("YYYY-MM-DD") : true;
    return matchesPhone && matchesDate;
  });

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <Container>
        <Typography variant="h4" gutterBottom>
          All Issued Bills
        </Typography>

        {/* Filter Inputs */}
        <Box display="flex" gap={2} mb={2}>
          <TextField
            label="Filter by Telephone"
            variant="outlined"
            value={filterPhone}
            onChange={(e) => setFilterPhone(e.target.value)}
          />
          <DatePicker
            label="Filter by Date"
            value={filterDate}
            onChange={setFilterDate}
            slotProps={{ textField: { variant: "outlined" } }} // FIXED: Using `textField` instead of `renderInput`
          />
          <Button variant="contained" onClick={() => {
            setFilterPhone("");
            setFilterDate(null);
          }}>
            Reset Filters
          </Button>
        </Box>

        {/* Bills Table */}
        <TableContainer component={Paper}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell><strong>Bill Number</strong></TableCell>
                <TableCell><strong>Customer</strong></TableCell>
                <TableCell><strong>Date</strong></TableCell>
                <TableCell><strong>Amount (USD)</strong></TableCell>
                <TableCell><strong>Telephone</strong></TableCell>
                <TableCell><strong>Action</strong></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {filteredBills.map((bill) => (
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

        {/* Side Drawer to Show Bill Details */}
        <Drawer
          anchor="right"
          open={openDrawer}
          onClose={handleCloseDrawer}
          sx={{
            "& .MuiDrawer-paper": {
              width: 300,
              padding: 2,
            },
          }}
        >
          <Box>
            {selectedBill ? (
              <>
                <Typography variant="h6" gutterBottom>
                  Bill Details
                </Typography>
                <Typography><strong>Bill Number:</strong> {selectedBill.billNumber}</Typography>
                <Typography><strong>Customer:</strong> {selectedBill.customer}</Typography>
                <Typography><strong>Date:</strong> {selectedBill.date}</Typography>
                <Typography><strong>Amount:</strong> ${selectedBill.amount}</Typography>
                <Typography><strong>Telephone:</strong> {selectedBill.phone}</Typography>
              </>
            ) : (
              <Typography>No bill selected</Typography>
            )}
          </Box>
        </Drawer>
      </Container>
    </LocalizationProvider>
  );
};

export default BillList;
