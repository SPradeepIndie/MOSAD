 import Grid from "@mui/material/Grid2";
 import { 
   TextField, 
   FormControl, 
   InputLabel, 
   Select, 
   MenuItem, 
   Paper
 } from '@mui/material';
import { useState } from "react";

export default function UserDetailsForm({
    userData,
    handleUserDtoChange,
    handleUserContactDtoChange,
    handleUserRoleDtoChange,
    handlePasswordChange,
    handleSubmit
    }){
    const initialPwds={
        pwd_1:"",
        pwd_2:""
    }
        
    const[pwds,setPwds]=useState(initialPwds)
    const[pwdMatcher,setPwdMatcher]=useState(false)

    //user paswords  handling
    const handlePwds = (event) => {
        const { name, value } = event.target;
        setPwds({ [name]: value });
        pwds.pwd_2===pwds.pwd_1 ? setPwdMatcher(true) :setPwdMatcher(false);
    };


    return(
        <form onSubmit={handleSubmit} >
            <Paper elevation={1} sx={{p:2,m:2}}>
                <Grid container spacing={2} >
                    <Grid size={{ xs: 12, sm: 6 }}>
                        <TextField 
                        label="First name" 
                        variant="outlined" 
                        name="firstName" 
                        value={userData.userDto.firstName} 
                        onChange={handleUserDtoChange} 
                        fullWidth 
                        />
                    </Grid>
                    <Grid size={{ xs: 12, sm: 6 }}>
                        <TextField 
                        label="Last name" 
                        variant="outlined" 
                        name="lastName" 
                        value={userData.userDto.lastName} 
                        onChange={handleUserDtoChange} 
                        fullWidth 
                        />
                    </Grid>
                    
                    <Grid size={{ xs: 12, sm: 6 }}>
                        <TextField 
                            label="Username" 
                            variant="outlined" 
                            name="username" 
                            value={userData.userDto.username} 
                            onChange={handleUserDtoChange} 
                            fullWidth 
                        />
                    </Grid>
                    
                    <Grid size={{ xs: 12, sm: 6 }}>
                    <TextField 
                        label="Email" 
                        variant="outlined" 
                        name="email" 
                        value={userData.userDto.email} 
                        onChange={handleUserDtoChange} 
                        fullWidth 
                    />
                    </Grid>
                    <Grid size={{ xs: 12, sm: 6 }}>
                     {/* User contact */}
                        <TextField 
                        label="User contact" 
                        variant="outlined" 
                        name="contactNum" 
                        value={userData.userContactDto.contactNum} 
                        onChange={handleUserContactDtoChange} 
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
                        name="roleName"
                        labelId="role-label" 
                        id="role" 
                        value={userData.userRoleDto.roleName} 
                        onChange={handleUserRoleDtoChange} 
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
                    name="pwd_1" 
                    value={pwds.pwd_1} 
                    onChange={handlePwds} 
                    fullWidth 
                />
                </Grid>
                <Grid size={{ xs: 12, sm: 6 }}>
                <TextField 
                    type='password'
                    label="Re-enter Password" 
                    variant="outlined" 
                    name="pwd_2" 
                    value={pwds.pwd_2} 
                    onChange={handlePwds} 
                    fullWidth 
                />
                 </Grid>
                 </Grid>
            </Paper>
        </form>
    );
}
