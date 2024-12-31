 import Grid from "@mui/material/Grid2";
 import { 
   TextField, 
   Button, 
   FormControl, 
   InputLabel, 
   Select, 
   MenuItem, 
   Box,
   Paper
 } from '@mui/material';
import React,{ useState, useEffect } from "react";

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
        contactNum:"0112536722"
    }]
}
// const useStyle=makeStyles(theme=>({
//     root:{

//     }
// }))

const UserDetailsForm=()=>{    
    //use sate for add and view user
    const [userData, setUserData] = useState(initialUser);
   //const classes=useStyle()

    //Use states for editing
    const [editMode, setEditMode] = useState(false);
    const [selectedUserId, setSelectedUserId] = useState(null);
    
    const handleChange = (event) => {
        const { name, value } = event.target;
        setUserData({ ...userData, [name]: value });
    };
    
    const handleSubmit = (event) => {
        event.preventDefault();
        // Handle form submission (add or update user)
        if (editMode) {
        // Update user logic (e.g., API call)
        } else {
        // Add user logic (e.g., API call)
        }
    };
    
    const handleEdit = (userId) => {
        // Fetch user details by userId (e.g., API call)
        // Set editMode to true
        // Set selectedUserId
    };
    
    const handleCancel = () => {
        setEditMode(false);
    };
    
    useEffect(() => {
        // Fetch user details if there is token is saved
        if (true) {
        
        }
    }, []);

    return(
        <form onSubmit={handleSubmit} >
            <Paper elevation={1} sx={{p:2,m:2}}>
                <Grid container spacing={2} >
                    <Grid size={{ xs: 12, sm: 6 }}>
                        <TextField 
                        label="First name" 
                        variant="outlined" 
                        name="firstname" 
                        value={userData.userDto.firstName} 
                        onChange={handleChange} 
                        fullWidth 
                        />
                    </Grid>
                    <Grid size={{ xs: 12, sm: 6 }}>
                        <TextField 
                        label="Last name" 
                        variant="outlined" 
                        name="lastname" 
                        value={userData.userDto.lastName} 
                        onChange={handleChange} 
                        fullWidth 
                        />
                    </Grid>
                    
                    <Grid size={{ xs: 12, sm: 6 }}>
                        <TextField 
                            label="Username" 
                            variant="outlined" 
                            name="username" 
                            value={userData.userDto.username} 
                            onChange={handleChange} 
                            fullWidth 
                        />
                    </Grid>
                    
                    <Grid size={{ xs: 12, sm: 6 }}>
                    <TextField 
                        label="Email" 
                        variant="outlined" 
                        name="email" 
                        value={userData.userDto.email} 
                        onChange={handleChange} 
                        fullWidth 
                    />
                    </Grid>
                    <Grid size={{ xs: 12, sm: 6 }}>
                     {/* User contact */}
                        <TextField 
                        type='password'
                        label="User contact" 
                        variant="outlined" 
                        name="contactNum" 
                        value={userData.password} 
                        onChange={handleChange} 
                        fullWidth 
                        />
                    </Grid>
                </Grid>
            </Paper>

            <Paper elevation={1} sx={{p:2,m:2}}>
                <Grid container spacing={2} >
                <Grid size={{ xs: 12, sm: 6 }}>
                {/* Example of a Select field for role */}
                <FormControl fullWidth>
                    <InputLabel id="role-label">Role</InputLabel>
                    <Select 
                    labelId="role-label" 
                    id="role" 
                    value={userData.userRoleDto.roleName} 
                    onChange={handleChange} 
                    label="Role"
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

            <Paper elevation={1} sx={{p:2,m:2}}>
            <Grid container spacing={2} >
            <Grid size={{ xs: 12, sm: 6 }}>
                {/* Enter password */}
                <TextField 
                    type='password'
                    label="Password" 
                    variant="outlined" 
                    name="password" 
                    value={userData.password} 
                    onChange={handleChange} 
                    fullWidth 
                />
                </Grid>
                <Grid size={{ xs: 12, sm: 6 }}>
                <TextField 
                    type='password'
                    label="Re-enter Password" 
                    variant="outlined" 
                    name="password" 
                    value={userData.password} 
                    onChange={handleChange} 
                    fullWidth 
                />
                 </Grid>
                 </Grid>
            </Paper>
            
            <Box sx={{p:2,m:2}} spacing={2}>
                <Button  variant="contained" color="primary">
                    Add user
                </Button>
                <Button  variant="contained" color="primary">
                    Delete
                </Button>
                <Button type="submit" variant="contained" color="primary" onClick={()=>setEditMode(true)}>
                    {editMode ? 'Save' : 'Edit'}
                </Button>
                {editMode && (
                    <Button variant="outlined" onClick={handleCancel}>
                    Cancel
                    </Button>
                )}
            </Box>
        </form>
    );
}

export default UserDetailsForm