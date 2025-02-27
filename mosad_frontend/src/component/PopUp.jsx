import { Dialog, DialogContent, DialogTitle, Typography, Button, Grid2 as Grid, Box } from "@mui/material";
import React from "react";
import CloseIcon from '@mui/icons-material/Close';
import PropTypes from "prop-types";

export default function PopUp({ popUpTitle, children, openPopup, setOpenPopup, setOkButtonAction, setCancelButtonAction, isDefaultButtonsDisplay }) {
  return (
    <Dialog open={openPopup} maxWidth="lg" fullWidth>
      <DialogTitle sx={{ p: 2 }}>
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
            <Grid item>
              <Button variant="contained" color="primary" onClick={setOkButtonAction}>
                Ok
              </Button>
            </Grid>
            <Grid item>
              <Button variant="outlined" onClick={setCancelButtonAction}>
                Cancel
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
  setOpenPopup: PropTypes.func.isRequired,
  setOkButtonAction: PropTypes.func,
  setCancelButtonAction: PropTypes.func.isRequired,
  isDefaultButtonsDisplay: PropTypes.bool
};
