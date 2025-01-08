import React, { useState,useEffect } from 'react';
import { Box, Button, Paper, TextField, Typography,Stack,Grid2 } from '@mui/material';
import { styled } from '@mui/material/styles';


const initialBranchDetails={
    branchName:"",
    contactNumber:"",
    address:""
}

const BranchPage=()=>{
    const userRole="admin";
    const [allBranchName,setAllBranchName]=useState([]);

    //load the all branch names at the rendering
    useEffect(() => {
        setAllBranchName(["branch1","branch2"])
    }, []);
    /*
    * Don't use dependency as 'allBranchName' in useEffect
    * Bevuase it tendss to create a infinite loop and freeze the whole process 
    */

    //load a branch detials accrdgin to the branch name
    

    const BranchPaper = styled(Paper)(({ theme }) => ({
        width: '10rem',
        height: '5rem',
        padding: theme.spacing(2),
        ...theme.typography.body2,
        textAlign: 'center',
        '&:hover': {
            backgroundColor: 'rgba(46, 139, 88, 0.7)',
        },
        cursor: 'pointer',
    }));

    const[branchDetails,setBranchDetails]=useState(initialBranchDetails);
    
    const handleBranchDetails=(event)=>{
        const {name,value}=event.target;
        setBranchDetails({...branchDetails,[name]:[value]})
    }
    
    const handleSubmit = (event) => {
        event.preventDefault();
        // Handle form submission logic here
        console.log(branchDetails);
        
    };

    return(
        <>
       { userRole==="admin" && <Stack direction="row" spacing={{ xs: 1, sm: 2, md: 5 }} sx={{
                justifyContent: "center",
                alignItems: "center",
                p:3,
                borderBottom:3,
                borderColor:'rgba(46, 139, 88, 1)'
            }}>
            {allBranchName.map((item,index)=>(
                <BranchPaper  key={index} variant="elevation">{item}</BranchPaper>
            ))}
        </Stack>}

        <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center',mt:2}}>
        <Typography variant="h5" component="h2" gutterBottom>
            Branch Details
        </Typography>
        <form onSubmit={handleSubmit}>
            <Grid2 container spacing={2}>
            <Grid2 size={{xs:12 ,md:6}}>
                <Typography  component="label" htmlFor="Branch_name" >
                    Branch Name:
                </Typography>
                <TextField
                id="Branch_name"
                name="branchName"
                label="Branch Name"
                variant="outlined"
                fullWidth
                value={branchDetails.branchName}
                onChange={handleBranchDetails}
                />
            </Grid2>
            <Grid2 size={{xs:12, md:6}}>
                <Typography  component="label" htmlFor="Contact_number" >
                Contact Number:
                </Typography>
                <TextField
                id="Contact_number"
                name="contactNumber"
                label="Contact Number"
                variant="outlined"
                fullWidth
                value={branchDetails.contactNumber}
                onChange={handleBranchDetails}
                />
            </Grid2>
            <Grid2 size={{xs:6}}>
                <Typography  component="label" htmlFor="address" >
                Address:
                </Typography>
                <TextField
                id="address"
                name="address"
                label="Address"
                variant="outlined"
                fullWidth
                multiline
                rows={4}
                value={branchDetails.address}
                onChange={handleBranchDetails}
                />
            </Grid2>
            <Grid2 size={{xs:12}}>
                <Stack direction="row" spacing={2}>
                    <Button variant="contained" color="primary" type="submit">
                    Update
                    </Button>
                    <Button variant="contained" color="error">
                    Delete
                    </Button>
                    {userRole==="admin" && <Button variant="contained">Add new branch</Button>}
                </Stack>
                
            </Grid2>
            </Grid2>
        </form>
        </Box>
    </>
    );
}


export default BranchPage;