import { useEffect, useState } from "react";
import PopUp from '../../component/PopUp'
import { Container, Grid2, Button, Paper } from "@mui/material";
import UserDetailsForm from "../../forms/UserDetailForm";
import { DataGrid } from '@mui/x-data-grid';
import { useGetAllUsername, useRegister } from '../../hooks/servicesHook/useApiUserService'
import { initialErrors, validateFullForm } from '../../utils/validateUserDetailsForm'; // Import the utility function
import GeneralSnackbarAlerts from "../../component/GeneralSnackbarAlerts";

const initialUserRegData = {
    userDto: {
        username: "",
        firstName: "",
        lastName: "",
        email: ""
    },
    password: "",
    userRoleDto: {
        roleName: ""
    },
    userContactDto: [{
        contactNum: ""
    }],
    branchName:""
}

const initialPwds = {
    pwd_1: "",
    pwd_2: ""
}

const columns = [
    { field: 'id', headerName: '#', width: 20 },
    { field: 'username', headerName: 'Username', width: 150 },
    { field: 'firstName', headerName: 'First name', width: 150 },
    { field: 'lastName', headerName: 'Last name', width: 150 },
    { field: 'email', headerName: 'Email', type: 'email', width: 250, },
    { field: 'role', headerName: 'Role', width: 250, },
];

const AllUsersView = () => {
    const [users, setUsers] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    //Show alerts using snack bar
    const [showSnack, setShowSnack] = useState(false);
    const [alertType, setAlertType] = useState("warning");
    const [alertMsg, setAlertMsg] = useState("");
    
    const getAllUsername = useGetAllUsername();
    const loadAllUsers = () => {
        setIsLoading(true)
        getAllUsername().then((response) => {
            setUsers(response.data);
        }).finally(() => {
            setIsLoading(false);
        })
    }

    useEffect(() => {
        loadAllUsers();
    }, []);

    const rows = users.map((item, index) => ({
        id: index,
        username: item.userDto.username,
        firstName: item.userDto.firstName,
        lastName: item.userDto.lastName,
        email: item.userDto.email,
        role: item.userRoleDto.roleName
    }));

    const [openPopup, setOpenPopup] = useState(false);
    const setOkButtonAction = (event) => {
        handleSubmit(event)
    }
    const setCancelButtonAction = () => {
        resetTheForm()
        setOpenPopup(false)
    }

    const [userRegData, setUserRegData] = useState(initialUserRegData);
    const [errors, setErrors] = useState(initialErrors);
    const registerUser = useRegister();

    const [pwds, setPwds] = useState(initialPwds);

    const handleSubmit = async (event) => {
        event.preventDefault();
        const { isValid, newErrors } = validateFullForm(userRegData, pwds);
        if (!isValid) {
            setErrors(newErrors);
            return;
        }
        userRegData.password = pwds.pwd_1; // Update password
        if (window.confirm('Are you sure you want to submit the form?')) {
            try {
                const response = await registerUser(userRegData);
                alert('Registration successful!');

                resetTheForm(); // Reset the form after successful submission
                setOpenPopup(false)

                // Update table data after successful registration
                loadAllUsers(); // Fetch updated user data
            } catch (error) {
                setAlertMsg(error.response?.data || error.message || 'user registration failed.')
                setShowSnack(true)
            }
        }
        setErrors(initialErrors);
    };

    const resetTheForm = () => {
        setUserRegData(initialUserRegData);
        setErrors(initialErrors);
        setPwds(initialPwds)
    }

    const handlePwds = (event) => {
        const { name, value } = event.target;
        setPwds({ ...pwds, [name]: value });
    };

    return (
        <Container sx={{ pt: 2 }} disableGutters>
            <GeneralSnackbarAlerts open={showSnack} type={alertType} msg={alertMsg} setOpen={setShowSnack}/>
            <Paper sx={{ height: "auto", width: '100%' }}>
                {!isLoading &&
                    <DataGrid
                        rows={rows}
                        columns={columns}
                        initialState={{ pagination: { page: 0, pageSize: 5 } }}
                        pageSizeOptions={[5, 10, 25, 50, 100]}
                        checkboxSelection
                                    
                        sx={{
                            '& .MuiDataGrid-row.Mui-selected': {
                            backgroundColor: '#a0d8a0', // Selected row color
                            '&:hover': {
                                backgroundColor: '#a0d8af', // A different hover color for better visibility
                            },
                            },
                            border: 0,
                        }}
                    />
                }
            </Paper>
            <Grid2 container spacing={2} justifyContent="end" sx={{ my: 2 }}>
                <Grid2 item xs="auto">
                    <Button variant="contained" color="primary" onClick={() => setOpenPopup(true)}>
                        Add User
                    </Button>
                </Grid2>
            </Grid2>

            <PopUp
                popUpTitle={"Add New User"}
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
                    error={errors}
                    setError={setErrors} />
            </PopUp>
        </Container>
    );
}

export default AllUsersView;
