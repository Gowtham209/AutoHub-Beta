import React from 'react'
import { useState } from 'react';
import { useEffect } from 'react';
import axios from '../utility/axios';
import authorize from '../utility/Authorizer';
import userIdExtract from '../utility/userIdExtractor';
import roleExtract from '../utility/roleExtractor'

import  { jwtDecode } from 'jwt-decode';
import { useNavigate } from 'react-router-dom';

function Address() {

    const navigate = useNavigate();
   const jwt=localStorage.getItem('jwt');
    const decodedToken = jwtDecode(jwt);
    const role=decodedToken.role;

  let apibuild="/admin";
  if(role==2)
  apibuild="/user";

  const [userAddress,setUserAddress]=useState({landMark:"",city:"",district:"",state:"",country:"",pincode:""})


  async function userNewAddressCreation()
  {
    try {
      const userId=userIdExtract();  
    const jwt=localStorage.getItem('jwt');

    const api=`${apibuild}/${userId}/address`;
    const payload=userAddress;
    console.log("Before Posting Final Check:",payload);
    const resp=await axios.post(api,payload,{
      headers: {
          'Authorization': `Bearer ${jwt}`
      }});
      alert('Successfully Update');
      navigate(`${apibuild}/profile`)
    } catch (error) {
      alert("UnExpected Error.Try Again Later")
    }

  }

    function updateHandlerForUserAddress(e)
    {
      const { name, value } = e.target;
      setUserAddress((data) => ({
        ...data,
        [name]: value,
      }));
    }

    function saveAddress()
    {
        userNewAddressCreation();
    }

  return (
    <div className='address-box'>
     <div className="input-container">
      <label htmlFor="">LandMark</label>
      <input type="text"
      name='landMark'
      value={userAddress.landMark}
      onChange={updateHandlerForUserAddress}
      />
    </div>
    <div className="input-container">
      <label htmlFor="">city</label>
      <input type="text"
      name='city'
      value={userAddress.city}
      
      onChange={updateHandlerForUserAddress}
      />
    </div>
    <div className="input-container">
      <label htmlFor="">district</label>
      <input type="text"
      name='district'
      value={userAddress.district}
      
      onChange={updateHandlerForUserAddress}
      />
    </div>
    <div className="input-container">
      <label htmlFor="">state</label>
      <input type="text"
      name='state'
      value={userAddress.state}
      
      onChange={updateHandlerForUserAddress}
      />
    </div>
    <div className="input-container">
      <label htmlFor="">country</label>
      <input type="text"
      name='country'
      value={userAddress.country}
      
      onChange={updateHandlerForUserAddress}
      />
    </div>
    <div className="input-container">
      <label htmlFor="">pincode</label>
      <input type="number"
      name='pincode'
      value={userAddress.pincode}
      
      onChange={updateHandlerForUserAddress}
      />
    </div>
    <div className="button-to-save">
        <button onClick={saveAddress}>Save</button>
    </div>
    </div>

  )
}

export default Address