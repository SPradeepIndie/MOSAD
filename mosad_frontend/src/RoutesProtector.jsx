import React  from "react";
import { Navigate, Outlet } from "react-router-dom";
import useAuth from "./hooks/useAuth"

const RoutesProtector = () => {
  const{ auth }= useAuth()
  // if (true) return <Navigate to="/home" />;
  if (!auth.success) return <Navigate to="/" />;
  return <Outlet />;
};

export default RoutesProtector;