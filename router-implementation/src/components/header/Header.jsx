import React from 'react'
import { Link,NavLink } from "react-router";
import './Header.css';
function Header() {
  return (
    <nav className='nav-bar'>
      
     <Link to='/' className='logo'>AutoHub</Link>

      <div className="search-bar">
      <input type="text" placeholder='Search' />
      </div>
  
      <div className='login-sign-div'>
      <NavLink to='/login' className={({ isActive, isPending }) => ` ${isActive ? "visible-off":""}`
  } >Login</NavLink>
  
      <NavLink to='/signup'  className={({ isActive, isPending }) => ` ${isActive ? "visible-off":""}`
  } >SignUp</NavLink>
      </div>
    </nav>
  )
}

export default Header