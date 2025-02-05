import UserDetailsForm from '../../forms/UserDetailForm';
import Grid from "@mui/material/Grid2";
import { 
    Container,
    Typography,
    Button
 } from '@mui/material';
import React,{ useState, useEffect } from "react";
import useAuth from "../../hooks/useAuth"
import { getUserDetailsByUsername,updateUserDetails } from "../../services/apiUserService";

const initialUserData={
    userDto:{
        username:"",
        firstName:"",
        lastName:"",
        email:""
    },
    userRoleDto:{
        roleName:""
    },
    userContactDto:[{
        contactNum:""
    }]
}


const UserDetailsView=()=>{
    //Getting access to he global auth object to get logging state
    const{auth}= useAuth();

    //control data loading asynchronus nature 
    const [isLoading, setIsLoading] = useState(false);

    //Handle editing
    const [editMode, setEditMode] = useState(false);

    //Load logged in user details
    const [userData, setUserData] = useState(initialUserData);
   
    const loadData=()=>{
        getUserDetailsByUsername({params:{username:auth.username}}).then(response=>{
            setUserData(response.data)
        }).finally(()=>{
            setIsLoading(false)
        });
    }
    
    // Fetch user details if there is token is saved
    useEffect(() => {
        if (auth.Authenticated) {
            setIsLoading(true);
            loadData();
        }
        else{
            throw console.error("No user is logged in");       
        }
    }, []);

     // replace null properties with empty strings
     const replaceNullWithEmptyString = (Dto) => {
        for (const key in Dto) {
            if (Dto[key] === null) {
                Dto[key] = ""; // Replace null with empty string
            }
        }
        return Dto;
    };


    const handleUpdatedDataSubmit = (event) => {
        event.preventDefault();
        replaceNullWithEmptyString(userData.userDto)
        replaceNullWithEmptyString(userData.userContactDto[0])

        //If user input is null transform into emty string
        if (editMode) {
            updateUserDetails(userData,{params:{username: auth.username}})
                .then((response)=>{
                    const {message}=response.data
                    alert(message);
            })
           
        } else{
            alert("Please be on edit mode. before submit")
        }
        
    };

    const handleEditMode=(event)=>{
        editMode ? setEditMode(false):setEditMode(true);
        if(editMode){
            loadData();
        }
    }

 

    return(
       <Container>
            <Typography sx={{pt:2}}>
                WELCOME 
            </Typography>

            {/* Render the user details form */}
            {!isLoading &&
            <UserDetailsForm onSubmit={handleUpdatedDataSubmit} userUpdateData={userData} editMode={editMode} setUserUpdateData={setUserData}/>
            }         

             {/* form handling buttons */}
            <Grid container spacing={2} justifyContent="end">
                <Grid size={{xs:"auto"}}>
                <Button type="submit" variant="contained" color="primary" onClick={handleUpdatedDataSubmit} >
                    Save
                </Button>
                </Grid>
                <Grid size={{xs:"auto"}}>
                <Button type="submit" variant="contained" color="primary" onClick={handleEditMode}>
                    {editMode? 'Reset': 'Edit'}
                </Button>
                </Grid>
                
                <Grid size={{xs:"auto"}}>
                </Grid>
            </Grid>  
       </Container>
    )
    
}

export default UserDetailsView;
