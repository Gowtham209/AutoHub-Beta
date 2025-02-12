import React from 'react'
import { useState } from 'react';
import { useEffect } from 'react';
import Tile from '../../test-tile/Tile';
import axios from '../../utility/axios';
import authorize from '../../utility/Authorizer';
import userIdExtract from '../../utility/userIdExtractor';
import roleExtract from '../../utility/roleExtractor'
import  { jwtDecode } from 'jwt-decode';
import { useNavigate } from 'react-router-dom';
import './UserHome.css'

function UserHome() {
  const navigate = useNavigate();
  if (!authorize(roleExtract())) {
    navigate('/unAuthorized');
  }

  const [carList, setCarList] = useState([]);
  const [mainCarList, setMainCarList] = useState([]);
  const [categoryList, setCategoryList] = useState([]);
  const [searchTypedCar, setSearchTypedCar] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('All');
  const [loading, setLoading] = useState(true); 

  useEffect(() => {
    carListFetcher();
    categoryListFetcher();
  }, []);

  const categoryListFetcher = async () => {
    try {
      const resp = await axios.get("/categories");
      setCategoryList(resp.data);
    } catch (error) {
      console.log(error);
    }
  };

  const carListFetcher = async () => {
    try {
      const resp = await axios.get("/cars");
      setMainCarList(resp.data);
      setCarList(resp.data); // ✅ Set carList immediately after fetch
      setLoading(false);     // ✅ Stop loading after data fetch
    } catch (error) {
      console.log(error);
      setLoading(false);
    }
  };

  useEffect(() => {
    if (!loading) { // ✅ Only filter when loading is complete
      filterCars();
    }
  }, [searchTypedCar, selectedCategory]);

  const filterCars = () => {
    let filteredCars = mainCarList;

    if (selectedCategory !== 'All') {
      filteredCars = filteredCars.filter(
        (car) => car.category.carType.toLowerCase() === selectedCategory.toLowerCase()
      );
    }

    if (searchTypedCar.trim() !== '') {
      filteredCars = filteredCars.filter((car) =>
        car.modelName.toLowerCase().includes(searchTypedCar.toLowerCase())
      );
    }

    setCarList(filteredCars);
  };

  return (
    <div className="base-page">
      <div className="search-box">
        <input
          type="text"
          placeholder="Search by Model Name"
          value={searchTypedCar}
          onChange={(e) => setSearchTypedCar(e.target.value)}
        />
      </div>

      <div className="category-drop-down">
        <label htmlFor="category">Choose a Category:</label>
        <select
          id="category"
          value={selectedCategory}
          onChange={(e) => setSelectedCategory(e.target.value)}
        >
          <option value="All">All</option>
          {categoryList.map((obj) => (
            <option key={obj.categoryId} value={obj.carType}>
              {obj.carType}
            </option>
          ))}
        </select>
      </div>

      <div className="grid">
        {loading ? (
          <p>Loading cars...</p> // ✅ Loading indicator
        ) : carList.length > 0 ? (
          carList.map((obj) => <Tile key={obj.modelId} props={obj} />)
        ) : (
          <p>No cars found.</p>
        )}
      </div>
    </div>
  );
}

export default UserHome;


// function UserHome() {
//   const navigate = useNavigate();
//   if (!authorize(roleExtract())) {
//     navigate('/unAuthorized');
//   }

//   const [carList, setCarList] = useState([]);
//   const [mainCarList, setMainCarList] = useState([]);
//   const [categoryList, setCategoryList] = useState([]);
//   const [searchTypedCar, setSearchTypedCar] = useState('');
//   const [selectedCategory, setSelectedCategory] = useState('All');

//   // Debounce for search input
//   useEffect(() => {
//     const timeoutId = setTimeout(() => {
//       filterCars();
//     }, 500); // Debounce time (500ms)

//     return () => clearTimeout(timeoutId);
//   }, [searchTypedCar, selectedCategory]);

//   const categoryListFetcher = async () => {
//     try {
//       const resp = await axios.get("/categories");
//       setCategoryList(resp.data);
//     } catch (error) {
//       console.log(error);
//     }
//   };

//   const carListFetcher = async () => {
//     try {
//       const resp = await axios.get("/cars");
//       setCarList(resp.data);
//       setMainCarList(resp.data);
//     } catch (error) {
//       console.log(error);
//     }
//   };

//   useEffect(() => {
//     carListFetcher();
//     categoryListFetcher();
//   }, []);
//   useEffect(() => {
//     if (mainCarList.length > 0) { // ✅ Only filter when data is available
//       filterCars();
//     }
//     console.log("carList Length:",carList.length)
//   }, [searchTypedCar, selectedCategory, mainCarList]);

