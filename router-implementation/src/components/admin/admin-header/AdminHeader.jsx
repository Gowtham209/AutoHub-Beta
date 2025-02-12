import React from 'react'
import { Link,NavLink } from "react-router";
import './AdminHeader.css'
function AdminHeader() {
  return (
    <nav className='admin-header'>
         <Link to='dash-board' className='logo'>AutoHub</Link>

         <NavLink to='profile'  className={({ isActive, isPending }) => `profile ${isActive ? "":""}`
  } >Admin</NavLink>
    </nav>
  )
}

export default AdminHeader