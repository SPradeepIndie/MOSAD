import UserDetailsForm from '../../forms/UserDetailForm';
import Grid from "@mui/material/Grid2";
import { 
    Container,
    Typography,
    Button
 } from '@mui/material';
import React,{ useState, useEffect } from "react";
import useAuth from "../../hooks/useAuth"
import {useGetUserDetailsByUsername,useUpdateUserDetails} from '../../hooks/servicesHook/useApiUserService';

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
const initialErrors = {
    firstNameError: '',
    lastNameError: '',
    usernameError: '',
    emailError: '',
    contactNumError: '',
    roleNameError: '',
  };

const validateEmail = (email) => {
    // More robust email validation regex
    const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return regex.test(email);
};

const validateContactNum = (contactNum) => {
    // Basic phone number validation regex (numbers and optional hyphens/spaces)
    const regex = /^[+]?[(]?[0-9]{3}[)]?[-\s.]?[0-9]{3}[-\s.]?[0-9]{4,6}$/im;
    return regex.test(contactNum);
}

const UserDetailsView=()=>{
    const [errors,setErrors]=useState(initialErrors);
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

    const validateForm = () => {
        let newErrors = { ...initialErrors };
        let isValid = true;
    
        if (!userUpdateData.userDto.firstName) {
          newErrors.firstName = 'First name is required';
          isValid = false;
        } else if (userUpdateData.userDto.firstName.length < 2) {
            newErrors.firstName = 'First name must be at least 2 characters';
            isValid = false;
        }
    
        if (!userUpdateData.userDto.lastName) {
          newErrors.lastName = 'Last name is required';
          isValid = false;
        } else if (userUpdateData.userDto.lastName.length < 2) {
            newErrors.lastName = 'Last name must be at least 2 characters';
            isValid = false;
        }
    
        if (!userUpdateData.userDto.username) {
          newErrors.username = 'Username is required';
          isValid = false;
        } else if (userUpdateData.userDto.username.length < 4) {
            newErrors.username = 'Username must be at least 4 characters';
            isValid = false;
        }
    
        if (!userUpdateData.userDto.email) {
          newErrors.email = 'Email is required';
          isValid = false;
        } else if (!validateEmail(userUpdateData.userDto.email)) {
          newErrors.email = 'Invalid email format';
          isValid = false;
        }
    
        if (userUpdateData.userContactDto.length > 0) {
            userUpdateData.userContactDto.forEach((contact, index) => {
                if (contact.contactNum && !validateContactNum(contact.contactNum)) {
                    newErrors.contactNum = `Invalid contact number format at index ${index + 1}`;
                    isValid = false;
                }
            });
        }
    
    
        if (!userUpdateData.userRoleDto.roleName) {
          newErrors.roleName = 'User role is required';
          isValid = false;
        }
    
         //Only validate passwords if we are on the correct page
         if (location.pathname === '/user/view-all') {
          if (!pwds.pwd_1) {
            newErrors.pwd_1 = 'Password is required';
            isValid = false;
          } else if (pwds.pwd_1.length < 8) {
              newErrors.pwd_1 = 'Password must be at least 8 characters';
              isValid = false;
          }
    
          if (!pwds.pwd_2) {
            newErrors.pwd_2 = 'Re-enter password is required';
            isValid = false;
          }  else if (pwds.pwd_2.length < 8) {
              newErrors.pwd_2 = 'Password must be at least 8 characters';
              isValid = false;
          }
    
          if (pwds.pwd_1 !== pwds.pwd_2) {
            newErrors.pwd_2 = 'Passwords do not match';
            isValid = false;
          }
        }
    
        setErrors(newErrors);
        return isValid;
    };
 
    return(
       <Container>
            <Typography sx={{pt:2}}>
                WELCOME 
            </Typography>

            {/* Render the user details form */}
            {!isLoading &&
            <UserDetailsForm 
            onSubmit={handleUpdatedDataSubmit} 
            userUpdateData={userData} 
            editMode={editMode}
            setUserUpdateData={setUserData}
            errors={errors}
            setErrors={setErrors}/>
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
