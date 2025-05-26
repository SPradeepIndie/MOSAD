import React, { useState ,useEffect } from "react";
import { DataGrid } from "@mui/x-data-grid";
import { Box, Typography } from "@mui/material";
import {useFetchPurchaseHistory} from "../../hooks/servicesHook/useRetailService";
import useAuth from "../../hooks/useAuth";;

const PurchaseHistory = () => {
    const fetchPurchaseHistory=useFetchPurchaseHistory();
    const [rows, setRows] = useState([]);
    const {auth}=useAuth();

            const loadingData = async () => {
                try {
                    const response = await fetchPurchaseHistory(auth.username); // Fetching data using `auth`
                    // Update rows with id for DataGrid
                    const updatedRows = response.data.map((billPurchase, index) => ({
                        id: index, // Unique id for each row
                        date: billPurchase.date,
                        productName: billPurchase.description,
                        quantity:billPurchase.paymentMethod,
                        price:billPurchase.amount
                    }));
                    setRows(updatedRows); // Set rows with the new data
                } catch (error) {
                    console.error("Error fetching purchase history:", error);
                }
            };

            useEffect(() => {
                loadingData();
            }, []);

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
