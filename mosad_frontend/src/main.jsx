import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { createBrowserRouter,RouterProvider } from 'react-router-dom'
import './index.css'
import App from './App.jsx'
import LoginPage from './pages/LoginPage.jsx'
import NotFoundPage from './pages/NotFoundPage.jsx'

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
    element:<NotFoundPage/>
  }
])

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <RouterProvider router={router}/>
  </StrictMode>,
)
