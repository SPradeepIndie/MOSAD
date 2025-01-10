import { useState } from "react";
import PopUp from '../../component/PopUp'
import { Container,Grid2,Button } from "@mui/material";
import UserDetailsForm from "../../forms/UserDetailForm";

const AllUsersView=()=>{
      //use state for handle pop up open/close
      const [openPopup,setOpenPopup]=useState(false);

      const handleSubmit=()=>{

      }

    return(
        <Container>
             
            <Grid2 container spacing={2} justifyContent="end">
                <Grid2 size={{xs:"auto"}}>
                <Button variant="contained" color="primary" onClick={()=>setOpenPopup(true)}>
                    Add User
                </Button>
                </Grid2>
            </Grid2>
            
        <PopUp 
                tite="Add new user"
                openPopup={openPopup}
                children={<UserDetailsForm  handleSubmit={handleSubmit} edit={true}/>}
                setOpenPopup={setOpenPopup}/>
        </Container>
       
    );
}

export default AllUsersView;