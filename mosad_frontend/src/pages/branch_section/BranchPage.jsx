import React, { useState,useEffect } from 'react';
import { Box, Button, Paper, Typography,Stack} from '@mui/material';
import { styled } from '@mui/material/styles';
import {
    useFetchAllBranchNames, 
    useAddBranch,
    useUdpateBranch,
    useFetchBranchDetailsByName, 
    useDeleteBranch} from '../../hooks/servicesHook/useBranchService'
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
const initialContact={
    contactNumber:""
}
const BranchPage=()=>{  
    const fetchAllBranchNames=useFetchAllBranchNames();
    const fetchBranchDetailsByName=useFetchBranchDetailsByName();
    const deleteBranch=useDeleteBranch();
    const udpateBranch=useUdpateBranch();
    const addBranch=useAddBranch();
    
    //--------------------------------Main page------------------------------------
    const userRole="admin";
    const [contactNumber, setContactNumber] = useState(initialContact);//handle contact number
    const [selectedBranch, setSelectedBranch] = useState("");//Set curently selected branch
    const [allBranchNames, setAllBranchNames] = useState([]);// load the all branch names at the rendering
    const [isLoadingBranchNames, setIsLoadingBranchNames] = useState(false);
    const [branchDetails, setBranchDetails] = useState(initialBranch);//load a branch detials according to the selcted card
    const [isLoadingBranchDetails, setIsLoadingBranchDetails] = useState(false);
    const [editMode, setEditMode]=useState(false); //Set text feilds disability
    const loadAllBranches=()=>{
        setIsLoadingBranchNames(true)
        fetchAllBranchNames().then((response)=>{
            setAllBranchNames(response.data)
        }).finally(()=>{
            setIsLoadingBranchNames(false);
        }
        )
    }
    /*
    * Don't use dependency as 'allBranchNames' in useEffect
    * Becuase it tendss to create a infinite loop and freeze the whole process 
    */
    useEffect(() => {
        loadAllBranches();
    }, []);
    
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
    const handleBranchCardOnClick=(branchName)=>{
        setEditMode(false)
        setIsLoadingBranchDetails(true)
        fetchBranchDetailsByName(branchName).then((response)=>{
            setBranchDetails(response.data);
        }).finally(()=>{
            setIsLoadingBranchDetails(false);
            setSelectedBranch(branchName);
        })
    }
    const handleDelete=()=>{
        if(selectedBranch){
            if(confirm("confirm branch deletion named: "+selectedBranch))
            deleteBranch(selectedBranch).then((response)=>{
                alert(response.data.message);
            }).finally(()=>{
                loadAllBranches();
                setBranchDetails(initialBranch);
            })
        }
        else{
            alert("Please select a branch first! ")
        }
    }
    //Handle Update
    const handleUpdatedDetailsSubmit=(event)=>{
        if(!addBranchPopUp){
            udpateBranch(branchDetails,selectedBranch).then((response)=>{
                alert(response.data.message);
            }).finally(()=>{
                setEditMode(false)
            })
        }
    }
    //Rest the BranchPage Form
    const handleEditMode=()=>{
        if(editMode){
            setEditMode(false)
            handleBranchCardOnClick(selectedBranch)
        }
        else{
            setEditMode(true)
        }
        
    }

    //--------------------------------Add new branch pop up------------------------------------
    const[addBranchPopUp,setAddBranchPopUp]=useState(false)//Adding branch pop up handling
    const[newBranchDetails,setNewBranchDetails]=useState(initialBranch);//new branch deails use State

    //Handle new branch submition
    const handleNewDetailsSubmit = (event) => {
        event.preventDefault();
        console.log(newBranchDetails);
        addBranch(newBranchDetails).then((response)=>{
            alert(response.data.message);
        }).finally(()=>{
            resetNewBranchAddingForm()
        })   
    };
    const setOkButtonAction=(event)=>{
        handleNewDetailsSubmit(event);
    };
    const setCancelButtonAction=()=>{
        resetNewBranchAddingForm()
        setAddBranchPopUp(false)
        loadAllBranches()
    };
    const resetNewBranchAddingForm=()=>{
        setNewBranchDetails(initialBranch);
        setContactNumber(initialContact);
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
                        <BranchPaper  key={"branchInfoCard"+index} variant="elevation" onClick={() => {
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
                        <BranchDetailForm 
                            handleSubmit={handleUpdatedDetailsSubmit} 
                            branchDetails={branchDetails} 
                            setBranchDetails={setBranchDetails}
                            editMode={editMode}
                            contactNum={contactNumber}
                            setContactNum={setContactNumber}
                        />
                    }
                </Paper>
               
                <Paper elevation={1} sx={{p:1,m:2,width:'100%'}}>
                    <Stack direction="row" spacing={2} >
                        {editMode && 
                        <Button variant="contained" color="primary" onClick={handleUpdatedDetailsSubmit}>
                            Save
                        </Button> }
                        <Button variant="contained" color="primary" onClick={()=>handleEditMode()}>
                            {!editMode ? "Update" : "Reset" }
                        </Button>
                        <Button  variant="contained" color="error" onClick={handleDelete}>
                            Delete
                        </Button>
                        {userRole==="admin" && <Button type="submit" variant="contained" onClick={()=>setAddBranchPopUp(true)}>
                                Add new branch
                        </Button>}
                    </Stack>  
                </Paper>
                 <PopUp 
                    popUpTitle={"Add New Branch"}
                    openPopup={addBranchPopUp}
                    isDefaultButtonsDisplay={true}
                    setOpenPopup={setAddBranchPopUp}
                    setOkButtonAction={setOkButtonAction}
                    setCancelButtonAction={setCancelButtonAction}>
                <BranchDetailForm 
                    handleSubmit={handleNewDetailsSubmit} 
                    branchDetails={newBranchDetails} 
                    setBranchDetails={setNewBranchDetails}
                    editMode={true}
                    contactNum={contactNumber}
                    setContactNum={setContactNumber}
                />
                </PopUp>
        </Box>
    </>
    );
}


export default BranchPage;