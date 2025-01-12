import React, {  useState  } from "react";
import { DataGrid } from "@mui/x-data-grid";
import { Box, Typography } from "@mui/material";
import { fetchPaymentHistory } from "../../services/apiRetailService";
import useAuth from "../../hooks/useAuth";

const PaymentHistory = () => {
    const [rows, setRows] = useState([]);
    const [billPayment,setBillPayment]=useState([]);

    const {auth}=useAuth();
    
    const loadingData=async ()=>{
            const response = await fetchPaymentHistory(auth.username,auth.billId);
            
            setBillPayment(response.data);

            loadingData();
            setRows([...rows,{
                date:billPayment.date,
                description:billPayment.description,
                paymentMethod:billPayment.paymentMethod,
                amount:billPayment.amount
            }])
            console.log(rows);
        }

        



    // Define columns for the DataGrid
    const columns = [
        { field: "date", headerName: "Date", width: 150 },
        { field: "description", headerName: "Description", width: 300 },
        { field: "paymentMethod", headerName: "Payment Status", width: 200 },
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
