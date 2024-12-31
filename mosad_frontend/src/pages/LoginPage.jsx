import { CheckBox, Label, LockOutlined } from "@mui/icons-material";
import { Avatar, Box, Button,Grid2, Container, FormControlLabel, Input, Paper, TextField, Typography } from "@mui/material";
import { useState, useEffect } from "react";
import { useNavigate, useLocation } from 'react-router-dom';
import { loginRequest } from "../services/apiUserService";


const LoginPage = ({ setIsLoggedIn, isLoggedIn }) => {
    const navigate = useNavigate();
    const location = useLocation();

    // Check if user is already logged in and try to navigate to home
    useEffect(() => {
        if (isLoggedIn && location.pathname === '/') {
            navigate('/home');
        }
    }, [isLoggedIn, location.pathname]);


    //Request data inital state
    const initalLoginState = {
        username: '',
        password: ''
    }
    let [loginData, setLoginData] = useState(initalLoginState)

    const handleOnChange = (e) => {
        let name = e.target.name;
        let value = e.target.value;
        setLoginData(values => ({ ...values, [name]: value }))
    }
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await loginRequest(loginData);
            const { success, message } = response.data;
            localStorage.setItem('token', message);
            setIsLoggedIn(true);
            navigate('/home', { replace: true }); // Used replace to prevent back navigation
        }
        catch (err) {

        }
    }
    return (
        <Container maxWidth="xs">
            <Paper elevation={10} sx={{ m: 1, p: 2 ,height:"85vh" }} >
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
                                        name="password"
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
                                        control={<CheckBox value="remembered" color="primary" />}
                                        label="Remember me"
                                    />
                                </Grid2>
                                <Grid2 size={{xs:6}}>
                                    <Typography>
                                        Forgot password
                                    </Typography>
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
        </Container>

    )

}

export default LoginPage;