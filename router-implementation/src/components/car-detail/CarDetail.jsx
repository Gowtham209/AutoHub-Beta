import React from 'react'
import { useParams } from 'react-router-dom';
import { useState } from 'react';
import { useEffect } from 'react';
import axios from '../utility/axios';
import { useNavigate } from 'react-router-dom';

function CarDetail() {
    
    const { carModelId } = useParams();
    const[data,setData]=useState([]);
    const navigate = useNavigate();

    function buyPage()
    {
      navigate(`/buy/${carModelId}`)
    }
    function bookTestDrivePage()
    {
      navigate(`/test-drive/${carModelId}`)
    }
    const dataFetcher = async ()=>
    {
    try {
    const api=`/car/${carModelId}`;
    const resp=await axios.get(api);
    const json=resp.data;
    setData(json);
    console.log(json)

    } catch (error) {
      console.log(error)
    }
    }

  useEffect(()=>{
    dataFetcher();
  },[])
  console.log(carModelId);
  return (
    <div>
        <h1>CarDetail</h1>
        <h2>Model:{data.modelName}</h2>
        <h2>Price:{data.price}</h2>
        <h2>Year:{data.year}</h2>
        <h2>Total Piece:{data.carLots}</h2>
        <h2>Availability: {data.available ? "Available" : "Not Available"}</h2>
        <button onClick={bookTestDrivePage}>Book Test Drive</button>
        <button onClick={buyPage}>Buy</button>
    </div>
  )
}

export default CarDetail