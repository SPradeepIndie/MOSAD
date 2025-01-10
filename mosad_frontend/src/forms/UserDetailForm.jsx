 import Grid from "@mui/material/Grid2";
 import { 
    Typography,
    TextField, 
    FormControl, 
    InputLabel, 
    Select, 
    MenuItem, 
    Paper,
    Button
 } from '@mui/material';
import { useState } from "react";
import { useLocation } from "react-router-dom";


const initialUserDto={
    username:"",
    firstName:"",
    lastName:"",
    email:""
}

const initialPassword={
    password:""
}
const initialPwds={
    pwd_1:"",
    pwd_2:""
}
const initialUserRoleDto={
    roleName:""
}

const initialUserContactDto=[{
    contactNum:""
}]


export default function UserDetailsForm({onSubmit,loggedUserDetails}){
    let location = useLocation()

    //Handle editing
    const [editMode, setEditMode] = useState(false);

    const[pwds,setPwds]=useState(initialPwds)
    const [password, setPassword] = useState(initialPassword); 
    const [userDto, setUserDto] = useState(loggedUserDetails?.userDto || initialUserDto); 
    const [userRoleDto, setUserRoleDto] = useState(loggedUserDetails?.userRoleDto || initialUserRoleDto);
    const [userContactDto, setUserContactDto] = useState(loggedUserDetails?.userContactDto || initialUserContactDto);

    //Form user inputs  handling
    const handleUserDtoChange = (event) => {
        const { name, value } = event.target;
        setUserDto({ ...userDto, [name]: value });
    };
    const handleUserRoleDtoChange = (event) => {
        const { name, value } = event.target;
        setUserRoleDto({ ...userRoleDto, [name]: value });
    };
    const handlePasswordChange = (event) => {
        setPassword(event.target.value); 
    };
    const handleUserContactDtoChange = (event) => {
        const { name, value, index } = event.target; 
        const updatedContact = [...userContactDto]; 
        updatedContact[index] = { ...updatedContact[index], [name]: value }; 
        setUserContactDto(updatedContact);
        
    };

    //user paswords  handling
    const handlePwds = (event) => {
        const { name, value } = event.target;
        setPwds({ [name]: value });
        pwds.pwd_2===pwds.pwd_1 ? setPassword(pwds.pwd_1) :alert("Password doesn't match");
    };

    // Function to replace null properties with empty strings
    const replaceNullWithEmptyString = (Dto) => {
        for (const key in Dto) {
            if (Dto[key] === null) {
                Dto[key] = ""; // Replace null with empty string
            }
        }
        return Dto;
    };

    
    const dataSubmitToParent=(event)=>{
        // Update the initialUserDto
        const updatedUserDto = replaceNullWithEmptyString(userDto);
        const updatedUserContactDto = replaceNullWithEmptyString(userContactDto[0]);
        console.log(userContactDto[0])
        event.preventDefault();
        if (editMode) {
            onSubmit({
                userDto: updatedUserDto,
                userRoleDto: userRoleDto,
                userContactDto: updatedUserContactDto
            });
            setEditMode(false);
         } else {
            setEditMode(true);
         }
        
           
    }
    const reset=(event)=>{
        setUserDto(loggedUserDetails?.userDto || initialUserDto)
        setUserContactDto(loggedUserDetails?.userContactDto || initialUserContactDto)
        setUserRoleDto(loggedUserDetails?.userRoleDto || initialUserRoleDto)
        setEditMode(false)
    }

    return(
        <form onSubmit={dataSubmitToParent} >
            {/* User details view */}
            <Paper elevation={1} sx={{p:2,m:2}}>
                <Grid container spacing={2} >
                    <Grid size={{ xs: 12, sm: 6 }}>
                        <TextField
                        required 
                        disabled={!editMode}
                        label="First name" 
                        variant="standard" 
                        name="firstName" 
                        value={userDto.firstName || ''} 
                        onChange={handleUserDtoChange} 
                        fullWidth
                        sx={{
                            "& .MuiInputBase-input.Mui-disabled": {
                              WebkitTextFillColor: "#000000",
                          },
                        }} 
                        />
                    </Grid>
                    <Grid size={{ xs: 12, sm: 6 }}>
                        <TextField
                        disabled={!editMode} 
                        label="Last name" 
                        variant="standard" 
                        name="lastName" 
                        value={userDto.lastName || ''} 
                        onChange={handleUserDtoChange} 
                        fullWidth
                        sx={{
                            "& .MuiInputBase-input.Mui-disabled": {
                              WebkitTextFillColor: "#000000",
                          },
                        }}
                        />
                    </Grid>
                    
                    <Grid size={{ xs: 12, sm: 6 }}>
                        <TextField
                            disabled={!editMode} 
                            required
                            label="Username" 
                            variant="standard" 
                            name="username" 
                            value={userDto.username || ''} 
                            onChange={handleUserDtoChange} 
                            fullWidth 
                            sx={{
                                "& .MuiInputBase-input.Mui-disabled": {
                                  WebkitTextFillColor: "#000000",
                              },
                            }}
                        />
                    </Grid>
                    
                    <Grid size={{ xs: 12, sm: 6 }}>
                        <TextField
                        disabled={!editMode} 
                        label="Email" 
                        variant="standard" 
                        name="email" 
                        value={userDto.email || ''} 
                        onChange={handleUserDtoChange} 
                        fullWidth
                        sx={{
                            "& .MuiInputBase-input.Mui-disabled": {
                              WebkitTextFillColor: "#000000",
                          },
                        }} 
                    />
                    </Grid>
                    <Grid size={{ xs: 12, sm: 6 }}>
                        <TextField
                        disabled={!editMode} 
                        label="User contact" 
                        variant="standard" 
                        name="contactNum" 
                        value={userContactDto[0]?.contactNum || ''} 
                        onChange={handleUserContactDtoChange} 
                        fullWidth
                        sx={{
                            "& .MuiInputBase-input.Mui-disabled": {
                              WebkitTextFillColor: "#000000",
                          },
                        }} 
                        />
                    </Grid>
                </Grid>
            </Paper>

            {/* User role section */}
            <Paper elevation={1} sx={{p:2,m:2}}>
                <Grid container spacing={2} >
                <Grid size={{ xs: 12, sm: 6 }}>
                <Typography>
                     Choose your user role:
                </Typography>
                <FormControl fullWidth>
                    <InputLabel id="role-label">Role</InputLabel>
                    <Select
                        disabled={!editMode} 
                        required
                        name="roleName"
                        labelId="role-label" 
                        id="role" 
                        value={userRoleDto.roleName} 
                        onChange={handleUserRoleDtoChange} 
                        label="Role"
                        sx={{
                            "& .MuiInputBase-input.Mui-disabled": {
                              WebkitTextFillColor: "#000000",
                          },
                        }} 
                    >
                    <MenuItem value="ADMIN">Admin</MenuItem>
                    <MenuItem value="OWNER">User</MenuItem>
                    <MenuItem value="STOCK_MANAGER">Stock Manager</MenuItem>
                    <MenuItem value="RETAIL_CUSTOMER">Retail Customer</MenuItem>
                    <MenuItem value="MECHANIC">Mechanic</MenuItem>
                    </Select>
                </FormControl>
                </Grid>
                </Grid>
            </Paper>

            {/* User password */}
            {location.pathname === '/user/view-all' &&
                <Paper elevation={1} sx={{p:2,m:2}}>
                <Grid container spacing={2} >
                <Grid size={{ xs: 12, sm: 6 }}>
                    <TextField
                        required
                        type='password'
                        label="Password" 
                        variant="standard" 
                        name="pwd_1" 
                        value={pwds.pwd_1} 
                        onChange={handlePwds} 
                        fullWidth 
                    />
                    </Grid>
                    <Grid size={{ xs: 12, sm: 6 }}>
                    <TextField 
                        required
                        type='password'
                        label="Re-enter Password" 
                        variant="standard" 
                        name="pwd_2" 
                        value={pwds.pwd_2} 
                        onChange={handlePwds} 
                        fullWidth 
                    />
                    </Grid>
                    </Grid>
                </Paper>
            }

            {/* form handling buttons */}
            {location.pathname === '/user' &&
            <Grid container spacing={2} justifyContent="end">
                <Grid size={{xs:"auto"}}>
                <Button type="submit" variant="contained" color="primary" onClick={dataSubmitToParent}>
                    {editMode ? 'Save' : 'Edit'}
                </Button>
                </Grid>
                <Grid size={{xs:"auto"}}>
                {editMode && (
                    <Button variant="outlined" onClick={reset}>
                        Cancel
                    </Button>
                )}
                </Grid>
            </Grid>
            }
        </form>
    );
}
