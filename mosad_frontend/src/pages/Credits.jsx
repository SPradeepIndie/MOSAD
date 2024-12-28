import  {React,useState,Fragment} from 'react';
import PropTypes from 'prop-types';
import Box from '@mui/material/Box';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import Button from '@mui/material/Button';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';


function createData(name, contact, billId, balance, dueDate, repayments) {
  return {
    name,
    contact,
    billId,
    balance,
    dueDate,
    repayments,
  };
}

function Row(props) {
  const { row } = props;
  const [open, setOpen] = useState(false);
  const [openDialog, setOpenDialog] = useState(false);
  const [newRepayment, setNewRepayment] = useState({
    repayId: '',
    date: '',
    amount: '',
  });

  const handleDialogOpen = () => {
    setOpenDialog(true);
  };

  const handleDialogClose = () => {
    setOpenDialog(false);
  };

  const handleAddRepayment = () => {
    row.repayments.push({
      ...newRepayment,
      amount: parseFloat(newRepayment.amount),
    });
    setOpenDialog(false);
    setNewRepayment({ repayId: '', date: '', amount: '' });
  };
var totalRepayed=0;

  return (
    <Fragment>
      <TableRow sx={{ '& > *': { borderBottom: 'unset' } }}>
        <TableCell>
          <IconButton
            aria-label="expand row"
            size="small"
            onClick={() => setOpen(!open)}
          >
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        <TableCell component="th" scope="row">
          {row.name}
        </TableCell>
        <TableCell>{row.contact}</TableCell>
        <TableCell>{row.billId}</TableCell>
        <TableCell align="right">{row.balance}</TableCell>
        <TableCell>{row.dueDate}</TableCell>
        <TableCell align="right">{row.balance - row.repayments.reduce((acc, repayment) => acc + repayment.amount, 0)}</TableCell>
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <Box sx={{ margin: 1 }}>
              <Typography variant="h6" gutterBottom component="div">
                Repayment History
              </Typography>
              <Table size="small" aria-label="repayments">
                <TableHead>
                  <TableRow>
                    <TableCell>Repay ID</TableCell>
                    <TableCell>Date</TableCell>
                    <TableCell align="right">Amount</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {row.repayments.map((repayment) => (
                    <TableRow key={repayment.repayId}>
                      <TableCell>{repayment.repayId}</TableCell>
                      <TableCell>{repayment.date}</TableCell>
                      <TableCell align="right">{repayment.amount}</TableCell>
                    </TableRow>
                  ))}
                  <TableRow sx={{borderTop: 2, borderBottom: 2}}>
                    <TableCell colSpan={2}><h4>Total Repayed</h4></TableCell>
                    <TableCell align="right" sx={{borderBottom:1}}>{row.repayments.reduce((acc, repayment) => acc + repayment.amount, 0)}</TableCell>
                  </TableRow>
                </TableBody>
              </Table>
              <Button variant="contained" size="small" onClick={handleDialogOpen} sx={{ marginTop: 2,backgroundColor: '#4CAF50', color: 'white' }}>
                Add New Repayment
              </Button>
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
      <Dialog open={openDialog} onClose={handleDialogClose}>
        <DialogTitle>Add New Repayment</DialogTitle>
        <DialogContent>
          <TextField
            margin="dense"
            label="Repay ID"
            type="text"
            fullWidth
            variant="outlined"
            value={newRepayment.repayId}
            //disabled
            onChange={(e) => setNewRepayment({ ...newRepayment, repayId: e.target.value })}
          />
          <TextField
            margin="dense"
            //label="Date"
            type="date"
            fullWidth
            variant="outlined"
            //value={new Date().toISOString().split('T')[0]}
            value={newRepayment.date}
            onChange={(e) => setNewRepayment({ ...newRepayment, date: e.target.value  })}
            //InputLabelProps={{ shrink: true }}
          />
        
          <TextField
            margin="dense"
            label="Amount"
            type="number"
            fullWidth
            variant="outlined"
            value={newRepayment.amount}
            onChange={(e) => setNewRepayment({ ...newRepayment, amount: e.target.value })}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleDialogClose} sx={{backgroundColor:''}}>Cancel</Button>
          <Button onClick={handleAddRepayment} variant="contained" sx={{backgroundColor: '#4CAF50', color: 'white'}}>
            Add
          </Button>
        </DialogActions>
      </Dialog>
    </Fragment>
  );
}

