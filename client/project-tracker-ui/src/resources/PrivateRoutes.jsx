import React from 'react'
import { Navigate, Outlet } from 'react-router-dom'

function PrivateRoutes() {
    return  localStorage.getItem('access_token') ? <Outlet /> : <Navigate to="/" />
}
export default PrivateRoutes
