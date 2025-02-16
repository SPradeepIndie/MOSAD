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
  backgroundColor: '#f5f5f5',
};

const getStatusColor = (status) => {
  if (status === 'IN_HOLD') return '#f44336'; // red
  if (status === 'SENT_TO_REBUILD') return '#ff9800'; // orange
  if (status === 'DONE') return '#4caf50'; // green
  return '#000';
};

const RebuildTyreTable = ({ tyres, onUpdate, onInfo, onDelete }) => {
  const [sortOrder, setSortOrder] = useState('asc');

  const handleSort = () => {
    setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc');
  };

  const sortedTyres = [...tyres].sort((a, b) => {
    return sortOrder === 'asc'
      ? a.status.localeCompare(b.status)
      : b.status.localeCompare(a.status);
  });

  return (
    <TableContainer component={Paper} sx={{ mt: 2, maxHeight: '70vh' }} stickyHeader>
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
            <TableRow
              key={index}
              sx={{
                backgroundColor: index % 2 === 0 ? '#ffffff' : '#f7f7f7',
                '&:hover': { backgroundColor: '#e0f7fa' },
                transition: 'background-color 0.3s',
              }}
            >
              <TableCell>{tyre.customerId}</TableCell>
              <TableCell>{tyre.itemId}</TableCell>
              <TableCell>{tyre.tyreNumber}</TableCell>
              <TableCell>{tyre.customerName}</TableCell>
              <TableCell>{tyre.contactNumber}</TableCell>
              <TableCell>{tyre.dateReceived}</TableCell>
              <TableCell>{tyre.billNumber}</TableCell>
              <TableCell>{tyre.price}</TableCell>
              <TableCell>
                <span style={{ color: getStatusColor(tyre.status), fontWeight: 'bold' }}>
                  {tyre.status}
                </span>
              </TableCell>
              <TableCell>
                <Tooltip title="Update">
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
