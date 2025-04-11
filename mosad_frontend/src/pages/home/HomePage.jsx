import {React, useEffect ,useState} from "react";
import Slideshow from '../../component/Slideshow';
import Tile from '../../component/Tile';
import { Box, Stack, Typography, useTheme, Paper, Button, Divider, Grid2,MenuItem,
  Select,
  FormControl,
  InputLabel } from "@mui/material";
import DescriptionIcon from '@mui/icons-material/Description';
import InventoryIcon from '@mui/icons-material/Inventory';
import StorefrontIcon from '@mui/icons-material/Storefront';
import CreditCardIcon from '@mui/icons-material/CreditCard';
import AccountTreeIcon from '@mui/icons-material/AccountTree';
import PeopleIcon from '@mui/icons-material/People';
import SearchIcon from '@mui/icons-material/Search';
import ReceiptIcon from '@mui/icons-material/Receipt';
import VisibilityIcon from '@mui/icons-material/Visibility';
import AssessmentIcon from '@mui/icons-material/Assessment';
import AnalyticalCard from "../../component/AnalyticalCard";
import { styled } from "@mui/material/styles";
import Chatbot from '../../component/chatbot';
import { teal, cyan } from '@mui/material/colors';
import Calendar from '../../component/Calendar';
import { useNavigate } from "react-router-dom";
import PopUp from "../../component/PopUp";
import { useFetchBrands,useHomeStats,useHomeCalDueDates } from "../../hooks/servicesHook/useStockService";
import GeneralSnackbarAlerts from "../../component/GeneralSnackbarAlerts";

// Styled Tile Component for a modern look
const ModernTile = styled(Tile)(({ theme }) => ({
  backgroundColor: theme.palette.background.paper,
  borderRadius: theme.spacing(1),
  boxShadow: theme.shadows[2],
  padding: theme.spacing(3),
  textAlign: 'center',
  transition: 'transform 0.2s ease-in-out',
  '&:hover': {
    transform: 'scale(1.05)',
    boxShadow: theme.shadows[4],
  },
  '& .MuiSvgIcon-root': {
    fontSize: '3rem',
    marginBottom: theme.spacing(1),
    color: teal[500], // Using teal color
  },
  '& .MuiTypography-h6': {
    fontWeight: 500,
    color: theme.palette.text.primary,
  },
}));

