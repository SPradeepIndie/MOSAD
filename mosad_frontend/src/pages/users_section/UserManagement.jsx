import React from 'react';
import UserDetailsForm from '../../forms/UserDetailForm';
import {Container, Paper, Typography} from '@mui/material';

const UserManagement=()=>{
    return(
       <Container>
            <Typography>
                WELCOME 
            </Typography>
            <UserDetailsForm/>
       </Container>
    )
    
}

export default UserManagement;
