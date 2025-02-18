import { LockOutlined } from "@mui/icons-material";
import { Avatar, Button,Grid2, Container, FormControlLabel, Input, Paper, TextField, Typography } from "@mui/material";
import Checkbox from '@mui/material/Checkbox';
import { useState, useRef } from "react";
import { useNavigate, useLocation } from 'react-router-dom';
import useAuth from "../hooks/useAuth"
import PopUp from "../component/PopUp";
import ForgotPasswordForm from "../forms/ForgotPasswordForm";
import { useLogin } from "../hooks/servicesHook/useApiUserService";
import Cookies from "universal-cookie";

const LoginPage = () => {
    const{auth,setAuth}= useAuth();
    const loginRequest=useLogin();
    const [rememberMe, setRememberMe] = useState(()=>{
        const remember_me=auth.remember_me;
        return remember_me?remember_me:true
    });
    const [openForgotPasswordPopup,setOpenForgotPasswordPopup]=useState(false);
    const navigate = useNavigate();
    const location = useLocation();
    const cookies= new Cookies();

    const forgotPasswordFormRef = useRef();

    const handleCancelButtonAction = () => {
        
        if (forgotPasswordFormRef.current?.resetForm) {
        forgotPasswordFormRef.current.resetForm();
        }
        setOpenForgotPasswordPopup(false); 
        window.location.reload(); 
    };

    //Initial Error states
    const initialErrors={
        usernameError: '',
        passwordError: ''
    }

    //Request data inital state
    const initalLoginState = {
        username: cookies.get("remember_me"),
        password: ''
    }
    let [loginData, setLoginData] = useState(initalLoginState)
    let [errors,setErrors]=useState(initialErrors);

    const formValidation=()=>{
        let isValid=true;
        let newErrors = { ...initialErrors };
        if(!loginData.username && !loginData.username.length){
            newErrors.usernameError= "Username is required"
            isValid=false;
        }
        if(!loginData.password && !loginData.password.length){
            newErrors.passwordError= "Password is required"
            isValid=false;
        }
        
        setErrors(newErrors);
        return isValid
        
    }

    const handleOnChange = (e) => {
        const { name, value, type, checked } = e.target;
        if(type=="text" || type=="password"){
            setLoginData(values => ({ ...values, [name]: value }))
        }
        if (type === 'checkbox') {
            setRememberMe(checked);
        } 
        
    }
    const handleSubmit = async (e) => {
        e.preventDefault();
        if(formValidation()){
            const response = await loginRequest(loginData);
            const { Authenticated, access_token, role,branchId } = response.data;
            setAuth({
                accessToken:access_token,
                Authenticated,
                username:loginData.username,
                roles:[role],
                branch:branchId,
                remember_me:rememberMe
            })
            if(rememberMe){
                cookies.set("remember_me",loginData.username,{path:'/',maxAge:60*60*24*7})
            }
            navigate('/home', { replace: true }); // Used replace to prevent back navigation
            setErrors(initialErrors)
        }       
    }
    return (
        <Container maxWidth="xs" sx={{p:2}}>
            <Paper elevation={10} sx={{ m: 1, p: 2 ,height:"auto" }} >
                <Grid2 container spacing={2} sx={{textAlign:"center",mt:1}}>
                    <Grid2 size={{xs:12}} sx={{display:"flex",justifyContent:"center",textAlign:"center"}}>
                        <Avatar
                            sx={{
                                bgcolor: "secondary.main",
                                textAlign: "center",
                            }}
                        >
                            <LockOutlined />
                        </Avatar>
                    </Grid2>
                    <Grid2 size={{xs:12}}>
                        <Typography component="h1" variant="h5">
                            Sign In
                        </Typography>
                    </Grid2>
                    <Grid2 size={{xs:12}}>
                        <form onSubmit={handleSubmit} noValidate  mt={1}>
                            <Grid2 container spacing={1} justifyContent="center" alignItems="center">
                                <Grid2 size={{xs:12}} textAlign={"left"}>
                                    <Typography>
                                        Enter your username:
                                    </Typography>
                                </Grid2>
                                <Grid2 size={{xs:12}}>
                                    <TextField
                                        name="username"
                                        placeholder="Username"
                                        error={errors.usernameError && errors.usernameError.length?true:false}
                                        helperText={errors.usernameError}
                                        fullWidth
                                        required
                                        autoFocus
                                        value={loginData.username || ""}
                                        onChange={handleOnChange}
                                        sx={{ mb: 2 }}
                                    />
                                </Grid2>
                                <Grid2 size={{xs:12}} textAlign={"left"}>
                                    <Typography>
                                        Enter your password:
                                    </Typography>
                                </Grid2>
                                <Grid2 size={{xs:12}} mb={1}>
                                    <TextField
                                        type="password"
                                        name="password"
                                        error={errors.passwordError && errors.passwordError.length?true:false}
                                        helperText={errors.passwordError}
                                        placeholder="Password"
                                        fullWidth
                                        required
                                        value={loginData.password || ""}
                                        onChange={handleOnChange}
                                        autoFocus
                                        sx={{ mb: 2 }}
                                    />
                                </Grid2>
                                <Grid2 size={{xs:6}}>
                                    <FormControlLabel
                                        control={<Checkbox 
                                            checked={rememberMe}
                                            onChange={handleOnChange}
                                            name="rememberMe"
                                            color="primary" />}
                                        label="Remember me"
                                    />
                                </Grid2>
                                <Grid2 size={{xs:6}}>
                                <Button variant="text" size="small" 
                                    onClick={(e)=>(setOpenForgotPasswordPopup(true))}>
                                    Forgot passowrd
                                </Button>
                                </Grid2>
                                <Grid2 size={{xs:12}} >
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
            <PopUp popUpTitle="Reset Your Password"  openPopup={openForgotPasswordPopup} setOpenPopup={setOpenForgotPasswordPopup} setCancelButtonAction={handleCancelButtonAction} isDefaultButtonsDisplay={false}>
                <ForgotPasswordForm/>
            </PopUp>
        </Container>
        
    )
}
export default LoginPage;