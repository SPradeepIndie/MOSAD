import UserDetailsForm from '../../forms/UserDetailForm';
import Grid from "@mui/material/Grid2";
import { 
    Container,
    Typography,
    Button,
    Avatar,
    Paper,
    Box
 } from '@mui/material';
import React,{ useState, useEffect } from "react";
import useAuth from "../../hooks/useAuth"
import {useGetUserDetailsByUsername,useUpdateUserDetails} from '../../hooks/servicesHook/useApiUserService';
import { initialErrors, validateGeneralForm } from '../../utils/validateUserDetailsForm'; // Import the utility function
import AccountCircleIcon from '@mui/icons-material/AccountCircle';

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
    }],
    branchName:""
}


const UserDetailsView=()=>{
    //Getting access to he global auth object to get logging state
    const{auth}= useAuth();
    const getUserDetails = useGetUserDetailsByUsername();
    const updateUserDetails=useUpdateUserDetails();

    //control data loading asynchronus nature 
    const [isLoading, setIsLoading] = useState(false);

    //Handle editing
    const [editMode, setEditMode] = useState(false);

    //Load logged in user details
    const [userData, setUserData] = useState(initialUserData);
   
    const loadData=()=>{
        getUserDetails({params:{username:auth.username}}).then(response=>{
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


    const [errors, setErrors] = useState(initialErrors);
    const handleUpdatedDataSubmit =async (event) => {
        event.preventDefault();
        replaceNullWithEmptyString(userData.userDto)
        replaceNullWithEmptyString(userData.userContactDto[0])
        const { isValid, newErrors } = validateGeneralForm(userData);
        if (!isValid) {
            setErrors(newErrors);
            return;
        }

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
            setErrors(initialErrors);
        }
    }


    return(   
    <Container sx={{ py: 4 }} disableGutters>
        <Paper elevation={3} sx={{ p: 4 }}>
            <Grid container spacing={2}>
                {/* Left Side - Big Icon */}
                <Grid size={{xs:12,md:2}} container direction={"column"} sx={{
                    justifyContent: "flex-start",
                    alignItems: "center",
                    mt: 3
                }}>  
                    <Avatar
                        sx={{
                            bgcolor: "primary.main",
                            width: 120,
                            height: 120,
                        }}
                    >
                        <AccountCircleIcon sx={{ fontSize: 80 }} />
                    </Avatar>
                    <Typography variant="h6" textAlign={"center"}>
                        Welcome {userData.userDto.firstName+" "+userData.userDto.lastName}
                    </Typography>
                </Grid>

                {/* Right Side - Form */}
                <Grid size={{xs:12,md:10}}>
                    {!isLoading && (
                        <UserDetailsForm
                            onSubmit={handleUpdatedDataSubmit}
                            userUpdateData={userData}
                            editMode={editMode}
                            setUserUpdateData={setUserData}
                            error={errors}
                            setError={setErrors}
                        />
                    )}
                    {isLoading && <Typography>Loading user details...</Typography>}

                    {/* Form Handling Buttons */}
                    <Box sx={{ mt: 3, display: 'flex', justifyContent: 'flex-end', gap: 2 }}>
                        <Button
                            variant="contained"
                            color="primary"
                            onClick={handleUpdatedDataSubmit}
                            disabled={!editMode || isLoading}
                        >
                            Save
                        </Button>
                        <Button
                            variant={editMode ? "contained" : "outlined"}
                            color={editMode ? "secondary" : "primary"}
                            onClick={handleEditMode}
                            disabled={isLoading}
                        >
                            {editMode ? 'Reset' : 'Edit'}
                        </Button>
                    </Box>
                </Grid>
            </Grid>
        </Paper>
    </Container>
        
    )
    
}

export default UserDetailsView;
