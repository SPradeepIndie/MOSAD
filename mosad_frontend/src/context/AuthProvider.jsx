import {createContext,useState,useEffect} from "react";
import PropTypes from "prop-types";

const AuthContext=createContext({});

export const AuthProvider = ({ children }) => {
  //Check stored auth object is in the local storage
  const [auth, setAuth] = useState(() => {
    let storedAuth = localStorage.getItem('auth'); 
    return storedAuth?JSON.parse(storedAuth):{}; 
  });
  
  useEffect(() => {
    localStorage.setItem('auth', JSON.stringify(auth))
  }, [auth]); 

  return (
    <AuthContext.Provider value={{setAuth,auth}}>
      {children}
    </AuthContext.Provider>
  );
};

//prop validation
AuthProvider.prototype={
  children:PropTypes.element.isRequired
}

export default AuthContext;
