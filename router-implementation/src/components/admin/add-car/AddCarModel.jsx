import React from 'react'
import { useEffect } from 'react';
import { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import axios from '../../utility/axios';
import './AddCarModel.css';

function AddCarModel() {
  const [carDetail, setCarDetail] = useState({ modelName: '', available: '', price: '', year: '', carLots: '' });
  const [category, setCategory] = useState('');
  const [categoryId,setCategoryId] =useState(null)
  const [categoryList, setCategoryList] = useState([]);
  const [filteredCategories, setFilteredCategories] = useState([]);
  const [popUp, setPopUp] = useState(false);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const resp = await axios.get('/categories');
        setCategoryList(resp.data);
      } catch (error) {
        console.log(error);
      }
    };
    fetchCategories();
  }, []);

  const handleCategoryChange = (e) => {
    const value = e.target.value;
    setCategory(value);
    setFilteredCategories(
      categoryList.filter((cat) =>
        cat.carType.toLowerCase().includes(value.toLowerCase())
      )
    );
  };

  const selectCategory = (carType) => {
    setCategory(carType);
    setFilteredCategories([]);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCarDetail((prev) => ({ ...prev, [name]: value }));
  };

  const confirmUpdate = async () => {
    try {
      const jwt = localStorage.getItem('jwt');
      const payload = { ...carDetail, category: { 
        "categoryId":categoryId,
        "carType": category
       } ,
      "isVisible":true};
      await axios.post('/admin/car', payload, {
        headers: { Authorization: `Bearer ${jwt}` },
      });
      alert('Successfully Added');
    } catch (error) {
      alert('Unexpected Error. Try Again Later');
    }
    setPopUp(false);
  };

  return (
    <div className="form-container">
      {popUp && (
        <div className="pop-up">
          <p>Confirm to Add New Car</p>
          <div className="pop-up-buttons">
            <button onClick={confirmUpdate}>Confirm</button>
            <button onClick={() => setPopUp(false)}>Cancel</button>
          </div>
        </div>
      )}

      <h1>Add Car Model</h1>
      <form className="car-form">
        <div className="input-group">
          <label>Category:</label>
          <input
            type="text"
            value={category}
            onChange={handleCategoryChange}
            placeholder="Search Category"
          />
          {filteredCategories.length > 0 && (
            <ul className="suggestions">
              {filteredCategories.map((cat) => (
                <li key={cat.categoryId} onClick={() => {
                  selectCategory(cat.carType)
                  setCategoryId(cat.categoryId)
                  }}>
                  {cat.carType}
                </li>
              ))}
            </ul>
          )}
        </div>

        <div className="input-group">
          <label>Model Name:</label>
          <input type="text" name="modelName" value={carDetail.modelName} onChange={handleInputChange} />
        </div>

        <div className="input-group">
          <label>Price:</label>
          <input type="number" name="price" value={carDetail.price} onChange={handleInputChange} />
        </div>

        <div className="input-group">
          <label>Year:</label>
          <input type="number" name="year" value={carDetail.year} onChange={handleInputChange} />
        </div>

        <div className="input-group">
          <label>Car Lots:</label>
          <input type="number" name="carLots" value={carDetail.carLots} onChange={handleInputChange} />
        </div>

        <div className="input-group">
          <label>Available:</label>
          <input type="text" name="available" value={carDetail.available} onChange={handleInputChange} />
        </div>

        <div className="form-buttons">
          <button type="button" onClick={() => setPopUp(true)}>Save</button>
          <button type="button" onClick={() => alert('Confirm to Exit the Page')}>Cancel</button>
        </div>
      </form>
    </div>
  );
}

export default AddCarModel;


// function AddCarModel() {
  
//     const [carDetail,setCarDetail]=useState({modelName:'',available:'',price:'',year:'',carLots:''})

//     const [category,setCategory]=useState('');

//     const [categoryList,setCategoryList]=useState([]);

//     const [popUp,setPopUp]=useState(false)


//     function confirmUpdate()
//   {
//     setPopUp(!popUp)
//     newCarRecordCreate();
//   }

//   function cancelUserUpdate()
//   {
//     setPopUp(!popUp)
//   }

//   function cancel()
//   {
//     alert('Confirm to Exit the Page')
//     navigator("/admin/dash-board")
//   }

//     const categoryFetcher = async ()=>
//         {
//         try {
//         const api="/categories";
//         const resp=await axios.get(api);
//         const json=resp.data;
//         setCategoryList(json);
//         console.log("category:",json)
    
//         } catch (error) {
//           console.log(error)
//         }
//         }
    
//       useEffect(()=>{
//         categoryFetcher();
//       },[])

//     function updateHandlerForCarDetail(e)
//     {
//       const { name, value } = e.target;
//       setCarDetail((data) => ({
//         ...data,
//         [name]: value,
//       }));
//       console.log(carDetail)
//     }



//     function addCar()
//     {
//         setPopUp(!popUp)
//     }

//     async function newCarRecordCreate()
//     {
//       try {
      
//     //   const userId=userIdExtract();  
//        const jwt=localStorage.getItem('jwt');
  
//        const api='/admin/car';
//        const temp={...carDetail,'category':{
//         "carType":category
//        }}
//        const payload=temp;
//        const resp=await axios.post(api,payload,{
//          headers: {
//              'Authorization': `Bearer ${jwt}`
//          }});
   
//         alert('Successfully Update');
//       } catch (error) {
//         alert("UnExpected Error.Try Again Later")
//       }
  
//     }
  
//     return (
//     <>
//     {popUp && <div className="pop-up">
//         Confirm to Add New Car
//         <div className="pop-up-buttons">
//           <button onClick={confirmUpdate}>Confirm</button>
//           <button onClick={cancelUserUpdate}>Cancel</button>
//         </div>
//       </div>}
//     <h1>AddCarModel</h1>
//     <div className="input-container">
//       <label htmlFor="">category:</label>
//       <input type="text"
//       name='categoryId'
//       value={category}
//       onChange={(e)=>setCategory(e.target.value)}
//       placeholder='CategoryID'
//       />
//     </div>
//     <div className="input-container">
//       <label htmlFor="">modelName:</label>
//       <input type="text"
//       name='modelName'
//       value={carDetail.modelName}
//       onChange={updateHandlerForCarDetail}
//       />
//     </div>
//     <div className="input-container">
//       <label htmlFor="">price:</label>
//       <input type="number"
//       name='price'
//       value={carDetail.price}
//       onChange={updateHandlerForCarDetail}
//       />
//     </div>
//     <div className="input-container">
//       <label htmlFor="">year:</label>
//       <input type="number"
//       name='year'
//       value={carDetail.year}
//       onChange={updateHandlerForCarDetail}
//       />
//     </div>
//     <div className="input-container">
//       <label htmlFor="">carLots:</label>
//       <input type="number"
//       name='carLots'
//       value={carDetail.carLots}
//       onChange={updateHandlerForCarDetail}
//       />
//     </div>
//     <div className="input-container">
//       <label htmlFor="">available:</label>
//       <input type="text"
//       name='available'
//       value={carDetail.available}
//       onChange={updateHandlerForCarDetail}
//       />
//     </div>
//     <div className="buttons">
//       <button onClick={addCar}>Save</button>
//       <button onClick={cancel}>Cancel</button>
//       </div>
//     </>
//   )
// }

// export default AddCarModel