import React,{Suspense}  from "react";
import { Navigate, Outlet } from "react-router-dom";
import useAuth from "./hooks/useAuth"

const RoutesProtector = () => {
  const{ auth }= useAuth()
  if (!auth.Authenticated) 
    return <Navigate to="/login" />;
  return (
    <Suspense fallback={<h1>Loading...</h1>}>
      <Outlet />
    </Suspense>
    
  );
};

export default RoutesProtector;