import React, {useState } from "react";
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
  IconButton,
  Grid2,
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete"; // Import DeleteIcon
import SearchComponent from "../../component/SearchComponent"; // Import SearchComponent
import { useNavigate } from 'react-router-dom';

function ccyFormat(num) {
  return num.toFixed(2);
}

const BillPage = () => {
  const navigate = useNavigate();
  const [rows, setRows] = React.useState([]); // Start with an empty array
  const [advance, setAdvance] = React.useState(0);
  const [quantity, setQuantity] = useState(1);
  const [customerName, setCustomerName] = useState("");
  const [telephone, setTelephone] = useState("");

  const handleAddToBill = (item) => {
    setRows((prevRows) => [
      ...prevRows,
      {
        brand: item.brand || "N/A",
        tyreSize: item.tyreSize || "N/A",
        description: item.description || "",
        unitPrice: parseFloat(item.unitPrice) || 0,
        quantity: parseInt(item.quantity, 10) || 1,
        subtotal: (parseFloat(item.unitPrice) || 0) * (parseInt(item.quantity, 10) || 1),
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
        const quantity = parseInt(updatedRows[index].quantity, 10) || 1;
        updatedRows[index].subtotal = unitPrice * quantity;
        if (field === "quantity") {
          setQuantity(quantity);
        }
      }
      return updatedRows;
    });
  };

  const handleAdvanceChange = (e) => {
    const value = e.target.value;
    if (value === "") {
      setAdvance(""); // Allow the input to be empty temporarily
    } else {
      setAdvance(parseFloat(value) || 0); // Otherwise, parse the value
    }
  };
  
  const total = rows.reduce((sum, row) => sum + parseFloat(row.subtotal || 0), 0);
  const balance = total - advance;
  const handleDeleteRow = (index) => {
    setRows((prevRows) => prevRows.filter((_, i) => i !== index));
  };

  const handlePrint = () => {
  const details = rows
    .map((row, index) => {
      return `Item ${index + 1}:
        - Brand: ${row.brand || "N/A"}
        - Size: ${row.tyreSize || "N/A"}
        - Quantity: ${row.quantity || "N/A"}
        - Unit Price: ${ccyFormat(row.unitPrice || 0)}
        - Subtotal: ${ccyFormat(row.subtotal || 0)};`;
    })
    .join("\n\n");

  const alertMessage = `
    Rashmi Tyre Center - Bill Details
    Date : ${new Date().toLocaleDateString()}
    Customer Name: ${customerName || "N/A"}
    Telephone: ${telephone || "N/A"}
    Total: ${ccyFormat(total)}
    Advance: ${ccyFormat(advance)}
    Balance: ${ccyFormat(balance)}
    Items:
    ${details}
    Thank you for your business!
  `;
  alert(alertMessage);
};

  return (
    <Box sx={{ p: 4 }}>
      {/* Search Component */}
      <Box sx={{ mb: 4 }}>
        <SearchComponent onAddToBill={handleAddToBill} quantity={quantity} setQuantity={setQuantity}/>
      </Box>

      {/* Bill Content */}
      <Box
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
          <Typography sx={{ fontSize: "1.35rem", fontWeight: "500", color: "#333", lineHeight: 1.6 }}>
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

        {/* Customer Info */}
        <Grid2 container spacing={2} sx={{ mb: 2 }}>
  <Grid2 item xs={12} sm={4}>
    <Typography
      sx={{
        fontSize: "1.2rem",
        fontWeight: "500",
        color: "#333",
        textAlign: "left",
      }}
    >
      Customer Name:
    </Typography>
    <TextField
      variant="outlined"
      size="small"
      fullWidth
      sx={{ fontSize: "1.2rem" }}
      value={customerName} // Bind to state
      onChange={(e) => setCustomerName(e.target.value)} // Update state on input
    />
  </Grid2>
  <Grid2 item xs={12} sm={4}>
    <Typography
      sx={{
        fontSize: "1.2rem",
        fontWeight: "500",
        color: "#333",
        textAlign: "left",
      }}
    >
      Telephone Number:
    </Typography>
    <TextField
      variant="outlined"
      size="small"
      fullWidth
      sx={{ fontSize: "1.2rem" }}
      value={telephone} // Bind to state
      onChange={(e) => setTelephone(e.target.value)} // Update state on input
    />
  </Grid2>
  <Grid2 item xs={12} sm={4}>
    <Typography
      sx={{
        fontSize: "1.2rem",
        fontWeight: "500",
        color: "#333",
        textAlign: "center",
      }}
    >
      Date: {new Date().toLocaleDateString()}
    </Typography>
  </Grid2>
</Grid2>


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
                <TableCell align="center" sx={{ width: "10%" }}></TableCell> {/* Column for delete button */}
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
                      slotProps={{
                        input: {
                          style: { fontSize: "1.2rem" },
                        },
                      }}
                      sx={{ width: "80%" }}
                    />
                  </TableCell>
                  <TableCell align="center">
                    <TextField
                      variant="outlined"
                      size="small"
                      value={row.description}
                      onChange={(e) => handleInputChange(index, "description", e.target.value)}
                      slotProps={{
                        input: {
                          style: { fontSize: "1.2rem" },
                        },
                      }}
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
                      slotProps={{
                        input: {
                          style: { fontSize: "1.2rem" },
                        },
                      }}
                      sx={{ width: "80%" }}
                    />
                  </TableCell>
                  <TableCell align="center">
                    <TextField
                      variant="outlined"
                      size="small"
                      value={ccyFormat(row.subtotal || 0)}
                      slotProps={{
                        input: {
                          readOnly: true,
                          style: { fontSize: "1.2rem" },
                        },
                      }}
                      sx={{ width: "90%" }}
                    />
                  </TableCell>
                  <TableCell align="center">
                    <IconButton color="error" onClick={() => handleDeleteRow(index)}>
                      <DeleteIcon />
                    </IconButton>
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
                    slotProps={{
                      input: {
                        readOnly: true,
                        style: { fontSize: "1.2rem" },
                      },
                    }}
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
                    slotProps={{
                      input: {
                        style: { fontSize: "1.2rem" },
                      },
                    }}
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
                    slotProps={{
                      input: {
                        readOnly: true,
                        style: { fontSize: "1.2rem" },
                      },
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
          
      <Button variant="contained" onClick={() => navigate("/AllBillsPage")} sx={{ backgroundColor: "yellow", color: "black", ml: 2 }}>
          All Bills
        </Button>

      </Box>
      
    </Box>
  );
};

export default BillPage;
