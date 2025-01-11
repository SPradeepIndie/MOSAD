import React, { useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import { Box, Typography } from "@mui/material";

const PurchaseHistory = () => {
    const [rows, setRows] = useState([]);

    // Define columns for the DataGrid
    const columns = [
        { field: "date", headerName: "Date", width: 150 },
        { field: "productName", headerName: "Product Name", width: 300 },
        { field: "quantity", headerName: "Quantity", width: 150, type: "number" },
        { field: "price", headerName: "Price", width: 150, type: "number" }
    ];


    return (
        <Box sx={{ height: 600, width: "100%", padding: 2 }}>
            <Typography variant="h4" gutterBottom>
                Retail Purchase History
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

export default PurchaseHistory;
