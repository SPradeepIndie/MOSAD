import {React,useState,useEffect} from 'react';
import { Button, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';


const StockInHistory = ({ open, onClose,rows }) => {
    
    const columns = [
        { field: 'date', headerName: 'Date', width: 150 },
        { field: 'quantity', headerName: 'Quantity', width: 150 },
        //{ field: 'price', headerName: 'Price', width: 150 },
    ];

    const rowsEx = rows.map((row,index) => {
        return {
            id: index,
            date: row.date,
            quantity: row.quantity,
            //price: row.officialSellingPrice,
        };
    }
    );



    return (
        <div>
            <Dialog open={open} onClose={onClose} maxWidth="md"  width="50%">
                <DialogTitle>Stock In History</DialogTitle>
                <DialogContent>
                    <div style={{ height: 400, width: '100%' }}>
                        <DataGrid rows={rowsEx} columns={columns} pageSize={3} />
                    </div>
                </DialogContent>
                <DialogActions>
                    <Button onClick={onClose} color="primary">
                        Close
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
};

export default StockInHistory;
