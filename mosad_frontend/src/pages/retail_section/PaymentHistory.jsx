import React, {  useState ,useEffect } from "react";
import { DataGrid } from "@mui/x-data-grid";
import { Box, Typography } from "@mui/material";
import {useFetchPaymentHistory} from '../../hooks/servicesHook/useRetailService'
import useAuth from "../../hooks/useAuth";

const PaymentHistory = () => {
    const fetchPaymentHistory=useFetchPaymentHistory();
    const [rows, setRows] = useState([]);
    const {auth}=useAuth();

            const loadingData = async () => {
                try {
                    const response = await fetchPaymentHistory(auth.username); // Fetching data using `auth`
                    // Update rows with id for DataGrid
                    const updatedRows = response.data.map((billPayment, index) => ({
                        id: index, // Unique id for each row
                        date: billPayment.date,
                        description: billPayment.description,
                        paymentStatus: billPayment.paymentStatus,
                        amount:billPayment.amount
                    }));
                    setRows(updatedRows); // Set rows with the new data
                } catch (error) {
                    console.error("Error fetching payment history:", error);
                }
            };
            useEffect(() => {
                loadingData();
            }, []);

    // Define columns for the DataGrid
    const columns = [
        { field: "date", headerName: "Date", width: 150 },
        { field: "description", headerName: "Description", width: 300 },
        { field: "paymentStatus", headerName: "Payment Status", width: 200 },
        { field: "amount", headerName: "Amount", width: 150, type: "number" }
    ];
    return (
        <Box sx={{ height: 600, width: "100%", padding: 2 }}>
            <Typography variant="h4" gutterBottom >
                Retail Payment History
            </Typography>
            <DataGrid
                rows={rows}
                columns={columns}
                getRowId={(row) => row.id} 
                pageSize={10}
                rowsPerPageOptions={[10, 20, 50]}
                disableSelectionOnClick
            />
        </Box>
    );
};

export default PaymentHistory;
