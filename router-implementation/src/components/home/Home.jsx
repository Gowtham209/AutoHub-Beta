import React from 'react'
import { useState } from 'react';
import { useEffect } from 'react'
import './Home.css'
import axios from '../utility/axios';

import Tile from '../test-tile/Tile'
function Home() {

  const[carList,setCarList]=useState([]);
 

    const dataFetcher = async ()=>
    {
    try {
    const api="/cars";
    const resp=await axios.get(api);
    console.log("Response for Home:",resp);
    const json=resp.data;
    setCarList(json);

    } catch (error) {
      console.log(error)
    }
    }

  useEffect(()=>{
    dataFetcher();
  },[])

  return (
    <div className='base-page'>

    <div className="grid">
      {carList.map((obj)=>(
        <Tile props={obj} key={obj.modelId}/>
      ))}
    </div>
    </div>

  )
}

export default Home