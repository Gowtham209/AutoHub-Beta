import React, { useRef } from 'react';
import { useState } from 'react';
import axios from '../utility/axios';
import  { jwtDecode } from 'jwt-decode';
import { useNavigate } from 'react-router-dom';
import { Link,NavLink } from "react-router";
import './Login.css'
function Login() {

    const [userEmail,setUserEmail]=useState('');
    const[password,setPassword]=useState('');
    const userRole=useRef("");
    const navigate = useNavigate();

    function loginProcess(e)
    {
      e.preventDefault();
        console.log(`userEmail:${userEmail}\npassword:${password}`);
        async function login()
        {
         
         try{
          //const api="/login";
          const api="/login";
          const resp=await axios.post(api,{emailId:userEmail,password:password});
          const status=resp.status;
          console.log("Status:",status);
          const data=resp.data;
          console.log('JWT:',data);

          // Decode the token
          const decodedToken = jwtDecode(data);
          const role=decodedToken.role;

          // Storing the JWT
          localStorage.setItem('jwt',data);
          
          console.log("UserId:",decodedToken.userId);
          console.log("Role:",role);

          if (role === 2) {
            navigate('/user/userhome');
          } else {
            navigate('/admin/dash-board');
          }
        }
         catch (error) {
              // The request was made and the server responded with a status code
              // that falls out of the range of 2xx
              console.log("Error Status:", error.response.status);
              console.log("Error Data:", error.response.data);
            }
        }

        login();
    }
  return (
    <>
    <div className="login-page">
    
    <form className='login-form'>
    <div>Login</div>
        <input 
        type="email" 
        placeholder='userEmail*'
        onChange={(e)=>setUserEmail(e.target.value)}
        value={userEmail}
        autoComplete='true'
        required/>
        <input 
        type="password" 
        placeholder='password*'
        onChange={(e)=>setPassword(e.target.value)}
        value={password}
        autoComplete='true'
        required/>
        <div>
            <button type='submit' onClick={loginProcess}>Login</button>
        </div>
        <span>Haven't registered? <Link to='/signup' className='register-link'>register</Link></span>
    </form>
    </div>
    </>
  )
}

export default Login