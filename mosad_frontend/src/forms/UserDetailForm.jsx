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
    FormHelperText,
    InputAdornment,
    Divider,
 } from '@mui/material';
import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import AddIcon from '@mui/icons-material/Add';
import { blue } from '@mui/material/colors';
import PropTypes from "prop-types";
import useAuth from "../hooks/useAuth";
import VisibilityIcon from '@mui/icons-material/Visibility';
import DeleteIcon from '@mui/icons-material/Delete';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import { useFetchAllBranchNames } from "../hooks/servicesHook/useBranchService";


const initialContactNumError = {
    contactNumError: ''
};

// Contact number validation
const isValidContactNum = (contact) => {
    const contactRegex = /^\d{10}$/; // Example: 10-digit number
    return contactRegex.test(contact);
};

export default function UserDetailsForm(
    {onSubmit,userUpdateData,editMode,setUserUpdateData,handlePwds,pwds,error,setError}
){
    const fetchAllBranchNames=useFetchAllBranchNames();
    const [allBranchNames,setAllBranchNames]=useState([]);
    const [isLoadingBranchNames, setIsLoadingBranchNames] = useState(false);
    const [contactNum,setContactNum]=useState({contactNum:""});
    const [contactNumErrors,setContactNumErrors]=useState(initialContactNumError);
    const {auth} = useAuth();
    let location = useLocation();

    //Password hide/show
    const [type, setType] = useState('password');
    const [icon, setIcon] = useState(<VisibilityOffIcon />);

    const handlePasswordToggle = () => {
        if (type === 'password') {
            setIcon(<VisibilityIcon />);
            setType('text')
        } else {
            setIcon(<VisibilityOffIcon />)
            setType('password')
        }
    }

    const loadAllBranches=()=>{
        setIsLoadingBranchNames(true)
        fetchAllBranchNames().then((response)=>{
            setAllBranchNames(response.data)
        }).finally(()=>{
            setIsLoadingBranchNames(false);
        }
        )
    }

    useEffect(()=>{
        loadAllBranches();
    },[])

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
    const handleBranchNameChange = (event) => {
        const { name, value } = event.target;
        setUserUpdateData({
            ...userUpdateData, [name]: value 
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

    const handleUserContactNumChange=(event)=>{
        setContactNum({...contactNum,[event.target.name]:event.target.value})
    }
    const addNewContact=(event)=>{
        if(contactNum.contactNum===""){
            setContactNumErrors({contactNumError:"Contact number is empty"})
            return
        }
        setUserUpdateData({
            ...userUpdateData,
            userContactDto: [
                ...userUpdateData.userContactDto,contactNum]})
        setContactNum({contactNum:""})
        setContactNumErrors({contactNumError:""})
    }
    const removeNumber = (index) => {
        if (window.confirm(`Do you want to delete ${userUpdateData.userContactDto[index].contactNumber}?`)) { 
            const updatedContactList = [...userUpdateData.userContactDto]; 
            updatedContactList.splice(index, 1); 
            setUserUpdateData({ 
                ...userUpdateData, 
                userContactDto: updatedContactList 
            });
            setContactNum({contactNumber:""})
        }
    };

    
    return(
        <form onSubmit={onSubmit}>
            <Typography variant="h5">User Details: </Typography>
            {/* User details view */}
            <Paper elevation={1} sx={{p:2,m:2}} >  
            <Grid container spacing={2} direction={'column'} sx={{mb:2}}>
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
                        error={!!error.firstNameError}
                        helperText={error.firstNameError}
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
                        label="Last name" 
                        variant="standard" 
                        name="lastName" 
                        value={userUpdateData.userDto.lastName || ''}
                        error={!!error.lastNameError}
                        helperText={error.lastNameError} 
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
                            error={!!error.usernameError}
                            helperText={error.usernameError}
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
                        required
                        variant="standard" 
                        name="email" 
                        value={userUpdateData.userDto.email || ''}
                        error={!!error.emailError}
                        helperText={error.emailError} 
                        onChange={handleUserDtoChange} 
                        fullWidth
                        sx={{
                            "& .MuiInputBase-input.Mui-disabled": {
                              WebkitTextFillColor: "#616161",
                          },
                        }} 
                    />
                    </Grid>
                      
            {/* User contact section */}
                    <Grid size={{ xs: 10,sm:6}}>
                        <TextField
                        type="tel"
                        disabled={!editMode} 
                        label="User contact" 
                        variant="standard" 
                        name="contactNum" 
                        value={contactNum.contactNum|| ''} 
                        onChange={handleUserContactNumChange} 
                        error={!!contactNumErrors.contactNumError}
                        helperText={contactNumErrors.contactNumError}
                        fullWidth
                        sx={{
                            "& .MuiInputBase-input.Mui-disabled": {
                              WebkitTextFillColor: "#616161",
                          },
                        }} 
                        />
                    </Grid>
                    <Grid size={{ xs: 4}} alignContent={"end"}>
                        <Button disabled={!editMode} variant="contained" startIcon={<AddIcon />} onClick={addNewContact}>
                            Add
                        </Button>
                    </Grid>
                    <Grid size={{ xs: "auto" }}>
                    {userUpdateData.userContactDto.map((item, index) => (
                        item.contactNum !== ""  &&
                            <Button key={"ContactNumberCard"+index} 
                                    disabled={!editMode}
                                    sx={{ textAlign: "center", mr: 2 }} 
                                    variant="contained" 
                                    endIcon={<DeleteIcon />} 
                                    size='small'
                                    onClick={()=>removeNumber(index)}>
                                {item.contactNum}
                            </Button>
                            
                    ))}
                    </Grid>
                </Grid>
            </Grid>
            </Paper>

            <Divider variant="middle" sx={{mt:5}}>
                Select your Role and branch
            </Divider>

            <Typography variant="caption">*Only administraion can change this </Typography>
            {/* User role and branch section */}
            <Paper elevation={1} sx={{p:2,m:2}}>
                <Grid container direction="row" sx={{justifyContent: "space-between",alignItems: "center",}} >
                <Grid size={{ xs: 12, sm: 5 }}>
                <Typography>
                {!(editMode && auth.roles.includes("ADMIN"))? "Choose your user role:":"Your role:"}
                </Typography>
                <FormControl fullWidth>
                    <InputLabel id="role-label">Role</InputLabel>
                    <Select
                        disabled={!(editMode && auth.roles.includes("ADMIN"))} 
                        required
                        name="roleName"
                        labelId="role-label" 
                        id="role" 
                        value={userUpdateData.userRoleDto.roleName} 
                        onChange={handleUserRoleDtoChange} 
                        error={!!error.roleNameError} 
                        label="Role"
                        sx={{
                            "& .MuiInputBase-input.Mui-disabled": {
                              WebkitTextFillColor: "#616161",
                          },
                        }} 
                    >
                    {auth.roles.includes("ADMIN")&& <MenuItem value="ADMIN">Admin</MenuItem>}
                    {auth.roles.includes("ADMIN")&& <MenuItem value="OWNER">Owner</MenuItem>}
                    <MenuItem value="STOCK_MANAGER">Stock Manager</MenuItem>
                    <MenuItem value="BRANCH_MANAGER">Branch Manager</MenuItem>
                    <MenuItem value="RETAIL_CUSTOMER">Retail Customer</MenuItem>
                    <MenuItem value="MECHANIC">Mechanic</MenuItem>
                    </Select>
                    <FormHelperText>{error.roleNameError}</FormHelperText>
                </FormControl>
                </Grid>
                <Grid size={{ xs: 12,sm:5 }}>
                    <Typography>
                    {!(editMode && auth.roles.includes("ADMIN"))? "Choose the branch:":"Your branch:"}
                    </Typography>
                    <FormControl fullWidth>
                        <InputLabel id="branch-label">Branch</InputLabel>
                        <Select
                            disabled={!(editMode && auth.roles.includes("ADMIN"))} 
                            required
                            name="branchName"
                            labelId="branch-label" 
                            id="branch" 
                            value={userUpdateData.branchName || ''} 
                            onChange={handleBranchNameChange}
                            error={!!error.branchNameError} 
                            label="Branch"
                            sx={{
                                "& .MuiInputBase-input.Mui-disabled": {
                                WebkitTextFillColor: "#616161",
                            },
                            }} 
                        >
                            {!isLoadingBranchNames &&
                                allBranchNames.map((branch,index)=>(
                                    <MenuItem key={"branch"+index} value={branch}>{branch}</MenuItem>
                            ))
                            }
                        
                        </Select>
                        <FormHelperText>{error.branchNameError}</FormHelperText>
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
                    type={type}
                    label="Password" 
                    variant="standard" 
                    name="pwd_1" 
                    value={pwds.pwd_1} 
                    onChange={handlePwds} 
                    error={!!error.pwd_1Error}
                    helperText={error.pwd_1Error}
                    fullWidth
                    slotProps={{
                        input: {
                            endAdornment:
                                <InputAdornment position="end">
                                    <IconButton
                                        aria-label="Toggle the password"
                                        onClick={handlePasswordToggle}
                                    >
                                        {icon}
                                    </IconButton>
                                </InputAdornment>
                        },
                    }} 
                />
                </Grid>
                <Grid size={{ xs: 12, sm: 6 }}>
                <TextField 
                    required
                    type={type}
                    label="Re-enter Password" 
                    variant="standard" 
                    name="pwd_2" 
                    value={pwds.pwd_2} 
                    error={!!error.pwd_2Error}
                    helperText={error.pwd_2Error}
                    onChange={handlePwds} 
                    fullWidth 
                    slotProps={{
                    input: {
                        endAdornment:
                            <InputAdornment position="end">
                                <IconButton
                                    aria-label="Toggle the password"
                                    onClick={handlePasswordToggle}
                                >
                                    {icon}
                                </IconButton>
                            </InputAdornment>
                    },
                }}
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
        })),
        branchName:PropTypes.string
    }),
    editMode:PropTypes.bool.isRequired,
    setUserUpdateData:PropTypes.func,
    handlePwds:PropTypes.func,
    pwds:PropTypes.shape({
        pwd_1:PropTypes.string,
        pwd_2:PropTypes.string
    }),
    error:PropTypes.shape({
        firstNameError: PropTypes.string,
        lastNameError: PropTypes.string,
        usernameError: PropTypes.string,
        emailError: PropTypes.string,
        roleNameError: PropTypes.string,
        pwd_1Error: PropTypes.string,
        pwd_2Error: PropTypes.string,
    }),
    setError:PropTypes.func
}