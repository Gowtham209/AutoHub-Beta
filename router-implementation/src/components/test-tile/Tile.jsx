import React from 'react'
import { useEffect } from 'react';
import { useState } from 'react'
import './Tile.css'
import { useNavigate } from 'react-router-dom';

function Tile({props}) {
    const [avaliable,setAvaliable]=useState('');
    const navigate = useNavigate();

    const jwt=localStorage.getItem('jwt');
    

    function detailPageRoute()
    {
        navigate(`/cardetail/${props.modelId}`)
    }
    useEffect(()=>{
        if(props.available==true)
            setAvaliable('Available')
        else
        setAvaliable('Not - Available')
    },[])
    function buyPage()
    {
    
      if(jwt==null)
      navigate(`/buy/${props.modelId}`)

      navigate(`/user/buy/${props.modelId}`)
    }
    function bookTestDrivePage(e)
    {

      if(jwt==null)
      navigate(`/test-drive/${props.modelId}`)

      navigate(`/user/test-drive/${props.modelId}`)
    }

  return (
 <div className='tile' onClick={detailPageRoute}>
  <div className="image"></div>
  <h2>Model: {props.modelName}</h2>
  <h3>Price: {props.price}</h3>
  <h3>Availability: {avaliable}</h3>
  <div className="buy-book-buttons">
    <button onClick={(e) => { e.stopPropagation(); bookTestDrivePage(); }}>Book Test Drive</button>
    <button onClick={(e) => { e.stopPropagation(); buyPage(); }}>Buy</button>
  </div>
</div> 
  )
}

export default Tile