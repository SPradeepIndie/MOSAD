import './App.css'
import React,{useEffect, lazy} from 'react';
import { Route,Routes,Navigate,useLocation} from 'react-router-dom';
import { Box, Container } from '@mui/material'
import RoutesProtector from './RoutesProtector'
import useAuth  from "./hooks/useAuth";
import LoginPage from './pages/LoginPage'
import backgroundImage from './assets/bg-image.jpg'
import HomePage from './pages/home/HomePage'
import Footer from './component/Footer';
import HeaderBar from './component/Header';

const BillPage=lazy(()=>import('./pages/bill_section/BillPage'));
const AllBillsPage = lazy(() => import('./pages/bill_section/AllBillsPage'));
const CreditPage=lazy(()=>import('./pages/credit_section/CreditPage'));
const DackPage =lazy(()=>import( './pages/dack_section/DackPage'));
const EmployeePage =lazy(()=>import( './pages/employee_section/EmployeePage'));
// const ReportPredictionPage =lazy(()=>import( './pages/prediction_report_section/ReportPredictionPage'));
const RetailPageLayout =lazy(()=>import( './pages/retail_section/layout/RetailPageLayout'));
const BillSectionLayout =lazy(()=>import('./pages/bill_section/BillSectionLayout'));
const ServicesPage =lazy(()=>import( './pages/services_section/ServicesPage'));

const UserManagementLayout =lazy(()=>import( './pages/users_section/UserManagementLayout'));
const UserDetailsView =lazy(()=> import( './pages/users_section/UserDetailsView'));
const AllUsersView =lazy(()=>import( './pages/users_section/AllUsersView'));

const StockPageLayout =lazy(()=>import( './pages/stock_section/StockPageLayout'));
const StockPage =lazy(()=>import( './pages/stock_section/StockPage'));
const BrandPage =lazy(()=>import( './pages/stock_section/BrandPage'));
const ItemView =lazy(()=>import( './pages/stock_section/ItemView'));


const BranchPageLayout =lazy(()=>import( './pages/branch_section/BranchPageLayout'));
const BranchPage =lazy(()=>import( './pages/branch_section/BranchPage'));
const BranchStockLayout =lazy(()=>import( './pages/branch_section/BranchStockLayout'));

const PaymentHistory =lazy(()=>import( './pages/retail_section/PaymentHistory'));
const PurchaseHistory =lazy(()=>import( './pages/retail_section/PurchaseHistory'));
const IncompleteTransactions =lazy(()=>import( './pages/retail_section/IncompleteTransactions'));
const ProductAvailabilityChecker =lazy(()=>import( './pages/retail_section/ProductAvailabilityChecker'));

function App() {
  const {auth}=useAuth();

  //Scroll to top of the page alway wehn 
  //use location hook had change
  const location = useLocation();
  useEffect(() => {
    window.scrollTo(0, 0); 
  }, [location]);  

  return (
    <Container maxWidth="xl" disableGutters sx={{
      width:'100vw',
      backgroundImage: `url(${!auth.Authenticated && backgroundImage})`, // Apply background only on login page
      backgroundSize: 'cover', 
      backgroundPosition: 'center' ,
      height:'100vh'}}>
     <Box maxWidth="xl">
        { auth.Authenticated && <HeaderBar/>} 
      </Box>
      
      <Box  sx={{ p: 2,minHeight: '100vh'}} >
        <Routes>
        <Route path='/' element={auth.Authenticated ? <Navigate to="/home" replace /> : <LoginPage/>} /> 
        <Route element={<RoutesProtector />}>
          <Route path="/home" element={ <HomePage />} />

          <Route path="/stock" element={ <StockPageLayout />} > 
            <Route index element={<StockPage isFromBranch={false}/>}/>
            <Route path="brand" element={<BrandPage isFromBranch={false}/>}/>
            <Route path="item-view" element={<ItemView />} /> 
          </Route>

          <Route path="/branch" element={ <BranchPageLayout />} >
            <Route index element={<BranchPage/>}/>
            <Route path="bill-history" element={<BillPage />}/>
            <Route path="stock" element={<BranchStockLayout />}>
              <Route index element={<StockPage isFromBranch={true}/>}/>
              <Route path="brand" element={<BrandPage isFromBranch={true}/>}/>
              <Route path="item-view" element={<ItemView />}/>
            </Route>
            <Route path="employee-view" element={<EmployeePage />}/>
          </Route>

          <Route path="/credit" element={ <CreditPage />} />
          
          <Route path="/dack" element={ <DackPage />} />

          <Route path="/retail" element={ <RetailPageLayout />} >
              <Route index element={ <PaymentHistory />} />
              <Route path="purchase-history" element={ <PurchaseHistory />} />
              <Route path="incomplete-transactions" element={ <IncompleteTransactions />} />
              <Route path="product-availability" element={ <ProductAvailabilityChecker />} />
          </Route>

          {/* <Route path="/future" element={ <ReportPredictionPage />} /> */}
          <Route path="/employee" element={ <EmployeePage />} />
          <Route path="/services" element={ <ServicesPage />} />
          
          <Route path="/user" element={ <UserManagementLayout />} >
            <Route index element={<UserDetailsView/>}/>
            <Route path="view-all" element={<AllUsersView />}/>
          </Route>

          <Route path="/bill" element={ <BillSectionLayout />} >
              <Route index element={ <BillPage />} />
              <Route path="AllBillsPage" element={ <AllBillsPage />} />
              
          </Route>

        </Route>
      </Routes>
      </Box>

      <Box maxWidth="xl">
        {auth.Authenticated && <Footer />}
      </Box>
    </Container>
  )

}



export default App;
