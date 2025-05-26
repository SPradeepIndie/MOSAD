import { LockOutlined } from "@mui/icons-material";
import {
    Avatar,
    Button, Grid2,
    Container,
    FormControlLabel,
    Input, Paper, TextField, Typography, IconButton, InputAdornment
} from "@mui/material";
import Checkbox from '@mui/material/Checkbox';
import { useState, useRef } from "react";
import { useNavigate } from 'react-router-dom';
import useAuth from "../hooks/useAuth"
import PopUp from "../component/PopUp";
import ForgotPasswordForm from "../forms/ForgotPasswordForm";
import { useLogin } from "../hooks/servicesHook/useApiUserService";
import Cookies from "universal-cookie";
import { jwtDecode } from "jwt-decode";
import VisibilityIcon from '@mui/icons-material/Visibility';
import VisibilityOffIcon from '@mui/icons-material/VisibilityOff';
import GeneralSnackbarAlerts from "../component/GeneralSnackbarAlerts";
import { fadeInAndUpAnimation } from "../utils/generalAnimation";


const LoginPage = () => {
    const { auth, setAuth } = useAuth();
    const loginRequest = useLogin();
    const [rememberMe, setRememberMe] = useState(auth.remember_me || true);
    const [openForgotPasswordPopup, setOpenForgotPasswordPopup] = useState(false);
    const navigate = useNavigate();
    const cookies = new Cookies();
    //Show alerts using snack bar
    const [showSnack, setShowSnack] = useState(false);
    const [alertType, setAlertType] = useState("warning");
    const [alertMsg, setAlertMsg] = useState("");

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

    const forgotPasswordFormRef = useRef();

    const handleCancelButtonAction = () => {
        if (forgotPasswordFormRef.current?.resetForm) {
            forgotPasswordFormRef.current.resetForm();
        }
        setOpenForgotPasswordPopup(false);
    };

    //Initial Error states
    const initialErrors = {
        usernameError: '',
        passwordError: ''
    }

    //Request data inital state
    const initalLoginState = {
        username: cookies.get("remember_me"),
        password: ''
    }
    let [loginData, setLoginData] = useState(initalLoginState)
    let [errors, setErrors] = useState(initialErrors);

    const formValidation = () => {
        let isValid = true;
        let newErrors = { ...initialErrors };
        if (!loginData.username) {
            newErrors.usernameError = "Username is required"
            setShowSnack(true);
            setAlertMsg(newErrors.usernameError)
            isValid = false;
        }
        if (!loginData.password) {
            newErrors.passwordError = "Password is required"
            setShowSnack(true);
            setAlertMsg(newErrors.passwordError)
            isValid = false;
        }
        setErrors(newErrors);
        return isValid

    }

    const handleOnChange = (e) => {
        const { name, value, type, checked } = e.target;
        if (type == "text" || type == "password") {
            setLoginData(values => ({ ...values, [name]: value }))
        }
        if (type === 'checkbox') {
            setRememberMe(checked);
        }

    }
    const handleSubmit = async (e) => {
        e.preventDefault();
        if (formValidation()) {
            try {
                const response = await loginRequest(loginData);
                const { Authenticated, access_token, } = response.data;
                if (!Authenticated) {
                    setShowSnack(true);
                    setAlertMsg(response.data);
                    setAlertType("warning");
                }
                const decodedToken = jwtDecode(access_token)
                setAuth({
                    accessToken: access_token,
                    Authenticated,
                    username: loginData.username,
                    roles: [decodedToken.role],
                    branch: decodedToken.branchID || '',
                    remember_me: rememberMe
                })
                if (rememberMe) {
                    cookies.set("remember_me", loginData.username, { path: '/', maxAge: 60 * 60 * 24 * 7 })
                }
                navigate('/home', { replace: true }); // Used replace to prevent back navigation
                setErrors(initialErrors)
            } catch (error) {
                setShowSnack(true);
                setAlertMsg(error.response?.data || error.message || 'Login failed.')
            }
        }
    }

    return (
        <Container maxWidth="xs" sx={{ p: 2 }}>
            <GeneralSnackbarAlerts open={showSnack} type={alertType} msg={alertMsg} setOpen={setShowSnack}/>
            <Paper elevation={10} sx={{
                animation: `${fadeInAndUpAnimation} 0.7s ease-out`,
                m: 1, p: 2, height: "auto"
            }} >
                <Grid2 container spacing={2} sx={{ textAlign: "center", mt: 1 }}>
                    <Grid2 size={{ xs: 12 }} sx={{ display: "flex", justifyContent: "center", textAlign: "center" }}>
                        <Avatar
                            sx={{
                                bgcolor: "secondary.main",
                                textAlign: "center",
                            }}
                        >
                            <LockOutlined />
                        </Avatar>
                    </Grid2>
                    <Grid2 size={{ xs: 12 }}>
                        <Typography component="h1" variant="h5">
                            Sign In
                        </Typography>
                    </Grid2>
                    <Grid2 size={{ xs: 12 }}>
                        <form onSubmit={handleSubmit} noValidate mt={1}>
                            <Grid2 container spacing={1} justifyContent="center" alignItems="center">
                                <Grid2 size={{ xs: 12 }} textAlign={"left"}>
                                    <Typography>
                                        Enter your username:
                                    </Typography>
                                </Grid2>
                                <Grid2 size={{ xs: 12 }}>
                                    <TextField
                                        name="username"
                                        placeholder="Username"
                                        error={!!errors.usernameError}
                                        helperText={errors.usernameError}
                                        fullWidth
                                        required
                                        autoFocus
                                        value={loginData.username || ""}
                                        onChange={handleOnChange}
                                        sx={{ mb: 2 }}
                                    />
                                </Grid2>
                                <Grid2 size={{ xs: 12 }} textAlign={"left"}>
                                    <Typography>
                                        Enter your password:
                                    </Typography>
                                </Grid2>
                                <Grid2 size={{ xs: 12 }} mb={1}>
                                    <TextField
                                        type={type}
                                        name="password"
                                        error={!!errors.passwordError}
                                        helperText={errors.passwordError}
                                        placeholder="Password"
                                        fullWidth
                                        required
                                        value={loginData.password || ""}
                                        onChange={handleOnChange}
                                        autoFocus
                                        sx={{ mb: 2 }}
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
                                </Grid2>
                                <Grid2 size={{ xs: 6 }}>
                                    <FormControlLabel
                                        control={<Checkbox
                                            checked={rememberMe}
                                            onChange={handleOnChange}
                                            name="rememberMe"
                                            color="primary" />}
                                        label="Remember me"
                                    />
                                </Grid2>
                                <Grid2 size={{ xs: 6 }}>
                                    <Button variant="text" size="small"
                                        onClick={(e) => (setOpenForgotPasswordPopup(true))}>
                                        Forgot passowrd
                                    </Button>
                                </Grid2>
                                <Grid2 size={{ xs: 12 }} >
                                    <Button
                                        component={Input}
                                        type="submit"
                                        variant="contained"
                                        fullWidth
                                        sx={{ mt: 2 }}
                                    >
                                        Log in
                                    </Button>
                                </Grid2>

                            </Grid2>
                        </form>

                    </Grid2>
                </Grid2>
            </Paper>
            <PopUp
                popUpTitle="Forgot Password"
                openPopup={openForgotPasswordPopup}
                setOpenPopup={setOpenForgotPasswordPopup}
                setCancelButtonAction={handleCancelButtonAction}
                isDefaultButtonsDisplay={false}
                width="md">
                <ForgotPasswordForm />
            </PopUp>
        </Container>
    )
}
export default LoginPage;