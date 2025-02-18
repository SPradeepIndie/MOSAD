import React from 'react';
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
} from '@mui/material';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

const RebuildTyreTable = ({ tyres, onEdit, onDelete }) => {
  return (
    <TableContainer component={Paper}>
      <Table aria-label="rebuild tyre table">
        <TableHead>
          <TableRow>
            <TableCell>ID</TableCell>
            <TableCell>Customer ID</TableCell>
            <TableCell>Tyre Number</TableCell>
            <TableCell>Tyre Size</TableCell>
            <TableCell>Tyre Brand</TableCell>
            <TableCell>Customer Name</TableCell>
            <TableCell>Contact Number</TableCell>
            <TableCell>Date Received</TableCell>
            <TableCell>Date Sent To Company</TableCell>
            <TableCell>Sales Rep Number</TableCell>
            <TableCell>Job Number</TableCell>
            <TableCell>Date Received From Company</TableCell>
            <TableCell>Date Delivered To Customer</TableCell>
            <TableCell>Bill Number</TableCell>
            <TableCell>Price</TableCell>
            <TableCell>Status</TableCell>
            <TableCell>Actions</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {tyres.map((tyre) => (
            <TableRow key={tyre.itemId}>
              <TableCell>{tyre.itemId}</TableCell>
              <TableCell>{tyre.customerId}</TableCell>
              <TableCell>{tyre.tyreNumber}</TableCell>
              <TableCell>{tyre.tyreSize}</TableCell>
              <TableCell>{tyre.tyreBrand}</TableCell>
              <TableCell>{tyre.customerName}</TableCell>
              <TableCell>{tyre.contactNumber}</TableCell>
              <TableCell>{tyre.dateReceived}</TableCell>
              <TableCell>{tyre.dateSentToCompany || '-'}</TableCell>
              <TableCell>{tyre.salesRepNumber || '-'}</TableCell>
              <TableCell>{tyre.jobNumber || '-'}</TableCell>
              <TableCell>{tyre.dateReceivedFromCompany || '-'}</TableCell>
              <TableCell>{tyre.dateDeliveredToCustomer || '-'}</TableCell>
              <TableCell>{tyre.billNumber || '-'}</TableCell>
              <TableCell>{tyre.price || '-'}</TableCell>
              <TableCell>{tyre.status}</TableCell>
              <TableCell>
                <IconButton onClick={() => onEdit(tyre)}>
                  <EditIcon />
                </IconButton>
                <IconButton onClick={() => onDelete(tyre.itemId)}>
                  <DeleteIcon />
                </IconButton>
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
      itemId: PropTypes.number.isRequired,
      customerId: PropTypes.number.isRequired,
      tyreNumber: PropTypes.string.isRequired,
      tyreSize: PropTypes.string.isRequired,
      tyreBrand: PropTypes.string.isRequired,
      customerName: PropTypes.string.isRequired,
      contactNumber: PropTypes.string.isRequired,
      dateReceived: PropTypes.string.isRequired,
      dateSentToCompany: PropTypes.string,
      salesRepNumber: PropTypes.string,
      jobNumber: PropTypes.string,
      dateReceivedFromCompany: PropTypes.string,
      dateDeliveredToCustomer: PropTypes.string,
      billNumber: PropTypes.string,
      price: PropTypes.string,
      status: PropTypes.string.isRequired,
    })
  ).isRequired,
  onEdit: PropTypes.func.isRequired,
  onDelete: PropTypes.func.isRequired,
};

export default RebuildTyreTable;

