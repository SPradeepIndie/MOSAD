import './App.css'
import HomePage from './pages/home/HomePage'
import BillPage from './pages/bill_section/BillPage'
import CreditPage from './pages/credit_section/CreditPage'
import StockPage from './pages/stock_section/StockPage'
import StockPageLayout from './pages/stock_section/StockPageLayout'
import BranchPage from './pages/branch_section/BranchPage'
import DackPage from './pages/dack_section/DackPage'
import EmployeePage from './pages/employee_section/EmployeePage'
import ReportPredictionPage from './pages/prediction_report_section/ReportPredictionPage'
import RetailPage from './pages/retail_section/RetailPage'
import ServicesPage from './pages/services_section/ServicesPage'
import LoginPage from './pages/LoginPage'
import Footer from './component/Footer';
import HeaderBar from './component/Header';
import BrandPage from './pages/stock_section/BrandPage'
import ItemView from './pages/stock_section/ItemView'
import { Route,Routes } from 'react-router-dom';
import { Box, Container } from '@mui/material'
import UserManagement from './pages/users_section/UserManagement'
import backgroundImage from './assets/bg-image.jpg';
import RoutesProtector from './RoutesProtector'
import useAuth  from "./hooks/useAuth";

function App() {
  
  const {auth}=useAuth()  

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
      
      <Box  sx={{ p: 3}} >
        <Routes>
          <Route path='/' element={<LoginPage />} /> 
          <Route element={<RoutesProtector />}>
            <Route path="/home" element={ <HomePage />} />
            <Route path="/stock" element={ <StockPageLayout />} > 
              <Route index element={<StockPage />}/>
              <Route path="brand" element={<BrandPage />}/>
              <Route path="item-view" element={<ItemView />} /> 
            </Route>
            <Route path="/branch" element={ <BranchPage />} />
            <Route path="/credit" element={ <CreditPage />} />
            <Route path="/bill" element={ <BillPage />} />
            <Route path="/dack" element={ <DackPage />} />
            <Route path="/retail" element={ <RetailPage />} />
            <Route path="/future" element={ <ReportPredictionPage />} />
            <Route path="/employee" element={ <EmployeePage />} />
            <Route path="/services" element={ <ServicesPage />} />
            <Route path="/user" element={ <UserManagement />} />
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


{/* <Routes>
          <Route path='/'
            element={!isLoggedIn ? <LoginPage setIsLoggedIn={setIsLoggedIn} isLoggedIn={isLoggedIn} /> : <Navigate to="/home" replace />} /> 
          <Route path="/home" 
            element={isLoggedIn ? <HomePage /> : <Navigate to="/" replace />} />
          <Route path="/stock" element={isLoggedIn ? <StockPageLayout /> : <Navigate to="/" replace />} > 
            <Route index element={<StockPage />}/>
            <Route path="brand" element={<BrandPage />}/>
            <Route path="item-view" element={<ItemView />} /> 
          </Route>
          <Route path="/branch" element={isLoggedIn ? <BranchPage /> : <Navigate to="/" replace />} />
          <Route path="/credit" element={isLoggedIn ? <CreditPage /> : <Navigate to="/" replace />} />
          <Route path="/bill" element={isLoggedIn ? <BillPage /> : <Navigate to="/" replace />} />
          <Route path="/dack" element={isLoggedIn ? <DackPage /> : <Navigate to="/" replace />} />
          <Route path="/retail" element={isLoggedIn ? <RetailPage /> : <Navigate to="/" replace />} />
          <Route path="/future" element={isLoggedIn ? <ReportPredictionPage /> : <Navigate to="/" replace />} />
          <Route path="/employee" element={isLoggedIn ? <EmployeePage /> : <Navigate to="/" replace />} />
          <Route path="/services" element={isLoggedIn ? <ServicesPage /> : <Navigate to="/" replace />} />
          <Route path="/user" element={isLoggedIn ? <UserManagement /> : <Navigate to="/" replace />} />
        
        </Routes> */}
