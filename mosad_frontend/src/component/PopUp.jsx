import { Dialog, DialogContent, DialogTitle, Typography ,Button,Paper, Box} from "@mui/material";
import React from "react";
import CloseIcon from '@mui/icons-material/Close';

export default function PopUp({tite,children,openPopup,setOpenPopup}){
    return(
        <Dialog open={openPopup} maxWidth="md">
            <DialogTitle sx={{p:2}}>
                <Box display='flex'>
                <Typography variant="h5" component="div" flexGrow={1} sx={{pt:1,pl:3}}>
                    {tite}
                </Typography>
                <Button onClick={()=>setOpenPopup(false)}>
                    <CloseIcon/>
                </Button>
                </Box>  
            </DialogTitle>
            <DialogContent dividers>
                {children}
                <Paper elevation={1} sx={{p:2,m:2}} spacing={2}>
                    <Button  variant="contained" color="primary" onClick={()=>setOpenPopup(false)}>
                        Ok
                    </Button>
                    <Button variant="outlined" onClick={()=>setOpenPopup(false)}>
                        Cancel
                    </Button>
                
                </Paper>
            </DialogContent>
        </Dialog>
    );
}