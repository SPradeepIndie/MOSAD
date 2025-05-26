import React,{Suspense}  from "react";
import { Navigate, Outlet } from "react-router-dom";
import useAuth from "./hooks/useAuth"
import Loading from "./component/Loading"

const RoutesProtector = () => {
  const{ auth }= useAuth()
  if (!auth.Authenticated ) 
    return <Navigate to="/login" />;
  return (
    <Suspense fallback={<Loading WhatsLoading={'Please wait'} />}>
      <Outlet />
    </Suspense>
    
  );
};

export default RoutesProtector;