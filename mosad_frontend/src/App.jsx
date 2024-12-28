
import './App.css'
import HomePage from './pages/home/HomePage'
import BillPage from './pages/bill_section/BillPage'
import CreditPage from './pages/credit_section/CreditPage'
import StockPage from './pages/stock_section/StockPage'
import BranchPage from './pages/branch_section/BranchPage'
import DackPage from './pages/dack_section/DackPage'
import EmployeePage from './pages/employee_section/EmployeePage'
import ReportPredictionPage from './pages/prediction_report_section/ReportPredictionPage'
import RetailPage from './pages/retail_section/RetailPage'
import ServicesPage from './pages/services_section/ServicesPage'
import LoginPage from './pages/LoginPage'
import Footer from './component/Footer';
import HeaderBar from './component/Header';
import { Route,Routes,Navigate } from 'react-router-dom';
import { useState,useEffect } from 'react';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false); 
  

  useEffect(() => {
    //Check if user is already logged in
    const storedToken = localStorage.getItem('token'); 
    if (storedToken) {
      setIsLoggedIn(true); 
    }
  }, []);

  return (
    <>
      {isLoggedIn && <HeaderBar setIsLoggedIn={setIsLoggedIn}/>} 
      <Routes>
        <Route path='/'
          element={!isLoggedIn ? <LoginPage setIsLoggedIn={setIsLoggedIn} isLoggedIn={isLoggedIn} /> : <Navigate to="/home" replace />} /> 
        <Route path="/home" 
          element={isLoggedIn ? <HomePage /> : <Navigate to="/" replace />} /> 
        <Route path='/stock' element={<StockPage/>} />
        <Route path='/branch' element={<BranchPage/>} />
        <Route path='/credit' element={<CreditPage/>} />
        <Route path='/bill' element={<BillPage/>} />
        <Route path='/dack' element={<DackPage/>} />
        <Route path='/retail' element={<RetailPage/>} />
        <Route path='/future' element={<ReportPredictionPage/>} />
        <Route path='/employee' element={<EmployeePage/>} />
        <Route path='/services' element={<ServicesPage/>} />
      </Routes>
      {isLoggedIn && <Footer />}
    </>
  )

}

export default App;

import React, { useState } from "./pages/ItemView";
import ItemView from "./pages/ItemView";
import "./App.css";




