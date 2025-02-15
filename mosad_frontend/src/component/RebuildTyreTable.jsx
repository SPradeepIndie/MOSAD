// src/component/RebuildTyreTable.jsx
import React, { useState } from 'react';
import PropTypes from 'prop-types';
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Button,
  IconButton,
  TableSortLabel
} from '@mui/material';
import RIcon from '../assets/R.png'; // adjust path if necessary
import redIcon from '../assets/red.png';
import yellowIcon from '../assets/yellow.png';
import greenIcon from '../assets/green.png';

const headerStyle = {
  fontWeight: 'bold',
  backgroundColor: '#f5f5f5', // Customize header background color here
};

const getStatusImage = (status) => {
  if (status === "IN_HOLD") return redIcon;
  if (status === "SENT_TO_REBUILD") return yellowIcon;
  if (status === "DONE") return greenIcon;
  return null;
};

const RebuildTyreTable = ({ tyres, onUpdate, onInfo, onDelete }) => {
  const [sortOrder, setSortOrder] = useState("asc"); // Track sorting order

  // Function to handle sorting
  const handleSort = () => {
    setSortOrder(sortOrder === "asc" ? "desc" : "asc");
  };

  // Sorting logic
  const sortedTyres = [...tyres].sort((a, b) => {
    if (sortOrder === "asc") {
      return a.status.localeCompare(b.status);
    } else {
      return b.status.localeCompare(a.status);
    }
  });

  return (
    <TableContainer component={Paper} sx={{ mt: 2 }}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell sx={headerStyle}>Customer ID</TableCell>
            <TableCell sx={headerStyle}>Item ID</TableCell>
            <TableCell sx={headerStyle}>Tyre Number</TableCell>
            <TableCell sx={headerStyle}>Customer Name</TableCell>
            <TableCell sx={headerStyle}>Contact Number</TableCell>
            <TableCell sx={headerStyle}>Date Received</TableCell>
            <TableCell sx={headerStyle}>Bill Number</TableCell>
            <TableCell sx={headerStyle}>Price</TableCell>
            <TableCell sx={headerStyle}>
              <TableSortLabel active direction={sortOrder} onClick={handleSort}>
                Status
              </TableSortLabel>
            </TableCell>
            <TableCell sx={headerStyle}>Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {sortedTyres.map((tyre, index) => (
            <TableRow key={index}>
              <TableCell>{tyre.customerId}</TableCell>
              <TableCell>{tyre.itemId}</TableCell>
              <TableCell>{tyre.tyreNumber}</TableCell>
              <TableCell>{tyre.customerName}</TableCell>
              <TableCell>{tyre.contactNumber}</TableCell>
              <TableCell>{tyre.dateReceived}</TableCell>
              <TableCell>{tyre.billNumber}</TableCell>
              <TableCell>{tyre.price}</TableCell>
              <TableCell>
                {getStatusImage(tyre.status) ? (
                  <img
                    src={getStatusImage(tyre.status)}
                    alt={tyre.status}
                    style={{ width: 24, height: 24 }}
                  />
                ) : (
                  tyre.status
                )}
              </TableCell>
              <TableCell>
                <Button variant="contained" onClick={() => onUpdate(tyre)} sx={{ mr: 1 }}>
                  Update
                </Button>
                <IconButton onClick={() => onInfo(tyre)}>
                  <img src={RIcon} alt="More Info" style={{ width: 24, height: 24 }} />
                </IconButton>
                <Button variant="outlined" color="error" onClick={() => onDelete(tyre.itemId)} sx={{ ml: 1 }}>
                  Delete
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

RebuildTyreTable.propTypes = {
  tyres: PropTypes.arrayOf(
    PropTypes.shape({
      customerId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
      itemId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
      tyreNumber: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
      customerName: PropTypes.string.isRequired,
      contactNumber: PropTypes.string.isRequired,
      dateReceived: PropTypes.string.isRequired,
      billNumber: PropTypes.string.isRequired,
      price: PropTypes.oneOfType([PropTypes.number, PropTypes.string]).isRequired,
      status: PropTypes.string.isRequired,
      tyreSize: PropTypes.string,
      tyreBrand: PropTypes.string,
      dateSentToCompany: PropTypes.string,
      salesRepNumber: PropTypes.string,
      jobNumber: PropTypes.string,
      dateReceivedFromCompany: PropTypes.string,
      dateDeliveredToCustomer: PropTypes.string,
    })
  ).isRequired,
  onUpdate: PropTypes.func.isRequired,
  onInfo: PropTypes.func.isRequired,
  onDelete: PropTypes.func.isRequired,
};

export default RebuildTyreTable;
