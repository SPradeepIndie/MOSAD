import React from 'react'
import useAuth from './hooks/useAuth';
import { Navigate, Outlet } from 'react-router-dom';
import PropTypes from 'prop-types';

export default function CheckPrivileges({allowedRoles}) {
    const{ auth }= useAuth()
    return (
        auth?.roles?.find(role=>allowedRoles?.includes(role)) ? <Outlet />
            :<Navigate to='/unathorized' replace/>
      
    );
}

CheckPrivileges.propTypes={
    allowedRoles:PropTypes.arrayOf(PropTypes.string)
}