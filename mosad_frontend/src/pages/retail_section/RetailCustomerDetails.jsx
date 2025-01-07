import React, { useState, useEffect } from 'react';
import './RetailCustomerDetails.css';
import axios from 'axios';
import { Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper } from '@mui/material';

const RetailCustomerDetails = () => {
  const [data, setData] = useState([]);

 
  useEffect(() => {
    axios
      .get('http://localhost:8080/api/retail-customers') 
      .then((response) => {
        setData(response.data);
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
      });
  }, []);


  const handleDelete = (id) => {
    axios
      .delete(`http://localhost:8080/api/retail-customers/${id}`) // Replace with your delete endpoint
      .then(() => {
        setData(data.filter((item) => item.id !== id));
      })
      .catch((error) => {
        console.error('Error deleting record:', error);
      });
  };

  
  const handleUpdate = (id) => {

    console.log('Update button clicked for ID:', id);
  };

  return (
    <div className="customer-details-container">
      <TableContainer component={Paper} className="table-container">
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Name</TableCell>
              <TableCell>Email</TableCell>
              <TableCell>Mobile Number</TableCell>
              <TableCell>Address</TableCell>
              <TableCell>Business Name</TableCell>
              <TableCell></TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {data.map((row) => (
              <TableRow key={row.id}>
                <TableCell>{row.name}</TableCell>
                <TableCell>{row.email}</TableCell>
                <TableCell>{row.mobileNumber}</TableCell>
                <TableCell>{row.address}</TableCell>
                <TableCell>{row.businessName}</TableCell>
                <TableCell>
                  <Button
                    variant="contained"
                    color="error"
                    onClick={() => handleDelete(row.id)}
                    className="action-button"
                  >
                    Delete
                  </Button>
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={() => handleUpdate(row.id)}
                    className="action-button"
                  >
                    Update
                  </Button>
                </TableCell>
              </TableRow>


            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
};

export default RetailCustomerDetails;
