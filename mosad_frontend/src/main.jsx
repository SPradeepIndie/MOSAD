import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { createBrowserRouter,RouterProvider } from 'react-router-dom'
import './index.css'
import App from './App.jsx'
import LoginPage from './pages/LoginPage.jsx'
import NotFoundPage from './pages/NotFoundPage.jsx'
import StockCategory from './pages/StockCategory.jsx'
import Brands from './pages/Brands.jsx'
import ItemView from './pages/ItemView.jsx'
import Credits from './pages/Credits.jsx'

const router=createBrowserRouter([
  {
    path:'/',
    element:<LoginPage/>,
    errorElement:<NotFoundPage/>
  },
  {
    path:'/home',
    element:<App/>
  },
  {
    path:'/stock',
    element:<StockCategory/>
  },
  {
    path:'/stock/brands',
    element:<Brands/>
  },
  {
    path:'/stock/brands/itemview',
    element:<ItemView/>
  },
  {
    path:'/credits',
    element:<Credits/>
  }
])

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <RouterProvider router={router}/>
  </StrictMode>,
)
