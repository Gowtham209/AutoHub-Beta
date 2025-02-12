import React from 'react'
import axios from '../../utility/axios';
import authorize from '../../utility/Authorizer';
import userIdExtract from '../../utility/userIdExtractor';
import roleExtract from '../../utility/roleExtractor'
import  { jwtDecode } from 'jwt-decode';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { useEffect } from 'react';


function DashBoard() {
  const [carList, setCarList] = useState([]);
  const [mainCarList, setMainCarList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTypedCar, setSearchTypedCar] = useState('');
  const [viewMode, setViewMode] = useState('current'); // 'current' or 'discontinued'
  const [editingCar, setEditingCar] = useState(null);

  const carListFetcher = async () => {
    try {
      const resp = await axios.get('/cars');
      setMainCarList(resp.data);
      setCarList(resp.data.filter(car => car.isVisible));
      setLoading(false);
    } catch (error) {
      console.log(error);
      setLoading(false);
    }
  };

  useEffect(() => {
    carListFetcher();
  }, []);

  useEffect(() => {
    if (!loading) {
      filterCars();
    }
  }, [searchTypedCar, viewMode]);

  const filterCars = () => {
    let filteredCars = mainCarList.filter(car => (viewMode === 'current' ? car.isVisible : !car.isVisible));

    if (searchTypedCar.trim() !== '') {
      filteredCars = filteredCars.filter(car =>
        car.modelName.toLowerCase().includes(searchTypedCar.toLowerCase())
      );
    }

    setCarList(filteredCars);
  };

  const handleDelete = async (modelId) => {
    try {
      const jwt = localStorage.getItem('jwt');
      const api=`/admin/car/${modelId}`;
      console.log("Delete is Triggered")
      const resp=await axios.delete(api,{
          headers: { 'Authorization': `Bearer ${jwt}` }
        });
      console.log("Response APi:",resp.data);
      setCarList(prev => prev.filter(car => car.modelId !== modelId));
    } catch (error) {
      console.log('Error deleting model:', error.response.data);
      alert(error.response.data)
    }
  };

  const handleSave = async (modelId) => {
    try {
      const jwt = localStorage.getItem('jwt');
      const api=`/admin/car/${modelId}`;
      delete editingCar.category;
      console.log("Payload:",editingCar)

     const response=await axios.put(api, editingCar,{
          headers: { 'Authorization': `Bearer ${jwt}` }
        });
      console.log("After Update:",response.data)
      setCarList(prev => prev.map(car => (car.modelId === editingCar.modelId ? editingCar : car)));
      setEditingCar(null);
    } catch (error) {
      console.log('Error updating model:', error);
    }
  };

  return (
    <>
      <h1>Dashboard</h1>
      <div className="controls">
        <input
          type="text"
          placeholder="Search by Model Name"
          value={searchTypedCar}
          onChange={(e) => setSearchTypedCar(e.target.value)}
        />
        <label>
          <input
            type="radio"
            name="viewMode"
            checked={viewMode === 'current'}
            onChange={() => setViewMode('current')}
          />{' '}
          Current Cars
        </label>
        <label>
          <input
            type="radio"
            name="viewMode"
            checked={viewMode === 'discontinued'}
            onChange={() => setViewMode('discontinued')}
          />{' '}
          Discontinued Cars
        </label>
      </div>

      {loading ? (
        <p>Loading cars...</p>
      ) : carList.length > 0 ? (
        <table className="car-table">
          <thead>
            <tr>
              <th>Model ID</th>
              <th>Model Name</th>
              <th>Year</th>
              <th>Price</th>
              <th>Available</th>
              <th>Car Lots</th>
              {viewMode === 'current' && <th>Actions</th>}
            </tr>
          </thead>
          <tbody>
            {carList.map((car) => (
              <tr key={car.modelId}>
                <td>{car.modelId}</td>
                <td>{car.modelName}</td>
                <td>{car.year}</td>
                <td>{car.price}</td>
                <td>{car.available ? 'Yes' : 'No'}</td>
                <td>{car.carLots}</td>
                {viewMode === 'current' && (
                  <td>
                    <button onClick={() => handleDelete(car.modelId)}>Delete</button>
                    <button onClick={() => setEditingCar(car)}>Update</button>
                  </td>
                )}
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No cars found.</p>
      )}

      {editingCar && (
        <div className="popup">
          <h2>Update Car Model</h2>
          <label>Model Name:
            <input
              type="text"
              value={editingCar.modelName}
              onChange={(e) => setEditingCar({ ...editingCar, modelName: e.target.value })}
            />
          </label>
          <label>Year:
            <input
              type="number"
              value={editingCar.year}
              onChange={(e) => setEditingCar({ ...editingCar, year: e.target.value })}
            />
          </label>
          <label>Price:
            <input
              type="number"
              value={editingCar.price}
              onChange={(e) => setEditingCar({ ...editingCar, price: e.target.value })}
            />
          </label>
          <label>Available:
            <input
              type="checkbox"
              checked={editingCar.available}
              onChange={(e) => setEditingCar({ ...editingCar, available: e.target.checked })}
            />
          </label>
          <label>Car Lots:
            <input
              type="number"
              value={editingCar.carLots}
              onChange={(e) => setEditingCar({ ...editingCar, carLots: e.target.value })}
            />
          </label>
          <button onClick={() => handleSave(editingCar.modelId)}>Save</button>
          <button onClick={() => setEditingCar(null)}>Cancel</button>
        </div>
      )}
    </>
  );
}

export default DashBoard;

// function DashBoard() {
//     const [carList, setCarList] = useState([]);
//     const [mainCarList, setMainCarList] = useState([]);
//     const [loading, setLoading] = useState(true);
//     const [searchTypedCar, setSearchTypedCar] = useState('');
//     const [viewMode, setViewMode] = useState('current'); // 'current' or 'discontinued'
//     const [editingCar, setEditingCar] = useState({});
  
//     const carListFetcher = async () => {
//       try {
//         const resp = await axios.get('/cars');
//         console.log("CarList:",resp.data)
//         setMainCarList(resp.data);
//         setCarList(resp.data.filter(car => car.isVisible));
//         setLoading(false);
//       } catch (error) {
//         console.log(error);
//         setLoading(false);
//       }
//     };
  
//     useEffect(() => {
//       carListFetcher();
//     }, []);
  
//     useEffect(() => {
//       if (!loading) {
//         filterCars();
//       }
//     }, [searchTypedCar, viewMode]);
  
//     const filterCars = () => {
//         let filteredCars = mainCarList.filter(car => (viewMode === 'current' ? car.isVisible : !car.isVisible));

  
//       if (searchTypedCar.trim() !== '') {
//         filteredCars = filteredCars.filter(car =>
//           car.modelName.toLowerCase().includes(searchTypedCar.toLowerCase())
//         );
//       }
  
//       setCarList(filteredCars);
//     };
  
    // const handleDelete = async (modelId) => {
    //   try {
    //     const jwt = localStorage.getItem('jwt');
    //     const api=`/admin/car/${modelId}`;
    //     console.log("Delete is Triggered")
    //     const resp=await axios.delete(api,{
    //         headers: { 'Authorization': `Bearer ${jwt}` }
    //       });
    //     console.log("Response APi:",resp.data);
    //     setCarList(prev => prev.filter(car => car.modelId !== modelId));
    //   } catch (error) {
    //     console.log('Error deleting model:', error.response.data);
    //     alert(error.response.data)
    //   }
    // };
  
    // const handleSave = async (modelId) => {
    //   try {
    //     const jwt = localStorage.getItem('jwt');
    //     const api=`/admin/car/${modelId}`;
    //     console.log("Payload:",editingCar)
    //    const response=await axios.put(`/cars/${editingCar.modelId}/update`, editingCar,{
    //         headers: { 'Authorization': `Bearer ${jwt}` }
    //       });
    //     console.log("After Update:",response.data)
    //     setCarList(prev => prev.map(car => (car.modelId === editingCar.modelId ? editingCar : car)));
    //     setEditingCar(null);
    //   } catch (error) {
    //     console.log('Error updating model:', error);
    //   }
    // };
  
//     return (
//       <>
//         <h1>Dashboard</h1>
//         <div className="controls">
//           <input
//             type="text"
//             placeholder="Search by Model Name"
//             value={searchTypedCar}
//             onChange={(e) => setSearchTypedCar(e.target.value)}
//           />
//           <label>
//             <input
//               type="radio"
//               name="viewMode"
//               checked={viewMode === 'current'}
//               onChange={() => setViewMode('current')}
//             />{' '}
//             Current Cars
//           </label>
//           <label>
//             <input
//               type="radio"
//               name="viewMode"
//               checked={viewMode === 'discontinued'}
//               onChange={() => setViewMode('discontinued')}
//             />{' '}
//             Discontinued Cars
//           </label>
//         </div>
  
//         {loading ? (
//           <p>Loading cars...</p>
//         ) : carList.length > 0 ? (
//           <table className="car-table">
//             <thead>
//               <tr>
//                 <th>Model ID</th>
//                 <th>Model Name</th>
//                 <th>Year</th>
//                 <th>Price</th>
//                 <th>Available</th>
//                 <th>Car Lots</th>
//                 {viewMode === 'current' && <th>Actions</th>}
//               </tr>
//             </thead>
//             <tbody>
//               {carList.map((car) => (
//                 <tr key={car.modelId}>
//                   <td>{car.modelId}</td>
//                   <td>{car.modelName}</td>
//                   <td>{car.year}</td>
//                   <td>{car.price}</td>
//                   <td>{car.available ? 'Yes' : 'No'}</td>
//                   <td>{car.carLots}</td>
//                   {viewMode === 'current' && (
//                     <td>
//                       <button onClick={() => handleDelete(car.modelId)}>Delete</button>
//                       <button onClick={() => setEditingCar(car)}>Update</button>
//                     </td>
//                   )}
//                 </tr>
//               ))}
//             </tbody>
//           </table>
//         ) : (
//           <p>No cars found.</p>
//         )}
  
//         {editingCar && (
//           <div className="popup">
//             <h2>Update Car Model</h2>
//             <label>Model Name:
//               <input
//                 type="text"
//                 value={editingCar.modelName}
//                 onChange={(e) => setEditingCar({ ...editingCar, modelName: e.target.value })}
//               />
//             </label>
//             <label>Year:
//               <input
//                 type="number"
//                 value={editingCar.year}
//                 onChange={(e) => setEditingCar({ ...editingCar, year: e.target.value })}
//               />
//             </label>
//             <label>Price:
//               <input
//                 type="number"
//                 value={editingCar.price}
//                 onChange={(e) => setEditingCar({ ...editingCar, price: e.target.value })}
//               />
//             </label>
//             <label>Available:
//               <input
//                 type="checkbox"
//                 checked={editingCar.available}
//                 onChange={(e) => setEditingCar({ ...editingCar, available: e.target.checked })}
//               />
//             </label>
//             <label>Car Lots:
//               <input
//                 type="number"
//                 value={editingCar.carLots}
//                 onChange={(e) => setEditingCar({ ...editingCar, carLots: e.target.value })}
//               />
//             </label>
//             <button onClick={handleSave}>Save</button>
//             <button onClick={() => setEditingCar(null)}>Cancel</button>
//           </div>
//         )}
//       </>
//     );
//   }
  
//   export default DashBoard;
  
// function DashBoard() {
//     const [carList, setCarList] = useState([]);
//     const [mainCarList, setMainCarList] = useState([]);
//     const [loading, setLoading] = useState(true);
//     const [searchTypedCar, setSearchTypedCar] = useState('');
//     const [showOldModels, setShowOldModels] = useState(false);
//     const [editingCar, setEditingCar] = useState(null);
  
//     const carListFetcher = async () => {
//       try {
//         const resp = await axios.get('/cars');
//         setMainCarList(resp.data);
//         setCarList(resp.data.filter(car => car.isVisible));
//         setLoading(false);
//       } catch (error) {
//         console.log(error);
//         setLoading(false);
//       }
//     };
  
//     useEffect(() => {
//       carListFetcher();
//     }, []);
  
//     useEffect(() => {
//       if (!loading) {
//         filterCars();
//       }
//     }, [searchTypedCar, showOldModels]);
  
//     const filterCars = () => {
//       let filteredCars = mainCarList.filter(car => car.isVisible !== showOldModels);
  
//       if (searchTypedCar.trim() !== '') {
//         filteredCars = filteredCars.filter(car =>
//           car.modelName.toLowerCase().includes(searchTypedCar.toLowerCase())
//         );
//       }
  
//       setCarList(filteredCars);
//     };
  
//     const handleDelete = async (modelId) => {
//       try {
//         await axios.put(`/cars/${modelId}/delete`, { isVisible: false });
//         setCarList(prev => prev.filter(car => car.modelId !== modelId));
//       } catch (error) {
//         console.log('Error deleting model:', error);
//       }
//     };
  
//     const handleSave = async () => {
//       try {
//         await axios.put(`/cars/${editingCar.modelId}/update`, editingCar);
//         setCarList(prev => prev.map(car => (car.modelId === editingCar.modelId ? editingCar : car)));
//         setEditingCar(null);
//       } catch (error) {
//         console.log('Error updating model:', error);
//       }
//     };
  
//     return (
//       <>
//         <h1>Dashboard</h1>
//         <div className="controls">
//           <input
//             type="text"
//             placeholder="Search by Model Name"
//             value={searchTypedCar}
//             onChange={(e) => setSearchTypedCar(e.target.value)}
//           />
//           <label>
//             <input
//               type="radio"
//               checked={showOldModels}
//               onChange={() => setShowOldModels(!showOldModels)}
//             />{' '}
//             Show Discontinued Models
//           </label>
//         </div>
  
//         {loading ? (
//           <p>Loading cars...</p>
//         ) : carList.length > 0 ? (
//           <table className="car-table">
//             <thead>
//               <tr>
//                 <th>Model ID</th>
//                 <th>Model Name</th>
//                 <th>Year</th>
//                 <th>Price</th>
//                 <th>Available</th>
//                 <th>Car Lots</th>
//                 <th>Actions</th>
//               </tr>
//             </thead>
//             <tbody>
//               {carList.map((car) => (
//                 <tr key={car.modelId}>
//                   <td>{car.modelId}</td>
//                   <td>{car.modelName}</td>
//                   <td>{car.year}</td>
//                   <td>{car.price}</td>
//                   <td>{car.available ? 'Yes' : 'No'}</td>
//                   <td>{car.carLots}</td>
//                   <td>
//                     <button onClick={() => handleDelete(car.modelId)}>Delete</button>
//                     <button onClick={() => setEditingCar(car)}>Update</button>
//                   </td>
//                 </tr>
//               ))}
//             </tbody>
//           </table>
//         ) : (
//           <p>No cars found.</p>
//         )}
  
//         {editingCar && (
//           <div className="popup">
//             <h2>Update Car Model</h2>
//             <label>Model Name:
//               <input
//                 type="text"
//                 value={editingCar.modelName}
//                 onChange={(e) => setEditingCar({ ...editingCar, modelName: e.target.value })}
//               />
//             </label>
//             <label>Year:
//               <input
//                 type="number"
//                 value={editingCar.year}
//                 onChange={(e) => setEditingCar({ ...editingCar, year: e.target.value })}
//               />
//             </label>
//             <label>Price:
//               <input
//                 type="number"
//                 value={editingCar.price}
//                 onChange={(e) => setEditingCar({ ...editingCar, price: e.target.value })}
//               />
//             </label>
//             <label>Available:
//               <input
//                 type="checkbox"
//                 checked={editingCar.available}
//                 onChange={(e) => setEditingCar({ ...editingCar, available: e.target.checked })}
//               />
//             </label>
//             <label>Car Lots:
//               <input
//                 type="number"
//                 value={editingCar.carLots}
//                 onChange={(e) => setEditingCar({ ...editingCar, carLots: e.target.value })}
//               />
//             </label>
//             <button onClick={handleSave}>Save</button>
//             <button onClick={() => setEditingCar(null)}>Cancel</button>
//           </div>
//         )}
//       </>
//     );
//   }
  
//   export default DashBoard;
  
// function DashBoard() {
//   const [carList, setCarList] = useState([]);
//   const [mainCarList, setMainCarList] = useState([]);
//   const [loading, setLoading] = useState(true); 
//   const [searchTypedCar, setSearchTypedCar] = useState('');

//   const carListFetcher = async () => {
//     try {
//       const resp = await axios.get("/cars");
//       setMainCarList(resp.data);
//       setCarList(resp.data); 
//       setLoading(false);    
//     } catch (error) {
//       console.log(error);
//       setLoading(false);
//     }
//   };
//       useEffect(() => {
//         carListFetcher();
//       }, []);
//         useEffect(() => {
//           if (!loading) {
//             filterCars();
//           }
//         }, [searchTypedCar]);

//         const filterCars = () => {
//             let filteredCars = mainCarList;    
//             if (searchTypedCar.trim() !== '') {
//               filteredCars = filteredCars.filter((car) =>
//                 car.modelName.toLowerCase().includes(searchTypedCar.toLowerCase())
//               );
//             }
        
//             setCarList(filteredCars);
//           };
//   return (
//     <>
//     <div>DashBoard</div>
//     <div className="search-box">
//         <input
//           type="text"
//           placeholder="Search by Model Name"
//           value={searchTypedCar}
//           onChange={(e) => setSearchTypedCar(e.target.value)}
//         />
//       </div>
//     <div className="car-model-listing-table">
//     {loading ? (
//           <p>Loading cars...</p> 
//         ) : carList.length > 0 ? (
//           carList.map((obj) => <td></td>)
//         ) : (
//           <p>No cars found.</p>
//         )}
//     </div>
//     </>
//   )
// }

// export default DashBoard