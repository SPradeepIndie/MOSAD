import {useContext } from "react";
import selectionContext from "../context/GlobalAccessProvider";

const useGlobalAccess = () => {
  return useContext(selectionContext);
};

export default useGlobalAccess;
