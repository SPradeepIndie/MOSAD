 import Grid from "@mui/material/Grid2";
 import { 
    Typography,
    TextField, 
    FormControl, 
    InputLabel, 
    Select, 
    MenuItem, 
    Paper,
    IconButton,
    Button,
   
 } from '@mui/material';
import { useState } from "react";
import { useLocation } from "react-router-dom";
import AddIcon from '@mui/icons-material/Add';
import { blue } from '@mui/material/colors';
import PropTypes from "prop-types";


export default function UserDetailsForm({onSubmit,userUpdateData,editMode,setUserUpdateData,handlePwds,pwds}){
    let location = useLocation();

    const handleUserDtoChange = (event) => {
        const { name, value } = event.target;
        setUserUpdateData({
            ...userUpdateData, 
            userDto: {
                ...userUpdateData.userDto,
                [name]: value 
            }
        });
    };

    const handleUserRoleDtoChange = (event) => {
        const { name, value } = event.target;
        setUserUpdateData({
            ...userUpdateData,
            userRoleDto: {
                ...userUpdateData.userRoleDto,
                [name]: value
            }
        });
    };

    const [contactNum,setContactNum]=useState({contactNum:""});
    const handleUserContactNumChange=(event)=>{
        setContactNum({...contactNum,[event.target.name]:event.target.value})
    }
    const addNewContact=(event)=>{
        setUserUpdateData({
            ...userUpdateData,
            userContactDto: [
                ...userUpdateData.userContactDto,contactNum]})
    }

    
    return(
        <form onSubmit={onSubmit} >
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
                        value={userUpdateData.userDto.firstName || ''} 
                        onChange={handleUserDtoChange} 
                        fullWidth
                        sx={{
                            "& .MuiInputBase-input.Mui-disabled": {
                              WebkitTextFillColor: "#616161",
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
                        value={userUpdateData.userDto.lastName || ''} 
                        onChange={handleUserDtoChange} 
                        fullWidth
                        sx={{
                            "& .MuiInputBase-input.Mui-disabled": {
                              WebkitTextFillColor: "#616161",
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
                            value={userUpdateData.userDto.username || ''} 
                            onChange={handleUserDtoChange} 
                            fullWidth 
                            sx={{
                                "& .MuiInputBase-input.Mui-disabled": {
                                  WebkitTextFillColor: "#616161",
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
                        value={userUpdateData.userDto.email || ''} 
                        onChange={handleUserDtoChange} 
                        fullWidth
                        sx={{
                            "& .MuiInputBase-input.Mui-disabled": {
                              WebkitTextFillColor: "#616161",
                          },
                        }} 
                    />
                    </Grid>
                    <Grid size={{ xs: 10,sm:6}}>
                        <TextField
                        disabled={!editMode} 
                        label="User contact" 
                        variant="standard" 
                        name="contactNum" 
                        value={contactNum.contactNum|| ''} 
                        onChange={handleUserContactNumChange} 
                        fullWidth
                        sx={{
                            "& .MuiInputBase-input.Mui-disabled": {
                              WebkitTextFillColor: "#616161",
                          },
                        }} 
                        />
                    </Grid>
                    <Grid size={{ xs: 2,sm:6}} alignContent={"end"}>
                        <IconButton disabled={!editMode} onClick={addNewContact}>
                            <AddIcon />
                        </IconButton>
                    </Grid>
                    <Grid size={{ xs: "auto" }}>
                    {userUpdateData.userContactDto.map((item, index) => (
                        item.contactNum === "" ? (
                            <Paper key={index} sx={{ backgroundColor: blue[100], textAlign: "center" }} component={Button}>
                              No saved contact numbers
                            </Paper>
                          ) : (
                            <Paper key={index} sx={{ backgroundColor: blue[100], textAlign: "center", p: 1, mr: 2 }} component={Button}>
                              {item.contactNum}
                            </Paper>
                          )
                    ))}
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
                        value={userUpdateData.userRoleDto.roleName} 
                        onChange={handleUserRoleDtoChange} 
                        label="Role"
                        sx={{
                            "& .MuiInputBase-input.Mui-disabled": {
                              WebkitTextFillColor: "#616161",
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

           
        </form>
    );
}

UserDetailsForm.propTypes={
    onSubmit:PropTypes.func.isRequired,
    userUpdateData:PropTypes.shape({
        userDto:PropTypes.shape({
            username:PropTypes.string,
            firstName:PropTypes.string,
            lastName:PropTypes.string,
            email:PropTypes.string
        }),
        userRoleDto:PropTypes.shape({
            roleName:PropTypes.string
        }),
        userContactDto:PropTypes.arrayOf(PropTypes.shape({
            contactNum:PropTypes.string
        }))
    }),
    editMode:PropTypes.bool.isRequired,
    setUserUpdateData:PropTypes.func,
    handlePwds:PropTypes.func,
    pwds:PropTypes.shape({
        pwd_1:PropTypes.string,
        pwd_2:PropTypes.string
    })

}