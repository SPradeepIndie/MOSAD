import { Outlet } from "react-router-dom";
import { Suspense } from "react";

const BranchStockLayout=()=>{
    return(
        <Suspense fallback={<h1>Loading...</h1>}>
            <Outlet/>
        </Suspense>
          
    );
}


export default BranchStockLayout;