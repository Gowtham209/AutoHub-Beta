import React from 'react'
import { useParams } from 'react-router-dom';
import { useState } from 'react';
import { useEffect } from 'react';
import axios from '../utility/axios';
import { useNavigate } from 'react-router-dom';
import  { jwtDecode } from 'jwt-decode';

import './BuyPage.css'
function BuyPage() {
  const { carModelId } = useParams();
  const[data,setData]=useState([]);
  const navigate = useNavigate();
  const [quantity,setQuantity]=useState(1);
  const [amount,setAmount] = useState(0);
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
  const [deliveryDate, setDeliveryDate] = useState(currentDateTime);
  
  // Set the min and max times for the input (e.g., 08:00 and 20:00)
  const minDateTime = `${year}-${month}-${day}T08:00`;
  const maxDateTime = `${year}-${month}-${day}T20:00`;
  
  console.log(currentDateTime); // Example: "2025-02-12T15:30"
  

  function finalAmount()
  {
    setAmount((quantity*data.price).toFixed(2))
    console.log("Amount:",amount,"\nQuantity:",quantity)
  }
  function addQuantity()
  {

    if(data.carLots>quantity)
    {
      setQuantity(quantity+1);
    //  setAmount(amount+data.price)
    }
    else{
      alert("Can't Add More Quantity")
    }
    // finalAmount();
   
  }

  function removeQuantity()
  {
    if(quantity>1)
      {
        setQuantity(quantity-1);
       // setAmount(amount-data.price)
      }
      else{
        alert("Can't Remove More Quantity")
      }
      // finalAmount();
      
  }
  const dataFetcher = async ()=>
    {
    try {
    const api=`/car/${carModelId}`;
    const resp=await axios.get(api);
    const json=resp.data;
    setData(json);
    console.log(json,"\nQuantity:",quantity)
    setAmount(json.price)


    } catch (error) {
      console.log(error)
    }
    }

  useEffect(()=>{
    dataFetcher();
  },[])

   useEffect(() => {
    setAmount((quantity * data.price).toFixed(2));
  }, [quantity]);

  function bookTestDrivePage()
  {
    console.log("Book test Drive")
    navigate(`/test-drive/${carModelId}`)
  }

  function buyNow()
  {
    
      console.log("Final Amount:",amount)
      if(quantity>0 && quantity<=data.carLots)
      {
        async function buy()
        {
         // Decode the token
         ///user/{userId}/model/{modelId}/car-purchase-booking
         const jwt=localStorage.getItem('jwt');

         const decodedToken = jwtDecode(jwt);
         let userId=decodedToken.userId;

         const payload={
          "finalPurchaseAmount":amount,
          "quantity":quantity,
          "isVisible":true,
          "bookedDate":currentDateTime,
          "deliveryDate":deliveryDate
         }
         console.log("Before BackEnd Push:",payload);
          //const api="/buy";
          const api=`/user/${userId}/model/${carModelId}/car-purchase-booking`;
          console.log("Before BuyAPI:",payload)
          const resp=await axios.post(api,payload,{
            headers: {
                'Authorization': `Bearer ${jwt}`
            }}).then(response=>{
              alert("Success")
             console.log("Success")
          }).catch(error=>{
            alert("Error")
          });
        }
        buy();
      }
      else
      alert('Quantity is Appropriate')
    
  }

  return (
    <div className='buy-page'>

    <div className="image">

    </div>
    <div className="content">

    <div className="car-details">
    <h1>Buy Page</h1>
    <h2>Model:{data.modelName}</h2>
    <h2>Year:{data.year}</h2>
    <h2>Price:{data.price}</h2>
    <div className="quantity-buttons">
      <label htmlFor="">Quantity:</label>
      <button onClick={addQuantity}>+</button>
      <p>{quantity}</p>
      <button onClick={removeQuantity}>-</button>
    </div>
    {/* <h2>Price:{(data.price*quantity).toFixed(2)}</h2> */}
    <h2>Total:{amount}</h2>
    <div>
      <label htmlFor="deliverydate">Delivery Date:</label>
      <input
        type="datetime-local"
        id="deliverydate"
        value={deliveryDate}
        // min={minDateTime}
        // max={maxDateTime}
        step="3600"
        onChange={(e) => setDeliveryDate(e.target.value)}
      />
    </div>
    </div>

    <div className="buy-page-button-warpper">
    <button onClick={buyNow} disabled={!data.available} title={!data.available?"Sorry Can't Buy at the Moment":"Buy Now Button"}>Buy Now</button>
    <button onClick={bookTestDrivePage}>Book Test Drive</button>
    </div>

    </div>


    </div>
  )
}

export default BuyPage