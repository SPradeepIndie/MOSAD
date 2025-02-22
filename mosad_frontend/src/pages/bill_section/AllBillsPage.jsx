import React, { useState,useEffect } from "react";
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
import {useFetchAllBills} from '../../hooks/servicesHook/useBillService'


const BillList = () => {
  const fetchAllBills=useFetchAllBills();
  const [openDrawer, setOpenDrawer] = useState(false);
  const [selectedBill, setSelectedBill] = useState(null);
  const [filterPhone, setFilterPhone] = useState("");
  const [filterDate, setFilterDate] = useState(null);
  const [bills,setBills]=useState([]);

  useEffect(()=>{
    fetchAllBills().then((response)=>{
      setBills(response.data);
    })
  },[]);

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
    const matchesPhone = filterPhone
      ? bill.addCustomerDTO?.customerContactDTO?.contactNumber?.includes(filterPhone)
      : true;
  
    const matchesDate = filterDate
      ? bill.billDTO?.date === dayjs(filterDate).format("YYYY-MM-DD")
      : true;
  
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
                <TableCell><strong>Customer Name</strong></TableCell>
                <TableCell><strong>Date</strong></TableCell>
                <TableCell><strong>Total Amount </strong></TableCell>
                <TableCell><strong>Telephone</strong></TableCell>
                <TableCell><strong>Action</strong></TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
  {filteredBills.map((bill) => (
    <TableRow key={bill.id}>
      <TableCell>{bill.billDTO?.billId || "N/A"}</TableCell>
      <TableCell>{bill.addCustomerDTO?.customerDTO?.customerName || "N/A"}</TableCell>
      <TableCell>{bill.billDTO?.date || "N/A"}</TableCell>
      <TableCell>{bill.billDTO?.totalAmount || "N/A"}</TableCell>
      <TableCell>{bill.addCustomerDTO?.customerContactDTO?.contactNumber || "N/A"}</TableCell>
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
  {selectedBill ? (
    <>
    
    <Typography 
  variant="h6" 
  gutterBottom 
  sx={{ backgroundColor: "#81c784", padding: "8px", borderRadius: "4px" }}
>
  Bill Details
</Typography>
      <Box sx={{ mt: 1, p: 1, border: "1px solid #ddd", borderRadius: "8px" }}>
        <Typography>
          <strong>Bill Number:</strong> {selectedBill.billDTO.billId}
        </Typography>
        <Typography>
          <strong>Advanced:</strong> {selectedBill.billDTO.advance}
        </Typography>
        <Typography>
          <strong>Balance:</strong> {selectedBill.billDTO.balance}
        </Typography>
      </Box>
      <br></br>

      <Box>
      <Typography variant="h6" gutterBottom sx={{ backgroundColor: "#81c784", padding: "8px", borderRadius: "4px" }}>
        Item Details
      </Typography>

      {Array.isArray(selectedBill.billItemDTO) ? (
        selectedBill.billItemDTO.map((item) => (
          <Box
            key={item.billItemId}
            sx={{ mt: 1, p: 1, border: "1px solid #ddd", borderRadius: "8px" }}
          >
            <Typography>
              <strong>Description:</strong> {item.description}
            </Typography>
            <Typography>
              <strong>Quantity:</strong> {item.quantity}
            </Typography>
            <Typography>
              <strong>Unit Price:</strong> {item.unitPrice}
            </Typography>
          </Box>
        ))
      ) : (
        <Typography>No item details available</Typography>
      )}
      </Box>
    </>
  ) : (
    <Typography>No bill selected</Typography>
  )}
</Drawer>

      </Container>
    </LocalizationProvider>
  );
};

export default BillList;
