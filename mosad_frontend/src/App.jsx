import React from 'react'
import './App.css'
import Home from './pages/Home'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'
import Stock from './pages/StockCategory'
import Brands from './pages/Brands'
import ItemTable from './pages/ItemTable'

function App() {
  

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/stock" element={<Stock />} />
        <Route path="/stock/brands" element={<Brands />} />
        <Route path="/stock/brands/itemtable" element={<ItemTable />} />
      </Routes>
    </Router>
  )
}

export default App
