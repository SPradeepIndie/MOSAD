import { Paper, Typography } from "@mui/material"
import { Link } from "react-router-dom"
import React from "react"
import PropTypes from "prop-types"

const UnderConstruction=({displayTxt})=>{
    return (
        <Paper elevation={10}>
            <Typography>
                {displayTxt}
            </Typography>
            <Link component="button" variant="contained" to="/home">Back to home pgae</Link>
        </Paper>
    )
}

UnderConstruction.prototype={
    displayTxt:PropTypes.string.isRequired
}

export default UnderConstruction;