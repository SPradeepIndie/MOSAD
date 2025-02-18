import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { BrowserRouter } from 'react-router-dom'
import {AuthProvider} from './context/AuthProvider.jsx'
import { GlobalAccessProvider } from './context/GlobalAccessProvider.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
      <AuthProvider>
      <GlobalAccessProvider>
        <App/>
      </GlobalAccessProvider>
      </AuthProvider>
    </BrowserRouter>
  </StrictMode>,
)
