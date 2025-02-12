import React, { useRef } from 'react'
import { useState } from 'react';
import { useEffect } from 'react';
import axios from '../../utility/axios';
import authorize from '../../utility/Authorizer';
import userIdExtract from '../../utility/userIdExtractor';
import roleExtract from '../../utility/roleExtractor'

import  { jwtDecode } from 'jwt-decode';
import { useNavigate } from 'react-router-dom';
import './Profile.css'

function Profile() {

  const response=useRef({});
  const navigate = useNavigate();
  const jwt=localStorage.getItem('jwt');
  const decodedToken = jwtDecode(jwt);
  const role=decodedToken.role;

  let apibuild="/admin";
  if(role==2)
  apibuild="/user";
  console.log("Profile:Role:",role,"\napibuild:",apibuild);

  if(!authorize(roleExtract()))
  {
   console.log('From Profile')
   navigate('/unAuthorized');
  }
  const[userDetail,setUserDetails] = useState({});
  const[userAddress,setUserAddress] = useState({})
  const [isEdit,setIsEdit]=useState(false)
  const [popUp,setPopUp]=useState(false)

  function confirmUpdate()
  {
    setPopUp(!popUp)
    userProfileUpdate();
  }
  function editOn()
  {
    setIsEdit(!isEdit);
    console.log("isEdit:",isEdit)
  }

  function cancelUserUpdate()
  {
    setPopUp(!popUp)
  }

  function userProfileEdit()
  {
    setIsEdit(!isEdit);
    setPopUp(!popUp)
    console.log("Updated DATA:\n",userDetail)
  }

  async function userProfileUpdate()
  {
    try {
      const userId=userIdExtract();  
    const jwt=localStorage.getItem('jwt');

    const api=`${apibuild}/${userId}`;
    const payload=userDetail;
    const resp=await axios.put(api,payload,{
      headers: {
          'Authorization': `Bearer ${jwt}`
      }});
      alert('Successfully Update');
    } catch (error) {
      alert("UnExpected Error.Try Again Later")
    }

  }

  async function addressDelete()
  {
    try {
      const userId=userIdExtract();  
    const jwt=localStorage.getItem('jwt');

    const api=`${apibuild}/${userId}/address/${userAddress.userAddressId}`;
    const resp=await axios.delete(api,{
      headers: {
          'Authorization': `Bearer ${jwt}`
      }});
      alert('Successfully Address Deleted');
    } catch (error) {
      alert("UnExpected Error.Try Again Later")
    }

  }

  function updateHandlerForUserDetail(e)
  {
    const { name, value } = e.target;
    setUserDetails((data) => ({
      ...data,
      [name]: value,
    }));
  }

  function updateHandlerForUserAddress(e)
  {
    const { name, value } = e.target;
    setUserAddress((data) => ({
      ...data,
      [name]: value,
    }));
  }

  


  // /user/{userId}/address

  const userDetailFetch = async ()=>
    {
     
    try {
    const userId=userIdExtract();  
    const jwt=localStorage.getItem('jwt');

    const api=`${apibuild}/${userId}`;
    const resp=await axios.get(api,{
      headers: {
          'Authorization': `Bearer ${jwt}`
      }});
    const json=resp.data;
    setUserDetails(json);
    response.current=resp;
    console.log('user detail success:\n',json);
    } catch (error) {
      
      if (error.response && error.response.status === 403) {
        console.log("JWT token expired or unauthorized access. Redirecting to login.");
        alert('Session Expired Relogin')
        navigate('/login')
       
      }
    }

    }



  const userDetailAddresslFetch = async ()=>
    {
     
    try {
    const userId=userIdExtract();  
    const jwt=localStorage.getItem('jwt');
    
    const api=`${apibuild}/${userId}/addresses`;
    const resp=await axios.get(api,{
      headers: {
          'Authorization': `Bearer ${jwt}`
      }});
    const json=resp.data;
    setUserAddress(json);
    response.current=resp;
    console.log('Address Success:\n',json);
    } catch (error) {
      
      if (error.response && error.response.status === 403) {
        console.log("JWT token expired or unauthorized access. Redirecting to login.");
        alert('Session Expired Relogin')
        navigate('/login')
      }
    }

    }

  useEffect(()=>{
    const role=authorize(roleExtract());
    let apibuild="/admin";
    if(role==2)
    apibuild="/user";
    console.log("Profile:Role:",role,"\napibuild:",apibuild);
    userDetailFetch();
    userDetailAddresslFetch();
  },[])

  function deleteAddress()
  {
    addressDelete();
    setUserAddress("No Address");
  }

  function newAddressCreation()
  {
    navigate(`${apibuild}/address`)
  }

  const [updateAddress,setUpdateAddress]=useState(false)
  function addressUpdate()
  {
    setUpdateAddress(true);
  }

  async function userAddressUpdate()
  {
    try {
      const userId=userIdExtract();  
    const jwt=localStorage.getItem('jwt');

    const api=`${apibuild}/${userId}/address/${userAddress.userAddressId}`;
    //const payload=userAddress;
    const payload = { landMark: userAddress.landMark,city:userAddress.city,state:userAddress.state,country:userAddress.country,district:userAddress.district,pincode:userAddress.pincode };
     console.log("Before Update:",payload);
    const resp=await axios.put(api,payload,{
      headers: {
          'Authorization': `Bearer ${jwt}`
      }});
      alert('Successfully Update');
      setUpdateAddress(false);
    } catch (error) {
      alert("UnExpected Error.Try Again Later")
    }

  }


  function updateUserAddress()
  {
    userAddressUpdate();
  }
  function cancelUpdateAddress()
  {
    setUpdateAddress(false);  
  }

  return (
    <div className='profile-page'>
    {updateAddress && 
    
    <div className="update-container">
    <div className="address-update-popup">

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
    <div className="button-to-update-address button-group">
        <button onClick={updateUserAddress}>Update</button>
        <button onClick={cancelUpdateAddress}>Cancel</button>
    </div>

    </div>
    </div>
    
    }
      
      {/* {popUp && <div className="pop-up">
        Confirm to Update User Details
        <div className="pop-up-buttons">
          <button onClick={confirmUpdate}>Confirm</button>
          <button onClick={cancelUserUpdate}>Cancel</button>
        </div>
      </div>} */}


{popUp && (
        <div className="pop-up-overlay">
          <div className="pop-up">
            <p>Confirm to Update User Details</p>
            <div className="pop-up-buttons button-group">
              <button onClick={confirmUpdate}>Confirm</button>
              <button onClick={cancelUserUpdate} id='cancel-button'>Cancel</button>
            </div>
          </div>
        </div>
      )}

     <div className="user-details">
     <div className="input-container">
        <label htmlFor="">UserName</label>
        <input type="text"
        name='userName'
        value={userDetail.userName}
        disabled={!isEdit}
        onChange={updateHandlerForUserDetail}
        />
      </div>

      <div className="input-container">
        <label htmlFor="">Email</label>
        <input type="email"
        name='emailId'
        value={userDetail.emailId}
        disabled={!isEdit}
        onChange={updateHandlerForUserDetail}
        />
      </div>
      <h2>Addresses:</h2>
       
      {userAddress!="No Address" &&
      <>
      <div className="address-box">
      <label htmlFor="landmark">LandMark</label><span id="landmark">{userAddress.landMark}</span><br></br>
      <label htmlFor="city">city</label><span id="city">{userAddress.city}</span><br></br>
      <label htmlFor="district">district</label><span id="district">{userAddress.district}</span><br></br>
      <label htmlFor="state">state</label><span id="state">{userAddress.state}</span><br></br>
      <label htmlFor="country">country</label><span id="country">{userAddress.country}</span><br></br>
      <label htmlFor="pincode">pincode</label><span id="pincode">{userAddress.pincode}</span><br></br>    
      </div>
      <div className="button-group">
      <button onClick={addressUpdate}>Update</button>
      <button onClick={deleteAddress} className='cancel-delete-button'>Delete</button>
      </div>
      </>
     }
     {userAddress=="No Address" &&
     <>
     <div className="address-creation">
      <button onClick={newAddressCreation}>Create Address</button>
     </div>
     </>}
     </div>
      <div className="buttons">
        {!isEdit && <button onClick={editOn}>Edit</button>}
        {isEdit && <button onClick={userProfileEdit}>Save</button>}
        {isEdit && <button onClick={editOn}>Cancel</button>}
      </div>
      
    </div>
  )
}

export default Profile