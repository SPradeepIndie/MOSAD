import useAuth from "../hooks/useAuth";

const loadDashboard=()=>{
  let {auth}=useAuth();
    let loggedUserRole=auth.roles[0];
    switch(loggedUserRole){
      case "ADMIN":
      case  "OWNER":
        return "/dashboard";
      case "BRANCH_MANAGER":
        return "/branch";
      case "MECHANIC":
        return "/employee";
      case "RETAIL_CUSTOMER":
        return "/retail"
      case "STOCK_MANAGER":
        return "/stock/category"
    }
   
}

export default loadDashboard;