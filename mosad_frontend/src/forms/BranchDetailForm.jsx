import React,{useState} from 'react'
import { Grid2,Typography,TextField,IconButton,Paper,Button } from '@mui/material'
import AddIcon from '@mui/icons-material/Add';
import { blue } from '@mui/material/colors';

export default function BranchDetailForm({handleSubmit,branchDetails,setBranchDetails}) {
    const handleBranchDtoDetails=(event)=>{
        const {name,value}=event.target;
        setBranchDetails({
            ...branchDetails,
            branchDto:{
                ...branchDetails.branchDto,
                [name]:[value]
            }
        })
    }

    const [contactNumber,setcontactNumber]=useState({contactNumber:""});
    const handleBranchContactNumberChange=(event)=>{
        setcontactNumber({...contactNumber,[event.target.name]:event.target.value})
    }
    const addNewContact=(event)=>{
        if(contactNumber.contactNumber!="")
        setBranchDetails({
            ...branchDetails,
            branchContactDTOList: [
                ...branchDetails.branchContactDTOList,contactNumber
        ]})
    }
  
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
                fullWidth
                value={branchDetails.branchDto.branchName}
                onChange={handleBranchDtoDetails}
                />
            </Grid2>
          
            <Grid2 size={{xs:6}}>
                <Typography  component="label" htmlFor="address" >
                Address:
                </Typography>
                <TextField
                size='small'
                id="address"
                name="address"
                variant="outlined"
                fullWidth
                value={branchDetails.branchDto.addressNumber}
                onChange={handleBranchDtoDetails}
                />
            </Grid2>
            <Grid2 size={{xs:6}}>
                <Typography  component="label" htmlFor="address-line2" >
                Address line 2:
                </Typography>
                <TextField
                size='small'
                id="address-line2"
                name="address-line2"
                variant="outlined"
                fullWidth
                value={branchDetails.branchDto.streetName}
                onChange={handleBranchDtoDetails}
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
                fullWidth
                value={branchDetails.branchDto.city}
                onChange={handleBranchDtoDetails}
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
                fullWidth
                type='tel'
                value={contactNumber.contactNumber}
                onChange={handleBranchContactNumberChange}
                />
            </Grid2>
            <Grid2 size={{ xs: 4}} alignContent={"end"}>
                <Button variant="contained" startIcon={<AddIcon />} onClick={addNewContact}>
                    Click here to add
                </Button>
            </Grid2>
            <Grid2 size={{ xs: "auto" }}>
            {branchDetails.branchContactDTOList.map((item, index) => (
                item.contactNumber === "" ? (
                    <Paper key={index} sx={{ backgroundColor: blue[100], textAlign: "center" }} component={Button}>
                        No saved contact numbers
                    </Paper>
                    ) : (
                    <Paper key={index} sx={{ backgroundColor: blue[100], textAlign: "center", p: 1, mr: 2 }} component={Button}>
                        {item.contactNumber}
                    </Paper>
                    )
            ))}
            </Grid2>
        </Grid2>
    </form>
  )
}
