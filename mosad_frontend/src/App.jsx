import './App.css'
import React,{useEffect} from 'react';
import { Route,Routes,Navigate,useLocation} from 'react-router-dom';
import { Box, Container } from '@mui/material'
import RoutesProtector from './RoutesProtector'
import useAuth  from "./hooks/useAuth";

import HomePage from './pages/home/HomePage'
import BillPage from './pages/bill_section/BillPage'
import CreditPage from './pages/credit_section/CreditPage'
import DackPage from './pages/dack_section/DackPage'
import EmployeePage from './pages/employee_section/EmployeePage'
import ReportPredictionPage from './pages/prediction_report_section/ReportPredictionPage'
import RetailPageLayout from './pages/retail_section/layout/RetailPageLayout'
import ServicesPage from './pages/services_section/ServicesPage'

import UserDetailsView from './pages/users_section/UserDetailsView'
import UserManagementLayout from './pages/users_section/UserManagementLayout';
import AllUsersView from './pages/users_section/AllUsersView';

import StockPageLayout from './pages/stock_section/StockPageLayout'
import StockPage from './pages/stock_section/StockPage'
import BrandPage from './pages/stock_section/BrandPage'
import ItemView from './pages/stock_section/ItemView'

import LoginPage from './pages/LoginPage'
import backgroundImage from './assets/bg-image.jpg';

import Footer from './component/Footer';
import HeaderBar from './component/Header';

import BranchPageLayout from './pages/branch_section/BranchPageLayout'
import BranchPage from './pages/branch_section/BranchPage'
import BranchStockLayout from './pages/branch_section/BranchStockLayout';

import PaymentHistory from './pages/retail_section/PaymentHistory';
import PurchaseHistory from './pages/retail_section/PurchaseHistory';
import IncompleteTransactions from './pages/retail_section/IncompleteTransactions';
import ProductAvailabilityChecker from './pages/retail_section/ProductAvailabilityChecker';

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
      backgroundImage: `url(${!auth.success && backgroundImage})`, // Apply background only on login page
      backgroundSize: 'cover', 
      backgroundPosition: 'center' ,
      height:'100vh'}}>
     <Box maxWidth="xl">
        { auth.success && <HeaderBar/>} 
      </Box>
      
      <Box  sx={{ p: 2,minHeight: '100vh'}} >
        <Routes>
        <Route path='/' element={auth.success ? <Navigate to="/home" replace /> : <LoginPage/>} /> 
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
            <Route path="/bill" element={ <BillPage />} />
            <Route path="/dack" element={ <DackPage />} />

            <Route path="/retail" element={ <RetailPageLayout />} >
               <Route index element={ <PaymentHistory />} />
               <Route path="purchase-history" element={ <PurchaseHistory />} />
               <Route path="incomplete-transactions" element={ <IncompleteTransactions />} />
               <Route path="product-availability" element={ <ProductAvailabilityChecker />} />
            </Route>
            <Route path="/future" element={ <ReportPredictionPage />} />
            <Route path="/employee" element={ <EmployeePage />} />
            <Route path="/services" element={ <ServicesPage />} />
            
            <Route path="/user" element={ <UserManagementLayout />} >
              <Route index element={<UserDetailsView/>}/>
              <Route path="view-all" element={<AllUsersView />}/>
            </Route>
          </Route>
        </Routes>
      </Box>

      <Box maxWidth="xl">
        {auth.success && <Footer />}
      </Box>
    </Container>
  )

}

export default App;
