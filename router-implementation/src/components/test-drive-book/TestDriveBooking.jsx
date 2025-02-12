import React from 'react'
import { useParams } from 'react-router-dom';
import { useState } from 'react';
import { useEffect } from 'react';
import axios from '../utility/axios';
import { useNavigate } from 'react-router-dom';
import  { jwtDecode } from 'jwt-decode';
import './TestDriveBooking.css'

function TestDriveBooking() {
  const { carModelId } = useParams();
  const[data,setData]=useState([]);
  const navigate = useNavigate();
    const today = new Date();
    const year = today.getFullYear();
    const month = String(today.getMonth() + 1).padStart(2, '0'); // Months are zero-based
    const day = String(today.getDate()).padStart(2, '0');
    
    // Calculate the next hour properly, ensuring it stays within valid range (0-23)
    let hours = today.getHours() + 1; // Increment hour by 1
    if (hours === 24) hours = 0; // If hours go beyond 23, set it to 0 (next day)
    hours = String(hours).padStart(2, '0');
    
    // Set minutes to the current minutes (or you can modify it if needed)
    let minutes = String(today.getMinutes()).padStart(2, '0');
    
    // Format the current date and time
    const currentDateTime = `${year}-${month}-${day}T${hours}:${minutes}`;
    const [testDriveSlot, setTestDriveSlot] = useState(currentDateTime);
    
    // Set the min and max times for the input (e.g., 08:00 and 20:00)
    const minDateTime = `${year}-${month}-${day}T08:00`;
    const maxDateTime = `${year}-${month}-${day}T20:00`;
    
    console.log(currentDateTime); // Example: "2025-02-12T15:30"
    




  function buyPage()
  {
    navigate(`/buy/${carModelId}`)
  }
  function bookTestDrive()
  {

    async function bookDrive()
        {
         // Decode the token
         const jwt=localStorage.getItem('jwt');

         const decodedToken = jwtDecode(jwt);
         let userId=decodedToken.userId;

         const payload={
          "testDriveDate":testDriveSlot,
          "bookedDate":currentDateTime,
          "driveCompleted":false,
          "isVisible":true
         }
         console.log("Before BackEnd Push:",payload);
          //const api="/bookDrive";
          const api=`/user/${userId}/model/${carModelId}/test-drive-booking`;
          const resp=await axios.post(api,payload,{
            headers: {
                'Authorization': `Bearer ${jwt}`
            }}).then(response=>{
             console.log("Success")
          }).catch(error=>{
            alert("Error")
          });
        }
        bookDrive();

    console.log("Slot:",testDriveSlot)
  }
  const dataFetcher = async ()=>
    {
    try {
    const api=`/car/${carModelId}`;
    const resp=await axios.get(api);
    const json=resp.data;
    setData(json);
    } catch (error) {
      console.log(error)
    }
    }

  useEffect(()=>{
    dataFetcher();
  },[])

  return (
    <div className='test-drive-page'>

    <div className="image">

    </div>
    <div className="content">

    <div className="car-details">
    <h1>Test Drive</h1>
    <h2>Model:{data.modelName}</h2>
    <h2>Year:{data.year}</h2>
     <div>
      <label htmlFor="testDriveSlot">Select Test Drive Slot:</label>
      <input
        type="datetime-local"
        id="testDriveSlot"
        value={testDriveSlot}
        // min={minDateTime}
        // max={maxDateTime}
        step="3600"
        onChange={(e) => setTestDriveSlot(e.target.value)}
      />
    </div>

    </div>
    <div className="test-drive-page-button-warpper">
    <button onClick={bookTestDrive}>Book Test Drive</button>
    <button onClick={buyPage}>Buy Now</button>
    </div>

    </div>


    </div>
  )
}

export default TestDriveBooking