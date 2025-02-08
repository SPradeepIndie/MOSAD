// src/pages/RebuildTyrePage.js
import React, { useEffect, useState } from 'react';
import { Container, Typography, TextField, Button, Box } from '@mui/material';
import RebuildTyreTable from '../components/RebuildTyreTable';
import RebuildTyreForm from '../forms/RebuildTyreForm';
import {
  getAllTyres,
  getTyresByContactNumber,
  createTyre,
  updateTyre,
  deleteTyre,
} from '../services/apiDackService';

const RebuildTyrePage = () => {
  const [tyres, setTyres] = useState([]);
  const [filter, setFilter] = useState('');
  const [editingTyre, setEditingTyre] = useState(null);
  const [refresh, setRefresh] = useState(false);

  const fetchTyres = async () => {
    try {
      let data;
      if (filter) {
        data = await getTyresByContactNumber(filter);
      } else {
        data = await getAllTyres();
      }
      setTyres(data);
    } catch (error) {
      console.error(error);
      alert('Error fetching tyre data');
    }
  };

  useEffect(() => {
    fetchTyres();
  }, [filter, refresh]);

  const handleFilterChange = (e) => {
    setFilter(e.target.value);
  };

  const handleAddTyre = async (tyreData) => {
    try {
      await createTyre(tyreData);
      setRefresh(!refresh);
    } catch (error) {
      console.error(error);
      alert('Error creating tyre');
    }
  };

  const handleUpdateTyre = async (tyreData) => {
    try {
      await updateTyre(editingTyre.itemId, tyreData);
      setEditingTyre(null);
      setRefresh(!refresh);
    } catch (error) {
      console.error(error);
      alert('Error updating tyre');
    }
  };

  const handleDeleteTyre = async (id) => {
    if (window.confirm('Are you sure you want to delete this tyre?')) {
      try {
        await deleteTyre(id);
        setRefresh(!refresh);
      } catch (error) {
        console.error(error);
        alert('Error deleting tyre');
      }
    }
  };

  const handleEdit = (tyre) => {
    setEditingTyre(tyre);
  };

  const handleFormSubmit = (formData) => {
    if (editingTyre) {
      handleUpdateTyre(formData);
    } else {
      handleAddTyre(formData);
    }
  };

  const handleCancelEdit = () => {
    setEditingTyre(null);
  };

  return (
    <Container>
      <Typography variant="h4" align="center" gutterBottom>
        Rebuild Tyre Management
      </Typography>
      <Box mb={2} display="flex" alignItems="center">
        <TextField
          label="Filter by Contact Number"
          value={filter}
          onChange={handleFilterChange}
          variant="outlined"
          sx={{ mr: 2 }}
        />
        <Button variant="contained" onClick={fetchTyres}>
          Search
        </Button>
        <Button variant="outlined" onClick={() => setFilter('')} sx={{ ml: 2 }}>
          Clear Filter
        </Button>
      </Box>
      <RebuildTyreTable tyres={tyres} onEdit={handleEdit} onDelete={handleDeleteTyre} />
      <Typography variant="h5" align="center" gutterBottom sx={{ mt: 4 }}>
        {editingTyre ? 'Update Tyre' : 'Add New Tyre'}
      </Typography>
      <RebuildTyreForm
        initialData={editingTyre || {}}
        onSubmit={handleFormSubmit}
        onCancel={editingTyre ? handleCancelEdit : null}
      />
    </Container>
  );
};

export default RebuildTyrePage;
