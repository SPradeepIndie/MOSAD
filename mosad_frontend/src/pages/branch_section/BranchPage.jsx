import React, { useState,useEffect } from 'react';
import { Box, Button, Paper, Typography,Stack} from '@mui/material';
import { styled } from '@mui/material/styles';
import { fetchAllBranchNames,fetchBranchDetailsByName } from '../../services/apiBranchService';
import BranchDetailForm from '../../forms/BranchDetailForm';
import PopUp from '../../component/PopUp';

const initialBranch={
    branchDto:{
        branchName:"",
        addressNumber:"",
        streetName:"",
        city:""
    },
    branchContactDTOList:[
        {
            contactNumber:""
        }
    ]
}

const BranchPage=()=>{
    const userRole="admin";

    //Set curently selected branch
    const [selectedBranch,setselectedBranch]=useState("");

    const [allBranchNames,setAllBranchNames]=useState([]);
    const [isLoadingBranchNames, setIsLoadingBranchNames] = useState(false);

    const loadAllBranches=()=>{
        setIsLoadingBranchNames(true)
        fetchAllBranchNames().then((response)=>{
            setAllBranchNames(response.data)
        }).finally(()=>{
            setIsLoadingBranchNames(false);
        }
        )
    }

    //------------------------------------------------------------------------
    //load a branch detials according to the branch name
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

    const[branchDetails,setBranchDetails]=useState(initialBranch);
    const [isLoadingBranchDetails, setIsLoadingBranchDetails] = useState(false);

    const handleBranchCardOnClick=(branchName)=>{
        setIsLoadingBranchDetails(true)
        fetchBranchDetailsByName(branchName).then((response)=>{
            setBranchDetails(response.data);
        }).finally(()=>{
            setIsLoadingBranchDetails(false);
            setselectedBranch(branchName);
        })
    }
    
    const handleSubmit = (event) => {
        event.preventDefault();
        // Handle form submission logic here
        console.log(branchDetails);   
    };

    //--------------------------------------------------------------------
    // load the all branch names at the rendering
    /*
    * Don't use dependency as 'allBranchNames' in useEffect
    * Becuase it tendss to create a infinite loop and freeze the whole process 
    */
    useEffect(() => {
        loadAllBranches();
    }, []);

    //--------------------------------------------------------------------
    //pop up handling
    const[addBranchPopUp,setAddBranchPopUp]=useState(false)
    const setOkButtonAction=(event)=>{
        handleSubmit(event)
    }
    const setCancelButtonAction=()=>{
        //resetTheForm()
        setAddBranchPopUp(false)
    }


    return(
        <>
       { userRole==="admin" && <Stack direction="row" spacing={{ xs: 1, sm: 2, md: 5 }} sx={{
                justifyContent: "center",
                alignItems: "center",
                p:3,
                borderBottom:3,
                borderColor:'rgba(46, 139, 88, 1)'
            }}>
                {!isLoadingBranchNames &&
                    allBranchNames.map((item,index)=>(
                        <BranchPaper  key={index} variant="elevation" onClick={() => {
                            handleBranchCardOnClick(item)}
                        } >{item}</BranchPaper>
                    ))
                }
           
        </Stack>}

        <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center',mt:2}}>
            <Typography variant="h5" component="h2" gutterBottom>
                Branch Details
            </Typography>
                <Paper elevation={2} sx={{p:2}}>
                    {!isLoadingBranchDetails &&
                        <BranchDetailForm handleSubmit={handleSubmit} branchDetails={branchDetails} setBranchDetails={setBranchDetails}/>
                    }
                </Paper>
               
                <Paper elevation={1} sx={{p:1,m:2,width:'100%'}}>
                    <Stack direction="row" spacing={2} >
                        <Button variant="contained" color="primary">
                            Update
                        </Button>
                        <Button  variant="contained" color="error">
                            Delete
                        </Button>
                        {userRole==="admin" && <Button type="submit" variant="contained" onClick={()=>setAddBranchPopUp(true)}>Add new branch</Button>}
                    </Stack>  
                </Paper>
                 <PopUp 
                    popUpTitle={"Add New Branch"}
                    openPopup={addBranchPopUp}
                    isDefaultButtonsDisplay={true}
                    setOpenPopup={setAddBranchPopUp}
                    setOkButtonAction={setOkButtonAction}
                    setCancelButtonAction={setCancelButtonAction}>
                <BranchDetailForm handleSubmit={handleSubmit} branchDetails={branchDetails} setBranchDetails={setBranchDetails}/>
                </PopUp>
        </Box>
    </>
    );
}


export default BranchPage;