import React from "react";
import {
  Box,
  Typography,
  TextField,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
} from "@mui/material";

const TAX_RATE = 0.07; // Example tax rate

function ccyFormat(num) {
  return `${num.toFixed(2)}`;
}

const Bill = () => {
  const [rows, setRows] = React.useState(
    Array(5).fill({ description: "", unitPrice: "", quantity: "", total: 0 })
  );

  const handleInputChange = (index, field, value) => {
    setRows((prevRows) => {
      const updatedRows = [...prevRows];
      updatedRows[index] = {
        ...updatedRows[index],
        [field]: value,
      };

      // Calculate total when unitPrice or quantity is updated
      if (field === "unitPrice" || field === "quantity") {
        const unitPrice = parseFloat(updatedRows[index].unitPrice) || 0;
        const quantity = parseInt(updatedRows[index].quantity, 10) || 0;
        updatedRows[index].total = unitPrice * quantity;
      }

      // Add a new row if the last row is filled
      const lastRow = updatedRows[updatedRows.length - 1];
      if (
        lastRow.description &&
        lastRow.unitPrice &&
        lastRow.quantity &&
        updatedRows.length < 50 // Optional limit to prevent too many rows
      ) {
        updatedRows.push({ description: "", unitPrice: "", quantity: "", total: 0 });
      }

      return updatedRows;
    });
  };

  const subtotal = rows.reduce((sum, row) => sum + parseFloat(row.total || 0), 0);
  const tax = subtotal * TAX_RATE;
  const total = subtotal + tax;

  return (
    <Box sx={{ p: 4 }}>
      {/* Header Section */}
      <Typography variant="h4" align="center" gutterBottom>
        Rashmi Tyre Center
      </Typography>
      <Typography variant="body1" align="center">
        Address Line 1, Address Line 2, Contact Details
      </Typography>
      <Typography variant="body2" align="center" gutterBottom>
        Invoice No: 2235 | Date: {new Date().toLocaleDateString()}
      </Typography>

      {/* Combined Table Section */}
      <TableContainer component={Paper} sx={{ mt: 4 }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell align="center" sx={{ width: "10%" }}>
                Quantity
              </TableCell>
              <TableCell align="center" sx={{ width: "50%" }}>
                Description
              </TableCell>
              <TableCell align="center" sx={{ width: "20%" }}>
                Unit Price
              </TableCell>
              <TableCell align="center" sx={{ width: "20%" }}>
                Total
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
                  />
                </TableCell>
                <TableCell align="center">
                  <TextField
                    variant="outlined"
                    size="small"
                    value={row.description}
                    onChange={(e) => handleInputChange(index, "description", e.target.value)}
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
                  />
                </TableCell>
                <TableCell align="center">
                  <TextField
                    variant="outlined"
                    size="small"
                    value={ccyFormat(row.total)}
                    InputProps={{ readOnly: true }}
                  />
                </TableCell>
              </TableRow>
            ))}
            {/* Subtotal, Tax, and Total Rows */}
            <TableRow>
              <TableCell align="left" colSpan={2} sx={{ borderLeft: "none" }}>
                <Typography variant="body2" sx={{ fontStyle: "italic" }}>
                  Thank you for your payment
                </Typography>
              </TableCell>
              <TableCell align="right" sx={{ borderLeft: "none" }}>
                Subtotal
              </TableCell>
              <TableCell align="center">{ccyFormat(subtotal)}</TableCell>
            </TableRow>
            <TableRow>
              <TableCell colSpan={3} align="right">
                Tax ({`${(TAX_RATE * 100).toFixed(0)}%`})
              </TableCell>
              <TableCell align="center">{ccyFormat(tax)}</TableCell>
            </TableRow>
            <TableRow>
              <TableCell colSpan={3} align="right" sx={{ fontWeight: "bold" }}>
                Total
              </TableCell>
              <TableCell align="center" sx={{ fontWeight: "bold" }}>
                {ccyFormat(total)}
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </TableContainer>

      {/* Footer Text */}
      <Box sx={{ mt: 2, display: "flex", justifyContent: "space-between" }}>
        <Typography variant="body2" sx={{ fontStyle: "italic" }}>
          Terms & Conditions: Payment due within 30 days.
        </Typography>
        <Typography variant="body2" align="center">
          Thank you for your business!
        </Typography>
      </Box>
    </Box>
  );
};

export default Bill;
