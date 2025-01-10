import UserDetailsForm from '../../forms/UserDetailForm';
import { 
    Container,
    Typography,
 } from '@mui/material';
import React,{ useState, useEffect } from "react";
import useAuth from "../../hooks/useAuth"
import { getUserDetailsByUsername,updateUserDetails } from "../../services/apiUserService";


const UserDetailsView=()=>{
    //Getting access to he global auth object to get logging state
    const{auth}= useAuth();

    //Load logged in user details
    const [userData, setUserData] = useState({});
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        // Fetch user details if there is token is saved
        if (auth.success) {
            setIsLoading(true);
            getUserDetailsByUsername({params:{username:auth.username}}).then(response=>{
                setUserData(response.data)  
            }).finally(()=>{
                setIsLoading(false)
            });
        }
        else{
            throw console.error("No user is logged in");       
        }
    }, []);

    const handleUpdatedDataSubmit = (data) => {
            updateUserDetails(data,{params:{username: auth.username}})
            .then((respose)=>{
                const {success,message}=respose
                alert(message);
            }
            
            )
     };
     
 

   
    return(
       <Container>
            <Typography sx={{pt:2}}>
                WELCOME 
            </Typography>

            {/* Render the user details form */}
            {!isLoading &&
            <UserDetailsForm onSubmit={handleUpdatedDataSubmit} loggedUserDetails={userData} />
            }           
       </Container>
    )
    
}

export default UserDetailsView;
