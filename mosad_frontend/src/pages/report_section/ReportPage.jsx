import React, { useEffect, useState } from 'react';
import { Divider, Stack, Box, FormControl, InputLabel, Select, MenuItem, Button } from '@mui/material';
import { Paper, Typography, Grid2 } from '@mui/material';
import { DataGrid } from '@mui/x-data-grid';
import SalesGraph from '../../component/GraphComponent'; // Assuming this path is correct
import { cyan } from '@mui/material/colors';
import { useItemCounts } from '../../hooks/servicesHook/useReportService';
import GeneralSnackbarAlerts from '../../component/GeneralSnackbarAlerts';

const StockReport = () => {
  const [reportName, setReportName] = useState('');
  const [itemCount,setItemCount]=useState([]);
  const fetchItemCount=useItemCounts();
  //Show alerts using snack bar
  const [showSnack, setShowSnack] = useState(false);
  const [alertType, setAlertType] = useState("warning");
  const [alertMsg, setAlertMsg] = useState("");

  const [loading, setLoading] = useState(true);

  const handleReportNameChange = (event) => {
    setReportName(event.target.value);
  };

  const loadItemCount=async ()=>{
    try {
      const response = await fetchItemCount();
      const dataWithIds = response.data.map((item, index) => ({
        id: index + 1,
        ...item,
      }));
      setItemCount(dataWithIds);
    } catch (error) {
      setAlertMsg(error.response?.data || error.message || 'Table data  fetching failed.')
      setShowSnack(true)
    } finally {
      setLoading(false);
    }
  }
  useEffect(()=>{
    loadItemCount();
  },[])

  const handleGenerateReport = () => { 
    console.log(`Generating report with name: ${reportName}`);
  };

  const columns = [
    { field: 'id', headerName: 'ID', width: 70 },
    { field: 'categoryName', headerName: 'Category', width: 150 },
    { field: 'totalBrands', headerName: 'Total Brands', width: 130 },
    { field: 'totalItems', headerName: 'Total Items', width: 130 },
  ];

  return (
    <Box sx={{mx:5}}>
      <GeneralSnackbarAlerts open={showSnack} type={alertType} msg={alertMsg} setOpen={setShowSnack}/>
      <Paper elevation={3} sx={{px:5,py:3,width:'100%'}}>
        <Stack spacing={2}>
            <Typography variant="h6" gutterBottom>
                  Sales graph acros past 7 days
            </Typography>
          <Paper elevation={3}>  
            <SalesGraph
              dataSet={[
                { data: [35, 44, 24, 34] },
                { data: [51, 6, 49, 30] },
                { data: [15, 25, 30, 50] },
                { data: [60, 50, 15, 25] },
              ]}
              xaxis={['Q1', 'Q2', 'Q3', 'Q4']}
            />
          </Paper>
          <Grid2 size={{xs:12}}>
          <Box sx={{ display: 'flex', justifyContent: 'center', my: 3 }}>
            <Divider
              variant="middle"
              sx={{
                borderBottomWidth: 4,
                borderColor: cyan[400],
                width: '80%',
              }}
            />
          </Box>
        </Grid2>
          <Grid2 container spacing={2}>
            <Grid2 item xs={12} md={6}>
              <Typography variant="h6" gutterBottom>
                Table of All Products
              </Typography>
              <div style={{ height: 400, width: '100%' }}>
                <DataGrid
                  rows={itemCount}
                  columns={columns}
                  pageSizeOptions={[5, 10, 25]}
                  checkboxSelection
                />
              </div>
            </Grid2>
          </Grid2>

          {/* Report Generation Section */}
          <Paper elevation={3} sx={{ p: 2 }}>
            <Typography variant="h6" gutterBottom>
              Report Generation
            </Typography>

            <Box sx={{ display: 'flex', alignItems: 'center', gap: 2 }}>
              <FormControl fullWidth>
                <InputLabel id="report-name-label">Select Report Name</InputLabel>
                <Select
                  labelId="report-name-label"
                  id="report-name"
                  value={reportName}
                  label="Select Report Name"
                  onChange={handleReportNameChange}
                >
                  <MenuItem value="stock_summary">Stock Summary</MenuItem>
                  <MenuItem value="sales_overview">Sales Overview</MenuItem>
                </Select>
              </FormControl>

              {reportName === 'sales_overview' && (
              <a
                href="http://127.0.0.1:8001/forecast/download/revenue-forecast"
                download="sales_revenue_forecast.csv"
                style={{ textDecoration: 'none' }}
              >
              <Button variant="contained" color="primary">
                  Download Sales Overview Report
              </Button>
              </a>
             )}

              {reportName === 'tyre_forecast' && (
              <a
                href="http://127.0.0.1:8001/forecast/download/tyre-forecast"
                download="tyre_forecast.csv"
                style={{ textDecoration: 'none' }}
              >
              <Button variant="contained" color="primary">
                Download Tyre Forecast
              </Button>
                </a>
              )}

{reportName === 'tube_forecast' && (
  <a
    href="http://127.0.0.1:8001/forecast/download/tube-forecast"
    download="tube_forecast.csv"
    style={{ textDecoration: 'none' }}
  >
    <Button variant="contained" color="primary">
      Download Tube Forecast
    </Button>
  </a>
)}

{(reportName !== 'sales_overview' &&
  reportName !== 'tyre_forecast' &&
  reportName !== 'tube_forecast') && (
  <Button
    variant="contained"
    color="primary"
    onClick={handleGenerateReport}
    disabled={!reportName}
  >
    Generate Report
  </Button>
)}
            </Box>
          </Paper>
        </Stack>
      </Paper>
    </Box>
  );
};

export default StockReport;