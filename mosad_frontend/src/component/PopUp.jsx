import { Dialog, DialogContent, DialogTitle, Typography ,Button,Grid2, Box} from "@mui/material";
import React from "react";
import CloseIcon from '@mui/icons-material/Close';

export default function PopUp({title,children,openPopup,setOpenPopup}){
    return(
        <Dialog open={openPopup} maxWidth="md">
            <DialogTitle sx={{p:2}}>
                <Box display='flex'>
                <Typography variant="h5" component="div" flexGrow={1} sx={{pt:1,pl:3}}>
                    {title}
                </Typography>
                <Button onClick={()=>setOpenPopup(false)}>
                    <CloseIcon/>
                </Button>
                </Box>  
            </DialogTitle>
            <DialogContent dividers>
                {children}
                <Grid2 container spacing={2} justifyContent="end">
                <Grid2 size={{xs:"auto"}}>
                    <Button  variant="contained" color="primary" onClick={()=>setOpenPopup(false)}>
                        Ok
                    </Button>
                </Grid2>
                <Grid2 size={{xs:"auto"}}>
                    <Button variant="outlined" onClick={()=>setOpenPopup(false)}>
                        Cancel
                    </Button>
                </Grid2>
            </Grid2>
            </DialogContent>
        </Dialog>
    );
}