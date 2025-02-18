import { useEffect, useState } from "react";
import PopUp from '../../component/PopUp'
import { Container,Grid2,Button,Paper} from "@mui/material";
import UserDetailsForm from "../../forms/UserDetailForm";
import { DataGrid } from '@mui/x-data-grid';
import {useGetAllUsername} from '../../hooks/servicesHook/useApiUserService'
import { useRegister } from "../../hooks/servicesHook/useApiUserService";

const initialErrors = {
    firstNameError: '',
    lastNameError: '',
    usernameError: '',
    emailError: '',
    contactNumError: '',
    roleNameError: '',
    pwd_1Error: '',
    pwd_2Error: '',
  };

const initialUserRegData={
    userDto:{
        username:"",
        firstName:"",
        lastName:"",
        email:""
    },
    password:"",
    userRoleDto:{
        roleName:""
    },
    userContactDto:[{
        contactNum:""
    }]
}
const initialPwds={
    pwd_1:"",
    pwd_2:""
}

const columns = [
    { field: 'id', headerName: '#', width: 20 },
    { field: 'username', headerName: 'Username', width: 100 },
    { field: 'firstName', headerName: 'First name', width: 130 },
    { field: 'lastName', headerName: 'Last name', width: 130 },
    { field: 'email',headerName: 'Email',type: 'email',width: 130,},
    { field: 'role',headerName: 'Role',width: 120,},
];

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

const AllUsersView=()=>{
    const [errors,setErrors]=useState(initialErrors);
    const [users,setUsers]=useState([]);
    const getAllUsername=useGetAllUsername();
    const registerUser = useRegister();
    
    //control data loading asynchronus nature 
    const [isLoading, setIsLoading] = useState(false);

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

    //fetch all users data
    const loadAllUsers=()=>{
        setIsLoading(true)
        getAllUsername().then((response)=>{
            setUsers(response.data);
        }).finally(()=>{
            setIsLoading(false);
        })
    }

    useEffect(()=>{
        loadAllUsers();
    },[]);

    const rows=users.map((item,index)=>({
                id: index,
                username: item.userDto.username, 
                firstName: item.userDto.firstName, 
                lastName: item.userDto.lastName, 
                email: item.userDto.email, 
                role: item.userRoleDto.roleName
    }));

   
    //use state for handle pop up open/close
    const [openPopup,setOpenPopup]=useState(false);

    const [userRegData,setUserRegData]=useState(initialUserRegData);

    const[pwds,setPwds]=useState(initialPwds);
    const handlePwds = (event) => {
            const { name, value } = event.target;
            setPwds({ ...pwds,[name]: value });
            
    };

    const setOkButtonAction=(event)=>{
        handleSubmit(event)
    }
    const setCancelButtonAction=()=>{
        resetTheForm()
        setOpenPopup(false)
    }

    const handleSubmit = async (event) => {
        event.preventDefault(); 
        if(!setPassword()){
            return
        }
        if (window.confirm('Are you sure you want to submit the form?')) {
          try {
            const response = await registerUser(userRegData);
            console.log(response.data); 
            // Handle successful registration (optional)
            console.log(userRegData)
            alert('Registration successful!');
      
            resetTheForm(); // Reset the form after successful submission
            setOpenPopup(false)
            
            // Update table data after successful registration
            loadAllUsers(); // Fetch updated user data
          } catch (error) {
            console.log(userRegData)
            console.error('Error during registration:', error);
            alert('Registration failed. Please try again.'); // Or display an error message
          }
        }
      };

    const resetTheForm=()=>{
        setUserRegData(initialUserRegData);
        setPwds(initialPwds)
    }

    const setPassword=()=>{
        // Validate password match
        if (pwds.pwd_1 !== pwds.pwd_2) {
            alert('Passwords do not match. Please re-enter your password.');
            return false; 
        }
        userRegData.password=pwds.pwd_1; // Update password
        return true;
    }

    return(
        <Container sx={{pt:2}}>
            <Paper sx={{ height: "auto", width: '100%'}}>
            
            {!isLoading &&
                <DataGrid
                    rows={rows}
                    columns={columns}
                    //defines the intial page and intial page rows count
                    initialState={{ pagination: { page: 0, pageSize: 5 } }}
                    pageSizeOptions={[5, 10, 25, 50, 100]}
                    checkboxSelection
                    sx={{ border: 0 }}
                />
            }
            </Paper>
            <Grid2 container spacing={2} justifyContent="end" sx={{my:2}}>
                <Grid2 size={{xs:"auto"}}>
                <Button variant="contained" color="primary" onClick={()=>setOpenPopup(true)}>
                    Add User
                </Button>
                </Grid2>
            </Grid2>
            
        <PopUp 
            popUpTitle={"Add new user"}
            openPopup={openPopup}
            isDefaultButtonsDisplay={true}
            setOpenPopup={setOpenPopup}
            setOkButtonAction={setOkButtonAction}
            setCancelButtonAction={setCancelButtonAction}>
        <UserDetailsForm  
        onSubmit={handleSubmit} 
        userUpdateData={userRegData} 
        editMode={true} 
        setUserUpdateData={setUserRegData}
        handlePwds={handlePwds}
        pwds={pwds}
        errors={errors}
        setErrors={setErrors}/>
        </PopUp>
        </Container>
       
    );
}

export default AllUsersView;