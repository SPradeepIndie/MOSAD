import { CheckBox, LockOutlined } from "@mui/icons-material";
import { Avatar, Box, Button, Container,FormControlLabel,Paper, TextField, Typography } from "@mui/material";
import React from "react";
import { Link } from "react-router-dom";

const LoginPage = ()=>{
    const handleSubmit=()=>{console.log("logged in")}
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
                        placeholder="Enter Username"
                        fullWidth
                        required
                        autoFocus
                        sx={{mb:2}}
                    />
                    <TextField 
                        placeholder="Enter Password"
                        fullWidth
                        required
                        autoFocus
                        sx={{mb:2}}
                    />
                    <FormControlLabel
                        control={<CheckBox value="remebered" color="primary"/>}
                        label="Remember me"
                    />
                    <Button component={Link} to="/home" type="submit" variant="contained" fullWidth sx={{mt:1}}>
                        Log in
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