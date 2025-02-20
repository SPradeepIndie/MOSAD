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
  IconButton,
  TableSortLabel,
  Tooltip,
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import InfoIcon from '@mui/icons-material/Info';

const headerStyle = {
  fontWeight: 'bold',
  backgroundColor: '#1976d2', // professional blue header
  color: '#fff',
};

const RebuildTyreTable = ({ tyres, onUpdate, onInfo, onDelete }) => {
  const [sortOrder, setSortOrder] = useState('asc');

  const handleSort = () => {
    setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc');
  };

  const sortedTyres = [...tyres].sort((a, b) =>
    sortOrder === 'asc'
      ? a.status.localeCompare(b.status)
      : b.status.localeCompare(a.status)
  );

  return (
    <TableContainer
      component={Paper}
      sx={{ mt: 3, boxShadow: 3, borderRadius: 2 }}
    >
      <Table stickyHeader>
        <TableHead>
          <TableRow>
            <TableCell sx={headerStyle}>Item ID</TableCell>
            <TableCell sx={headerStyle}>Tyre Number</TableCell>
            <TableCell sx={headerStyle}>Customer Name</TableCell>
            <TableCell sx={headerStyle}>Contact Number</TableCell>
            <TableCell sx={headerStyle}>Date Received</TableCell>
            <TableCell sx={headerStyle}>Bill Number</TableCell>
            <TableCell sx={headerStyle}>Price</TableCell>
            <TableCell sx={headerStyle}>
              <TableSortLabel
                active
                direction={sortOrder}
                onClick={handleSort}
                sx={{ color: '#fff' }}
              >
                Status
              </TableSortLabel>
            </TableCell>
            <TableCell sx={headerStyle} align="center">
              Actions
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {sortedTyres.map((tyre, index) => (
            <TableRow
              key={index}
              sx={{
                backgroundColor: index % 2 === 0 ? '#f9f9f9' : '#fff',
                '&:hover': { backgroundColor: '#e3f2fd' },
                transition: 'background-color 0.3s ease',
              }}
            >
              <TableCell>{tyre.itemId}</TableCell>
              <TableCell>{tyre.tyreNumber}</TableCell>
              <TableCell>{tyre.customerName}</TableCell>
              <TableCell>{tyre.contactNumber}</TableCell>
              <TableCell>{tyre.dateReceived}</TableCell>
              <TableCell>{tyre.billNumber}</TableCell>
              <TableCell>{tyre.price}</TableCell>
              <TableCell>{tyre.status}</TableCell>
              <TableCell align="center">
                <Tooltip title="Edit">
                  <IconButton onClick={() => onUpdate(tyre)} color="primary">
                    <EditIcon />
                  </IconButton>
                </Tooltip>
                <Tooltip title="More Info">
                  <IconButton onClick={() => onInfo(tyre)} color="info">
                    <InfoIcon />
                  </IconButton>
                </Tooltip>
                <Tooltip title="Delete">
                  <IconButton onClick={() => onDelete(tyre.itemId)} color="error">
                    <DeleteIcon />
                  </IconButton>
                </Tooltip>
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
      itemId: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
      tyreNumber: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
      customerName: PropTypes.string.isRequired,
      contactNumber: PropTypes.string.isRequired,
      dateReceived: PropTypes.string.isRequired,
      billNumber: PropTypes.string.isRequired,
      price: PropTypes.oneOfType([PropTypes.string, PropTypes.number]).isRequired,
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
