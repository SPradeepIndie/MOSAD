import { createContext,useState } from "react";

const selectionContext = createContext({});

export const GlobalAccessProvider = ({ children }) => {
  const [selectedCategory, setSelectedCategory] = useState("");
  const [selectedBrand, setSelectedBrand] = useState("");

  return (
    <selectionContext.Provider value={{ selectedCategory, setSelectedCategory, selectedBrand, setSelectedBrand }}>
      {children}
    </selectionContext.Provider>
  );
};

export default selectionContext;