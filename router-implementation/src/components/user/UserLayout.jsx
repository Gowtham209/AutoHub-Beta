import React from 'react'
import { Outlet } from 'react-router-dom'
import UserHeader from './user-header/UserHeader'

function UserLayout() {
  return (
   <>
    <UserHeader/>    
    <Outlet/>
   </>
  )
}

export default UserLayout