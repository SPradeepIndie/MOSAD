import React, {  useState ,useEffect } from "react";
import { DataGrid } from '@mui/x-data-grid';
import { Box, Typography } from "@mui/material";
import useAuth from "../../hooks/useAuth";
import { useFetchIncompleteTransactions } from "../../hooks/servicesHook/useRetailService";

const IncompleteTransactions = () => {
    const fetchIncompleteTransactions=useFetchIncompleteTransactions();  
    const [rows, setRows] = useState([]);
    const {auth}=useAuth();
    const loadingData = async () => {
        try {
            const response = await fetchIncompleteTransactions(auth.username); // Fetching data using `auth`
    
            // Update rows with id for DataGrid
            const updatedRows = response.data.map((transaction, index) => ({
                id: index, // Unique id for each row
                date: transaction.date,
                description: transaction.description,
                balance: transaction.balance,
                dueDate: transaction.dueDate
            }));
            setRows(updatedRows); // Set rows with the new data
        } catch (error) {
            console.error("Error fetching incomplete transactions:", error);
        }
    };
    useEffect(() => {
        loadingData();
    }, []); 

    const columns = [
        { field: "date", headerName: "Date", flex: 1 }, 
        { field: "description", headerName: "Description", flex: 2 },
        { field: "balance", headerName: "Credit Balance", flex: 1, type: "number" },
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
               
            />
        </Box>
    );
};
export default IncompleteTransactions;