Row.propTypes = {
  row: PropTypes.shape({
    name: PropTypes.string.isRequired,
    contact: PropTypes.string.isRequired,
    billId: PropTypes.string.isRequired,
    balance: PropTypes.number.isRequired,
    dueDate: PropTypes.string.isRequired,
    repayments: PropTypes.arrayOf(
      PropTypes.shape({
        repayId: PropTypes.string.isRequired,
        date: PropTypes.string.isRequired,
        amount: PropTypes.number.isRequired,
      })
    ).isRequired,
  }).isRequired,
};

const initialRows = [
  createData('John Doe', '123-456-7890', 'B001', 150.0, '2024-01-15', [
    { repayId: 'R001', date: '2023-12-01', amount: 50.0 },
    { repayId: 'R002', date: '2023-12-10', amount: 10.0 },
  ]),
  createData('Jane Smith', '987-654-3210', 'B002', 200.0, '2024-01-20', [
    { repayId: 'R003', date: '2023-11-25', amount: 200.0 },
  ]),
  createData('John Doe', '123-456-7890', 'B003', 150.0, '2024-01-15', [
    { repayId: 'R001', date: '2023-12-01', amount: 50.0 },
    { repayId: 'R002', date: '2023-12-10', amount: 100.0 },
  ]),
    createData('Jane Smith', '987-654-3210', 'B004', 200.0, '2024-01-20', [
    { repayId: 'R003', date: '2023-11-25', amount: 200.0 },
    ]),
    createData('John Doe', '123-456-7890', 'B005', 150.0, '2024-01-15', [
    { repayId: 'R001', date: '2023-12-01', amount: 50.0 },
    { repayId: 'R002', date: '2023-12-10', amount: 100.0 },
    ]),
    createData('Jane Smith', '987-654-3210', 'B006', 200.0, '2024-01-20', [
    { repayId: 'R003', date: '2023-11-25', amount: 200.0 },
    ]),
    createData('John Doe', '123-456-7890', 'B007', 150.0, '2024-01-15', [
    { repayId: 'R001', date: '2023-12-01', amount: 50.0 },
    { repayId: 'R002', date: '2023-12-10', amount: 100.0 },
    ]),
    createData('Jane Smith', '987-654-3210', 'B008', 200.0, '2024-01-20', [
    { repayId: 'R003', date: '2023-11-25', amount: 200.0 },
    ]),
    createData('John Doe', '123-456-7890', 'B009', 150.0, '2024-01-15', [
    { repayId: 'R001', date: '2023-12-01', amount: 50.0 },
    { repayId: 'R002', date: '2023-12-10', amount: 100.0 },
    ]),
    createData('Jane Smith', '987-654-3210', 'B010', 200.0, '2024-01-20', [
    { repayId: 'R003', date: '2023-11-25', amount: 200.0 },
    ]),
    createData('John Doe', '123-456-7890', 'B011', 150.0, '2024-01-15', [
    { repayId: 'R001', date: '2023-12-01', amount: 50.0 },
    { repayId: 'R002', date: '2023-12-10', amount: 100.0 },
    ]),
    createData('Jane Smith', '987-654-3210', 'B012', 200.0, '2024-01-20', [
    { repayId: 'R003', date: '2023-11-25', amount: 200.0 },
    ]),
  
];

export default function Credits() {
  const [rows, setRows] = useState(initialRows);
  const [searchText, setSearchText] = useState('');

  const filteredRows = rows.filter((row) =>
    row.name.toLowerCase().includes(searchText.toLowerCase()) ||
    row.contact.toLowerCase().includes(searchText.toLowerCase()) ||
    row.billId.toLowerCase().includes(searchText.toLowerCase())
  );

  return (

    <>
    <TextField
        fullWidth
        label="Search By Name / Contact Number / Bill ID"
        variant="outlined"
        value={searchText}
        onChange={(e)=>setSearchText(e.target.value)}
        sx={{ marginBottom: 2 }}
      />
    <Box sx={{ padding: 2 }}>
      
      <TableContainer component={Paper} sx={{ maxHeight: 400 }}>
        <Table stickyHeader aria-label="collapsible table">
          <TableHead>
            <TableRow>
              <TableCell />
              <TableCell>Customer Name</TableCell>
              <TableCell>Contact Number</TableCell>
              <TableCell>Bill ID</TableCell>
              <TableCell align="right">Balance ($)</TableCell>              
              <TableCell>Due Date</TableCell>
              <TableCell align="right">Remaining Balance ($)</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {filteredRows.map((row) => (
              <Row key={row.billId} row={row} />
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
   </> 
  );
}
