import React, { useState } from "react";
import { DataGrid } from '@mui/x-data-grid';
import { Box, Typography } from "@mui/material";

const IncompleteTransactions = () => {
    const [rows, setRows] = useState([]);


    const columns = [
        { field: "date", headerName: "Date", flex: 1 }, 
        { field: "description", headerName: "Description", flex: 2 },
        { field: "creditBalance", headerName: "Credit Balance", flex: 1, type: "number" },
        { field: "dueDate", headerName: "Due Date", flex: 1 }
    ];

    return (
        <Box sx={{ height: "calc(100vh - 100px)", width: "100%", padding: 2 }}>
            <Typography variant="h4" gutterBottom>
                Retail Credit History
            </Typography>
            <DataGrid
                rows={rows}
                columns={columns}
                getRowId={(row) => row.id} // Map each row to its unique "id" field
                pageSize={10}
                rowsPerPageOptions={[10, 20, 50]}
                disableSelectionOnClick
                autoHeight={false} // Ensure it adjusts height within parent container
            />
        </Box>
    );
};

export default IncompleteTransactions;