//   // Filter function for category and search
//   const filterCars = () => {
//     let filteredCars = mainCarList;

//     if (selectedCategory !== 'All') {
//       filteredCars = filteredCars.filter(
//         (car) => car.category.carType.toLowerCase() === selectedCategory.toLowerCase()
//       );
//     }

//     if (searchTypedCar.trim() !== '') {
//       filteredCars = filteredCars.filter((car) =>
//         car.modelName.toLowerCase().includes(searchTypedCar.toLowerCase())
//       );
//     }

//     setCarList(filteredCars);
//   };

//   return (
//     <div className="base-page">
//       <div className="search-box">
//         <input
//           type="text"
//           placeholder="Search by Model Name"
//           value={searchTypedCar}
//           onChange={(e) => setSearchTypedCar(e.target.value)}
//         />
//       </div>

//       <div className="category-drop-down">
//         <label htmlFor="category">Choose a Category:</label>
//         <select
//           id="category"
//           value={selectedCategory}
//           onChange={(e) => setSelectedCategory(e.target.value)}
//         >
//           <option value="All">All</option>
//           {categoryList.map((obj) => (
//             <option key={obj.categoryId} value={obj.carType}>
//               {obj.carType}
//             </option>
//           ))}
//         </select>
//       </div>

//       <div className="grid">
//         {carList.length > 0 ? (
//           carList.map((obj) => <Tile key={obj.modelId} props={obj} />)
//         ) : (
//           <p>No cars found.</p>
//         )}
//       </div>
//     </div>
//   );
// }

// export default UserHome;

// function UserHome() {

//      const navigate = useNavigate();
//      console.log("RoleExtract:",roleExtract())
//      if(!authorize(roleExtract()))
//      {
//       console.log('From UserHome')
//       navigate('/unAuthorized');
//      }
    

//     const[carList,setCarList]=useState([]);
//     const[mainCarList,setMainCarList]=useState([]);
//     const[categoryList,setCategoryList]=useState([]);
//     const[searchTypedCar,setSearchTypedCar]=useState('');
//     const [debouncedSearchTerm, setDebouncedSearchTerm] = useState(searchTerm);
//     const [results, setResults] = useState([]);
//     useEffect(() => {
//       // Set up debounce
//       const timeoutId = setTimeout(() => {
//         setDebouncedSearchTerm(searchTypedCar);
//       }, 500); // debounce time (500ms)
  
//       // Clean up timeout on each input change
//       return () => clearTimeout(timeoutId);
//     }, [searchTypedCar]);

//     useEffect(() => {
//       // Filter results when debounced search term changes
//       if (debouncedSearchTerm) {
//         const filteredResults = mainCarList.filter((word) =>
//           word.toLowerCase().includes(debouncedSearchTerm.toLowerCase())
//         );
//         // setResults(filteredResults);
//         setCarList(filteredResults);
//       } else {
//         setResults([]);
//       }
//     }, [debouncedSearchTerm]);

//     const categoryListFetcher = async ()=>
//       {
//       try {
//       const api="/categories";
//       const resp=await axios.get(api);
//       const json=resp.data;
//       setCategoryList(json);
  
//       } catch (error) {
//         console.log(error)
//       }
//       }
    
//     const carListFetcher = async ()=>
//     {
//     try {
//     const api="/cars";
//     const resp=await axios.get(api);
//     const json=resp.data;
//     setCarList(json);
//     setMainCarList(json);

//     } catch (error) {
//       console.log(error)
//     }
//     }

//   useEffect(()=>{
//     carListFetcher();
//     categoryListFetcher();
//   },[])

//   return (
//     <div className='base-page'>
//       <div className="search-box">
//       <input type="text" placeholder='Search' value={searchTypedCar} onChange={(e)=>setSearchTypedCar(e.target.value)}/>
//       {/* <ul>
//         {results.map((result, index) => (
//           <li key={index}>{result}</li>
//         ))}
//       </ul> */}
//       </div>
//     <div className="category-drop-down">
//     <label for="car">Choose a Category:</label>
//         <select id="car" name="car">
//             <option selected>All</option>
//           {categoryList.map((obj)=>{
//             <option key={obj.categoryId} value={obj.carType}></option>
//           })}
//           </select>
//     </div>
//     <div className="grid">
//       {carList.map((obj)=>(
//         <Tile props={obj} key={obj.modelId}/>
//       ))}
//     </div>
//     </div>
//   )
// }

// export default UserHome