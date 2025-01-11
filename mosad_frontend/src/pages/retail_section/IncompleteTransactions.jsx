import React, {  useState } from "react";
import { DataGrid } from '@mui/x-data-grid';
import { Box, Typography } from "@mui/material";
import { fetchIncompleteTransactions } from "../../services/apiRetailService";
import useAuth from "../../hooks/useAuth";

const IncompleteTransactions = () => {  
    const [rows, setRows] = useState([]);
    const [billTransaction,setBillTransaction] =useState([])

    const {auth}=useAuth();

    const loadingData=async ()=>{
        const response = await fetchIncompleteTransactions(auth.username);
        setBillTransaction(response.data);

        loadingData();
        setRows([...rows,{
            date:billTransaction.date,
            description:billTransaction.description,
            creditBalance:billTransaction.creditBalance,
            dueDate:billTransaction.dueDate
        }])
        console.log(rows);
    }

    


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
               
            />
        </Box>
    );
};

export default IncompleteTransactions;
