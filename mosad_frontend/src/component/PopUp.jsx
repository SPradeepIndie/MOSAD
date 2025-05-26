import { Dialog, DialogContent, DialogTitle, Typography, Button, Grid2 as Grid, Box } from "@mui/material";
import React from "react";
import CloseIcon from '@mui/icons-material/Close';
import PropTypes from "prop-types";
import {fadeIn} from "../utils/generalAnimation"

export default function PopUp(
  { 
    popUpTitle, 
    children, 
    openPopup, 
    setOpenPopup, 
    setOkButtonAction, 
    setCancelButtonAction, 
    isDefaultButtonsDisplay,
    width="lg"
  }) {
  
  return (
    <Dialog open={openPopup} maxWidth={width} sx={{animation: `${fadeIn} 0.5s ease-out`,}}>
      <DialogTitle sx={{ p: 2 ,textAlign:'center'}}>
        <Box display="flex">
          <Typography variant="h5" component="div" flexGrow={1} sx={{ pt: 1, pl: 3 }}>
            {popUpTitle}
          </Typography>
          <Button onClick={setCancelButtonAction}>
            <CloseIcon />
          </Button>
        </Box>
      </DialogTitle>
      <DialogContent dividers>
        {children}
        {isDefaultButtonsDisplay &&
          <Grid container spacing={2} justifyContent="flex-end" sx={{ mt: 2 }}>
            <Grid>
              <Button variant="outlined" onClick={setCancelButtonAction}>
                Cancel
              </Button>
            </Grid>
            <Grid>
              <Button variant="contained" color="primary" onClick={setOkButtonAction}>
                Ok
              </Button>
            </Grid>
          </Grid>
        }
      </DialogContent>
    </Dialog>
  );
}

PopUp.propTypes = {
  popUpTitle: PropTypes.string.isRequired,
  children: PropTypes.node.isRequired,
  openPopup: PropTypes.bool.isRequired,
  setOpenPopup: PropTypes.func,
  setOkButtonAction: PropTypes.func,
  setCancelButtonAction: PropTypes.func.isRequired,
  isDefaultButtonsDisplay: PropTypes.bool,
  width:PropTypes.string
};
