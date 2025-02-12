import React from 'react'
import './UserHeader.css'
import { Link,NavLink } from "react-router";
function UserHeader() {
  return (
    <nav className='user-header'>
         <Link to='userhome' className='logo'>AutoHub</Link>
      {/* <div className="search-bar">
      <input type="text" placeholder='Search' />
      </div> */}
          <Link to='test-drive-bookings' className='test-drive'>TestDriveBooking</Link>
          <Link to='orders' className='order'>Orders</Link>
          <Link to='profile' className='user'>User</Link>
         {/* <NavLink to='profile'  className={({ isActive, isPending }) => `profile ${isActive ? "":""}`
  } >User</NavLink> */}
    </nav>
  )
}

export default UserHeader