function HomePage() {
  //Show alerts using snack bar
  const [showSnack, setShowSnack] = useState(false);
  const [alertType, setAlertType] = useState("warning");
  const [alertMsg, setAlertMsg] = useState("");
  const homestats = useHomeStats();
  const { theme } = useTheme();
  const [stats, setStats] = useState({});
  const fetchHomeCalDates = useHomeCalDueDates();
  const [dueDates,setDueDates] =useState([]);

  useEffect(() => {
    const fetchStats = async () => {
      try {
        const response = await homestats();
        setStats(response.data); // Update stats state with the fetched data
        //console.log("Home stats fetched successfully:", stats);
      } catch (error) {
        console.error("Error fetching home stats:", error);
      }
    };
    fetchStats();
  }, []);

  useEffect(() => {
    const fetchDueDates = async () => {
      try {
        const response = await fetchHomeCalDates();
        setDueDates(response.data); // Update stats state with the fetched data
      } catch (error) {
        console.error("Error fetching home stats:", error);
      }
    };
    fetchDueDates();
  }, []);

  console.log("Home stats after :", stats);

  console.log("Home stats before :", stats.past7DaysBillCount);
  const billsData = stats.past7DaysBillCount?.map(item => item.billsCount);
  console.log(billsData);

  const fetchBrands = useFetchBrands();
  const [dialogOpen, setDialogOpen] = useState(false);
  const [selectedBrand, setSelectedBrand] = useState(""); // Changed state name for clarity
  const [allbrands,setAllBrands]=useState([])
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleBrandSelection = () => {
    if (selectedBrand) {
      navigate('/stocks/category/brands/item-view', { state: { brand: selectedBrand,category:"Tyre" } });
      setDialogOpen(false); // Close the dialog after navigation
    } else {
      // Optionally handle the case where no brand is selected
      console.warn("Please select a brand.");
    }
  };

  useEffect(() => {
    const getBrands = async () => {
      try {
        const response = await fetchBrands("Tyre");
        setAllBrands(response.data.map((brand) => brand.brandName));
      } catch (err) {
        setAlertMsg(error.response?.data || error.message || 'fetching failed.')
        setShowSnack(true)
      } finally {
        setLoading(false);
      }
    };

    getBrands();
  }, []); // Added fetchBrands to the dependency array

  const brandSelectForm=()=>{
    return (
      <Box sx={{ p: 3, maxWidth: '400px', mx: 'auto' }}>
        <GeneralSnackbarAlerts open={showSnack} type={alertType} msg={alertMsg} setOpen={setShowSnack}/>
            <Stack spacing={2} direction="column">
                <Typography variant="body1" gutterBottom>
                  Please select the brand
                </Typography>
                <FormControl fullWidth sx={{ minWidth: 120 }}>
                  <InputLabel id="brand-select-label">Brand</InputLabel>
                  <Select
                    labelId="brand-select-label"
                    value={selectedBrand}
                    onChange={(e) => {
                      setSelectedBrand(e.target.value); // Update selectedBrand state
                    }}
                    label="Brand"
                    variant="outlined"
                    fullWidth
                  >
                    {allbrands.map((b) => (
                      <MenuItem key={b} value={b}>
                        {b}
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>

              <Grid2 size={{ xs:12}} container justifyContent="flex-end" spacing={2}>
                <Grid2 >
                  <Button onClick={() => setDialogOpen(false)} color="secondary">
                    Cancel
                  </Button>
                </Grid2>
                <Grid2 >
                  <Button
                    onClick={handleBrandSelection} // Call handleBrandSelection on click
                    sx={{ color: 'white', backgroundColor: 'green', '&:hover': { backgroundColor: 'darkgreen' } }}
                  >
                    Go
                  </Button>
                </Grid2>
              </Grid2>
            </Stack>
      </Box>
    )
  }


  const tiles = [
    { title: 'Bill Generate', icon: <DescriptionIcon />, link: '/bills', authorizedRoles: ["OWNER", "ADMIN"] },
    { title: 'Stock', icon: <InventoryIcon />, link: '/stocks/category', authorizedRoles: ["OWNER", "ADMIN", "STOCK_MANAGER"] },
    { title: 'Retail', icon: <StorefrontIcon />, link: '/retails', authorizedRoles: ["OWNER", "ADMIN", "RETAIL_CUSTOMER"] },
    { title: 'Credit', icon: <CreditCardIcon />, link: '/credits', authorizedRoles: ["OWNER", "ADMIN"] },
    { title: 'Branches', icon: <AccountTreeIcon />, link: '/branches', authorizedRoles: ["OWNER", "ADMIN", "BRANCH_MANAGER"] },
    { title: 'Employee', icon: <PeopleIcon />, link: '/employees', authorizedRoles: ["OWNER", "ADMIN", "STOCK_MANAGER", "BRANCH_MANAGER", "MECHANIC"] },
    { title: 'Reports', icon: <AssessmentIcon />, link: "/reports", authorizedRoles: ["OWNER", "ADMIN"] },
    { title: 'Dack Tires', icon: <AssessmentIcon />, link: '/dacks', authorizedRoles: ["OWNER", "ADMIN"] }
  ];

  return (
    <Box sx={{ flexGrow: 1, padding: 2 }}>
      <Typography variant="h4" component="h2" mb={3} textAlign="center" color={teal[500]}>
        Admin Dashboard
      </Typography>

      <Grid2 container spacing={3}>
        <Grid2 size={{xs:12}}>
          <Slideshow />
        </Grid2>
        <Grid2 size={{xs:12}}>
          <AnalyticalCard stats={stats}/>
        </Grid2>
        <Grid2 size={{xs:12}}>
          <Box sx={{ display: 'flex', justifyContent: 'center', my: 3 }}>
            <Divider
              variant="middle"
              sx={{
                borderBottomWidth: 4,
                borderColor: cyan[400],
                width: '50%',
              }}
            />
          </Box>
        </Grid2>
        <Grid2 container size={{xs:12}} justifyContent={{ md: 'center' }}>
          <Grid2 size={{xs:12,md:4}}>
            <Paper elevation={2} sx={{
              p: 3,
              borderRadius: 2,
              height: '100%',
              display: 'flex',
              flexDirection: 'column',
              justifyContent: 'space-between'
            }}>
              <Typography variant="h6" component="h3" mb={2} textAlign="center" color={teal[500]}>
                Upcoming Due Dates
              </Typography>
              <Box sx={{ flexGrow: 1, display: 'flex', justifyContent: 'center' }}>
                <Calendar highlightedDates={dueDates} />
              </Box>
              <Typography variant="body2" color="text.secondary" textAlign="center" mt={2}>
                This calendar displays the due dates for each credit.
                <br />
                Items marked with an <Typography component="span" fontWeight="bold">*</Typography> denote special attention.
              </Typography>
            </Paper>
          </Grid2>

          {/* Vertical Divider - Only shows on medium screens and up */}
          <Grid2 size={{xs:'none',md:'block'}}>
            <Divider orientation="vertical" flexItem sx={{
              borderRightWidth: 2,
              borderColor: cyan[200],
              mx: 'auto',
              height: '100%'
            }} />
          </Grid2>

          {/* Horizontal Divider - Only shows on small screens */}
          <Grid2 size={{xs:'block' ,md:'none'}}>
            <Divider sx={{
              borderBottomWidth: 2,
              borderColor: cyan[200],
              my: 2
            }} />
          </Grid2>

          <Grid2 size={{xs:12,md:4}}>
            <Paper elevation={2} sx={{
              p: 3,
              borderRadius: 2,
              height: '100%',
              display: 'flex',
              flexDirection: 'column'
            }}>
              <Typography variant="h6" component="h3" mb={3} textAlign="center" color={teal[500]}>
                Quick Access
              </Typography>

              <Stack spacing={2} sx={{ flexGrow: 1 }}>
                <Button
                  variant="outlined"
                  size="large"
                  startIcon={<SearchIcon />}
                  fullWidth
                  sx={{
                    py: 1.5,
                    borderColor: teal[300],
                    color: teal[700],
                    '&:hover': {
                      backgroundColor: teal[50],
                      borderColor: teal[500]
                    }
                  }}
                  onClick={()=>{
                    navigate('/retails/product-availability')
                  }}
                >
                  Product Search
                </Button>

                <Button
                  variant="outlined"
                  size="large"
                  startIcon={<ReceiptIcon />}
                  fullWidth
                  sx={{
                    py: 1.5,
                    borderColor: teal[300],
                    color: teal[700],
                    '&:hover': {
                      backgroundColor: teal[50],
                      borderColor: teal[500]
                    }
                  }}
                  onClick={()=>{
                    navigate('/bills/all-bills')
                  }}
                >
                  View All Bills
                </Button>

                <Button
                  variant="outlined"
                  size="large"
                  startIcon={<VisibilityIcon />}
                  fullWidth
                  sx={{
                    py: 1.5,
                    borderColor: teal[300],
                    color: teal[700],
                    '&:hover': {
                      backgroundColor: teal[50],
                      borderColor: teal[500]
                    }
                  }}
                  onClick={()=>{
                    setDialogOpen(true);
                  }}
                >
                  View tires
                </Button>

                {/* Additional Quick Actions */}
                <Box sx={{ mt: 'auto', pt: 2 }}>
                  <Button
                    variant="contained"
                    size="medium"
                    fullWidth
                    sx={{
                      backgroundColor: teal[500],
                      '&:hover': { backgroundColor: teal[700] }
                    }}
                    onClick={()=>{
                      navigate('/reports')
                    }}
                  >
                    Overivew
                  </Button>
                </Box>
              </Stack>
            </Paper>
          </Grid2>
        </Grid2>

        <Grid2 size={{xs:12}}>
          <Typography variant="h4" component="h2" gutterBottom textAlign="center" mt={4} color={teal[500]}>
            Choose a section
          </Typography>
          <Stack
            direction="row"
            sx={{
              gap: { xs: '16px', sm: '24px', md: '32px' },
              marginBottom: { xs: 3, sm: 4, md: 5 },
              flexWrap: 'wrap',
              justifyContent: 'center',
            }}
          >
            {tiles.map((tile) => (
              <ModernTile
                key={tile.title}
                allowedRoles={tile.authorizedRoles}
                title={tile.title}
                icon={tile.icon}
                link={tile.link}
              />
            ))}
          </Stack>
        </Grid2>
      </Grid2>
       {/* Dialog for choose brand and category */}
        <PopUp
            popUpTitle="Choose you brand"
            openPopup={dialogOpen}
            setOpenPopup={setDialogOpen}
            // Removed setOkButtonAction here, the action is handled directly in the button
            setCancelButtonAction={() => setDialogOpen(false)}
            isDefaultButtonsDisplay={false}
            width="md">
            {brandSelectForm()}
        </PopUp>

      {/* Floating Chatbot Icon and Window */}
      <Chatbot />
    </Box>
  );
}

export default HomePage;