import UserDetailsForm from '../../forms/UserDetailForm';
import Grid from "@mui/material/Grid2";
import { 
    Container,
    Typography,
    Button, 
    Paper
 } from '@mui/material';
import React,{ useState, useEffect } from "react";
import PopUp from '../../component/PopUp'
import useAuth from "../../hooks/useAuth"
import { getUserbyUsername } from "../../services/apiUserService";

const initialUser={
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


const UserManagement=()=>{
    const{auth}= useAuth();

    const [openPopup,setOpenPopup]=useState(false);
    
     //Handle form data 
    const [userData, setUserData] = useState(initialUser);
    const [userDto, setUserDto] = useState(initialUser.userDto); 
    const [userRoleDto, setUserRoleDto] = useState(initialUser.userRoleDto);
    const [userContactDto, setUserContactDto] = useState(initialUser.userContactDto);
    const [password, setPassword] = useState(initialUser.password); 

    //Form user inputs  handling
    const handleUserDtoChange = (event) => {
        const { name, value } = event.target;
        setUserDto({ ...userDto, [name]: value });
        setUserData({ ...userData, userDto: { ...userDto, [name]: value } }); 
    };

    const handleUserRoleDtoChange = (event) => {
        const { name, value } = event.target;
        setUserRoleDto({ ...userRoleDto, [name]: value });
        setUserData({ ...userData, userRoleDto: { ...userRoleDto, [name]: value } }); 
    };

    const handleUserContactDtoChange = (event) => {
        // Assuming you have a way to identify which contact to update (e.g., index)
        const { name, value, index } = event.target; 
        const updatedContact = [...userContactDto]; 
        updatedContact[index] = { ...updatedContact[index], [name]: value }; 
        setUserContactDto(updatedContact);
        setUserData({ ...userData, userContactDto: updatedContact }); 
    };

    const handlePasswordChange = (event) => {
        setPassword(event.target.value); 
        setUserData({ ...userData, password: event.target.value }); 
    };

    
    useEffect(() => {
            // Fetch user details if there is token is saved
            if (auth.success) {
                const response = getUserbyUsername(auth.username);
                console.log(response?.data);
            }
    }, []);
    return(
       <Container>
            <Typography>
                WELCOME 
            </Typography>
            <UserDetailsForm 
                userData={userData} 
                handleUserDtoChange={handleUserDtoChange} 
                handleUserContactDtoChange={handleUserContactDtoChange}
                handleUserRoleDtoChange={handleUserRoleDtoChange}
                handlePasswordChange={handlePasswordChange}
                handleSubmit={handleSubmit}/>
            <Paper elevation={1} sx={{p:1,m:1}}>
                <Grid container spacing={2} justifyContent="end">
                    <Grid size={{xs:"auto"}}>
                        <Button  variant="contained" color="primary" onClick={()=>setOpenPopup(true)}>
                            Add user
                        </Button>
                    </Grid>
                    <Grid size={{xs:"auto"}}>
                    <Button  variant="contained" color="primary">
                        Delete
                    </Button>
                    </Grid>

                    <Button type="submit" variant="contained" color="primary" onClick={()=>setEditMode(true)}>
                        {editMode ? 'Save' : 'Edit'}
                    </Button>
                    <Grid size={{xs:"auto"}}>
                    {editMode && (
                        <Button variant="outlined" onClick={handleCancel}>
                        Cancel
                        </Button>
                    )}
                    </Grid>
                </Grid>
            </Paper>
            <PopUp 
                tite="Add new user"
                openPopup={openPopup}
                children={<UserDetailsForm 
                    userData={userData} 
                    handleUserDtoChange={handleUserDtoChange} 
                    handleUserContactDtoChange={handleUserContactDtoChange}
                    handleUserRoleDtoChange={handleUserRoleDtoChange}
                    handlePasswordChange={handlePasswordChange}
                    handleSubmit={handleSubmit}/>}
                setOpenPopup={setOpenPopup}/>
       </Container>
    )
    
}

export default UserManagement;
