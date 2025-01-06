import React, { useEffect, useState } from "react";
import axios from "axios";
import { DataGrid } from "@mui/x-data-grid";
import { Box, Typography } from "@mui/material";

const PaymentHistory = () => {
    const [rows, setRows] = useState([]);

    // Define columns for the DataGrid
    const columns = [
        { field: "date", headerName: "Date", width: 150 },
        { field: "description", headerName: "Description", width: 300 },
        { field: "paymentMethod", headerName: "Payment Method", width: 200 },
        { field: "amount", headerName: "Amount", width: 150, type: "number" },
    ];

    // Fetch data from the backend API
    useEffect(() => {
        axios
            .get("http://localhost:8080/api/payments")
            .then((response) => {
                setRows(response.data);
            })
            .catch((error) => {
                console.error("Error fetching payment history:", error);
            });
    }, []);

    return (
        <Box sx={{ height: 600, width: "100%", padding: 2 }}>
            <Typography variant="h4" gutterBottom>
                Retail Payment History
            </Typography>
            <DataGrid
                rows={rows}
                columns={columns}
                getRowId={(row) => row.id} // Map each row to its unique "id" field
                pageSize={10}
                rowsPerPageOptions={[10, 20, 50]}
                disableSelectionOnClick
            />
        </Box>
    );
};

export default PaymentHistory;
