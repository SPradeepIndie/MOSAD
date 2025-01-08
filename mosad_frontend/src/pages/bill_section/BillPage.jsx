import React, { useRef } from "react";
import {
  Box,
  TextField,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Typography,
  Button,
} from "@mui/material";
import HeaderBar from "../../component/Header";
import Footer from "../../component/Footer";
import "../../App.css";
import { useReactToPrint } from "react-to-print";
import SearchComponent from "../../component/SearchComponent"; // Import SearchComponent


function ccyFormat(num) {
  return num.toFixed(2);
}

const BillPage = () => {
  const [rows, setRows] = React.useState(
    Array(5).fill({ description: "", unitPrice: "", quantity: "", subtotal: 0 })
  );
  const [advance, setAdvance] = React.useState(0);

  const handleAddToBill = (item) => {
    setRows((prevRows) => [
      ...prevRows,
      {
        description: item.description,
        unitPrice: item.unitPrice,
        quantity: item.quantity,
        subtotal: item.subtotal,
      },
    ]);
  };
   

  const handleInputChange = (index, field, value) => {
    setRows((prevRows) => {
      const updatedRows = [...prevRows];
      updatedRows[index] = {
        ...updatedRows[index],
        [field]: value,
      };

      if (field === "unitPrice" || field === "quantity") {
        const unitPrice = parseFloat(updatedRows[index].unitPrice) || 0;
        const quantity = parseInt(updatedRows[index].quantity, 10) || 0;
        updatedRows[index].subtotal = unitPrice * quantity;
      }

      return updatedRows;
    });
  };

  const handleAdvanceChange = (e) => {
    const value = parseFloat(e.target.value) || 0;
    setAdvance(value);
  };

  const total = rows.reduce((sum, row) => sum + parseFloat(row.subtotal || 0), 0);
  const balance = total - advance;

  const printRef = useRef();
  const handlePrint = useReactToPrint({
    content: () => printRef.current,
  });

  const handleKeyPress = (index, e) => {
    if (e.key === "Enter") {
      const lastRow = rows[index];
      if (
        lastRow.description &&
        lastRow.unitPrice &&
        lastRow.quantity &&
        index === rows.length - 1 // Ensure it's the last row
      ) {
        setRows((prevRows) => [
          ...prevRows,
          { description: "", unitPrice: "", quantity: "", subtotal: 0 },
        ]);
      }
    }
  };

  return (
    <Box sx={{ p: 4 }}>
     

      {/* Search Component */}
      <Box sx={{ mb: 4 }}>
        <SearchComponent onAddToBill={handleAddToBill} />
      </Box>

      {/* Bill Content */}
      <Box
        ref={printRef}
        sx={{
          background: "#f9f9f9",
          borderRadius: "8px",
          boxShadow: "0 4px 10px rgba(0, 128, 0, 0.15)",
          padding: "16px",
        }}
      >
        {/* Business Info */}
        <Box
          sx={{
            textAlign: "center",
            mb: 2,
            background: "#B5FCB5",
            padding: "16px",
            borderRadius: "8px",
            boxShadow: "0 4px 10px rgba(0, 128, 0, 0.15)",
          }}
        >
          <Typography variant="h3" sx={{ fontWeight: "bold", color: "#003366" }}>
            Rashmi Tyre Center
          </Typography>
        </Box>

        {/* Address and Contact */}
        <Box
          sx={{
            mb: 2,
            textAlign: "center",
            background: "#f1f1f1",
            p: 4,
            borderRadius: "8px",
            boxShadow: "0 4px 15px rgba(0, 0, 0, 0.1)",
          }}
        >
          <Typography
            sx={{
              fontSize: "1.35rem",
              fontWeight: "500",
              color: "#333",
              lineHeight: 1.6,
            }}
          >
            We provide high-quality tires and tubes for motorcycles, three-wheelers, cars, vans,
            lorries, and buses. Additionally, we offer vehicle battery charging and nitrogen
            services.
          </Typography>
          <Typography sx={{ fontSize: "1.2rem", color: "#555", mt: 2 }}>
            Visit us at: <strong>205/106, Pattiyakuburawatta, Hakurukubura, Mirigama</strong>
          </Typography>
          <Typography sx={{ fontSize: "1.2rem", color: "#555", mt: 1 }}>
            Contact Us: <strong>078 3918504, 0764690290, 0332274577</strong>
          </Typography>
        </Box>

        

        

        {/* Table and Bill */}
        <TableContainer component={Paper}>
          <Table sx={{ minWidth: 650 }} aria-label="simple table">
            <TableHead>
              <TableRow>
                <TableCell align="center" sx={{ width: "10%", fontWeight: "bold", fontSize: "1.2rem" }}>
                  Quantity
                </TableCell>
                <TableCell align="center" sx={{ width: "50%", fontWeight: "bold", fontSize: "1.2rem" }}>
                  Description
                </TableCell>
                <TableCell align="center" sx={{ width: "20%", fontWeight: "bold", fontSize: "1.2rem" }}>
                  Unit Price
                </TableCell>
                <TableCell align="center" sx={{ width: "20%", fontWeight: "bold", fontSize: "1.2rem" }}>
                  Subtotal
                </TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {rows.map((row, index) => (
                <TableRow key={index}>
                  <TableCell align="center">
                    <TextField
                      variant="outlined"
                      size="small"
                      type="number"
                      value={row.quantity}
                      onChange={(e) => handleInputChange(index, "quantity", e.target.value)}
                      onKeyPress={(e) => handleKeyPress(index, e)}
                      InputProps={{ style: { fontSize: "1.2rem" } }}
                      sx={{ width: "80%" }}
                    />
                  </TableCell>
                  <TableCell align="center">
                    <TextField
                      variant="outlined"
                      size="small"
                      value={row.description}
                      onChange={(e) => handleInputChange(index, "description", e.target.value)}
                      onKeyPress={(e) => handleKeyPress(index, e)}
                      InputProps={{ style: { fontSize: "1.2rem" } }}
                      sx={{ width: "90%" }}
                    />
                  </TableCell>
                  <TableCell align="center">
                    <TextField
                      variant="outlined"
                      size="small"
                      type="number"
                      value={row.unitPrice}
                      onChange={(e) => handleInputChange(index, "unitPrice", e.target.value)}
                      onKeyPress={(e) => handleKeyPress(index, e)}
                      InputProps={{ style: { fontSize: "1.2rem" } }}
                      sx={{ width: "90%" }}
                    />
                  </TableCell>
                  <TableCell align="center">
                    <TextField
                      variant="outlined"
                      size="small"
                      value={ccyFormat(row.subtotal || 0)}
                      InputProps={{ readOnly: true, style: { fontSize: "1.2rem" } }}
                      sx={{ width: "90%" }}
                    />
                  </TableCell>
                </TableRow>
              ))}
              <TableRow>
                <TableCell colSpan={2} align="left" sx={{ fontWeight: "bold", fontSize: "1.2rem" }}>
                  Note:
                </TableCell>
                <TableCell colSpan={1} align="center" sx={{ fontWeight: "bold", fontSize: "1.2rem" }}>
                  Total
                </TableCell>
                <TableCell align="center">
                  <TextField
                    variant="outlined"
                    size="small"
                    type="number"
                    value={ccyFormat(total)}
                    InputProps={{ readOnly: true, style: { fontSize: "1.2rem" } }}
                    sx={{ width: "90%" }}
                  />
                </TableCell>
              </TableRow>
              <TableRow>
                <TableCell colSpan={2} align="left" sx={{ fontWeight: "bold", fontSize: "1.2rem" }}></TableCell>
                <TableCell colSpan={1} align="center" sx={{ fontWeight: "bold", fontSize: "1.2rem" }}>
                  Advance
                </TableCell>
                <TableCell align="center">
                  <TextField
                    variant="outlined"
                    size="small"
                    type="number"
                    value={advance}
                    onChange={handleAdvanceChange}
                    InputProps={{ style: { fontSize: "1.2rem" } }}
                    sx={{ width: "90%" }}
                  />
                </TableCell>
              </TableRow>
              <TableRow>
                <TableCell colSpan={2} align="left" sx={{ fontWeight: "bold", fontSize: "1.2rem" }}></TableCell>
                <TableCell colSpan={1} align="center" sx={{ fontWeight: "bold", fontSize: "1.2rem" }}>
                  Balance
                </TableCell>
                <TableCell align="center">
                  <TextField
                    variant="outlined"
                    size="medium"
                    type="number"
                    value={ccyFormat(balance)}
                    InputProps={{
                      readOnly: true,
                      style: { fontSize: "1.2rem" },
                    }}
                    sx={{ width: "90%" }}
                  />
                </TableCell>
              </TableRow>
            </TableBody>
          </Table>
        </TableContainer>

        {/* Footer Note */}
        <Box
          sx={{
            mb: 2,
            textAlign: "center",
            background: "#f1f1f1",
            p: 4,
            borderRadius: "8px",
            boxShadow: "0 4px 15px rgba(0, 0, 0, 0.1)",
          }}
        >
          <Typography sx={{ fontSize: "1.2rem", color: "#555", mt: 2 }}>
            <strong>We will not be responsible for any tires you give us for DAG and re-build after one month.</strong>
          </Typography>
          <Typography sx={{ fontSize: "1.4rem", color: "#003366", mt: 1 }}>
            Thank you, Come Again!
          </Typography>
        </Box>
      </Box>

      {/* Print Button */}
      <Box sx={{ textAlign: "center", mt: 3 }}>
        <Button variant="contained" color="primary" onClick={handlePrint}>
          Print Bill
        </Button>
      </Box>

      {/* Footer */}
      
    </Box>
  );
};

export default BillPage;
