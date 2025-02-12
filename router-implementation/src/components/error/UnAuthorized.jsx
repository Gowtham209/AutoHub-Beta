import React from 'react'
import { Link,NavLink } from "react-router";
import './UnAuthorized.css';

function UnAuthorized() {
  return (
    <div className='un-authorized'>
        <div className="content">
            <h1>UnAuthorized</h1>
            <div className="button">
                <Link to='/login' >Login</Link>
            </div>
        </div>
        </div>
  )
}

export default UnAuthorized