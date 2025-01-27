import React from 'react'
import { Grid2,Typography,TextField,Button} from '@mui/material'
import AddIcon from '@mui/icons-material/Add';
import DeleteIcon from '@mui/icons-material/Delete';
import PropTypes from 'prop-types';

export default function BranchDetailForm({handleSubmit,branchDetails,setBranchDetails,editMode,contactNum,setContactNum}) {
    const handleBranchDtoDetails=(event)=>{
        const {name,value}=event.target;
        setBranchDetails({
            ...branchDetails,
            branchDto:{
                ...branchDetails.branchDto,
                [name]:value
            }
        })
    }
    const handleBranchContactNumberChange=(event)=>{
        setContactNum({...contactNum,[event.target.name]:event.target.value})
    }
    const addNewContact=(event)=>{
        // Remove the default empty contact if it exists
        const updatedContacts = branchDetails.branchContactDTOList.filter(item => item.contactNumber !== "");
        updatedContacts.push(contactNum); 
        if(contactNum){
            setBranchDetails({
                ...branchDetails,
                branchContactDTOList: updatedContacts
            });
        }
    }

    const removeNumber = (index) => {
        if (window.confirm(`Do you want to delete ${branchDetails.branchContactDTOList[index].contactNumber}?`)) { 
            const updatedContactList = [...branchDetails.branchContactDTOList]; 
            updatedContactList.splice(index, 1); 
            setBranchDetails({ 
                ...branchDetails, 
                branchContactDTOList: updatedContactList 
            });
            setContactNum({contactNumber:""})
        }
    };
  
    return (
    <form onSubmit={handleSubmit}>
        <Grid2 container spacing={2}>
            <Grid2 size={{xs:12 ,md:6}}>
                <Typography  component="label" htmlFor="Branch_name" >
                    Branch Name:
                </Typography>
                <TextField
                size='small'
                id="Branch_name"
                name="branchName"
                variant="outlined"
                disabled={!editMode}
                fullWidth
                value={branchDetails.branchDto.branchName}
                onChange={handleBranchDtoDetails}
                sx={{
                    "& .MuiInputBase-input.Mui-disabled": {
                      WebkitTextFillColor: "#616161",
                  },
                }} 
                />
            </Grid2>
            <Grid2 size={{xs:6}}>
                <Typography  component="label" htmlFor="address" >
                Address:
                </Typography>
                <TextField
                size='small'
                id="address"
                name="addressNumber"
                variant="outlined"
                disabled={!editMode}
                fullWidth
                value={branchDetails.branchDto.addressNumber}
                onChange={handleBranchDtoDetails}
                sx={{
                    "& .MuiInputBase-input.Mui-disabled": {
                      WebkitTextFillColor: "#616161",
                  },
                }} 
                />
            </Grid2>
            <Grid2 size={{xs:6}}>
                <Typography  component="label" htmlFor="streetName" >
                Street name:
                </Typography>
                <TextField
                size='small'
                id="streetName"
                name="streetName"
                variant="outlined"
                disabled={!editMode}
                fullWidth
                value={branchDetails.branchDto.streetName}
                onChange={handleBranchDtoDetails}
                sx={{
                    "& .MuiInputBase-input.Mui-disabled": {
                      WebkitTextFillColor: "#616161",
                  },
                }} 
                />
            </Grid2>
            <Grid2 size={{xs:6}}>
                <Typography  component="label" htmlFor="city" >
                City:
                </Typography>
                <TextField
                size='small'
                id="city"
                name="city"
                variant="outlined"
                disabled={!editMode}
                fullWidth
                value={branchDetails.branchDto.city}
                onChange={handleBranchDtoDetails}
                sx={{
                    "& .MuiInputBase-input.Mui-disabled": {
                      WebkitTextFillColor: "#616161",
                  },
                }} 
                />
            </Grid2>

            <Grid2 size={{xs:8 }}>
                <Typography  component="label" htmlFor="Contact_number" >
                Contact Number:
                </Typography>
                <TextField
                size='small'
                id="Contact_number"
                name="contactNumber"
                variant="outlined"
                disabled={!editMode}
                fullWidth
                type='tel'
                value={contactNum.contactNumber}
                onChange={handleBranchContactNumberChange}
                sx={{
                    "& .MuiInputBase-input.Mui-disabled": {
                      WebkitTextFillColor: "#616161",
                  },
                }} 
                />
            </Grid2>
            <Grid2 size={{ xs: 4}} alignContent={"end"}>
                <Button disabled={!editMode} variant="contained" startIcon={<AddIcon />} onClick={addNewContact}>
                    Add
                </Button>
            </Grid2>
            <Grid2 size={{ xs: "auto" }}>
            {branchDetails.branchContactDTOList.map((item, index) => (
                item.contactNumber !== ""  &&
                    <Button key={index} 
                            disabled={!editMode}
                            sx={{ textAlign: "center", mr: 2 }} 
                            variant="contained" 
                            endIcon={<DeleteIcon />} 
                            size='small'
                            onClick={()=>removeNumber(index)}>
                        {item.contactNumber}
                    </Button>
                    
            ))}
            </Grid2>
        </Grid2>
    </form>
  );
};

BranchDetailForm.propTypes = {
    handleSubmit:PropTypes.func.isRequired,
    branchDetails:PropTypes.shape({
        branchDto:PropTypes.shape({
            branchName:PropTypes.string,
            addressNumber:PropTypes.string,
            streetName:PropTypes.string,
            city:PropTypes.string
        }),
        branchContactDTOList:PropTypes.arrayOf([
            PropTypes.shape({
                contactNumber:PropTypes.string
            })
        ])
    }).isRequired,
    setBranchDetails:PropTypes.func.isRequired,
    editMode:PropTypes.oneOfType([PropTypes.bool,PropTypes.func]),
    contactNum:PropTypes.shape({
        contactNumber:PropTypes.string
    }).isRequired,
    setContactNum:PropTypes.func.isRequired
};
