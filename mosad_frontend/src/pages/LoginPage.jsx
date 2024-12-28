import { CheckBox, LockOutlined } from "@mui/icons-material";
import { Avatar, Box, Button, Container,FormControlLabel,Input,Paper, TextField, Typography } from "@mui/material";
import {useState,useEffect} from "react";
import { useNavigate, useLocation } from 'react-router-dom';
import { loginRequest } from "../services/apiUserService"; 


const LoginPage = ({ setIsLoggedIn,isLoggedIn })=>{
    const navigate = useNavigate();
    const location = useLocation(); 

     // Check if user is already logged in and try to navigate to home
     useEffect(() => {
        if (isLoggedIn && location.pathname === '/') {
            navigate('/home');
        }
    }, [isLoggedIn, location.pathname]); 


    //Request data inital state
    const initalLoginState={
        username:'',
        password:''
    }
    let [loginData, setLoginData]=useState(initalLoginState)
     
    const handleOnChange= (e)=>{
        let name = e.target.name;
        let value=e.target.value;
        setLoginData(values => ({...values, [name]: value}))
    }
    const handleSubmit=async (e)=>{
        e.preventDefault();
        try{
            const response = await loginRequest(loginData);
            const { success, message } = response.data;
            localStorage.setItem('token', message);
            setIsLoggedIn(true);
            navigate('/home', { replace: true }); // Used replace to prevent back navigation
        }
        catch (err){
            
        }   
    }
    return(
        <Container maxWidth="xs">
            <Paper elevation={10} sx={{margin:8,padding:2}}>
                <Avatar
                    sx={{
                        mx: "auto",
                        bgcolor: "secondary.main",
                        textAlign: "center",
                        mb:1
                    }}
                >
                    <LockOutlined/>
                </Avatar>
                <Typography component="h1" variant="h5">
                    Sign In
                </Typography>
                <Box component="form" onSubmit={handleSubmit} noValidate sx={{mt:1}}>
                    <TextField 
                        name="username"
                        placeholder="Enter Username"
                        fullWidth
                        required
                        autoFocus
                        value={loginData.username || ""} 
                        onChange={handleOnChange}
                        sx={{mb:2}}
                    />
                    <TextField
                        name="password" 
                        placeholder="Enter Password"
                        fullWidth
                        required
                        value={loginData.password || ""} 
                        onChange={handleOnChange}
                        autoFocus
                        sx={{mb:2}}
                    />
                    <FormControlLabel
                        control={<CheckBox value="remebered" color="primary"/>}
                        label="Remember me"
                    />
                    <Button component={Input}  type="submit" variant="contained" fullWidth sx={{mt:1}}>
                        Log in 
                        {/* to="/home" */}
                    </Button>
                </Box>
                {/* <Grid2>
                    <Grid item>
                        <Typography>
                            Remeber me
                        </Typography>
                    </Grid>
                </Grid2> */}
            </Paper>
        </Container>

    )

}

export default LoginPage;